package br.com.jobs;
import br.com.jobs.commands.CommandJobSelect;
import br.com.jobs.commands.CommandJobs;
import br.com.jobs.commands.CommandReloadConfig;
import br.com.jobs.commands.CommandWorking;
import br.com.jobs.profissions.GuiConfigYML;
import br.com.jobs.profissions.JobSelectGuiListener;
import br.com.jobs.profissions.miner.BreakArea;
import br.com.jobs.profissions.miner.BreakAreaGUI;
import br.com.jobs.profissions.miner.MinerGuiListener;
import br.com.jobs.profissions.miner.PickaxeObject;
import br.com.jobs.profissions.professionsConfigYML.ProfessionsFile;
import br.com.jobs.profissions.professionsConfigYML.MinerYML;
import br.com.jobs.sql.SqlConnection;
import br.com.jobs.sql.SqlConnectionYML;
import br.com.jobs.sql.SqlJobManager;
import br.com.jobs.utils.Papi.SomeExpansion;
import br.com.jobs.utils.TabComplete;
import br.com.jobs.utils.messages.MessageConfigYML;
import br.com.jobs.utils.messages.MessagesHandle;
import org.bstats.bukkit.Metrics;
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
    private static MinerYML minerYML;
    private static ProfessionsFile professionsFile;
    private static SqlConnection sqlConnection;
    private MessagesHandle messageHandler;

    @Override
    public void onEnable() {
        instance = this;
        Messages();
        General();

        registerEvents();
        registerCommands();
        registerTab();
        //bStats();
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

    public static SqlConnectionYML getSqlConnectionYML() {return sqlConnectionYML;}

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

    private void Messages(){
        messageyml = new MessageConfigYML();
        messageHandler = new MessagesHandle();
        sqlConnectionYML = new SqlConnectionYML();
        professionsFile = new ProfessionsFile();
        guiConfigYML = new GuiConfigYML();
        minerYML = new MinerYML();
        minerYML.initialize();
    }

    private void General(){
        //Database
        sqlConnection = new SqlConnection();
        sqlConnection.connect();
        SqlJobManager.init(sqlConnection.getConnection());
        SqlJobManager sqlJobManager = new SqlJobManager(sqlConnection.getConnection());


        new BukkitRunnable() {
            @Override
            public void run() {
                if (sqlConnection != null) {
                    sqlConnection.attemptReconnect();
                }
            }
        }.runTaskTimer(this, 0, 6000);
        // PlaceholderAPI
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new SomeExpansion(sqlJobManager).register();
        }
    }
    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new JobSelectGuiListener(), this);
        Bukkit.getPluginManager().registerEvents(new MinerGuiListener(), this);
        Bukkit.getPluginManager().registerEvents(new PickaxeObject(), this);
        Bukkit.getPluginManager().registerEvents(new BreakArea(), this);
        Bukkit.getPluginManager().registerEvents(new BreakAreaGUI(), this);
    }
    private void registerCommands() {
        getCommand("reloadconfig").setExecutor(new CommandReloadConfig());
        getCommand("profission").setExecutor(new CommandJobSelect());
        getCommand("jobs").setExecutor(new CommandJobs());
        getCommand("working").setExecutor(new CommandWorking());
        getCommand("teste").setExecutor(new teste());//todo retirar aqui e no plugin.YML
    }
    private void bStats(){
        int pluginId = 25814;
        Metrics metrics = new Metrics(this, pluginId);
    }

}