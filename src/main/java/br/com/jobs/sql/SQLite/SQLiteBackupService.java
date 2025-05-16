package br.com.jobs.sql.SQLite;
import br.com.jobs.Jobs;
import br.com.jobs.sql.DatabaseBackupService;
import java.io.*;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static br.com.jobs.utils.TextUtils.*;
import static br.com.jobs.utils.messages.MessagesHandle.*;


public class SQLiteBackupService implements DatabaseBackupService {
    
    private final Jobs plugin;
    private final SQLiteManager sqLiteManager;
    
    public SQLiteBackupService(Jobs plugin, SQLiteManager sqLiteManager) {
        this.plugin = plugin;
        this.sqLiteManager = sqLiteManager;
    }
    
    @Override
    public File createBackup(File backupDir, String filename) {
        if (!backupDir.exists() && !backupDir.mkdirs()) {
            warnLoggers(Msg_BackupFile_error + backupDir.getAbsolutePath());
            return null;
        }

        if (filename == null || filename.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
            filename = "sqlite_backup_" + dateFormat.format(new Date());
        }
        
        File backupFile = new File(backupDir, filename + ".db");

        File dataFolder = new File(plugin.getDataFolder(), "Database");
        File sourceFile = new File(dataFolder, "jobs.db");
        
        if (!sourceFile.exists()) {
            warnLoggers(Msg_RestoreFile_error + sourceFile.getAbsolutePath());
            return null;
        }

        try {
            Connection connection = sqLiteManager.getConnection();
            if (connection == null || connection.isClosed()) {
                warnLoggers(Msg_BackupConnection_error);
                return null;
            }

            try (PreparedStatement st = connection.prepareStatement("VACUUM")) {
                st.execute();
            }
        } catch (SQLException e) {
            warnLoggers(e.getMessage());
        }

        try (FileChannel source = new FileInputStream(sourceFile).getChannel();
             FileChannel destination = new FileOutputStream(backupFile).getChannel()) {
            
            destination.transferFrom(source, 0, source.size());
            infoLoggers(String.format(Backup_Success + backupFile.getAbsolutePath(), "SQLite"));
            return backupFile;
            
        } catch (IOException e) {
            warnLoggers(String.format(Backup_Error + e.getMessage(), "SQLite"));
            return null;
        }
    }
    
    @Override
    public boolean restoreFromBackup(File backupFile) {
        if (!backupFile.exists() || !backupFile.isFile()) {
            warnLoggers(Msg_Restore_error + backupFile.getAbsolutePath());
            return false;
        }

        sqLiteManager.disconnect();

        File dataFolder = new File(plugin.getDataFolder(), "database");
        File destinationFile = new File(dataFolder, "jobs.db");

        if (destinationFile.exists()) {
            File tempBackup = new File(dataFolder, "jobs_before_restore_" + 
                    new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date()) + ".db");
            try {
                copyFile(destinationFile, tempBackup);
            } catch (IOException e) {
                return false;
            }
        }

        try {
            copyFile(backupFile, destinationFile);
            infoLoggers(String.format(Msg_Restore_Success + backupFile.getAbsolutePath(), "SQLite"));

            sqLiteManager.connect();
            return true;
            
        } catch (IOException e) {
            warnLoggers(Msg_Restore_error + e.getMessage());

            sqLiteManager.connect();
            return false;
        }
    }
    
    @Override
    public File[] listBackups(File backupDir) {
        if (!backupDir.exists() || !backupDir.isDirectory()) {
            return new File[0];
        }
        
        return backupDir.listFiles((dir, name) -> name.endsWith(".db"));
    }
    
    private void copyFile(File source, File destination) throws IOException {
        try (FileChannel sourceChannel = new FileInputStream(source).getChannel();
             FileChannel destinationChannel = new FileOutputStream(destination).getChannel()) {
            
            destinationChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        }
    }
}
