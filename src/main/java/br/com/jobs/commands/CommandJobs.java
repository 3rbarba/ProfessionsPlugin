package br.com.jobs.commands;
import br.com.jobs.Jobs;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static br.com.jobs.utils.TextUtils.*;
import static br.com.jobs.utils.messages.MessagesHandle.*;

public class CommandJobs implements CommandExecutor, TabCompleter {
    private final String[] commandAliases = {"jobs"};
    private final CommandBackup backupCommand;
    private final CommandRestore restoreCommand;
    
    public CommandJobs() {
        this.backupCommand = new CommandBackup();
        this.restoreCommand = new CommandRestore();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!Arrays.asList(commandAliases).contains(cmd.getName().toLowerCase())) return false;

        try {
            if (args.length == 0) {
                showHelp(sender);
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "reload":
                    handleReload(sender);
                    break;
                case "dbbackup":
                    handleBackupCommand(sender, args);
                    break;
                case "dbrestore":
                    handleRestoreCommand(sender, args);
                    break;
                case "help":
                case "ajuda":
                    showHelp(sender);
                    break;
                default:
                    showHelp(sender);
                    break;
            }
            return true;
            
        } catch (Exception e) {
            sendConsoleMessage(Msg_command_Error + e.getMessage());
        }
        return false;
    }
    private void handleReload(CommandSender sender) {
        if (!sender.hasPermission("jobs.reload")) {
            sendPlayerMessage(sender, Msg_NoPermission);
            return;
        }
        Jobs.getInstance().onDisable();
        Jobs.getInstance().onEnable();
        sendPlayerMessage(sender, Msg_command_Jobsreload);
    }
    private void handleBackupCommand(CommandSender sender, String[] args) {
        String[] newArgs = args.length > 1 
            ? Arrays.copyOfRange(args, 1, args.length) 
            : new String[0];

        Command mockCommand = new Command("dbbackup") {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                return true;
            }
        };
        backupCommand.onCommand(sender, mockCommand, "dbbackup", newArgs);
    }
    private void handleRestoreCommand(CommandSender sender, String[] args) {
        String[] newArgs = args.length > 1 
            ? Arrays.copyOfRange(args, 1, args.length) 
            : new String[0];

        Command mockCommand = new Command("dbrestore") {
            @Override
            public boolean execute(CommandSender sender, String s, String[] args) {
                return true;
            }
        };

        restoreCommand.onCommand(sender, mockCommand, "dbrestore", newArgs);
    }
    private void showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "======= Commands Jobs =======");
        
        if (sender.hasPermission("jobs.reload")) {
            sendPlayerMessage(sender, Msg_command_Jobsreloadusage);
        }
        
        if (sender.hasPermission("jobs.admin.backup")) {
            sendPlayerMessage(sender, Msg_Command_BackupUsage);
            sendPlayerMessage(sender, Msg_Command_BackupListUsage);
            sendPlayerMessage(sender, Msg_Command_RestoreUsage);
        }
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {


        if (args.length == 1) {
            List<String> commands = new ArrayList<>();
            
            commands.add("help");
            commands.add("ajuda");
            
            if (sender.hasPermission("jobs.reload")) {
                commands.add("reload");
            }
            
            if (sender.hasPermission("jobs.admin.backup")) {
                commands.add("dbbackup");
                commands.add("dbrestore");
            }
            
            return commands.stream()
                    .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        } else if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("dbbackup") && sender.hasPermission("jobs.admin.backup")) {
                // Delegar para o CommandBackup
                String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
                return backupCommand.onTabComplete(sender, cmd, "dbbackup", newArgs);
            } else if (args[0].equalsIgnoreCase("dbrestore") && sender.hasPermission("jobs.admin.backup")) {
                // Delegar para o CommandRestore
                String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
                return restoreCommand.onTabComplete(sender, cmd, "dbrestore", newArgs);
            }
        }

        return new ArrayList<>();
    }
}
