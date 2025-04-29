package br.com.jobs.commands;

import br.com.jobs.Sql.SqlConnectionYML;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadConfig implements CommandExecutor{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (cmd.getName().equalsIgnoreCase("reloadconfig")) {
            if (!sender.hasPermission("Jobs.reloadconfig")) {
                sender.sendMessage("§cVocê não tem permissão para usar este comando!");
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("sqlconnection")) {
                    new SqlConnectionYML().reloadConfig();
                    sender.sendMessage("§aRecarregando MODT");
                    return true;
                } else {
                    sender.sendMessage("O uso correto é /reloadconfig [sqlconnection]");
                }
            }
        }
        return false;
    }}