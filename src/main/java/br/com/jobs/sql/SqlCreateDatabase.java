package br.com.jobs.sql;
import org.bukkit.configuration.ConfigurationSection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import static br.com.jobs.Jobs.getSqlConnectionYML;
import static br.com.jobs.utils.TextUtils.*;
import static br.com.jobs.utils.messages.MessagesHandle.*;

public class SqlCreateDatabase {
    ConfigurationSection cf = getSqlConnectionYML().getConfig().getConfigurationSection("MySql");
    private final Connection connection;
    private final String database;

    public SqlCreateDatabase(Connection connection) {
        this.database = cf != null ? cf.getString("DATABASE", "jobs") : "jobs";
        this.connection = connection;
    }

    public void createDatabase() {
        String sql = "CREATE DATABASE IF NOT EXISTS " + database;

        try (Statement statement = connection.createStatement()) {
            if (isDefaultDatabase()) {
                statement.executeUpdate(sql);
                sendConsoleMessage(String.format(Msg_db_sucess, database));
            }
        } catch (SQLException e) {
            warnLoggers(String.format(Msg_db_error, database) + e.getMessage());
        }
    }

    public void createJobsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS jobs (" +
                "uuid VARCHAR(36) PRIMARY KEY," +
                "name VARCHAR(16) NOT NULL," +
                "profession VARCHAR(32) NOT NULL" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            sendConsoleMessage(Msg_tableDB_sucess);
        } catch (SQLException e) {
            warnLoggers(Msg_tableDB_error + e.getMessage());
        }
    }

    private boolean isDefaultDatabase() {
        return database != null && !database.isEmpty();
    }

}
