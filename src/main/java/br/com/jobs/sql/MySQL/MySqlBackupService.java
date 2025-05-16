package br.com.jobs.sql.MySQL;
import br.com.jobs.Jobs;
import br.com.jobs.sql.DatabaseBackupService;
import br.com.jobs.sql.DatabaseYML;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static br.com.jobs.utils.TextUtils.*;
import static br.com.jobs.utils.messages.MessagesHandle.*;

public class MySqlBackupService extends MySQLConnection implements DatabaseBackupService {
    private final Jobs plugin;
    private final DatabaseYML databaseYML;
    String url = "jdbc:mysql://" + host + ":" + port + "/" + data;
    public MySqlBackupService(Jobs plugin) {
        this.plugin = plugin;
        this.databaseYML = Jobs.getSqlConnectionYML();
    }
    @Override
    public File createBackup(File backupDir, String filename) {
        if (!backupDir.exists() && !backupDir.mkdirs()) {
            warnLoggers(Msg_BackupFile_error + backupDir.getAbsolutePath());
            return null;
        }
        if (filename == null || filename.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
            filename = "mysql_backup_" + dateFormat.format(new Date());
        }
        File backupFile = new File(backupDir, filename + ".sql");

        
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             FileWriter writer = new FileWriter(backupFile);
             PrintWriter printWriter = new PrintWriter(writer)) {

            printWriter.println("-- Backup created in " + new Date());
            printWriter.println("-- Database: " + data);
            printWriter.println();
            printWriter.println("SET FOREIGN_KEY_CHECKS = 0;");
            printWriter.println();

            List<String> tables = new ArrayList<>();
            try (ResultSet rs = connection.getMetaData().getTables(data, null, "%", new String[]{"TABLE"})) {
                while (rs.next()) {
                    tables.add(rs.getString("TABLE_NAME"));
                }
            }

            for (String table : tables) {
                exportTable(connection, printWriter, data, table);
                printWriter.println();
            }
            
            printWriter.println("SET FOREIGN_KEY_CHECKS = 1;");
            printWriter.flush();
            
            infoLoggers(String.format(Backup_Success + backupFile.getAbsolutePath(), "MySQL"));
            return backupFile;
            
        } catch (SQLException | IOException e) {
            warnLoggers(String.format(Backup_Error, "MySQL"));
            return null;
        }
    }
    private void exportTable(Connection connection, PrintWriter writer, String database, String table) throws SQLException {
        writer.println("-- Table structure `" + table + "`");
        writer.println("DROP TABLE IF EXISTS `" + table + "`;");

        String createTableSql = "";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SHOW CREATE TABLE `" + table + "`")) {
            if (rs.next()) {
                createTableSql = rs.getString(2);
            }
        }
        writer.println(createTableSql + ";");
        writer.println();

        writer.println("-- Table data`" + table + "`");
        
        // Criar um Statement scrollable para poder usar beforeFirst()
        try (Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery("SELECT * FROM `" + table + "`")) {
            
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            boolean hasData = rs.next();
            if (hasData) {
                rs.beforeFirst();

                while (rs.next()) {
                    StringBuilder insertBuilder = new StringBuilder("INSERT INTO `");
                    insertBuilder.append(table).append("` VALUES (");

                    for (int i = 1; i <= columnCount; i++) {
                        Object value = rs.getObject(i);
                        if (value == null) {
                            insertBuilder.append("NULL");
                        } else if (value instanceof String || value instanceof Date || value instanceof Timestamp) {
                            insertBuilder.append("'").append(escapeSQL(value.toString())).append("'");
                        } else {
                            insertBuilder.append(value);
                        }
                        if (i < columnCount) {
                            insertBuilder.append(", ");
                        }
                    }
                    insertBuilder.append(");");
                    writer.println(insertBuilder.toString());
                }
            }
        }
    }
    private String escapeSQL(String input) {
        return input.replace("\\", "\\\\")
                   .replace("'", "\\'")
                   .replace("\r", "\\r")
                   .replace("\n", "\\n");
    }
    @Override
    public boolean restoreFromBackup(File backupFile) {
        if (!backupFile.exists() || !backupFile.isFile()) {
            warnLoggers(Msg_Restore_NoFindFile + backupFile.getAbsolutePath());
            return false;
        }
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             BufferedReader reader = new BufferedReader(new FileReader(backupFile))) {
            try (Statement st = connection.createStatement()) {
                st.execute("SET FOREIGN_KEY_CHECKS = 0");
            }
            StringBuilder sqlCommand = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("--") || line.trim().isEmpty()) {
                    continue;
                }
                sqlCommand.append(line);

                if (line.endsWith(";")) {
                    try (Statement st = connection.createStatement()) {
                        st.execute(sqlCommand.toString());
                    } catch (SQLException e) {
                        warnLoggers(Restore_SQL_Error + sqlCommand.toString());
                    }
                    
                    sqlCommand.setLength(0);
                }
            }
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            }
            infoLoggers(String.format(Backup_Success + backupFile.getAbsolutePath(), "MySQL"));
            return true;
        } catch (SQLException | IOException e) {
            warnLoggers( e.getMessage());
            return false;
        }
    }
    @Override
    public File[] listBackups(File backupDir) {
        if (!backupDir.exists() || !backupDir.isDirectory()) {
            return new File[0];
        }
        return backupDir.listFiles((dir, name) -> name.endsWith(".sql"));
    }
}
