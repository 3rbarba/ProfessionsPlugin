package br.com.jobs.sql;
import br.com.jobs.Jobs;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import static br.com.jobs.utils.TextUtils.*;
import static br.com.jobs.utils.messages.MessagesHandle.*;

public class SqlConnectionYML {
    private final File file;
    private FileConfiguration fileConfiguration;

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
            warnLoggers(Msg_SaveFileDB_Error);
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
            getConfig().set("MySql.DATABASE", "professions");
            getConfig().set("MySql.TABLE", "jobs");
            saveConfig();
        }
    }
}
