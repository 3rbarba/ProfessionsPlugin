package br.com.jobs.profissions;
import br.com.jobs.Jobs;
import br.com.jobs.sql.SqlJobManager;
import br.com.jobs.utils.TextUtils;
import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import static br.com.jobs.Jobs.getGuiConfigYML;
import static br.com.jobs.utils.TextUtils.*;
import static br.com.jobs.utils.messages.MessagesHandle.*;

public class JobSelectGuiListener implements Listener {

    private static final NamespacedKey PROFESSION_KEY = new NamespacedKey(Jobs.getInstance(), "profession");

    private final SqlJobManager jobManager;

    public JobSelectGuiListener() {
        this.jobManager = new SqlJobManager(Jobs.getInstance().getConnection());
    }

    public static void openGUI(Player target) {
        ConfigurationSection guiSection = getGuiConfigYML().getConfig().getConfigurationSection("gui");

        if (guiSection == null) {
            sendPlayerMessage(target, Msg_Command_JobsSelect_InvalidGui);
            return;
        }

        String title = guiSection.getString("title", "Seleção de Profissões");
        Inventory inv = Bukkit.createInventory(null, 45, title);

        ConfigurationSection itemsSection = guiSection.getConfigurationSection("items");
        if (itemsSection != null) {
            for (String jobKey : itemsSection.getKeys(false)) {
                ConfigurationSection itemConfig = itemsSection.getConfigurationSection(jobKey);
                if (itemConfig == null) continue;

                int slot = itemConfig.getInt("slot");
                if (slot < 0 || slot >= inv.getSize()) {
                    continue;
                }

                String materialName = itemConfig.getString("material", "BOOK");
                Material material = Material.matchMaterial(materialName);

                if (material == null) {
                    sendConsoleMessage(Msg_Command_JobsSelect_InvalidMaterial + jobKey);
                    continue;
                }

                String displayName = color(itemConfig.getString("display_name", "§aProfissão"));
                List<String> lore = itemConfig.isSet("lore")
                        ? itemConfig.getStringList("lore").stream().map(TextUtils::color).toList()
                        : Collections.emptyList();

                ItemStack item = new ItemStack(material);
                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(displayName);
                    meta.setLore(lore);

                    meta.getPersistentDataContainer().set(
                            PROFESSION_KEY,
                            PersistentDataType.STRING,
                            jobKey
                    );
                    item.setItemMeta(meta);
                }

                inv.setItem(slot, item);
            }
        }

        fillPlaceholderItems(inv);
        target.openInventory(inv);
    }

    private static void fillPlaceholderItems(Inventory inv) {
        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = placeholder.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(" ");
            placeholder.setItemMeta(meta);
        }

        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null || Objects.requireNonNull(inv.getItem(i)).getType() == Material.AIR) {
                inv.setItem(i, placeholder);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        String guiTitle = getGuiConfigYML().getConfig().getString("gui.title", "Seleção de Profissões");
        if (!e.getView().getTitle().equals(guiTitle)) {
            return;
        }

        e.setCancelled(true);

        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR || !clickedItem.hasItemMeta()) {
            return;
        }

        ItemMeta meta = clickedItem.getItemMeta();
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();

        if (!dataContainer.has(PROFESSION_KEY, PersistentDataType.STRING)) {
            return;
        }
        String jobKey = dataContainer.get(PROFESSION_KEY, PersistentDataType.STRING);
        String displayName = removeColors(getGuiConfigYML().getConfig().getString("gui.items." + jobKey + ".display_name"));
        if (jobKey == null) return;

        Player player = (Player) e.getWhoClicked();
        jobManager.setPlayerProfession(player.getUniqueId(), player.getName(), jobKey);

        sendPlayerMessage(player, Msg_Command_JobsSelect_SelectProfission + displayName);
        Player p = (Player) player;
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        player.closeInventory();
    }
}
