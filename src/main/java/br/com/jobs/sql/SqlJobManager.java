package br.com.jobs.sql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import static br.com.jobs.utils.TextUtils.warnLoggers;

public class SqlJobManager {

    private final Connection connection;

    public SqlJobManager(Connection connection) {
        this.connection = connection;
    }

    public void setPlayerProfession(UUID uuid, String name, String profession) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO jobs (uuid, name, profession) VALUES (?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE name = VALUES(name), profession = VALUES(profession)"
            );
            ps.setString(1, uuid.toString());
            ps.setString(2, name);
            ps.setString(3, profession);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasPlayer(UUID uuid) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT 1 FROM jobs WHERE uuid = ?");
            ps.setString(1, uuid.toString());
            return ps.executeQuery().next();
        } catch (SQLException e) {
            warnLoggers(e.toString());
            return false;
        }
    }

    public boolean hasProfission(String profession) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT 1 FROM jobs WHERE uuid = ? AND profession IS NOT NULL");
            ps.setString(1, profession);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public String getPlayerProfession(UUID playerUUID) {
        String query = "SELECT profession FROM jobs WHERE uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, playerUUID.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("profession");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}