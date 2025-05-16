package br.com.jobs.commands;
import br.com.jobs.Jobs;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.scheduler.BukkitRunnable;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import static br.com.jobs.utils.TextUtils.*;
import static br.com.jobs.utils.messages.MessagesHandle.*;


public class CommandBackup implements CommandExecutor, TabCompleter {

    private final Jobs plugin;

    public CommandBackup() {
        this.plugin = Jobs.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        try {
            if (!sender.hasPermission("jobs.backup")) {
                sendPlayerMessage(sender, Msg_NoPermission);
                return true;
            }

            if (args.length == 0) {
                createBackup(sender, null);
                return true;
            }

            switch (args[0].toLowerCase()) {
                case "list":
                case "listar":
                    listBackups(sender);
                    break;
                default:
                    createBackup(sender, args[0]);
                    break;
            }

            return true;
        } catch (Exception e) {
            sendConsoleMessage(Msg_command_Error + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private void createBackup(CommandSender sender, String filename) {
        sendPlayerMessage(sender, Msg_Command_BackupCreating);

        new BukkitRunnable() {
            @Override
            public void run() {
                File backupFile = plugin.createDatabaseBackup(filename);
    
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (backupFile != null) {
                            String successMsg = Msg_Command_BackupSuccess.replace("%filename%", backupFile.getName());
                            sendPlayerMessage(sender, successMsg);
                        } else {
                            sendPlayerMessage(sender, Msg_Command_BackupError);
                        }
                    }
                }.runTask(plugin);
            }
        }.runTaskAsynchronously(plugin);
    }

    private void listBackups(CommandSender sender) {
        File[] backups = plugin.listDatabaseBackups();

        if (backups.length == 0) {
            sendPlayerMessage(sender, Msg_Command_BackupNoFound);
            return;
        }

        sendPlayerMessage(sender, ChatColor.GOLD + "======= Backups ======");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for (File backup : backups) {
            String date = dateFormat.format(new Date(backup.lastModified()));
            String size = String.format("%.2f KB", backup.length() / 1024.0);
            sendPlayerMessage(sender, ChatColor.YELLOW + backup.getName() + ChatColor.WHITE + " - " + date + " (" + size + ")");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("jobs.admin.backup")) {
            return new ArrayList<>();
        }

        if (args.length == 1) {
            List<String> options = new ArrayList<>();
            options.add("list");
            options.add("listar");

            return options.stream()
                    .filter(option -> option.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
