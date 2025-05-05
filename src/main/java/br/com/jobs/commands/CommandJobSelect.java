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

        if (!(sender instanceof Player executor)) {
            sendPlayerMessage(sender, Msg_NoPermission);
            return true;
        }
        Player target = executor;

        try {
            SqlJobManager jobManager = new SqlJobManager(Jobs.getInstance().getConnection());
            UUID executorUUID = executor.getUniqueId();

            if (jobManager.hasProfission(executorUUID.toString()) && !executor.hasPermission("jobs.jobselect.others")) {
                sendPlayerMessage(executor, Msg_command_Error);
                return true;
            }
            if (args.length == 1) {
                if (!executor.hasPermission("jobs.jobselect.others")) {
                    sendPlayerMessage(executor, Msg_NoPermission);
                    return true;
                }
                target = Bukkit.getPlayerExact(args[0]);

                if (target == null) {
                    sendPlayerMessage(executor, Msg_Target_Error);
                    return true;
                }
            }

            JobSelectGuiListener.openGUI(target);
            return true;

        } catch (Exception e) {
            warnLoggers(Msg_command_Error + e.getMessage());//todo remover getmessage
            sendPlayerMessage(sender, Msg_command_Error);
            return true;
        }
    }
}
