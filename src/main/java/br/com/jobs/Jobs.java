package br.com.jobs;
import br.com.jobs.commands.CommandJobSelect;
import br.com.jobs.commands.CommandJobs;
import br.com.jobs.commands.CommandReloadConfig;
import br.com.jobs.commands.CommandWorking;
import br.com.jobs.profissions.GuiConfigYML;
import br.com.jobs.profissions.JobSelectGuiListener;
import br.com.jobs.profissions.miner.break1x2;
import br.com.jobs.profissions.miner.minerGuiListener;
import br.com.jobs.profissions.miner.pickaxeObject;
import br.com.jobs.profissions.professionsConfigYML.ProfessionsFile;
import br.com.jobs.profissions.professionsConfigYML.MinerYML;
import br.com.jobs.sql.SqlConnection;
import br.com.jobs.sql.SqlConnectionYML;
import br.com.jobs.sql.SqlJobManager;
import br.com.jobs.utils.Papi.SomeExpansion;
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
    private static MinerYML minerYML;
    private static ProfessionsFile professionsFile;
    private static SqlConnection sqlConnection;
    private MessagesHandle messageHandler;

    @Override
    public void onEnable() {
        instance = this;
        Messages();
        General();

        registerTab();
        registerCommands();
        registerEvents();
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
        getCommand("working").setExecutor(new CommandWorking());
        getCommand("teste").setExecutor(new teste());//todo retirar aqui e no plugin.YML
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new JobSelectGuiListener(), this);
        Bukkit.getPluginManager().registerEvents(new minerGuiListener(), this);
        Bukkit.getPluginManager().registerEvents(new pickaxeObject(), this);
        Bukkit.getPluginManager().registerEvents(new break1x2(), this);
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
        sqlConnection = new SqlConnection();
        sqlConnection.connect();
        final SqlJobManager sqlJobManager = new SqlJobManager(sqlConnection.getConnection());
        SqlJobManager.init(sqlConnection.getConnection());



        new BukkitRunnable() {
            @Override
            public void run() {
                if (sqlConnection != null) {
                    sqlConnection.attemptReconnect();
                }
            }
        }.runTaskTimer(this, 0, 6000);
        // é Placeholder api mas tá usando o banco de dados para buscar as info
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new SomeExpansion(sqlJobManager).register();
        }
    }
}