package br.com.jobs.sql.MySQL;

import br.com.jobs.sql.JobsDataRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import static br.com.jobs.sql.MySQL.MySQLConnection.cf;
import static br.com.jobs.utils.TextUtils.warnLoggers;

public class MySqlJobsDataRepository implements JobsDataRepository {

    private final String tableName = cf.getString("TABLE", "jobs");
    private final Connection connection;

    public MySqlJobsDataRepository(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void setPlayerProfession(UUID uuid, String name, String profession) {
        try {
            String query = String.format("INSERT INTO %s (uuid, name, profession) VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE name = VALUES(name), profession = VALUES(profession)", tableName);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, uuid.toString());
            ps.setString(2, name);
            ps.setString(3, profession);
            ps.executeUpdate();
        } catch (SQLException e) {
            warnLoggers(e.getMessage());
        }
    }
    @Override
    public void setPlayerWorking(UUID uuid, String working) {
        try {
            String query = String.format("UPDATE %s SET working = ? WHERE uuid = ?", tableName);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, working);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            warnLoggers(e.getMessage());
        }
    }
    @Override
    public boolean hasPlayer(UUID uuid) {
        try {
            String query = String.format("SELECT 1 FROM %s WHERE uuid = ?", tableName);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, uuid.toString());
            return ps.executeQuery().next();
        } catch (SQLException e) {
            warnLoggers(e.getMessage());
            return false;
        }
    }
    @Override
    public boolean hasProfession(String profession) {
        try {
            String query = String.format("SELECT 1 FROM %s WHERE profession = ?", tableName);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, profession);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            warnLoggers(e.getMessage());
            return false;
        }
    }
    @Override
    public String hasWorking(UUID playerUUID) {
        try {
            String query = String.format("SELECT working FROM %s WHERE uuid = ?", tableName);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, playerUUID.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("working");
            }
        } catch (SQLException e) {
            warnLoggers(e.getMessage());
        }
        return "false";
    }
    @Override
    public String getPlayerProfession(UUID playerUUID) {
        try {
            String query = String.format("SELECT profession FROM %s WHERE uuid = ?", tableName);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, playerUUID.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("profession");
            }
        } catch (SQLException e) {
            warnLoggers(e.getMessage());
        }
        return null;
    }
}