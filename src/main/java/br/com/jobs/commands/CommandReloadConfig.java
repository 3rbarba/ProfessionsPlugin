package br.com.jobs.commands;

import br.com.jobs.profissions.GuiConfigYML;
import br.com.jobs.sql.SqlConnectionYML;
import br.com.jobs.sql.SqlJobManager;
import br.com.jobs.utils.messages.MessageConfigYML;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import static br.com.jobs.Jobs.getSqlConnection;
import static br.com.jobs.utils.TextUtils.*;
import static br.com.jobs.utils.messages.MessagesHandle.*;

public class CommandReloadConfig implements CommandExecutor {
    private final List<String> commandAliases = Arrays.asList("reloadconfig", "rlc");


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!commandAliases.contains(cmd.getName().toLowerCase())) return false;

        try {
            if (!sender.hasPermission("Jobs.reloadconfig")) {
                sendPlayerMessage(sender, Msg_NoPermission);
                return true;
            }

            if (args.length == 1) {
                switch (args[0].toLowerCase()) {
                    case "message":
                        new MessageConfigYML().reloadConfig();
                        sendPlayerMessage(sender, Msg_command_reloadconfig);
                        return true;
                    case "database":
                        getSqlConnection().disconnect();
                        sendPlayerMessage(sender, Msg_db_error);
                        Thread.sleep(2000);

                        new SqlConnectionYML().reloadConfig();
                        sendPlayerMessage(sender, Msg_CreationFileDB_sucess);
                        Thread.sleep(2000);

                        getSqlConnection().connect();
                        if (getSqlConnection().getConnection() != null) {
                            sendPlayerMessage(sender, Msg_db_sucess);
                        } else {
                            sendPlayerMessage(sender, Msg_Command_ReloadConfigDB_Error);
                        }
                        return true;
                    case "guiconfig":
                        new GuiConfigYML().reloadConfig();
                        sendPlayerMessage(sender, Msg_db_sucess);
                        return true;
                    default:
                        sendPlayerMessage(sender, Msg_Sintax_Error);
                        return true;
                }
            }

            sendPlayerMessage(sender, Msg_Sintax_Error);

        } catch (Exception e) {
            sendPlayerMessage(sender, Msg_command_Error);
            warnLoggers(Msg_command_Error + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }
}
