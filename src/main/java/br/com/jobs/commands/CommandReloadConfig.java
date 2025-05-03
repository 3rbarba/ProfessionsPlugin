package br.com.jobs.commands;
import br.com.jobs.sql.SqlConnectionYML;
import br.com.jobs.utils.MessageConfigYML;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import static br.com.jobs.Jobs.*;
import static br.com.jobs.sql.SqlConnection.Msg_connectionDB_failed;
import static br.com.jobs.utils.TextUtils.color;

public class CommandReloadConfig implements CommandExecutor {
    private String[] commandAliases = {"reloadconfig", "rlc"};

    private static final ConfigurationSection cfMessage = getMessageyml().getConfig().getConfigurationSection("Messages");

    private static final String Msg_NoPermission = color(cfMessage.getString("NoPermission"));
    private static final String Msg_db_ComReloadConfig = color(cfMessage.getString("Msg_ReloadConfigDB"));
    private static final String Msg_Sintax_Error = color(cfMessage.getString("Msg_SintaxReloadConf_Error"));
    private static final String Msg_Msg_ComReloadConfig = color(cfMessage.getString("Msg_message_ReloadConfig"));
    private static final String Msg_command_Error = color(cfMessage.getString("Msg_Command_Error"));
    private static final String Msg_command_DisconnectDB = color(cfMessage.getString("Msg_command_DisconnectDB"));
    private static final String Msg_command_ReconnectDB = color(cfMessage.getString("Msg_connectionDB_sucess"));

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!Arrays.asList(commandAliases).contains(cmd.getName().toLowerCase())) return false; {
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
                        case "database":
                            getSqlConnection().disconnect();
                            sendMessagePlayer(sender, color(Msg_command_DisconnectDB));
                            Thread.sleep(2000);
                            new SqlConnectionYML().reloadConfig();
                            sendMessagePlayer(sender, Msg_db_ComReloadConfig);
                            Thread.sleep(2000);
                            getSqlConnection().connect();
                            if (getSqlConnection().getConnection() != null) {
                                sendMessagePlayer(sender, color(Msg_command_ReconnectDB));
                            } else {
                                sendMessagePlayer(sender, color(Msg_connectionDB_failed) + " Verifique o Database.yml e reinicie o servidor");
                                //TODO trocar por uma mensagem la no Message.yml
                            }
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

    }

    private void sendMessagePlayer(CommandSender sender, String message) {
        if (sender instanceof Player) sender.sendMessage(prefix + message);
    }
}
