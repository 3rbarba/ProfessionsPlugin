package br.com.jobs.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        switch (cmd.getName().toLowerCase()) {
            case "reloadconfig":
                return SugestReloadConfig(args);
            case "jobs":
                    return SugestJobs(args);
            case "profession":
                return SugestProfession(sender, args);
            default:
                return new ArrayList<>();
        }
    }

    private List<String> SugestReloadConfig(String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            suggestions.add("database");
            suggestions.add("message");
            suggestions.add("guiconfig");
        }
        return suggestions;
    }

    private List<String> SugestJobs(String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            suggestions.add("reload"); // Adiciona o argumento 'reload'
            suggestions.add(""); // Adiciona uma string vazia
        }
        return suggestions;
    }

    private List<String> SugestProfession(CommandSender sender, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1 && sender.hasPermission("jobs.profession.others")) {
            if (sender instanceof Player) {
                String prefix = args[0].toLowerCase();
                suggestions.addAll(
                        Bukkit.getOnlinePlayers().stream()
                                .map(Player::getName)
                                .filter(name -> name.toLowerCase().startsWith(prefix))
                                .collect(Collectors.toList())
                );
            }
        }
        return suggestions;
    }
}