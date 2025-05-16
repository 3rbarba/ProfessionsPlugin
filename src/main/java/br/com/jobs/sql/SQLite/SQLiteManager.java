package br.com.jobs.sql.SQLite;
import br.com.jobs.Jobs;
import br.com.jobs.sql.DatabaseManager;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import static br.com.jobs.utils.TextUtils.*;
import static br.com.jobs.utils.messages.MessagesHandle.*;

public class SQLiteManager implements DatabaseManager {
    private Connection connection;
    private final Jobs plugin;

    public SQLiteManager(Jobs plugin) {
        this.plugin = plugin;
    }

    @Override
    public void connect() {
        File dataFolder = new File(plugin.getDataFolder(), "Database");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File dbFile = new File(dataFolder, "jobs.db");

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
            createTables();
            sendConsoleMessage(Msg_db_success);
        } catch (ClassNotFoundException | SQLException e) {
            warnLoggers(Msg_db_error + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createTables() {
        try (Statement st = connection.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS jobs (" +
                    "uuid TEXT PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "profession TEXT, " +
                    "working TEXT DEFAULT 'false'" +
                    ")");
            sendConsoleMessage(String.format(Msg_tableDB_success, "jobs"));
        } catch (SQLException e) {
            warnLoggers(Msg_tableDB_error);
        }
    }

    @Override
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                infoLoggers(Msg_connectionDB_finished);
            }
        } catch (SQLException e) {
            warnLoggers(Msg_connectionDB_FinishedError);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public boolean isConnectionValid() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}