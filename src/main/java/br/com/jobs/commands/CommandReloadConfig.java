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
import static br.com.jobs.utils.TextUtils.color;

public class CommandReloadConfig implements CommandExecutor {

    private static final ConfigurationSection cfMessage = getMessageyml().getConfig().getConfigurationSection("Messages");

    private static final String Msg_NoPermission = color(cfMessage.getString("NoPermission"));
    private static final String Msg_db_ComReloadConfig = color(cfMessage.getString("Msg_ReloadConfigDB"));
    private static final String Msg_Sintax_Error = color(cfMessage.getString("Msg_SintaxReloadConf_Error"));
    private static final String Msg_Msg_ComReloadConfig = color(cfMessage.getString("Msg_message_ReloadConfig"));
    private static final String Msg_command_Error = color(cfMessage.getString("Msg_Command_Error"));
    private static final String Msg_command_DisconnectDB = color(cfMessage.getString("Msg_command_DisconnectDB"));
    private static final String Msg_command_ReconnectDB = color(cfMessage.getString("Msg_command_ReconnectDB"));
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!cmd.getName().equalsIgnoreCase("reloadconfig")) return false;

        try {
            if (!sender.hasPermission("Jobs.reloadconfig")) {
                sendMessagePlayer(sender, Msg_NoPermission);
                return true;
            }

            if (args.length == 1) {
                switch (args[0].toLowerCase()) {
                    case "message":
                        new MessageConfigYML().reloadConfig();
                        sendMessagePlayer(sender, Msg_Msg_ComReloadConfig);
                        return true;
                    case "sqlconnection":
                        new SqlConnectionYML().reloadConfig();
                        sendMessagePlayer(sender, Msg_db_ComReloadConfig);
                        getSqlConnection().disconnect();
                        sendMessagePlayer(sender, color(Msg_command_DisconnectDB));
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            Bukkit.getLogger().warning(prefix + "error waiting before reconnection: " + e.getMessage());
                            Thread.currentThread().interrupt();
                        }
                        getSqlConnection().connect();
                        sendMessagePlayer(sender, color(Msg_command_ReconnectDB));
                        return true;
                    default:
                        sendMessagePlayer(sender, Msg_Sintax_Error);
                        return true;
                }
            }

            sendMessagePlayer(sender, Msg_Sintax_Error);
        } catch (Exception e) {
            sendMessagePlayer(sender, Msg_command_Error);
            Bukkit.getLogger().warning("Erro ao recarregar configs: " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }

    private void sendMessagePlayer(CommandSender sender, String message) {
        sender.sendMessage(prefix + message);
    }
}
