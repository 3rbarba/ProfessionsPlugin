package br.com.jobs.utils.messages;

import br.com.jobs.Jobs;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            List<String> header = new ArrayList<>();
            header.add(" -------------------------------------------------------");
            header.add("|                      Messages.yml                     |");
            header.add("|                      Português-BR                     |");
            header.add(" -------------------------------------------------------");
            getConfig().options().setHeader(header);
            getConfig().set("Messages.Prefix", "&6[Professions] ");
            getConfig().set("Messages.NoPermission", "&cVocê não tem permissão para usar este comando!");
            getConfig().set("Messages.Msg_Command_Error", "&cOcorreu um erro ao executar este comando.");
            getConfig().set("Messages.Msg_Target_Error", "&c'%s' não é um jogador.");

            getConfig().set("Messages.Placeholder.hasPermission_false", "False");
            getConfig().set("Messages.Placeholder.hasPermission_true", "True");
            getConfig().set("Messages.Placeholder.hasWorking_false", "Folgando");
            getConfig().set("Messages.Placeholder.hasWorking_true", "Trabalhando");
            //Database
            getConfig().set("Messages.Database.InvalidUUID", "&cUUID inválido: ");
            getConfig().set("Messages.Database.UsingMySQL", "§aUsando MySQL como banco de dados");
            getConfig().set("Messages.Database.UsingSQLite", "§aUsando SQLite como banco de dados");
            getConfig().set("Messages.Database.Unknown_Database", "&cTipo de banco de dados desconhecido. Usando SQLite como padrão.");
            getConfig().set("Messages.Database.Msg_tableDB_success", "&aTabela %s criada/verificada com sucesso.");
            getConfig().set("Messages.Database.Msg_tableDB_error", "&cErro ao criar/verificar a tabela %s: ");
            getConfig().set("Messages.Database.Msg_db_success", "&aDatabase %s criada/verificada com sucesso.");
            getConfig().set("Messages.Database.Msg_db_error", "&cErro ao criar/verificar a database '%s': ");
            getConfig().set("Messages.Database.Msg_connectionDB_finished", "&aConexão com o banco encerrada.");
            getConfig().set("Messages.Database.Msg_connectionDB_finishedError", "&cErro ao encerrar a conexão.");
            getConfig().set("Messages.Database.Backup_Success", "Backup do %s criado com sucesso: ");
            getConfig().set("Messages.Database.Backup_Error", "&cBackup do %s não foi criado: ");
            getConfig().set("Messages.Database.Msg_Restore_error", "Erro ao restaurar o backup: ");
            getConfig().set("Messages.Database.Msg_Restore_Success", "Backup do %s restaurado com sucesso a partir de: ");
            //MySQL
            getConfig().set("Messages.Database.MySQL.Msg_connectionDB_success", "&aConexão com o banco de dados estabelecida com sucesso.");
            getConfig().set("Messages.Database.MySQL.Msg_connectionDB_failed", "&cFalha ao conectar ao banco.");
            getConfig().set("Messages.Database.MySQL.Msg_IncorrectFieldsDB", "&cCampos do MySQL não preenchidos corretamente no Database.YML.");
            getConfig().set("Messages.Database.MySQL.Msg_command_DisconnectDB", "&aDesconectando do banco de dados.");
            getConfig().set("Messages.Database.MySQL.Msg_command_ConnectDB", "&aReconectando ao banco de dados.");
            getConfig().set("Messages.Database.MySQL.Msg_ReconnectingDB", "&eTentando reconectar ao banco de dados");
            getConfig().set("Messages.Database.MySQL.Restore_NoFindFile", "&cArquivo de backup não encontrado: ");
            getConfig().set("Messages.Database.MySQL.Restore_SQLError", "&cErro ao executar comando SQL durante restauração: ");
            //SQLite
            getConfig().set("Messages.Database.SQLite.Msg_BackupFile_error", "&cNão foi possível criar o diretório de backup: ");
            getConfig().set("Messages.Database.SQLite.Msg_BackupConnection_error", "&cNão é possível fazer backup: conexão com SQLite está fechada");
            getConfig().set("Messages.Database.SQLite.Msg_RestoreFile_error", "&cArquivo de banco de dados SQLite não encontrado:");
            //Files
            getConfig().set("Messages.File.Msg_Error_Saved", "&cNão foi possível salvar o arquivo messages.yml.");
            getConfig().set("Messages.File.Msg_FileCreated", "&2O arquivo messages.yml foi criado com sucesso.");
            getConfig().set("Messages.File.Msg_Error_Creation", "&cErro ao criar o arquivo 'messages.yml': ");
            getConfig().set("Messages.File.Msg_FileLoaded", "&aArquivo de mensagens 'messages.yml' carregado.");
            getConfig().set("Messages.File.Msg_CreationFileDB_success", "&2Database.yml foi criado com sucesso.");
            getConfig().set("Messages.File.Msg_SaveFileDB_Error", "&cErro ao salvar o arquivo Database.yml.");
            getConfig().set("Messages.File.Msg_ReloadConfigDB", "&2Database.yml foi recarregado com sucesso.");
            getConfig().set("Messages.File.Msg_NoFoundDB_file", "&2Arquivo Database.yml não encontrado.");
            //Comando de reloadconfig
            getConfig().set("Messages.Commands.ReloadConfig.Msg_Command_ReloadConfig", "&2Arquivo messages.yml recarregado com sucesso.");
            getConfig().set("Messages.Commands.ReloadConfig.Msg_Command_ReloadConfigDB_Error", "&cFalha ao conectar ao banco de dados. Verifique o Database.yml e reinicie o plugin");
            getConfig().set("Messages.Commands.ReloadConfig.Msg_Command_ReloadConfigGuiYML", "&2Arquivo ConfigGUI.yml recarregado com sucesso.");
            getConfig().set("Messages.Commands.ReloadConfig.Msg_Command_ReloadConfigGuiYML_Error", "&cErro ao recarregar o arquivo ConfigGUI.yml. Verifique os logs.");
            getConfig().set("Messages.Commands.ReloadConfig.Msg_Command_ReloadConfigGuiYML_Create", "&2Arquivo ConfigGUI.yml criado com sucesso.");
            getConfig().set("Messages.Commands.ReloadConfig.Msg_Command_ReloadConfigGuiYML_Create_Error", "&cErro ao criar o arquivo ConfigGUI.yml. Verifique os logs.");
            getConfig().set("Messages.Commands.ReloadConfig.Msg_SintaxReloadConf_Error", "&cUso correto: /reloadconfig [message | guiconfig]");
            //Comando de jobs
            //job reload
            getConfig().set("Messages.Commands.Jobs.Msg_Command_JobsUsage", "&cUso correto: /jobs reload");
            getConfig().set("Messages.Commands.Jobs.Msg_Command_JobsReload", "&2Plugin reiniciado com sucesso.");
            //job dbbackup
            getConfig().set("Messages.Commands.Jobs.Backup.Msg_Command_BackupCreating", "&eRealizando backup do banco de dados...");
            getConfig().set("Messages.Commands.Jobs.Backup.Msg_Command_BackupSuccess", "&aBackup criado com sucesso: %filename%");
            getConfig().set("Messages.Commands.Jobs.Backup.Msg_Command_Backup_Success", "&aBackup realizado com sucesso!");
            getConfig().set("Messages.Commands.Jobs.Backup.Msg_Command_BackupError", "&cErro ao criar backup. Verifique o console para mais detalhes.");
            getConfig().set("Messages.Commands.Jobs.Backup.Msg_Command_BackupNoFound", "&cNenhum backup encontrado.");
            getConfig().set("Messages.Commands.Jobs.Backup.Msg_Command_BackupUsage", "&cUso correto: /jobs dbbackup <nome_do_backup>");
            getConfig().set("Messages.Commands.Jobs.Backup.Msg_Command_BackupListUsage", "&cUso correto: /jobs dbbackup list");
            //job dbrestore
            getConfig().set("Messages.Commands.Jobs.Restore.Msg_Command_RestoreSuccess", "&aBackup restaurado com sucesso!");
            getConfig().set("Messages.Commands.Jobs.Restore.Msg_Command_RestoreWarning", "&c&lATENÇÃO! &cRestaurar um backup substituirá TODOS os dados atuais!");
            getConfig().set("Messages.Commands.Jobs.Restore.Msg_Command_RestoreConfirm", "&cDigite '/jobs dbrestore confirmar %filename%' para confirmar.");
            getConfig().set("Messages.Commands.Jobs.Restore.Msg_Command_RestoreBackup", "&2 Restaurando backup: ");
            getConfig().set("Messages.Commands.Jobs.Restore.Msg_Command_RestoreUsage", "&cUso correto: /dbrestore confirmar <nome_do_backup>");
            //Comando de jobsselect
            getConfig().set("Messages.Commands.JobsSelect.Msg_Command_JobsSelect_InvalidGui", "§cAs configurações da GUI estão inválidas. Informe um administrador.");
            getConfig().set("Messages.Commands.JobsSelect.Msg_Command_JobsSelect_SelectProfission", "§aVocê selecionou a profissão: ");
            getConfig().set("Messages.Commands.JobsSelect.Msg_Command_JobsSelect_InvalidMaterial", "§cO material selecionado é inválido. Informe um administrador.");
            //Comando de working
            getConfig().set("Messages.Commands.Working.Msg_Command_Working_Execute_error", "&cOcorreu um erro ao executar este comando. Tente novamente. Se o erro persistir, entre em contato com um administrador.");
            getConfig().set("Messages.Commands.Working.Msg_Command_Working_NoJob", "&cVocê ainda não escolheu uma profissão. digite /jobselect");
            getConfig().set("Messages.Commands.Working.Msg_Command_Working_EmptyHand", "&cEsvazie sua mão primeiro");
            getConfig().set("Messages.Commands.Working.Msg_Command_Working_Start", "&aTrabalhando");
            getConfig().set("Messages.Commands.Working.Msg_Command_Working_End", "&aVocê não está mais trabalhando.");
            saveConfig();
        }
    }

    private static void sendConsoleMessage(String msg) {
            Bukkit.getConsoleSender().sendMessage(prefix() + msg);
        }
}
