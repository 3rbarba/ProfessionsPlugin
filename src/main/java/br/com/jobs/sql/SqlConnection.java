package br.com.jobs.sql;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static br.com.jobs.sql.SqlJobManager.cf;
import static br.com.jobs.utils.TextUtils.*;
import static br.com.jobs.utils.messages.MessagesHandle.*;

public class SqlConnection {
    private Connection connection;

    public void connect() {


        if (cf == null) {
            sendConsoleMessage(Msg_NoFoundDB_file);
            return;
        }

        String default_db = cf.getString("DATABASE", "jobs");
        String host = cf.getString("HOST");
        String port = cf.getString("PORT");
        String user = cf.getString("USER");
        String pass = cf.getString("PASSWORD");
        if (host == null || user == null || pass == null || port == null) {
            warnLoggers(Msg_IncorretFieldsDB);
            return;
        }

        try {
            String baseUrl = "jdbc:mysql://" + host + ":" + port;

            try (Connection tempConnection = DriverManager.getConnection(baseUrl, user, pass)) {
                SqlCreateDatabase creator = new SqlCreateDatabase(tempConnection);
                creator.createDatabase();
            }

            connection = DriverManager.getConnection
                    (baseUrl + "/" + default_db + "?autoReconnect=true&useSSL=false", user, pass);

            SqlCreateDatabase tableCreator = new SqlCreateDatabase(connection);
            tableCreator.createJobsTable();

            sendConsoleMessage(Msg_connectionDB_sucess);

        } catch (SQLException e) {
            warnLoggers(Msg_connectionDB_failed);
            infoLoggers("mysql://" + host + ":" + port + "/" + default_db);
            infoLoggers("user: " + user + ", password: " + pass);
            e.printStackTrace();
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
            sendConsoleMessage("Tentando reconectar ao banco de dados...");
            connect();
        }
    }
}
