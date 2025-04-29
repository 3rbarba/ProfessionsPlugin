package br.com.jobs.Sql;

import br.com.jobs.Jobs;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SqlConnectionYML {
    private File file;
    private FileConfiguration fileConfiguration;

    public SqlConnectionYML() {
        file = new File(Jobs.getInstance().getDataFolder(), "SqlConnection");
        if (!file.exists()) {
            try {
                file.createNewFile();
                Bukkit.getConsoleSender().sendMessage("§2SqlConnection foi criado com sucesso");
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage("§cOuve um erro ao criar o arquivo SqlConnection");
            }
            fileConfiguration = YamlConfiguration.loadConfiguration(file);
            loadConfig();
        }
    }

    public FileConfiguration getConfig() {
        return fileConfiguration;
    }

    public void saveConfig() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("§cErro ao salvar SqlConnection");
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        Bukkit.getConsoleSender().sendMessage("§2SqlConnection foi recarregado com sucesso");
    }

    private void loadConfig() {
        if (!getConfig().contains("MySql")) {
            getConfig().createSection("MySql:");
            getConfig().set("MySql.URL:", "");
            getConfig().set("MySql.PORT:", "");
            getConfig().set("MySql.USER:", "");
            getConfig().set("MySql.PASSWORD", "");
            saveConfig();
        }
    }
}
