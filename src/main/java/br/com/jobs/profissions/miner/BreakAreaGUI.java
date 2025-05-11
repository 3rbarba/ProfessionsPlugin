package br.com.jobs.profissions.miner;
import br.com.jobs.utils.ManagerGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import java.util.Arrays;
import java.util.List;
//todo falta as mensgaens
public class BreakAreaGUI implements Listener {
    private static final String gui = ChatColor.DARK_GRAY + "Quebrar em área";
    private static final ManagerGUI managerGUI = new ManagerGUI();
    static String breakAreaSize = "2x1";

    public BreakAreaGUI() {
    }

    public void openGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, BreakAreaGUI.gui);

        inv.setItem(10, managerGUI.createMenuItem(Material.TORCH, "§7Quebrar Área", loreBreakArea()));
        inv.setItem(13, managerGUI.createMenuItem(Material.IRON_PICKAXE, "§6Escolher Nível", loreBreakArea()));
        inv.setItem(16, managerGUI.createMenuItem(Material.BARRIER, "§cFechar", loreBreakArea()));
        player.openInventory(inv);
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {return;}
        if (e.getView().getTitle().equals(gui)) {
            if (e.getSlot() == 10 && e.getCurrentItem().getType() == Material.TORCH) {
                if (breakAreaSize.equals("2x1")) { // Deixar com if para por hasPermission depois
                    breakAreaSize = "3x2";
                } else if (breakAreaSize.equals("3x2")) {
                    breakAreaSize = "3x3";
                } else if (breakAreaSize.equals("3x3")) {
                    breakAreaSize = "5x5";
                } else if (breakAreaSize.equals("5x5")) {
                    breakAreaSize = "2x1";
                }
                e.getInventory().setItem(10, managerGUI.createMenuItem(Material.TORCH, "§7Quebrar Área", loreBreakArea()));
                e.setCancelled(true);
                return;
            }
            player.closeInventory();
        }
    }
    private List<String> loreBreakArea() {
        List<String> lore = Arrays.asList(new String[5]); // Inicializando com 5 espaços vazios
        lore.set(0, "§eSelecione");
        lore.set(1, "§72x1");
        lore.set(2, "§73x2");
        lore.set(3, "§73x3");
        lore.set(4, "§75x5");
        if (breakAreaSize.equals("2x1")) {
            lore.set(1, "§22x1");
            lore.set(2, "§73x2");
            lore.set(3, "§73x3");
            lore.set(4, "§75x5");
        }else if (breakAreaSize.equals("3x2")) {
            lore.set(1, "§72x1");
            lore.set(2, "§23x2");
        } else if (breakAreaSize.equals("3x3")) {
            lore.set(2, "§73x2");
            lore.set(3, "§23x3");
        } else if (breakAreaSize.equals("5x5")) {
            lore.set(3, "§73x3");
            lore.set(4, "§25x5");
        }
        return lore;
    }


}
