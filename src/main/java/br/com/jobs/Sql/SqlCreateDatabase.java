package br.com.jobs.Sql;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.Bukkit;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static br.com.jobs.Jobs.*;

public class SqlCreateDatabase {

    private static final ConfigurationSection cf = getSqlConnectionYML().getConfig().getConfigurationSection("MySql");
    private static final ConfigurationSection cfMessage = getMessageyml().getConfig().getConfigurationSection("Messages");
    private static final String default_db = "professions";

    private static final String Msg_db_sucess = cfMessage.getString("Msg_db_sucess");

    private static final String Msg_db_error = cfMessage.getString("Msg_db_error").replace("&", "§");
    private static final String Msg_tableDB_sucess = cfMessage.getString("Msg_table_sucess").replace("&", "§");
    private static final String Msg_tableDB_error = cfMessage.getString("Msg_table_error").replace("&", "§");
    private final Connection connection;

    private final String database;
    public SqlCreateDatabase(Connection connection) {
        this.connection = connection;
        this.database = cf.getString( "DATABASE", default_db);
    }


    public void createDatabase() {
        String sql = "CREATE DATABASE IF NOT EXISTS " + default_db;

        try (Statement statement = connection.createStatement()) {
            if (isDefaultDatabase()) {
                statement.executeUpdate(sql);
                sendConsoleMessage(Msg_db_sucess);
            }
        } catch (SQLException e) {
            sendConsoleMessage(String.format(Msg_db_error) + e.getMessage());
            e.printStackTrace();
        }
    }


    private boolean isDefaultDatabase() {
        return database == null || database.equalsIgnoreCase(default_db);
    }


    public void createJobsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS jobs_data (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "player_uuid VARCHAR(36) NOT NULL, " +
                "job_name VARCHAR(50) NOT NULL, " +
                "level INT DEFAULT 1" +
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