package br.com.jobs.sql.MySQL;

import br.com.jobs.sql.DatabaseManager;

import java.sql.Connection;

public class MySQLManager implements DatabaseManager {

    private final MySQLConnection connection;

    public MySQLManager() {
        this.connection = new MySQLConnection();
    }
    @Override
    public void connect() {
        connection.connect();
    }
    @Override
    public void disconnect() {
        connection.disconnect();
    }
    @Override
    public Connection getConnection() {
        return connection.getConnection();
    }
    @Override
    public boolean isConnectionValid() {
        return connection.isConnectionValid();
    }
    public void reconnect() {
        connection.attemptReconnect();
    }
}