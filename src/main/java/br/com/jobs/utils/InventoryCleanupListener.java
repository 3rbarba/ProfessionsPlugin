package br.com.jobs.utils;
import br.com.jobs.Jobs;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;

import java.util.logging.Level;

public class InventoryCleanupListener implements Listener {

    private BukkitTask cleanupTask;
    private static final long CLEANUP_INTERVAL_TICKS = 20L;
    public void startPeriodicCleanup(Jobs plugin) {
        if (cleanupTask != null) {
            cleanupTask.cancel();
        }

        cleanupTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            plugin.getLogger().log(Level.FINE, "Executando limpeza de itens de GUI para todos os jogadores online");
            cleanAllPlayersInventories();
        }, CLEANUP_INTERVAL_TICKS, CLEANUP_INTERVAL_TICKS);
    }
    private void cleanAllPlayersInventories() {
        int totalPlayers = Bukkit.getOnlinePlayers().size();
        int playersWithItems = 0;

        if (totalPlayers == 0) {
            return; // Não há jogadores online
        }

        Jobs.getInstance().getLogger().log(Level.FINE,
            "Iniciando limpeza periódica de itens de GUI para " + totalPlayers + " jogadores");

        for (Player player : Bukkit.getOnlinePlayers()) {
            int initialSlots = countGUIItems(player);

            if (initialSlots > 0) {
                removeGUIItemsFromInventory(player);
                playersWithItems++;

                // Verifica se a limpeza foi completa
                int remainingSlots = countGUIItems(player);
                if (remainingSlots > 0) {
                    Jobs.getInstance().getLogger().log(Level.WARNING,
                        "Ainda restam " + remainingSlots + " itens de GUI no inventário de " + player.getName());

                    // Registra informações detalhadas sobre os itens remanescentes para depuração
                    if (Jobs.getInstance().getLogger().isLoggable(Level.FINE)) {
                        ItemStack[] contents = player.getInventory().getContents();
                        for (int i = 0; i < contents.length; i++) {
                            ItemStack item = contents[i];
                            if (item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(ManagerGUI.key, PersistentDataType.STRING)) {
                                String value = item.getItemMeta().getPersistentDataContainer().get(ManagerGUI.key, PersistentDataType.STRING);
                                Jobs.getInstance().getLogger().log(Level.FINE,
                                    "Item não removido - Slot: " + i + ", Material: " + item.getType() +
                                    ", Nome: " + (item.hasItemMeta() && item.getItemMeta().hasDisplayName() ?
                                                item.getItemMeta().getDisplayName() : "[sem nome]") +
                                    ", Valor da chave: " + value);
                            }
                        }
                    }
                }
            }
        }

        if (playersWithItems > 0) {
            Jobs.getInstance().getLogger().log(Level.FINE,
                "Limpeza periódica concluída: " + playersWithItems + " jogadores tinham itens de GUI");
        }

        if (playersWithItems > 0) {
            Jobs.getInstance().getLogger().log(Level.FINE,
                "Limpeza periódica concluída: " + playersWithItems + " jogadores tinham itens de GUI");
        }
    }
    private int countGUIItems(Player player) {
        ItemStack[] contents = player.getInventory().getContents();
        int count = 0;

        for (ItemStack item : contents) {
            if (item != null && isGUIItem(item)) {
                count++;
            }
        }

        return count;
    }
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getPlayer();
        removeGUIItemsFromInventory(player);
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        removeGUIItemsFromInventory(event.getPlayer());
    }
    @EventHandler
    public void onPlayerSwitchItem(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());

        if (newItem != null && newItem.hasItemMeta()) {
            if (Jobs.getInstance().getLogger().isLoggable(Level.FINE)) {
                ItemMeta meta = newItem.getItemMeta();
                PersistentDataContainer container = meta.getPersistentDataContainer();
                if (container.has(ManagerGUI.key, PersistentDataType.STRING)) {
                    String value = container.get(ManagerGUI.key, PersistentDataType.STRING);
                    Jobs.getInstance().getLogger().log(Level.FINE,
                        "Jogador trocou para item com key " + ManagerGUI.key.getKey() + ", valor: " + value);
                }
            }

            if (isGUIItem(newItem)) {
                removeGUIItemsFromInventory(player);
            }
        }
    }
    public static void removeGUIItemsFromInventory(Player player) {
        ItemStack[] contents = player.getInventory().getContents();
        boolean inventoryChanged = false;
        int itemsRemoved = 0;
        StringBuilder removedItemsInfo = new StringBuilder();

        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item != null && isGUIItem(item)) {
                // Registra informações do item para depuração
                if (Jobs.getInstance().getLogger().isLoggable(Level.FINE)) {
                    ItemMeta meta = item.getItemMeta();
                    if (meta != null && meta.getPersistentDataContainer().has(ManagerGUI.key, PersistentDataType.STRING)) {
                        String value = meta.getPersistentDataContainer().get(ManagerGUI.key, PersistentDataType.STRING);
                        removedItemsInfo.append("\n  - Slot ").append(i)
                                        .append(", Material: ").append(item.getType())
                                        .append(", Valor da chave: ").append(value);
                    } else {
                        removedItemsInfo.append("\n  - Slot ").append(i)
                                        .append(", Material: ").append(item.getType())
                                        .append(", Sem valor de chave");
                    }
                }

                player.getInventory().setItem(i, null);
                inventoryChanged = true;
                itemsRemoved++;
            }
        }

        if (inventoryChanged) {
            player.updateInventory();
            // Log para depuração em nível FINE
            if (itemsRemoved > 0) {
                Jobs.getInstance().getLogger().log(Level.FINE,
                    "Removidos " + itemsRemoved + " itens de GUI do inventário de " + player.getName() + removedItemsInfo);
            }
        }
    }
    public static boolean isGUIItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }

        PersistentDataContainer container = meta.getPersistentDataContainer();

        // Verifica apenas se a chave existe, sem verificar o valor
        // Isso detecta tanto itens com valor "profession_gui" quanto itens com valores de jobKey
        if (container.has(ManagerGUI.key, PersistentDataType.STRING)) {
            // Para depuração, registra o valor da chave quando em nível de log apropriado
            if (Jobs.getInstance().getLogger().isLoggable(Level.FINEST)) {
                String value = container.get(ManagerGUI.key, PersistentDataType.STRING);
                Jobs.getInstance().getLogger().log(Level.FINEST,
                    "Item GUI detectado com chave: " + ManagerGUI.key.getKey() + ", valor: " + value);
            }
            return true;
        }

        // Também verifica características específicas de itens de preenchimento
        if (item.getType() == Material.BLACK_STAINED_GLASS_PANE) {
            String displayName = meta.getDisplayName();
            return displayName == null || displayName.trim().isEmpty() || displayName.equals(" ");
        }
        return false;
    }
}
