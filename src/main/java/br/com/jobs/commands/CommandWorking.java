package br.com.jobs.commands;

import br.com.jobs.Jobs;
import br.com.jobs.profissions.miner.pickaxeObject;
import br.com.jobs.sql.SqlJobManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static br.com.jobs.utils.TextUtils.sendPlayerMessage;
import static br.com.jobs.utils.messages.MessagesHandle.Msg_NoPermission;

public class CommandWorking implements CommandExecutor {
    // todo ajustar as mensagens
    //Ainda será refatorado
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
        if (player.getInventory().contains(pickMiner)) {
            if (SqlJobManager.getInstance().hasWoking(player.getUniqueId()).equalsIgnoreCase("true")) {
                stopWorking(player);
            }
        } else if (!player.getInventory().contains(pickMiner)) {
            if (SqlJobManager.getInstance().hasWoking(player.getUniqueId()).equalsIgnoreCase("false")) {
                startWorking(player);
            }
        } else if (SqlJobManager.getInstance().hasWoking(player.getUniqueId()).equalsIgnoreCase("null")) {
            //Só pra lembrar o de fazer o tratamento desse null
        } else {
            sendPlayerMessage(player, "Erro ao executar esse comando, procure um adminstrador");
        }
        return true;
    }
    SqlJobManager jobManager = new SqlJobManager(Jobs.getInstance().getConnection());


    private boolean isPlayerSetToWork(Player player) {
        try {
            return jobManager.hasProfission(player.getUniqueId().toString());
        } catch (Exception e) {
            player.sendMessage("§cErro no isPlayerSetToWork()");
            e.printStackTrace();//todo remover
            return false;
        }
    }

    private void noJob(Player player) {
        sendPlayerMessage(player, "§eVocê ainda não escolheu uma profissão. digite /jobselect");
    }

    pickaxeObject pickaxeManager = new pickaxeObject();
    ItemStack pickMiner = pickaxeManager.createPickMiner();

    private void startWorking(Player player) {
        switch (SqlJobManager.getInstance().getPlayerProfession(player.getUniqueId())) {
            case "miner":
                if (!player.getItemInHand().getType().isAir()) {
                    sendPlayerMessage(player, "Esvazie sua mão primeiro");
                } else {
                    SqlJobManager.getInstance().setPlayerWorking(player.getUniqueId(), "true");//colocar exeption nesse set true para se por um acaso der erro no database não bugar
                    player.getInventory().setItemInMainHand(pickMiner);
                    sendPlayerMessage(player, "Você está trabalhando"/* colocar o nome da profissão do guiconfigyml*/);
                }
                break;
            default:
                sendPlayerMessage(player, "você não possui profissão, digite /jobselect");
        }
    }

    private void stopWorking(Player player) {
        switch (SqlJobManager.getInstance().getPlayerProfession(player.getUniqueId())) {
            case "miner":
                player.getInventory().remove(pickMiner);
                sendPlayerMessage(player, "Você não está mais trabalhando");
                SqlJobManager.getInstance().setPlayerWorking(player.getUniqueId(), "false");
                break;
        }
    }
}