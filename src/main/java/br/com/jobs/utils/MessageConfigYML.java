package br.com.jobs.utils;
import br.com.jobs.Jobs;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MessageConfigYML {
    //TODO Tô com muito sono pra resolver isso @erick 02:17, 8h codando
    private final String Msg_FileCreated = "§2O arquivo messages.yml foi criado com sucesso";
    private final String Msg_Error_Saved = "§cNão foi possível salvar o arquivo messages.yml";
    private final String Msg_ReloadConfig = "§2Arquivo messages.yml recarregado com sucesso";
    //
    private static final String prefix = Jobs.prefix;
    private final File file;
    private FileConfiguration fileConfiguration;

    public MessageConfigYML() {
        file = new File(Jobs.getInstance().getDataFolder(), "Messages.yml");



        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.print("");
            }
            sendConsoleMessage(Msg_FileCreated);
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
            sendConsoleMessage(Msg_Error_Saved);
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        sendConsoleMessage(Msg_ReloadConfig);
    }

    private void loadConfig() {
        if (getConfig().getConfigurationSection("Messages") == null) {
            getConfig().createSection("Messages");
            getConfig().set("Messages.NoPermission", "&cVocê não tem permissão para usar este comando!");
            getConfig().set("Messages.Msg_NoFoundDB_file", "&2Arquivo SqlConection não encontrado");
            getConfig().set("Messages.Msg_connectionDB_sucess", "&aConexão com o banco de dados estabelecida com sucesso.");
            getConfig().set("Messages.Msg_connectionDB_failed", "&cFalha ao conectar ao banco.");
            getConfig().set("Messages.Msg_connectionDB_finished", "&aConexão com o banco encerrada.");
            getConfig().set("Messages.Msg_connectionDB_finishedError", "&cErro ao encerrar a conexão.");
            getConfig().set("Messages.Msg_IncorretFieldsDB", "&cCampos do MySQL não preenchidos corretamente no YML.");
            getConfig().set("Messages.Msg_db_sucess", "&aDatabase '%s' criada/verificada com sucesso.");
            getConfig().set("Messages.Msg_db_error", "&cErro ao criar/verificar a database '%s': ");
            getConfig().set("Messages.Msg_tableDB_sucess", "&aTabela jobs_data criada/verificada com sucesso.");
            getConfig().set("Messages.Msg_tableDB_error", "&cErro ao criar/verificar a tabela jobs_data: ");
            getConfig().set("Messages.Msg_CreationFileDB_sucess", " &2SqlConnection.yml foi criado com sucesso.");
            getConfig().set("Messages.Msg_SaveFileDB_Error", "&cErro ao salvar o arquivo SqlConnection.yml");
            getConfig().set("Messages.Msg_ReloadConfigDB", "&2SqlConnection.yml foi recarregado com sucesso.");
            getConfig().set("Messages.Msg_SintaxReloadConf_Error", "&cO uso correto é /reloadconfig [sqlconnection | message]");
            getConfig().set("Messages.Msg_message_ReloadConfig", "&2Arquivo messages.yml recarregado com sucesso");
            getConfig().set("Messages.Msg_Command_Error", "&c Ocorreu um erro ao executar este comando");
            getConfig().set("Messages.Msg_Error_Saved", "&cNão foi possivel salvar o arquivo messages.yml");
            getConfig().set("Messages.Msg_FileCreated", "&2O arquivo messages.yml foi criado com sucesso");
            saveConfig();
            sendConsoleMessage("§aConfigurações padrões foram adicionadas em SqlConnection.yml");
        }
    }

    public void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(prefix + message);
    }

}
