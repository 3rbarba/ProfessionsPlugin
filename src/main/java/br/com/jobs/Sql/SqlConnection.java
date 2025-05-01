package br.com.jobs.Sql;
import br.com.jobs.Jobs;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import static br.com.jobs.Jobs.getMessageyml;
import static br.com.jobs.Jobs.getSqlConnectionYML;

public class SqlConnection {

    private static final ConfigurationSection cfMessage = getMessageyml().getConfig().getConfigurationSection("Messages");
    private static final String Msg_NoFoundDB_file = cfMessage.getString("Msg_NoFoundDB_file").replace("&", "§");
    private static final String Msg_connectionDB_sucess = cfMessage.getString("Msg_connectionDB_sucess").replace("&", "§");
    private static final String Msg_connectionDB_failed = cfMessage.getString("Msg_connectionDB_failed").replace("&", "§");
    private static final String Msg_connectionDB_finished = cfMessage.getString("Msg_connectionDB_finished").replace("&", "§");
    private static final String Msg_connectionDB_FinishedError = cfMessage.getString("Msg_connectionDB_finishedError").replace("&", "§");
    private static final String Msg_IncorretFieldsDB = cfMessage.getString("Msg_IncorretFieldsDB").replace("&", "§");
    private Connection connection;

    public void connect() {
        ConfigurationSection cf = getSqlConnectionYML().getConfig().getConfigurationSection("MySql");
        if (cf == null) {
            sendConsoleMessage(Msg_NoFoundDB_file);
            return;
        }

        String host = cf.getString("HOST");
        String port = cf.getString("PORT");
//        String database = cf.getString("DATABASE");
        String user = cf.getString("USER");
        String pass = cf.getString("PASSWORD");

        if (host == null || user == null || pass == null || port == null) {
            sendConsoleMessage(Msg_IncorretFieldsDB);
            return;
        }

        try {
            String baseUrl = "jdbc:mysql://" + host + ":" + port;

            try (Connection tempConnection = DriverManager.getConnection(baseUrl, user, pass)) {
                SqlCreateDatabase creator = new SqlCreateDatabase(tempConnection);
                creator.createDatabase();
            }catch (NullPointerException e){
                sendConsoleMessage(String.valueOf(e));
                return;
            }

            connection = DriverManager.getConnection(baseUrl + "/professions?autoReconnect=true&useSSL=false", user, pass);

            SqlCreateDatabase tableCreator = new SqlCreateDatabase(connection);
            tableCreator.createJobsTable();

            sendConsoleMessage(Msg_connectionDB_sucess);
        } catch (SQLException e) {
            sendConsoleMessage(Msg_connectionDB_failed + e.getMessage());
            e.printStackTrace();
        }
    }
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                sendConsoleMessage(Msg_connectionDB_finished);
            }
        } catch (SQLException e) {
            sendConsoleMessage(Msg_connectionDB_FinishedError + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
    private void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(Jobs.prefix + message);
    }
}