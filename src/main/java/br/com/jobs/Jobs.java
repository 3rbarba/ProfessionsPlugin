package br.com.jobs;
import br.com.jobs.commands.CommandJobSelect;
import br.com.jobs.commands.CommandJobs;
import br.com.jobs.commands.CommandReloadConfig;
import br.com.jobs.profissions.GuiConfigYML;
import br.com.jobs.profissions.JobSelectGuiListener;
import br.com.jobs.sql.SqlConnection;
import br.com.jobs.sql.SqlConnectionYML;
import br.com.jobs.utils.TabComplete;
import br.com.jobs.utils.messages.MessageConfigYML;
import br.com.jobs.utils.messages.MessagesHandle;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.sql.Connection;

public final class Jobs extends JavaPlugin implements Listener {

    private static Jobs instance;
    private static SqlConnectionYML sqlConnectionYML;
    private static MessageConfigYML messageyml;
    private static GuiConfigYML guiConfigYML;
    private static SqlConnection sqlConnection;
    private static MessagesHandle messageHandler;

    @Override
    public void onEnable() {
        instance = this;
        messageyml = new MessageConfigYML();
        messageHandler = new MessagesHandle();
        sqlConnectionYML = new SqlConnectionYML();
        guiConfigYML = new GuiConfigYML();


        registerTab();
        registerCommands();
        registerEvents();

        sqlConnection = new SqlConnection();
        sqlConnection.connect();
        new BukkitRunnable() {            @Override
            public void run() {
                if (sqlConnection != null) {
                    sqlConnection.attemptReconnect();
                }
            }
        }.runTaskTimer(this, 0, 6000);
    }

    @Override
    public void onDisable() {
        if (sqlConnection != null) {
            sqlConnection.disconnect();
        }
    }


    public static Jobs getInstance() {
        return instance;
    }

    public static MessageConfigYML getMessageyml() {
        return messageyml;
    }


    public static SqlConnectionYML getSqlConnectionYML() {
        return sqlConnectionYML;
    }

    public static GuiConfigYML getGuiConfigYML() {
       return guiConfigYML;
    }

    public static SqlConnection getSqlConnection() {
        return sqlConnection;
    }

    public Connection getConnection() {
        return getSqlConnection().getConnection();
    }

    public void registerTab() {
        getCommand("reloadconfig").setTabCompleter(new TabComplete());
    }

    private void registerCommands() {
        getCommand("reloadconfig").setExecutor(new CommandReloadConfig());
        getCommand("profission").setExecutor(new CommandJobSelect());
        getCommand("jobs").setExecutor(new CommandJobs());
        getCommand("teste").setExecutor(new teste());//todo retirar aqui e no plugin.YML
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new JobSelectGuiListener(), this);
    }
}