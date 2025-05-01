package br.com.jobs.Sql;
import br.com.jobs.Jobs;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import static br.com.jobs.Jobs.getMessageyml;
import static br.com.jobs.Jobs.prefix;

public class SqlConnectionYML {
    private final File file;
    private FileConfiguration fileConfiguration;

    private static final ConfigurationSection cfMessage = getMessageyml().getConfig().getConfigurationSection("Messages");
    private static final String Msg_CreationFileDB_sucess = cfMessage.getString("Msg_CreationFileDB_sucess").replace("&", "§");
    private static final String Msg_SaveFileDB_Error = cfMessage.getString("Msg_SaveFileDB_Error").replace("&", "§");
    public static final String Msg_ReloadConfigDB = cfMessage.getString("Msg_ReloadConfigDB").replace("&", "§");

    public SqlConnectionYML() {
        file = new File(Jobs.getInstance().getDataFolder(), "SqlConnection.yml");

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
//            getConfig().set("MySql.DATABASE", "professions");
            getConfig().set("MySql.USER", "root");
            getConfig().set("MySql.PASSWORD", "root");
            saveConfig();
        }
    }
    public void sendConsoleMessage(String message){
        Bukkit.getConsoleSender().sendMessage(prefix + message);
    }
}
