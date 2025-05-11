package br.com.jobs.commands;
import br.com.jobs.Jobs;
import br.com.jobs.profissions.miner.PickaxeObject;
import br.com.jobs.sql.SqlJobManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.List;

import static br.com.jobs.utils.TextUtils.sendMessageActionbar;
import static br.com.jobs.utils.TextUtils.sendPlayerMessage;
import static br.com.jobs.utils.messages.MessagesHandle.*;

public class CommandWorking implements CommandExecutor {

    private final List<String> commandAliases = Arrays.asList("working", "trabalhar");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!commandAliases.contains(cmd.getName().toLowerCase())) {
            return false;
        }
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Msg_NoPermission);
            return true;
        }
        if (!player.hasPermission("jobs.working")) {
            player.sendMessage(Msg_NoPermission);
            return true;
        }
        if (!isPlayerSetToWork(player)) {
            noJob(player);
            return true;
        }
        if (SqlJobManager.getInstance().hasWoking(player.getUniqueId()).equalsIgnoreCase("true")) {
            if (player.getInventory().contains(pickMiner)) {
                stopWorking(player);
            } else {
                setPlayerWorking(player, "false");
                sendPlayerMessage(player, Msg_Command_Working_Execute_error);
            }
        } else if (SqlJobManager.getInstance().hasWoking(player.getUniqueId()).equalsIgnoreCase("false")) {
            if (!player.getInventory().contains(pickMiner)){
                startWorking(player);
            }else{
                setPlayerWorking(player, "true");
                sendPlayerMessage(player, Msg_Command_Working_Execute_error);
            }
        } else if (SqlJobManager.getInstance().hasWoking(player.getUniqueId()).equalsIgnoreCase("null")) {
            //Só pra lembrar o de fazer o tratamento desse null
        } else {
            sendPlayerMessage(player, Msg_Command_Working_Execute_error);
        }
        return true;
    }

    SqlJobManager jobManager = new SqlJobManager(Jobs.getInstance().getConnection());


    private boolean isPlayerSetToWork(Player player) {
        try {
            return jobManager.hasProfission(player.getUniqueId().toString());
        } catch (Exception e) {
            player.sendMessage(Msg_Command_Working_Execute_error);
            return false;
        }
    }

    private void noJob(Player player) {
        sendPlayerMessage(player, Msg_Command_Working_NoJob);
    }

    PickaxeObject pickaxeManager = new PickaxeObject();
    ItemStack pickMiner = pickaxeManager.createPickMiner();

    private void startWorking(Player player) {
        switch (SqlJobManager.getInstance().getPlayerProfession(player.getUniqueId())) {
            case "miner":
                if (!player.getItemInHand().getType().isAir()) {
                    sendPlayerMessage(player, Msg_Command_Working_EmptyHand);
                } else {
                    setPlayerWorking(player, "true");//colocar exeption nesse set true para se por um acaso der erro no database não bugar
                    player.getInventory().setItemInMainHand(pickMiner);
                    sendMessageActionbar(player, Msg_Command_Working_Start);
                }
                break;
            default:
                sendPlayerMessage(player, Msg_Command_Working_NoJob);
        }
    }

    private void stopWorking(Player player) {
        switch (SqlJobManager.getInstance().getPlayerProfession(player.getUniqueId())) {
            case "miner":
                player.getInventory().remove(pickMiner);
                setPlayerWorking(player, "false");
                sendMessageActionbar(player, Msg_Command_Working_End);
                break;
        }
    }

    public static void setPlayerWorking(Player player, String value) {
        SqlJobManager.getInstance().setPlayerWorking(player.getUniqueId(), value);
    }
}