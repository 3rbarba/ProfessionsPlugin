package br.com.jobs.utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static br.com.jobs.utils.messages.MessagesHandle.*;

public class TextUtils{

    public static String color(String msg) {
        return msg == null ? "" : msg.replace("&", "§");
    }

    public static void sendConsoleMessage(String msg) {
        try {
            Bukkit.getConsoleSender().sendMessage(prefix + msg);}
        catch (NullPointerException e){e.printStackTrace();}
    }
    public static void sendPlayerMessage(CommandSender sender, String msg) {
        try {if (sender instanceof Player) sender.sendMessage(prefix + msg);}
        catch (NullPointerException e){e.printStackTrace();}
    }
    public static void warnLoggers(String msg) {
        String msgmodif = msg;
        if (msgmodif.contains("§") && msgmodif.length() >= 2) msgmodif = msgmodif.substring(2);
        Bukkit.getLogger().warning("[Professions]%s".formatted(msgmodif));
    }
    public static void infoLoggers(String msg) {
        String msgmodif = msg;
        if (msgmodif.contains("§") && msgmodif.length() >= 2) msgmodif = msgmodif.substring(2);
        Bukkit.getLogger().info("[Professions]%s".formatted(msgmodif));
    }
    public static String removeColors(String msg) {
        String msgmodif = msg;
        if (msgmodif.contains("§") || msgmodif.contains("&") && msgmodif.length() >= 2) msgmodif = msgmodif.substring(2);
        return msgmodif;
    }
    public static String sendMessageActionbar(Player player, String msg) {
        player.sendActionBar(msg);
        return msg;
    }
}