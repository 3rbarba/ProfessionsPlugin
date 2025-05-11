package br.com.jobs.utils.Papi;

import br.com.jobs.Jobs;
import br.com.jobs.sql.SqlJobManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static br.com.jobs.utils.messages.MessagesHandle.*;


public class SomeExpansion extends PlaceholderExpansion {

    private SqlJobManager sqlJobManager;

    public SomeExpansion(SqlJobManager sqlJobManager) {
        this.sqlJobManager = sqlJobManager;
    }

    @Override
    @NotNull
    public String getAuthor() {
        return "ERICK_BARB";
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "professions";
    }

    @Override
    @NotNull
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (params.startsWith("hasProfission_player_")) {
            String playerName = params.replace("hasProfission_player_", "");
            OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(playerName);
            boolean hasProfession = sqlJobManager.hasPlayer(targetPlayer.getUniqueId());
            return hasProfession ? hasPermission_true : hasPermission_false;

        } else if (params.startsWith("jobName_player_")) {
            String playerName = params.replace("jobName_player_", "");
            OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(playerName);

            String jobName = sqlJobManager.getPlayerProfession(targetPlayer.getUniqueId());

            if (jobName == null) {
                return "";
            }
            String displayName = Jobs.getGuiConfigYML().getConfig().getString("gui.items." + jobName + ".display_name", "Profissão desconhecida");

            return displayName.toUpperCase();
        } else if (params.startsWith("hasWorking_player_")) {
            String playerName = params.replace("hasWorking_player_", "");
            OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(playerName);

            String hasWorking = sqlJobManager.hasWoking(targetPlayer.getUniqueId());

            if (hasWorking == null || hasWorking.isEmpty() || hasWorking.equalsIgnoreCase("false")) {
                return hasWorking_false;
            } else {
                return hasWorking_true;
            }

        }
        return "";
    }
}