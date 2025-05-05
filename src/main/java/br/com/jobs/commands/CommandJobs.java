package br.com.jobs.commands;
import br.com.jobs.Jobs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import static br.com.jobs.utils.TextUtils.*;
import static br.com.jobs.utils.messages.MessagesHandle.*;

public class CommandJobs implements CommandExecutor {
    private final String[] commandAliases = {"jobs"};

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!Arrays.asList(commandAliases).contains(cmd.getName().toLowerCase())) return false;
        try {
            if (!sender.hasPermission("jobs.jobs")) {
                sendPlayerMessage(sender, Msg_NoPermission);
                return true;
            }

            if (args.length == 0) {
                sendPlayerMessage(sender, Msg_command_Jobsreloadusage);
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("jobs.reload")) {
                    Jobs.getInstance().onDisable();
                    Jobs.getInstance().onEnable();
                    sendPlayerMessage(sender, Msg_command_Jobsreload);
                    return true;
                } else {
                    sendPlayerMessage(sender, Msg_NoPermission);
                    return true;
                }
            }

            sendPlayerMessage(sender, Msg_command_Jobsreloadusage);
            return true;

        } catch (ArrayIndexOutOfBoundsException e) {
            sendConsoleMessage(Msg_command_Error + e.getMessage());
        } catch (Exception e) {
            sendConsoleMessage(Msg_command_Error + e.getMessage());
        }
        return false;
    }

}
