package br.com.jobs.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ReloadConfigTab implements TabCompleter {
    private static List<String> TabCompleter = new ArrayList<>();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("reloadconfig")) {
            if (args.length == 1) {
                TabCompleter.add("database");
                TabCompleter.add("message");
                suggestions = TabCompleter;
            }
        }
        if(command.getName().equalsIgnoreCase("profession")){
            if(args.length == 1 && sender.hasPermission("jobs.profession.others")){
                    List<String> finalSuggestions = suggestions;
                    Bukkit.getOnlinePlayers().forEach(player -> finalSuggestions.add(player.getName()));
            }
        }
        return suggestions;
    }
}