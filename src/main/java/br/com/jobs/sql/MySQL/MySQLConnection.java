package br.com.jobs.sql.MySQL;
import org.bukkit.configuration.ConfigurationSection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static br.com.jobs.Jobs.*;
import static br.com.jobs.utils.TextUtils.*;
import static br.com.jobs.utils.messages.MessagesHandle.*;

public class MySQLConnection {
    private Connection connection;
    public static ConfigurationSection cf = getSqlConnectionYML().getConfig().getConfigurationSection("MySql");

    String data = cf.getString("DATABASE", "jobs");
    String host = cf.getString("HOST");
    String port = cf.getString("PORT");
    String user = cf.getString("USER");
    String pass = cf.getString("PASSWORD");

    public void connect() {



        if (cf == null) {
            sendConsoleMessage(Msg_NoFoundDB_file);
            return;
        }


        if (host == null || user == null || pass == null || port == null) {
            warnLoggers(Msg_IncorretFieldsDB);
            return;
        }

        try {
            String Url = "jdbc:mysql://" + host + ":" + port;

            try (Connection tempConnection = DriverManager.getConnection(Url, user, pass)) {
                MySqlCreateDatabase creator = new MySqlCreateDatabase(tempConnection);
                creator.createDatabase();
            }

            connection = DriverManager.getConnection
                    (Url + "/" + data + "?autoReconnect=true&useSSL=false", user, pass);

            MySqlCreateDatabase tableCreator = new MySqlCreateDatabase(connection);
            tableCreator.createJobsTable();

            sendConsoleMessage(Msg_connectionDB_success);

        } catch (SQLException e) {
            warnLoggers(Msg_connectionDB_failed + e.getMessage());
            infoLoggers("mysql://" + host + ":" + port + "/" + data);
            infoLoggers("user: " + user + ", password: " + pass);
        }
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                infoLoggers(Msg_connectionDB_finished);
            }
        } catch (SQLException e) {
            warnLoggers(Msg_connectionDB_FinishedError + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnectionValid() {
        try {
            if (connection == null || connection.isClosed()) {
                return false;
            }
            return connection.isValid(10);
        } catch (SQLException e) {
            return false;
        }
    }

    public void attemptReconnect() {
        if (!isConnectionValid()) {
            sendConsoleMessage(Msg_ReconnectingDB);
            connect();
        }
    }
}
