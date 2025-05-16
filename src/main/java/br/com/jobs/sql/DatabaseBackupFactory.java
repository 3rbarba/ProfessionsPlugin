package br.com.jobs.sql;
import br.com.jobs.Jobs;
import br.com.jobs.sql.MySQL.MySqlBackupService;
import br.com.jobs.sql.SQLite.SQLiteBackupService;
import br.com.jobs.sql.SQLite.SQLiteManager;

public class DatabaseBackupFactory {

    public static DatabaseBackupService createBackupService(Jobs plugin, String databaseType, SQLiteManager sqLiteManager) {
        if (databaseType == null || databaseType.isEmpty()) {
            databaseType = Jobs.getSqlConnectionYML().getConfig().getString("Database.Type", "SQLite");
        }
        
        switch (databaseType.toUpperCase()) {
            case "MYSQL":
                return new MySqlBackupService(plugin);
            case "SQLITE":
                if (sqLiteManager == null) {
                    throw new IllegalArgumentException("Error");
                }
                return new SQLiteBackupService(plugin, sqLiteManager);
            default:
                return null;
        }
    }
}
