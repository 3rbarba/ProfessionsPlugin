package br.com.jobs.sql;

import br.com.jobs.Jobs;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static br.com.jobs.Jobs.getMessageyml;
import static br.com.jobs.Jobs.prefix;
import static br.com.jobs.utils.TextUtils.color;

public class SqlConnectionYML {
    private final File file;
    private FileConfiguration fileConfiguration;

    private static final ConfigurationSection cfMessage = getMessageyml().getConfig().getConfigurationSection("Messages");

    private static final String Msg_CreationFileDB_sucess = color(cfMessage.getString("Msg_CreationFileDB_sucess"));
    private static final String Msg_SaveFileDB_Error = color(cfMessage.getString("Msg_SaveFileDB_Error"));
    public static final String Msg_ReloadConfigDB = color(cfMessage.getString("Msg_ReloadConfigDB"));

    public SqlConnectionYML() {
        file = new File(Jobs.getInstance().getDataFolder(), "Database.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.print("");
            }
            sendConsoleMessage(Msg_CreationFileDB_sucess);
        }

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        loadConfig();
    }

    public FileConfiguration getConfig() {
        return fileConfiguration;
    }

    public void saveConfig() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            sendConsoleMessage(Msg_SaveFileDB_Error);
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        sendConsoleMessage(Msg_ReloadConfigDB);
    }

    private void loadConfig() {
        if (getConfig().getConfigurationSection("MySql") == null) {
            getConfig().createSection("MySql");
            getConfig().set("MySql.HOST", "localhost");
            getConfig().set("MySql.PORT", 3306);
            getConfig().set("MySql.USER", "root");
            getConfig().set("MySql.PASSWORD", "root");
            saveConfig();
        }
    }

    public void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(prefix + message);
    }
}
