package br.com.jobs.utils.messages;

import org.bukkit.configuration.ConfigurationSection;
import static br.com.jobs.Jobs.getMessageyml;
import static br.com.jobs.utils.TextUtils.color;

public class MessagesHandle {
    private static final ConfigurationSection cfMessage = getMessageyml().getConfig().getConfigurationSection("Messages");

    public static final String prefix = color(cfMessage.getString("Prefix"));
    public static final String Msg_NoPermission = color(cfMessage.getString("NoPermission"));
    public static final String Msg_Target_Error = color(cfMessage.getString("Msg_Target_Error"));
    public static final String hasPermission_true = color(cfMessage.getString("Placeholder.hasPermission_true", "TRUE"));
    public static final String hasPermission_false = color(cfMessage.getString("Placeholder.hasPermission_false", "FALSE"));

    public static final String Msg_command_Error = color(cfMessage.getString("Msg_Command_Error"));
    public static final String Msg_ReloadConfigDB = color(cfMessage.getString("Database.Msg_ReloadConfigDB"));
    public static final String Msg_NoFoundDB_file = color(cfMessage.getString("Database.Msg_NoFoundDB_file"));
    public static final String Msg_connectionDB_sucess = color(cfMessage.getString("Database.Msg_connectionDB_success"));
    public static final String Msg_connectionDB_failed = color(cfMessage.getString("Database.Msg_connectionDB_failed"));
    public static final String Msg_connectionDB_finished = color(cfMessage.getString("Database.Msg_connectionDB_finished"));
    public static final String Msg_connectionDB_FinishedError = color(cfMessage.getString("Database.Msg_connectionDB_finishedError"));
    public static final String Msg_IncorretFieldsDB = color(cfMessage.getString("Database.Msg_IncorrectFieldsDB"));
    public static final String Msg_CreationFileDB_sucess = color(cfMessage.getString("Database.Msg_CreationFileDB_success"));
    public static final String Msg_SaveFileDB_Error = color(cfMessage.getString("Database.Msg_SaveFileDB_Error"));
    public static final String Msg_db_sucess = color(cfMessage.getString("Database.Msg_db_success"));
    public static final String Msg_db_error = color(cfMessage.getString("Database.Msg_db_error"));
    public static final String Msg_tableDB_sucess = color(cfMessage.getString("Database.Msg_tableDB_success"));
    public static final String Msg_tableDB_error = color(cfMessage.getString("Database.Msg_tableDB_error"));

    public static final String Msg_command_reloadconfig = color(cfMessage.getString("Commands.ReloadConfig.Msg_Command_ReloadConfig"));

    public static final String Msg_ReloadConfigGuiYML = color(cfMessage.getString("Commands.ReloadConfig.Msg_Command_ReloadConfigGuiYML"));
    public static final String Msg_Command_ReloadConfigGuiYML_Error = color(cfMessage.getString("Commands.ReloadConfig.Msg_Command_ReloadConfigGuiYML_Error"));
    public static final String Msg_Command_ReloadConfigGuiYML_Create = color(cfMessage.getString("Commands.ReloadConfig.Msg_Command_ReloadConfigGuiYML_Create"));
    public static final String Msg_Command_ReloadConfigGuiYML_Create_Error = color(cfMessage.getString("Commands.ReloadConfig.Msg_Command_ReloadConfigGuiYML_Create_Error"));

    public static final String Msg_Command_ReloadConfigDB_Error  = color(cfMessage.getString("Commands.ReloadConfigDB.Msg_Command_ReloadConfigDB_Error"));
    public static final String Msg_Sintax_Error = color(cfMessage.getString("Commands.ReloadConfig.Msg_SintaxReloadConf_Error"));
    public static final String Msg_command_Jobsreload = color(cfMessage.getString("Commands.Jobs.Msg_Command_JobsReload"));
    public static final String Msg_Command_JobsSelect_InvalidGui = color(cfMessage.getString("Commands.JobsSelect.Msg_Command_JobsSelect_InvalidGui"));
    public static final String Msg_Command_JobsSelect_SelectProfission = color(cfMessage.getString("Commands.JobsSelect.Msg_Command_JobsSelect_SelectProfission"));
    public static final String Msg_Command_JobsSelect_InvalidMaterial = color(cfMessage.getString("Commands.JobsSelect.Msg_Command_JobsSelect_InvalidMaterial"));
    public static final String Msg_command_Jobsreloadusage = color(cfMessage.getString("Commands.Jobs.Msg_Command_JobsReloadUsage"));
}