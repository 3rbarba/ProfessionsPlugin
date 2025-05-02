package br.com.jobs.sql;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.Bukkit;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import static br.com.jobs.Jobs.*;
import static br.com.jobs.utils.TextUtils.color;

public class SqlCreateDatabase {
    public static final String default_db = "professions";

    private static final ConfigurationSection cf = getSqlConnectionYML().getConfig().getConfigurationSection("MySql");
    private static final ConfigurationSection cfMessage = getMessageyml().getConfig().getConfigurationSection("Messages");

    private static final String Msg_db_sucess = color(cfMessage.getString("Msg_db_sucess"));
    private static final String Msg_db_error = color(cfMessage.getString("Msg_db_error"));
    private static final String Msg_tableDB_sucess = color(cfMessage.getString("Msg_tableDB_sucess"));
    private static final String Msg_tableDB_error = color(cfMessage.getString("Msg_tableDB_error"));

    private final Connection connection;
    private final String database;

    public SqlCreateDatabase(Connection connection) {
        this.connection = connection;
        this.database = cf.getString("DATABASE", default_db);
    }

    public void createDatabase() {
        String sql = "CREATE DATABASE IF NOT EXISTS " + default_db;

        try (Statement statement = connection.createStatement()) {
            if (isDefaultDatabase()) {
                statement.executeUpdate(sql);
                sendConsoleMessage(String.format(Msg_db_sucess, default_db));
            }
        } catch (SQLException e) {
            sendConsoleMessage(Msg_db_error + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isDefaultDatabase() {
        return database == null || database.equalsIgnoreCase(default_db);
    }

    public void createJobsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS jobs (" +
                     "uuid VARCHAR(36) PRIMARY KEY," +
                     "name VARCHAR(16) NOT NULL," +
                     "profession VARCHAR(32) NOT NULL"+
                     ");";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            sendConsoleMessage(Msg_tableDB_sucess);
        } catch (SQLException e) {
            sendConsoleMessage(Msg_tableDB_error + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(prefix + message);
    }
}
