package br.com.jobs;
import br.com.jobs.Sql.SqlConnection;
import br.com.jobs.Sql.SqlConnectionYML;
import br.com.jobs.commands.CommandReloadConfig;
import br.com.jobs.utils.ReloadConfigTab;
import br.com.jobs.utils.MessageConfigYML;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

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
    //registers
    private void registerEvents() {
    }
    private void registerCommands() {
        getCommand("reloadconfig").setExecutor(new CommandReloadConfig());
//        getCommand("jobs").setExecutor(new CommandJobs());
    }
    public void registerTab() {
        getCommand("reloadconfig").setTabCompleter(new ReloadConfigTab());
//        getCommand("jobs").setTabCompleter(new Jobs());
    }
    //sql
    public static SqlConnectionYML getSqlConnectionYML() {
        return sqlConnectionYML;
    }
    public static Jobs getInstance() {
        return instance;
    }
    public static SqlConnection getSqlConnection() {
        return sqlConnection;
    }
    //utils
    public static MessageConfigYML getMessageyml() {
        return messageyml;
    }
}
