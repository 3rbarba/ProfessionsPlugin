package br.com.jobs.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ReloadConfigTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();


        if (args.length == 1) {
            List<String> TabCompleter = new ArrayList<>();
            TabCompleter.add("sqlconnection");
            TabCompleter.add("message");
            suggestions = TabCompleter;
        }

        return suggestions;
    }
}