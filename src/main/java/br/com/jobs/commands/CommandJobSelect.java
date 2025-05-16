package br.com.jobs.commands;

import br.com.jobs.Jobs;
import br.com.jobs.profissions.JobSelectGuiListener;
import br.com.jobs.sql.SqlJobManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static br.com.jobs.utils.messages.MessagesHandle.*;
import static br.com.jobs.utils.TextUtils.*;

public class CommandJobSelect implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sendPlayerMessage(sender, Msg_NoPermission);
            return true;
        }
        Player player = (Player) sender;

        try {
            SqlJobManager jobManager = new SqlJobManager(Jobs.getInstance().getConnection());
            UUID executorUUID = player.getUniqueId();

            if (jobManager.hasProfession(executorUUID.toString()) && !player.hasPermission("jobs.jobselect.others")) {
                sendPlayerMessage(player, Msg_command_Error);
                return true;
            }
            if (args.length == 1) {
                if (!player.hasPermission("jobs.jobselect.others")) {
                    sendPlayerMessage(player, Msg_NoPermission);
                    return true;
                }
                player = Bukkit.getPlayerExact(args[0]);

                if (player == null) {
                    sendPlayerMessage(player, Msg_Target_Error);
                    return true;
                }
            }

            JobSelectGuiListener.openGUI(player);
            return true;

        } catch (Exception e) {
            warnLoggers(Msg_command_Error);
            sendPlayerMessage(sender, Msg_command_Error);
            return true;
        }
    }
}
