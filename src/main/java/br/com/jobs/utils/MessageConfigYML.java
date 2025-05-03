package br.com.jobs.utils;
import br.com.jobs.Jobs;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MessageConfigYML {

    private static final String prefix = Jobs.prefix;
    private final File file;
    private FileConfiguration fileConfiguration;

    public MessageConfigYML() {
        file = new File(Jobs.getInstance().getDataFolder(), "Messages.yml");


        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.print("Erro ao criar o arquivo de configuração!");
            }
            sendConsoleMessage("§2O arquivo messages.yml foi criado com sucesso.");
        }


        fileConfiguration = YamlConfiguration.loadConfiguration(file);


        loadConfig();
    }

    public FileConfiguration getConfig() {
        return fileConfiguration;
    }

    public void saveConfig() {
        try {
            getConfig().save(file);
        } catch (IOException e) {
            sendConsoleMessage("§cErro ao salvar o arquivo messages.yml");
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        sendConsoleMessage("§2Arquivo messages.yml recarregado com sucesso.");
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
            getConfig().set("Messages.Msg_db_sucess", "&aDatabase %s criada/verificada com sucesso.");
            getConfig().set("Messages.Msg_db_error", "&cErro ao criar/verificar a database '%s': ");
            getConfig().set("Messages.Msg_tableDB_sucess", "&aTabela jobs_data criada/verificada com sucesso.");
            getConfig().set("Messages.Msg_tableDB_error", "&cErro ao criar/verificar a tabela jobs_data: ");
            getConfig().set("Messages.Msg_CreationFileDB_sucess", " &2Database.yml foi criado com sucesso.");
            getConfig().set("Messages.Msg_SaveFileDB_Error", "&cErro ao salvar o arquivo Database.yml");
            getConfig().set("Messages.Msg_ReloadConfigDB", "&2Database.yml foi recarregado com sucesso.");
            getConfig().set("Messages.Msg_SintaxReloadConf_Error", "&cO uso correto é /reloadconfig [database | message]");
            getConfig().set("Messages.Msg_message_ReloadConfig", "&2Arquivo messages.yml recarregado com sucesso");
            getConfig().set("Messages.Msg_Command_Error", "&c Ocorreu um erro ao executar este comando");
            getConfig().set("Messages.Msg_command_DisconnectDB", "&aDesconectando do banco de dados.");
            getConfig().set("Messages.Msg_command_ConnectDB", "&aReconectando ao banco de dados.");
            getConfig().set("Messages.Msg_Error_Saved", "&cNão foi possivel salvar o arquivo messages.yml");
            getConfig().set("Messages.Msg_FileCreated", "&2O arquivo messages.yml foi criado com sucesso");
            getConfig().set("Messages.Msg_Target_Error", "'%s' não é um jogador");
            getConfig().set("Messages.Msg_Command_profission_Error", "Erro ao executar este comando");
            getConfig().set("Messages.Job_Archer", "Arqueiro");
            getConfig().set("Messages.Job_Enchanter", "Encantador");
            getConfig().set("Messages.Job_Lumberjack", "Lenhador");
            getConfig().set("Messages.Job_Farmer", "Fazendeiro");
            getConfig().set("Messages.Job_Fisherman", "Pescador");
            getConfig().set("Messages.Job_Miner", "Minerador");
            getConfig().set("Messages.Job_Tank", "Tanker");
            getConfig().set("Messages.Job_Agility", "Maratonista");
            getConfig().set("Messages.Job_Warrior", "Guerreiro");
            getConfig().set("Messages.Job_Alchemist", "Alquimista");
            getConfig().set("Messages.Job_Excavation", "Escavador");
            getConfig().set("Messages.Job_Gui", "Selecione sua profissão");
            saveConfig();
            sendConsoleMessage("§aConfigurações padrões foram adicionadas em Database.yml");
        }
    }

    public void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(prefix + message);
    }
}
