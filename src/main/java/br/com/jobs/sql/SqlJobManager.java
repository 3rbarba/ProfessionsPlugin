package br.com.jobs.sql;

import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static br.com.jobs.Jobs.getSqlConnectionYML;
import static br.com.jobs.utils.TextUtils.warnLoggers;

public class SqlJobManager {
    public static ConfigurationSection cf = getSqlConnectionYML().getConfig().getConfigurationSection("MySql");
    String default_db = cf.getString("TABLE", "jobs");
    private final Connection connection;
    private static SqlJobManager instance;

    public static void init(Connection connection) {
        if (instance == null) {
            instance = new SqlJobManager(connection);
        }
    }
    public static SqlJobManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Null SQLManager instance");
        }
        return instance;
    }
    public SqlJobManager(Connection connection) {
        this.connection = connection;
    }

    public void setPlayerProfession(UUID uuid, String name, String profession) {
        try {
            String query = String.format("INSERT INTO %s (uuid, name, profession) VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE name = VALUES(name), profession = VALUES(profession)", default_db);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, uuid.toString());
            ps.setString(2, name);
            ps.setString(3, profession);
            ps.executeUpdate();
        } catch (SQLException e) {
            warnLoggers("Error in table: " + default_db);
        }
    }
    public void setPlayerWorking(UUID uuid, String working) {
        try {
            String query = String.format("UPDATE %s SET working = ? WHERE uuid = ?", default_db);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, working.toString());
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            warnLoggers("Error in table: setWorking" + default_db + ": " + e.getMessage());
        }
    }
    public boolean hasPlayer(UUID uuid) {
        try {
            String query = String.format("SELECT 1 FROM %s WHERE uuid = ?", default_db);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, uuid.toString());
            return ps.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean hasProfission(String profession) {
        try {
            String query = String.format("SELECT 1 FROM %s WHERE uuid = ? AND profession IS NOT NULL", default_db);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, profession);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }
    public String hasWoking(UUID playerUUID) {
        String query = String.format("SELECT working FROM %s WHERE uuid = ?", default_db);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, playerUUID.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("working");
            }
        } catch (Exception e) {
            warnLoggers("Error in table: hasWorking in " + default_db);
        }
        return null;
    }
    public String getPlayerProfession(UUID playerUUID) {
        String query = String.format("SELECT profession FROM %s WHERE uuid = ?", default_db);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, playerUUID.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("profession");
            }
        } catch (Exception e) {
            warnLoggers("Error in table: " + default_db);
        }
        return null;
    }

}