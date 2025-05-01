package br.com.jobs.commands;

import br.com.jobs.Sql.SqlConnectionYML;
import br.com.jobs.utils.MessageConfigYML;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import static br.com.jobs.Jobs.*;

public class CommandReloadConfig implements CommandExecutor {
/*TODO outra solução "Temporaria" porque já to com sono */

//    private static final ConfigurationSection cfMessage = getMessageyml().getConfig().getConfigurationSection("Messages");
    private static final String Msg_NoPermission = "Msg_NoPermission"/*cfMessage.getString("NoPermission").replace("&", "§");*/;
    private static final String Msg_db_ComReloadConfig = "Msg_db_ComReloadConfig"/*cfMessage.getString("Msg_ReloadConfigDB").replace("&", "§");*/;
    private static final String Msg_Sintax_Error = "Msg_Sintax_Error"/*cfMessage.getString("Msg_SintaxReloadConf_Error").replace("&", "§");*/;
    private static final String Msg_Msg_ComReloadConfig = "Msg_Msg_ComReloadConfig"/*cfMessage.getString("Msg_message_ReloadConfig").replace("&", "§");*/;
    private static final String Msg_command_Error = "Msg_command_Error"/*cfMessage.getString("Msg_Command_Error").replace("&", "§");*/;

    @Override

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (cmd.getName().equalsIgnoreCase("reloadconfig")) {
            try {
                if (!sender.hasPermission("Jobs.reloadconfig")) {
                    sendMessagePlayer(sender, Msg_NoPermission);
                    return true;
                }
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("message")) {
                        new MessageConfigYML().reloadConfig();
                        sendMessagePlayer(sender, Msg_Msg_ComReloadConfig);
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("sqlconnection")) {
                        new SqlConnectionYML().reloadConfig();
                        sendMessagePlayer(sender, Msg_db_ComReloadConfig);
                        getSqlConnection().disconnect();
                        sendMessagePlayer(sender, "DataBase desconectada. Conectando novamente...");
                        getSqlConnection().connect();
                        sendMessagePlayer(sender, "DataBase conectada com sucesso!");
                        return true;
                    } else {
                        sendMessagePlayer(sender, Msg_Sintax_Error);
                    }
                }
                if (args.length == 0) {
                    sendMessagePlayer(sender, Msg_Sintax_Error);
                    return true;
                }
            } catch (Exception e) {
                sendMessagePlayer(sender, Msg_command_Error);
                Bukkit.getLogger().info(e.getMessage());
            }
        }
        return false;
    }


    private void sendMessagePlayer(CommandSender sender, String message) {
        sender.sendMessage(prefix + message);
    }
}