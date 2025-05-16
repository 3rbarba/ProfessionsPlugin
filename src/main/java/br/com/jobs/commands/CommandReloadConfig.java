package br.com.jobs.commands;
import br.com.jobs.profissions.GuiConfigYML;
import br.com.jobs.utils.messages.MessageConfigYML;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.List;
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
                    case "guiconfig":
                        new GuiConfigYML().reloadConfig();
                        sendPlayerMessage(sender, Msg_db_success);
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
