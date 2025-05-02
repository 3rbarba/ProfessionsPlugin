package br.com.jobs;
import br.com.jobs.profissions.JobSelectGuiListener;
import br.com.jobs.sql.SqlConnection;
import br.com.jobs.sql.SqlConnectionYML;
import br.com.jobs.commands.CommadJobSelect;
import br.com.jobs.commands.CommandReloadConfig;
import br.com.jobs.utils.MessageConfigYML;
import br.com.jobs.utils.ReloadConfigTab;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;

public final class Jobs extends JavaPlugin implements Listener {

    public static String prefix = "§6[Profession] ";


    private static Jobs instance;
    private static SqlConnectionYML sqlConnectionYML;
    private static SqlConnection sqlConnection;
    private static MessageConfigYML messageyml;

    @Override
    public void onEnable() {
        instance = this;

        messageyml = new MessageConfigYML();

        sqlConnectionYML = new SqlConnectionYML();
        sqlConnection = new SqlConnection();
        sqlConnection.connect();

        registerEvents();
        registerCommands();
        registerTab();
    }

    @Override
    public void onDisable() {
        if (sqlConnection != null) {
            sqlConnection.disconnect();
        }
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new JobSelectGuiListener(), this);
    }

    private void registerCommands() {
        getCommand("reloadconfig").setExecutor(new CommandReloadConfig());
        getCommand("profission").setExecutor(new CommadJobSelect());
    }

    public void registerTab() {
        getCommand("reloadconfig").setTabCompleter(new ReloadConfigTab());
    }

    public static MessageConfigYML getMessageyml() {
        return messageyml;
    }

    public static SqlConnectionYML getSqlConnectionYML() {
        return sqlConnectionYML;
    }

    public static Jobs getInstance() {
        return instance;
    }

    public static SqlConnection getSqlConnection() {
        return sqlConnection;
    }

    public Connection getConnection() {
        return getSqlConnection().getConnection();
    }
}
