package br.com.jobs.utils.messages;

import br.com.jobs.Jobs;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static br.com.jobs.utils.messages.MessagesInit.*;


public class MessageConfigYML {

    private final File file;
    private FileConfiguration fileConfiguration;

    public MessageConfigYML() {
        file = new File(Jobs.getInstance().getDataFolder(), "Messages.yml");

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                sendConsoleMessage(Msg_FileCreated());
            } catch (IOException e) {
                sendConsoleMessage(Msg_Error_Creation());
                e.printStackTrace();
            }
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
            sendConsoleMessage(Msg_Error_Saved());
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        sendConsoleMessage(Msg_FileLoaded());
    }

    private void loadConfig() {
        if (getConfig().getConfigurationSection("Messages") == null) {

            getConfig().set("Messages.Prefix", "&6[Professions] ");
            getConfig().set("Messages.NoPermission", "&cVocê não tem permissão para usar este comando!");
            getConfig().set("Messages.Msg_Command_Error", "&cOcorreu um erro ao executar este comando.");
            getConfig().set("Messages.Msg_Target_Error", "&c'%s' não é um jogador.");
            getConfig().set("Messages.Placeholder.hasPermission_false", "False");
            getConfig().set("Messages.Placeholder.hasPermission_true", "True");

            getConfig().set("Messages.Database.Msg_NoFoundDB_file", "&2Arquivo SqlConection não encontrado.");
            getConfig().set("Messages.Database.Msg_connectionDB_success", "&aConexão com o banco de dados estabelecida com sucesso.");
            getConfig().set("Messages.Database.Msg_connectionDB_failed", "&cFalha ao conectar ao banco.");
            getConfig().set("Messages.Database.Msg_connectionDB_finished", "&aConexão com o banco encerrada.");
            getConfig().set("Messages.Database.Msg_connectionDB_finishedError", "&cErro ao encerrar a conexão.");
            getConfig().set("Messages.Database.Msg_IncorrectFieldsDB", "&cCampos do MySQL não preenchidos corretamente no Database.YML.");
            getConfig().set("Messages.Database.Msg_command_DisconnectDB", "&aDesconectando do banco de dados.");
            getConfig().set("Messages.Database.Msg_command_ConnectDB", "&aReconectando ao banco de dados.");
            getConfig().set("Messages.Database.Msg_db_success", "&aDatabase %s criada/verificada com sucesso.");
            getConfig().set("Messages.Database.Msg_db_error", "&cErro ao criar/verificar a database '%s': ");
            getConfig().set("Messages.Database.Msg_tableDB_success", "&aTabela %s criada/verificada com sucesso.");
            getConfig().set("Messages.Database.Msg_tableDB_error", "&cErro ao criar/verificar a tabela %s: ");
            getConfig().set("Messages.Database.Msg_CreationFileDB_success", "&2Database.yml foi criado com sucesso.");
            getConfig().set("Messages.Database.Msg_SaveFileDB_Error", "&cErro ao salvar o arquivo Database.yml.");
            getConfig().set("Messages.Database.Msg_ReloadConfigDB", "&2Database.yml foi recarregado com sucesso.");

            getConfig().set("Messages.File.Msg_Error_Saved", "&cNão foi possível salvar o arquivo messages.yml.");
            getConfig().set("Messages.File.Msg_FileCreated", "&2O arquivo messages.yml foi criado com sucesso.");
            getConfig().set("Messages.File.Msg_Error_Creation", "&cErro ao criar o arquivo 'messages.yml': ");
            getConfig().set("Messages.File.Msg_FileLoaded", "&aArquivo de mensagens 'messages.yml' carregado.");

            getConfig().set("Messages.Commands.ReloadConfig.Msg_SintaxReloadConf_Error", "&cO uso correto é /reloadconfig [database | message | gui].");
            getConfig().set("Messages.Commands.ReloadConfig.Msg_Command_ReloadConfig", "&2Arquivo messages.yml recarregado com sucesso.");
            getConfig().set("Messages.Commands.ReloadConfig.Msg_Command_ReloadConfigDB_Error", "&cFalha ao conectar ao banco de dados. Verifique o Database.yml e reinicie o plugin");
            getConfig().set("Messages.Commands.ReloadConfig.Msg_Command_ReloadConfigGuiYML", "&2Arquivo ConfigGUI.yml recarregado com sucesso.");
            getConfig().set("Messages.Commands.ReloadConfig.Msg_Command_ReloadConfigGuiYML_Error", "&cErro ao recarregar o arquivo ConfigGUI.yml. Verifique os logs.");
            getConfig().set("Messages.Commands.ReloadConfig.Msg_Command_ReloadConfigGuiYML_Create", "&2Arquivo ConfigGUI.yml criado com sucesso.");
            getConfig().set("Messages.Commands.ReloadConfig.Msg_Command_ReloadConfigGuiYML_Create_Error", "&cErro ao criar o arquivo ConfigGUI.yml. Verifique os logs.");

            getConfig().set("Messages.Commands.Jobs.Msg_Command_JobsReloadUsage", "&cUso correto: /jobs reload");
            getConfig().set("Messages.Commands.Jobs.Msg_Command_JobsReload", "&2Plugin reiniciado com sucesso.");

            getConfig().set("Messages.Commands.JobsSelect.Msg_Command_JobsSelect_InvalidGui", "§cAs configurações da GUI estão inválidas. Informe um administrador.");
            getConfig().set("Messages.Commands.JobsSelect.Msg_Command_JobsSelect_SelectProfission", "§aVocê selecionou a profissão: ");
            getConfig().set("Messages.Commands.JobsSelect.Msg_Command_JobsSelect_InvalidMaterial", "§cO material selecionado é inválido. Informe um administrador.");


            saveConfig();
        }
    }

    private static void sendConsoleMessage(String msg) {
            Bukkit.getConsoleSender().sendMessage(prefix() + msg);
        }
}
