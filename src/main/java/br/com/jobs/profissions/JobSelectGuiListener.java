package br.com.jobs.profissions;

import br.com.jobs.Jobs;
import br.com.jobs.sql.SqlJobManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.util.List;
import java.util.Objects;
import static br.com.jobs.Jobs.getGuiConfigYML;
import static br.com.jobs.utils.TextUtils.*;
import static br.com.jobs.utils.messages.MessagesHandle.*;

public class JobSelectGuiListener implements Listener {

    public static void openGUI(Player target) {

        ConfigurationSection guiSection = getGuiConfigYML().getConfig().getConfigurationSection("gui");

        if (guiSection == null) {
            sendPlayerMessage(target, Msg_Command_JobsSelect_InvalidGui);
            return;
        }

        // Define o título do inventário
        String title = guiSection.getString("title", "Seleção de Profissões");
        Inventory inv = Bukkit.createInventory(null, 45, title);

        // Obtém os itens da GUI
        ConfigurationSection itemsSection = guiSection.getConfigurationSection("items");
        if (itemsSection != null) {
            for (String jobKey : itemsSection.getKeys(false)) { // jobKey é a chave fixa (ex: "archer", "warrior")
                ConfigurationSection itemConfig = itemsSection.getConfigurationSection(jobKey);
                if (itemConfig == null) continue;

                int slot = itemConfig.getInt("slot");
                String materialName = itemConfig.getString("material", "BOOK");
                Material material = Material.matchMaterial(materialName);

                if (material == null) {
                    sendConsoleMessage(Msg_Command_JobsSelect_InvalidMaterial + jobKey);
                    continue;
                }

                String displayName = itemConfig.getString("display-name", "§aProfissão");
                List<String> lore = itemConfig.getStringList("lore");

                ItemStack item = new ItemStack(material);
                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(displayName);
                    meta.setLore(lore);

                    meta.getPersistentDataContainer().set(
                            new NamespacedKey(Jobs.getInstance(), "profession"),
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


     // Preenche os espaços vazios com um item.

    private static void fillPlaceholderItems(Inventory inv) {
        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = placeholder.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("");
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
        // Verifica se o inventário clicado NÃO é a GUI de profissões
        String guiTitle = getGuiConfigYML().getConfig().getString("gui.title", "Seleção de Profissões");
        if (!e.getView().getTitle().equals(guiTitle)) {
            return;
        }

        e.setCancelled(true); // Cancela o clique (não permite mover itens)

        // Valida o item NÃO clicado
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR || !clickedItem.hasItemMeta()) {
            return;
        }

        ItemMeta meta = clickedItem.getItemMeta();
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();

        // Verifica se o item clicado NÃO possui a tag "profession"
        NamespacedKey professionKey = new NamespacedKey(Jobs.getInstance(), "profession");
        if (!dataContainer.has(professionKey, PersistentDataType.STRING)) {
            return;
        }

        // Obtém a chave fixa da profissão
        String jobKey = dataContainer.get(professionKey, PersistentDataType.STRING);
        if (jobKey == null) return; // Evitar erro inesperado

        Player player = (Player) e.getWhoClicked();

        // Salva a profissão no banco de dados usando a chave fixa
        SqlJobManager jobManager = new SqlJobManager(Jobs.getInstance().getConnection());
        jobManager.setPlayerProfession(player.getUniqueId(), player.getName(), jobKey);

        // Envia mensagem ao jogador e fecha o inventário
        sendPlayerMessage(player, Msg_Command_JobsSelect_SelectProfission + jobKey);
        player.closeInventory();
    }
}