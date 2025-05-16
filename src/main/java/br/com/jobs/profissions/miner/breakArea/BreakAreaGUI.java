package br.com.jobs.profissions.miner.breakArea;
import br.com.jobs.utils.ManagerGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static br.com.jobs.utils.TextUtils.*;

//todo falta as mensagens
public class BreakAreaGUI implements Listener {
    private static final String gui = ChatColor.DARK_GRAY + "Quebrar em área";
    private static final ManagerGUI managerGUI = new ManagerGUI();
    public static String breakAreaSize = "2x1";
    public static boolean enableBreakArea = true;
    public BreakAreaGUI() {
    }

    public void openGUI(Player player) {
        List<String> loreEnableBreakArea = new ArrayList<>();
            loreEnableBreakArea.add("§eClique para ativar ou desativar");
            loreEnableBreakArea.add("§eA função de quebrar na horizontal");
        Inventory inv = Bukkit.createInventory(null, 27, BreakAreaGUI.gui);

        inv.setItem(10, managerGUI.createMenuItem(Material.DISPENSER, "§7Quebrar Área", loreBreakArea()));
        inv.setItem(13, managerGUI.createMenuItem(Material.IRON_PICKAXE, "§6Quebrar na Horizontal", loreEnableBreakArea));
        inv.setItem(16, managerGUI.createMenuItem(Material.BARRIER, "§cFechar", null));
        player.openInventory(inv);
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {return;}
        if (e.getView().getTitle().equals(gui)) {
            if (e.getSlot() == 10 && e.getCurrentItem().getType() == Material.DISPENSER) {
                if (breakAreaSize.equals("2x1")) { // Deixar como if para por hasPermission depois
                    breakAreaSize = "3x2";
                } else if (breakAreaSize.equals("3x2")) {
                    breakAreaSize = "3x3";
                } else if (breakAreaSize.equals("3x3")) {
                    breakAreaSize = "5x5";
                } else if (breakAreaSize.equals("5x5")) {
                    breakAreaSize = "2x1";
                }
                e.getInventory().setItem(10, managerGUI.createMenuItem(Material.DISPENSER, "§7Quebrar Área", loreBreakArea()));
                e.setCancelled(true);
                return;
            }
            if (e.getSlot() == 13 && e.getCurrentItem().getType() == Material.IRON_PICKAXE) {
                if (enableBreakArea) {
                    enableBreakArea = false;
                   sendMessageActionbar(player, "§cQuebrar na horizontal desativado");
                }else {
                    enableBreakArea = true;
                    sendMessageActionbar(player, "§aQuebrar na horizontal ativado");
                }
            }
            player.closeInventory();
        }
    }
    private List<String> loreBreakArea() {
        List<String> lore = Arrays.asList(new String[5]);
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
