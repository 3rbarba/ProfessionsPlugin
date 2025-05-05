package br.com.jobs.utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {
    private static List<String> TabCompleter = new ArrayList<>();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("reloadconfig")) {
            if (args.length == 1) {
                TabCompleter.add("database");
                TabCompleter.add("message");
                TabCompleter.add("guiconfig");
                suggestions = TabCompleter;
            }
        }else
        if(cmd.getName().equalsIgnoreCase("jobs")){
            if(args.length == 1){
                TabCompleter.add("reload");
                suggestions = TabCompleter;
            }
        }else
        if(cmd.getName().equalsIgnoreCase("profession")){
            if(args.length == 1 && sender.hasPermission("jobs.profession.others")){
                List<String> finalSuggestions = suggestions;
                Bukkit.getOnlinePlayers().forEach(player -> finalSuggestions.add(player.getName()));
            }
        }
        return suggestions;
    }
}