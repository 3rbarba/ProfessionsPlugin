package br.com.jobs.commands;

import br.com.jobs.Jobs;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.jobs.utils.TextUtils.*;
import static br.com.jobs.utils.messages.MessagesHandle.*;

/**
 * Comando para restaurar backups do banco de dados.
 */
public class CommandRestore implements CommandExecutor, TabCompleter {
    
    private final Jobs plugin;
    
    public CommandRestore() {
        this.plugin = Jobs.getInstance();
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        try {
            if (!sender.hasPermission("jobs.admin.backup")) {
                sendPlayerMessage(sender, Msg_NoPermission);
                return true;
            }

            

            if (args.length > 1 && args[0].equalsIgnoreCase("confirm")) {
                File backup = findBackupByName(args[1]);
                if (backup != null) {
                    performRestore(sender, backup);
                } else {
                    sendPlayerMessage(sender, Msg_Command_BackupNoFound);
                }
                return true;
            }
            
            // Caso normal de restauração
            File backup = findBackupByName(args[0]);
            if (backup == null) {
                sendPlayerMessage(sender, Msg_Command_BackupNoFound + args[0]);
                return true;
            }
            
            // Confirmar a restauração se for um jogador
            if (sender instanceof Player) {
                sendPlayerMessage(sender, Msg_Command_RestoreWarning);
                String confirmMsg = Msg_Command_RestoreConfirm.replace("%filename%", backup.getName());
                sendPlayerMessage(sender, confirmMsg);
                return true;
            }
            
            // Se for console, continuar com a restauração
            performRestore(sender, backup);
            return true;
            
        } catch (Exception e) {
            sendConsoleMessage(Msg_command_Error + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    private void performRestore(CommandSender sender, File backupFile) {
        sendPlayerMessage(sender, Msg_Command_RestoreBackup + backupFile.getName());

        // Executar a restauração em uma tarefa assíncrona
        new BukkitRunnable() {
            @Override
            public void run() {
                boolean success = plugin.restoreDatabaseFromBackup(backupFile);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (success) {
                            sendPlayerMessage(sender, Msg_Command_RestoreSuccess);
                        } else {
                            sendPlayerMessage(sender, Msg_Command_BackupError);
                        }
                    }
                }.runTask(plugin);
            }
        }.runTaskAsynchronously(plugin);
    }
    
    private File findBackupByName(String name) {
        File backupDir = new File(plugin.getDataFolder(), "backups");
        if (!backupDir.exists() || !backupDir.isDirectory()) {
            return null;
        }

        File[] backups = backupDir.listFiles();
        if (backups == null) {
            return null;
        }

        // Verificar correspondência exata
        for (File backup : backups) {
            if (backup.getName().equals(name)) {
                return backup;
            }
        }

        // Verificar correspondência sem extensão
        for (File backup : backups) {
            String nameWithoutExt = backup.getName().replaceFirst("[.][^.]+$", "");
            if (nameWithoutExt.equals(name)) {
                return backup;
            }
        }

        // Verificar correspondência de início
        for (File backup : backups) {
            if (backup.getName().startsWith(name)) {
                return backup;
            }
        }

        return null;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("jobs.admin.backup")) {
            return new ArrayList<>();
        }
        
        if (args.length == 1) {
            // Listar todos os backups disponíveis
            File[] backups = plugin.listDatabaseBackups();
            if (backups == null || backups.length == 0) {
                return new ArrayList<>();
            }

            return Arrays.stream(backups)
                    .map(File::getName)
                    .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        } else if (args.length == 2 && args[0].equalsIgnoreCase("confirm")) {
            // Completar nome do backup para confirmar
            File[] backups = plugin.listDatabaseBackups();
            if (backups == null || backups.length == 0) {
                return new ArrayList<>();
            }

            return Arrays.stream(backups)
                    .map(File::getName)
                    .filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        return new ArrayList<>();
    }
}