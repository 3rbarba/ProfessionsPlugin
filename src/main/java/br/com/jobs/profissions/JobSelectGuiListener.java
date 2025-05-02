package br.com.jobs.profissions;
import br.com.jobs.Jobs;
import br.com.jobs.sql.SqlJobManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;


public class JobSelectGuiListener implements Listener {
    private static final ConfigurationSection cfMessage = Jobs.getMessageyml().getConfig().getConfigurationSection("Messages");
    private static final String Archer = cfMessage.getString("Job_Archer");
    private static final String Enchanter = cfMessage.getString("Job_Enchanter");
    private static final String Lumber = cfMessage.getString("Job_Lumberjack");
    private static final String Farmer = cfMessage.getString("Job_Farmer");
    private static final String Fisherman = cfMessage.getString("Job_Fisherman");
    private static final String Miner = cfMessage.getString("Job_Miner");
    private static final String Tank = cfMessage.getString("Job_Tank");
    private static final String Agil = cfMessage.getString("Job_Agility");
    private static final String Warrior = cfMessage.getString("Job_Warrior");
    private static final String Alchemist = cfMessage.getString("Job_Alchemist");
    private static final String Shovel = cfMessage.getString("Job_Excavation");
    public static final String gui = cfMessage.getString("Job_Gui");

    private static final String[] professions_name = new String[]{
            Archer != null ? Archer : "Archer",
            Enchanter != null ? Enchanter : "Enchanter",
            Lumber != null ? Lumber : "LumberJack",
            Farmer != null ? Farmer : "Farmer",
            Fisherman != null ? Fisherman : "Fisherman",
            Miner != null ? Miner : "Miner",
            Tank != null ? Tank : "Tanker",
            Agil != null ? Agil : "Agility",
            Warrior != null ? Warrior : "Warrior",
            Alchemist != null ? Alchemist : "Alchemist",
            Shovel != null ? Shovel : "Excavator"
    };


    public static void openGUI(Player target) {
        Inventory inv = Bukkit.createInventory(null, 45, gui);
        //todo teste rapido apenas para ver se tava tudo certo com o comando e as mensagens
        for (int i = 0; i < professions_name.length; i++) {
            ItemStack item = new ItemStack(Material.BOOK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§a" + professions_name[i]);
            meta.setLore(Arrays.asList("§7Clique para selecionar", "§eProfissão: " + professions_name[i]));
            item.setItemMeta(meta);
            inv.setItem( i, item);
        }

        target.openInventory(inv);
    }

}
