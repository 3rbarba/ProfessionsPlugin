package br.com.jobs;
import br.com.jobs.Sql.SqlConnectionYML;
import br.com.jobs.commands.ReloadConfig;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;

public final class Jobs extends JavaPlugin {

    private static Jobs instance;
    private static SqlConnectionYML sqlConnectionYML;

    @Override
    public void onEnable() {
        instance = this;

        sqlConnectionYML = new SqlConnectionYML();

        registerEvents();
        registerCommands();
    }

    public void onDisable() {
        //encerrar a conexão com o banco de dados
    }

    private void registerEvents() {
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("reloadconfig")).setExecutor(new ReloadConfig());
    }

    //TODO: YML
    public static SqlConnectionYML getSqlConnectionYML() {
        return sqlConnectionYML;
    }

    //TODO: instancia
    public static Jobs getInstance() {
        return instance;
    }
}
