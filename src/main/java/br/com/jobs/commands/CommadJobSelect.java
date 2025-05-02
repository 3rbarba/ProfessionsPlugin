package br.com.jobs.commands;

import br.com.jobs.Jobs;
import br.com.jobs.profissions.JobSelectGuiListener;
import br.com.jobs.sql.SqlJobManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.UUID;

import static br.com.jobs.Jobs.getMessageyml;
import static br.com.jobs.Jobs.prefix;
import static br.com.jobs.utils.TextUtils.color;


public class CommadJobSelect implements CommandExecutor {
    private final String[] commandAliases = {"jobselect", "profissao", "professionselect", "profissaoselecionar", "profissaoset"};
    private static final ConfigurationSection cfMessage = getMessageyml().getConfig().getConfigurationSection("Messages");
    private static final String Msg_NoPermission = color(cfMessage.getString("NoPermission"));
    private static final String Msg_Target_Error = color(cfMessage.getString("Msg_Target_Error"));
    private static final String Msg_Command_profission_Error = color(cfMessage.getString("Msg_Command_profission_Error"));

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!Arrays.asList(commandAliases).contains(cmd.getName().toLowerCase())) {
            Player PSender = (Player) sender;
            Player target;

            if (!(sender instanceof Player)) {
                sender.sendMessage(Msg_NoPermission);
                return true;
            }
            SqlJobManager jobManager = new SqlJobManager(Jobs.getInstance().getConnection());
            UUID executorUUID = PSender.getUniqueId();

            if (jobManager.hasProfission(String.valueOf(executorUUID)) && !PSender.hasPermission("jobs.jobselect.others")) {
                sender.sendMessage(color("Você já possui uma profissão "));
                return true;
            }


            if (args.length == 1) {
                target = Bukkit.getPlayerExact(args[0]);

                if (target == null) {
                    sendMessagePlayer(sender, Msg_Target_Error);
                    return true;
                }

                if (!PSender.hasPermission("jobs.jobselect.others")) {
                    sendMessagePlayer(sender, Msg_NoPermission);
                    return true;
                }
            } else {
                target = PSender;
            }
            //TODO metodo que verifica se o player está na database. Vai ser usado para o comando /profisson (nick) remove
/*            if (!jobManager.hasPlayer(target.getUniqueId())) {
                sendMessagePlayer(sender, "§cO jogador " + target.getName() + " ainda não possui dados no banco de dados.");
                return true;
            }
*/
            JobSelectGuiListener.openGUI(target);
            return true;
        }
        return false;
    }

    private void sendMessagePlayer(CommandSender sender, String message) {
        sender.sendMessage(prefix + message);
    }
}
