package br.com.jobs;
import br.com.jobs.commands.CommandJobSelect;
import br.com.jobs.commands.CommandJobs;
import br.com.jobs.commands.CommandReloadConfig;
import br.com.jobs.commands.CommandWorking;
import br.com.jobs.profissions.GuiConfigYML;
import br.com.jobs.profissions.JobSelectGuiListener;
import br.com.jobs.profissions.miner.breakArea.BreakArea;
import br.com.jobs.profissions.miner.breakArea.BreakAreaGUI;
import br.com.jobs.profissions.miner.MinerGuiListener;
import br.com.jobs.profissions.miner.PickaxeObject;
import br.com.jobs.profissions.professionsConfigYML.ProfessionsFile;
import br.com.jobs.profissions.professionsConfigYML.MinerYML;
import br.com.jobs.sql.*;
import br.com.jobs.sql.SQLite.SQLiteManager;
import br.com.jobs.utils.InventoryCleanupListener;
import br.com.jobs.utils.Papi.SomeExpansion;
import br.com.jobs.utils.TabComplete;
import br.com.jobs.utils.messages.MessageConfigYML;
import br.com.jobs.utils.messages.MessagesHandle;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.sql.Connection;
import static br.com.jobs.utils.TextUtils.warnLoggers;


public final class Jobs extends JavaPlugin implements Listener {

    private static Jobs instance;
    private static DatabaseYML databaseYML;
    private static MessageConfigYML messageyml;
    private static GuiConfigYML guiConfigYML;
    private static MinerYML minerYML;
    private static ProfessionsFile professionsFile;
    private MessagesHandle messageHandler;
    private DatabaseManager databaseManager;
    private JobsDataRepository jobRepository;

    @Override
    public void onEnable() {
        instance = this;
        Messages();
        General();

        registerEvents();
        registerCommands();
        registerTab();
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) {
            databaseManager.disconnect();
        }
    }
    public static Jobs getInstance() {return instance;}
    public static MessageConfigYML getMessageyml() {return messageyml;}
    public static DatabaseYML getSqlConnectionYML() {return databaseYML;}
    public static GuiConfigYML getGuiConfigYML() {return guiConfigYML;}
    public JobsDataRepository getJobRepository() {return jobRepository;}
    public Connection getConnection() {
        if (databaseManager != null) {return databaseManager.getConnection();}
        return null;}
    public File createDatabaseBackup(String filename) {
        if (backupService == null) {return null;}
        File backupDir = new File(getDataFolder(), "backups");
        return backupService.createBackup(backupDir, filename);}
    public boolean restoreDatabaseFromBackup(File backupFile) {
        if (backupService == null) {return false;}
        return backupService.restoreFromBackup(backupFile);}
    public File[] listDatabaseBackups() {
        if (backupService == null) {return new File[0];}
        File backupDir = new File(getDataFolder(), "backups");
        return backupService.listBackups(backupDir);}
    private void Messages() {
        messageyml = new MessageConfigYML();
        messageHandler = new MessagesHandle();
        databaseYML = new DatabaseYML();
        guiConfigYML = new GuiConfigYML();
        professionsFile = new ProfessionsFile();
        minerYML = new MinerYML();
        minerYML.initialize();
    }
    private DatabaseBackupService backupService;
    
    private void General() {
        try {
            // Database
            databaseManager = DatabaseFactory.createDatabase();
            databaseManager.connect();
    
            // Inicializar serviço de backup
            String dbType = getSqlConnectionYML().getConfig().getString("Database.Type", "SQLite");
            if (databaseManager instanceof SQLiteManager) {
                backupService = DatabaseBackupFactory.createBackupService(this, dbType, (SQLiteManager) databaseManager);
            } else {
                backupService = DatabaseBackupFactory.createBackupService(this, dbType, null);
            }

            jobRepository = DatabaseFactory.createJobRepository(databaseManager);

            SqlJobManager.init(databaseManager.getConnection());
            SqlJobManager sqlJobManager = SqlJobManager.getInstance();

            // PlaceholderAPI
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                new SomeExpansion(sqlJobManager).register();
            }
        } catch (Exception e) {
            warnLoggers(e.getMessage());
            onDisable();
        }
    }
    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new JobSelectGuiListener(), this);
        Bukkit.getPluginManager().registerEvents(new MinerGuiListener(), this);
        Bukkit.getPluginManager().registerEvents(new PickaxeObject(), this);
        Bukkit.getPluginManager().registerEvents(new BreakArea(), this);
        Bukkit.getPluginManager().registerEvents(new BreakAreaGUI(), this);

        InventoryCleanupListener inventoryCleanup = new InventoryCleanupListener();
        Bukkit.getPluginManager().registerEvents(inventoryCleanup, this);
        inventoryCleanup.startPeriodicCleanup(this);
    }
    private void registerCommands() {
        getCommand("reloadconfig").setExecutor(new CommandReloadConfig());
        getCommand("profission").setExecutor(new CommandJobSelect());
        getCommand("jobs").setExecutor(new CommandJobs());
        getCommand("working").setExecutor(new CommandWorking());
        getCommand("teste").setExecutor(new teste());//todo retirar aqui e no plugin.YML
    }
    public void registerTab() {
        getCommand("reloadconfig").setTabCompleter(new TabComplete());
        getCommand("jobs").setTabCompleter(new CommandJobs());
    }

}