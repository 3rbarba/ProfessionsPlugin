package br.com.jobs.utils.messages;

import org.bukkit.configuration.ConfigurationSection;
import static br.com.jobs.Jobs.getMessageyml;
import static br.com.jobs.utils.TextUtils.color;

public class MessagesHandle {
    private static final ConfigurationSection cfMessage = getMessageyml().getConfig().getConfigurationSection("Messages");

    public static final String prefix = color(cfMessage.getString("Prefix"));
    public static final String Msg_NoPermission = color(cfMessage.getString("NoPermission"));
    public static final String Msg_Target_Error = color(cfMessage.getString("Msg_Target_Error"));

    public static final String hasPermission_true = color(cfMessage.getString("Placeholder.hasPermission_true"));
    public static final String hasPermission_false = color(cfMessage.getString("Placeholder.hasPermission_false"));
    public static final String hasWorking_false = color(cfMessage.getString("Placeholder.hasWorking_false"));
    public static final String hasWorking_true = color(cfMessage.getString("Placeholder.hasWorking_true"));
    //Database
    public static final String Msg_tableDB_success = color(cfMessage.getString("Database.Msg_tableDB_success"));
    public static final String Msg_tableDB_error = color(cfMessage.getString("Database.Msg_tableDB_error"));
    public static final String Msg_db_success = color(cfMessage.getString("Database.Msg_db_success"));
    public static final String Msg_db_error = color(cfMessage.getString("Database.Msg_db_error"));
    public static final String InvalidUUID = color(cfMessage.getString("Database.InvalidUUID"));
    public static final String UsingMySQL = color(cfMessage.getString("Database.UsingMySQL"));
    public static final String UsingSQLite = color(cfMessage.getString("Database.UsingSQLite"));
    public static final String Unknown_Database = color(cfMessage.getString("Database.Unknown_Database"));
    public static final String Msg_connectionDB_FinishedError = color(cfMessage.getString("Database.Msg_connectionDB_finishedError"));
    public static final String Msg_connectionDB_finished = color(cfMessage.getString("Database.Msg_connectionDB_finished"));
    public static final String Backup_Success = color(cfMessage.getString("Database.Backup_Success"));
    public static final String Backup_Error = color(cfMessage.getString("Database.Backup_Error"));
    public static final String Msg_Restore_error = color(cfMessage.getString("Database.Msg_Restore_error"));
    public static final String Msg_Restore_Success = color(cfMessage.getString("Database.Msg_Restore_Success"));
    //MySQL
    public static final String Msg_command_Error = color(cfMessage.getString("Msg_Command_Error"));
    public static final String Msg_connectionDB_success = color(cfMessage.getString("Database.MySQL.Msg_connectionDB_success"));
    public static final String Msg_connectionDB_failed = color(cfMessage.getString("Database.MySQL.Msg_connectionDB_failed"));
    public static final String Msg_IncorretFieldsDB = color(cfMessage.getString("Database.MySQL.Msg_IncorrectFieldsDB"));
    public static final String Msg_ReconnectingDB = color(cfMessage.getString("Database.MySQL.Msg_Reconnect"));
    public static final String Msg_Restore_NoFindFile = color(cfMessage.getString("Database.MySQL.Restore_NoFind"));
    public static final String Restore_SQL_Error = color(cfMessage.getString("Database.MySQL.Restore_SQLError"));
    //SQLite
    public static final String Msg_BackupFile_error = color(cfMessage.getString("Database.SQLite.Msg_BackupFile_error"));
    public static final String Msg_RestoreFile_error = color(cfMessage.getString("Database.SQLite.Msg_RestoreFile_error"));
    public static final String Msg_BackupConnection_error = color(cfMessage.getString("Database.SQLite.Msg_BackupConnection_error"));

    //Files
    public static final String Msg_CreationFileDB_success = color(cfMessage.getString("File.Msg_CreationFileDB_success"));
    public static final String Msg_SaveFileDB_Error = color(cfMessage.getString("File.Msg_SaveFileDB_Error"));
    public static final String Msg_NoFoundDB_file = color(cfMessage.getString("File.Msg_NoFoundDB_file"));
    public static final String Msg_ReloadConfigDB = color(cfMessage.getString("File.Msg_ReloadConfigDB"));

    public static final String Msg_command_reloadconfig = color(cfMessage.getString("Commands.ReloadConfig.Msg_Command_ReloadConfig"));

    public static final String Msg_ReloadConfigGuiYML = color(cfMessage.getString("Commands.ReloadConfig.Msg_Command_ReloadConfigGuiYML"));
    public static final String Msg_Command_ReloadConfigGuiYML_Error = color(cfMessage.getString("Commands.ReloadConfig.Msg_Command_ReloadConfigGuiYML_Error"));
    public static final String Msg_Command_ReloadConfigGuiYML_Create = color(cfMessage.getString("Commands.ReloadConfig.Msg_Command_ReloadConfigGuiYML_Create"));
    public static final String Msg_Command_ReloadConfigGuiYML_Create_Error = color(cfMessage.getString("Commands.ReloadConfig.Msg_Command_ReloadConfigGuiYML_Create_Error"));
    public static final String Msg_Sintax_Error = color(cfMessage.getString("Commands.ReloadConfig.Msg_SintaxReloadConf_Error"));

    public static final String Msg_command_Jobsreload = color(cfMessage.getString("Commands.Jobs.Msg_Command_JobsReload"));
    public static final String Msg_Command_BackupCreating = color(cfMessage.getString("Commands.Jobs.Backup.Msg_Command_BackupCreating"));
    public static final String Msg_Command_BackupSuccess = color(cfMessage.getString("Commands.Jobs.Backup.Msg_Command_BackupSuccess"));
    public static final String Msg_Command_BackupError = color(cfMessage.getString("Commands.Jobs.Backup.Msg_Command_BackupError"));
    public static final String Msg_Command_BackupNoFound = color(cfMessage.getString("Commands.Jobs.Backup.Msg_Command_BackupNoFound"));
    public static final String Msg_Command_BackupUsage = color(cfMessage.getString("Commands.Jobs.Backup.Msg_Command_BackupUsage"));
    public static final String Msg_Command_BackupListUsage = color(cfMessage.getString("Commands.Jobs.Backup.Msg_Command_BackupListUsage"));
    public static final String Msg_Command_RestoreSuccess = color(cfMessage.getString("Commands.Jobs.Restore.Msg_Command_RestoreSuccess"));
    public static final String Msg_Command_RestoreWarning = color(cfMessage.getString("Commands.Jobs.Restore.Msg_Command_RestoreWarning"));
    public static final String Msg_Command_RestoreConfirm = color(cfMessage.getString("Commands.Jobs.Restore.Msg_Command_RestoreConfirm"));
    public static final String Msg_Command_RestoreBackup = color(cfMessage.getString("Commands.Jobs.Restore.Msg_Command_RestoreBackup"));
    public static final String Msg_Command_RestoreUsage = color(cfMessage.getString("Commands.Jobs.Restore.Msg_Command_RestoreUsage"));

    public static final String Msg_Command_JobsSelect_InvalidGui = color(cfMessage.getString("Commands.JobsSelect.Msg_Command_JobsSelect_InvalidGui"));
    public static final String Msg_Command_JobsSelect_SelectProfission = color(cfMessage.getString("Commands.JobsSelect.Msg_Command_JobsSelect_SelectProfission"));
    public static final String Msg_Command_JobsSelect_InvalidMaterial = color(cfMessage.getString("Commands.JobsSelect.Msg_Command_JobsSelect_InvalidMaterial"));
    public static final String Msg_command_Jobsreloadusage = color(cfMessage.getString("Commands.Jobs.Msg_Command_JobsUsage"));

    public static final String Msg_Command_Working_Start = color(cfMessage.getString("Commands.Working.Msg_Command_Working_Start"));
    public static final String Msg_Command_Working_End = color(cfMessage.getString("Commands.Working.Msg_Command_Working_End"));
    public static final String Msg_Command_Working_EmptyHand = color(cfMessage.getString("Commands.Working.Msg_Command_Working_EmptyHand"));
    public static final String Msg_Command_Working_NoJob = color(cfMessage.getString("Commands.Working.Msg_Command_Working_NoJob"));
    public static final String Msg_Command_Working_Execute_error = color(cfMessage.getString("Commands.Working.Msg_Command_Working_Execute_error"));
    
}
