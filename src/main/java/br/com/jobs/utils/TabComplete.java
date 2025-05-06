package br.com.jobs.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        switch (cmd.getName().toLowerCase()) {
            case "reloadconfig":
                return handleReloadConfig(args);
            case "jobs":
                return handleJobs(args);
            case "profession":
                return handleProfession(sender, args);
            default:
                return new ArrayList<>();
        }
    }

    private List<String> handleReloadConfig(String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            suggestions.add("database");
            suggestions.add("message");
            suggestions.add("guiconfig");
        }
        return suggestions;
    }

    private List<String> handleJobs(String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            suggestions.add("reload");
        }
        return suggestions;
    }

    private List<String> handleProfession(CommandSender sender, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1 && sender.hasPermission("jobs.profession.others")) {
            Bukkit.getOnlinePlayers()
                    .stream()
                    .map(player -> player.getName())
                    .filter(name -> args.length == 0 || name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .forEach(suggestions::add);
        }
        return suggestions;
    }
}