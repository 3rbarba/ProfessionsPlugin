package br.com.jobs.Sql;

import br.com.jobs.Jobs;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static br.com.jobs.Jobs.getMessageyml;
import static br.com.jobs.Jobs.getSqlConnectionYML;
import static br.com.jobs.utils.TextUtils.color;

public class SqlConnection {

    private static final ConfigurationSection cfMessage = getMessageyml().getConfig().getConfigurationSection("Messages");
    private static final String Msg_NoFoundDB_file = color(cfMessage.getString("Msg_NoFoundDB_file"));
    private static final String Msg_connectionDB_sucess = color(cfMessage.getString("Msg_connectionDB_sucess"));
    private static final String Msg_connectionDB_failed = color(cfMessage.getString("Msg_connectionDB_failed"));
    private static final String Msg_connectionDB_finished = color(cfMessage.getString("Msg_connectionDB_finished"));
    private static final String Msg_connectionDB_FinishedError = color(cfMessage.getString("Msg_connectionDB_finishedError"));
    private static final String Msg_IncorretFieldsDB = color(cfMessage.getString("Msg_IncorretFieldsDB"));

    private Connection connection;

    public void connect() {
        ConfigurationSection cf = getSqlConnectionYML().getConfig().getConfigurationSection("MySql");
        if (cf == null) {
            sendConsoleMessage(Msg_NoFoundDB_file);
            return;
        }

        String host = cf.getString("HOST");
        String port = cf.getString("PORT");
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
            } catch (NullPointerException e) {
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
