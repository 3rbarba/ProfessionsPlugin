package br.com.jobs.sql;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import static br.com.jobs.sql.SqlJobManager.cf;
import static br.com.jobs.utils.TextUtils.*;
import static br.com.jobs.utils.messages.MessagesHandle.*;

public class SqlCreateDatabase {
    private final Connection connection;
    private final String database;
    private final String table;

    public SqlCreateDatabase(Connection connection) {
        this.database = cf != null ? cf.getString("DATABASE", "professions") : "professions";
        this.table = cf != null ? cf.getString("TABLE", "jobs") : "jobs";
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
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                "uuid VARCHAR(36) PRIMARY KEY," +
                "name VARCHAR(16) NOT NULL," +
                "profession VARCHAR(32) NOT NULL," +
                "working VARCHAR(5) NOT NULL DEFAULT false" +
                ")", table);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            sendConsoleMessage(String.format(Msg_tableDB_sucess, table));
        } catch (SQLException e) {
            warnLoggers(String.format(Msg_tableDB_error + e.getMessage(), table));
        }
    }

    private boolean isDefaultDatabase() {
        return database != null && !database.isEmpty();
    }

}
