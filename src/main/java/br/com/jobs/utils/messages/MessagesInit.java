package br.com.jobs.utils.messages;
import br.com.jobs.Jobs;
import static br.com.jobs.utils.TextUtils.color;

public class MessagesInit {
    private static String getMessage(String path) {
        if (Jobs.getMessageyml() != null && Jobs.getMessageyml().getConfig() != null) {
            return Jobs.getMessageyml().getConfig().getString("Messages." + path, "§cMessage not found: " + path);
        }
        return null;
    }

    public static String Msg_FileCreated() {return color(getMessage("File.Msg_FileCreated"));}
    public static String Msg_Error_Saved() {
        return color(getMessage("File.Msg_Error_Saved"));
    }
    public static String Msg_Error_Creation() {
        return color(getMessage("File.Msg_Error_Creation"));
    }
    public static String Msg_FileLoaded() {
        return color(getMessage("File.Msg_FileLoaded"));
    }
    public static String prefix() {return color(getMessage("Prefix"));}
}
