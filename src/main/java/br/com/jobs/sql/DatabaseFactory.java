package br.com.jobs.sql;

import br.com.jobs.Jobs;
import br.com.jobs.sql.MySQL.MySQLManager;
import br.com.jobs.sql.MySQL.MySqlJobsDataRepository;
import br.com.jobs.sql.SQLite.SQLiteJobDataRepository;
import br.com.jobs.sql.SQLite.SQLiteManager;
import java.util.UUID;
import static br.com.jobs.Jobs.getSqlConnectionYML;
import static br.com.jobs.utils.TextUtils.sendConsoleMessage;
import static br.com.jobs.utils.messages.MessagesHandle.*;

public class DatabaseFactory {

    public static DatabaseManager createDatabase() {
        String databaseType = getSqlConnectionYML().getConfig().getString("Database.Type", "SQLite");

        switch (databaseType.toUpperCase()) {
            case "MYSQL":
                sendConsoleMessage(UsingMySQL);
                return new MySQLManager();
            case "SQLITE":
                sendConsoleMessage(UsingSQLite);
                return new SQLiteManager(Jobs.getInstance());
            default:
                sendConsoleMessage(Unknown_Database);
                return new SQLiteManager(Jobs.getInstance());
        }
    }

    public static JobsDataRepository createJobRepository(DatabaseManager dbManager) {
        String databaseType = getSqlConnectionYML().getConfig().getString("Database.Type", "SQLite");

        switch (databaseType.toUpperCase()) {
            case "MYSQL":
                return new MySqlJobsDataRepository(dbManager.getConnection());
            case "SQLITE":
                return new SQLiteJobDataRepository(dbManager.getConnection()) {

                };
            default:
                return new SQLiteJobDataRepository(dbManager.getConnection()) {
                };
        }
    }
}