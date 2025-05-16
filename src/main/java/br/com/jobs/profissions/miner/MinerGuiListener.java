package br.com.jobs.profissions.miner;
import br.com.jobs.profissions.miner.breakArea.BreakAreaGUI;
import br.com.jobs.utils.ManagerGUI;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import java.util.ArrayList;
import java.util.List;
import static br.com.jobs.utils.TextUtils.sendMessageActionbar;

//todo falta as mensagens
public class MinerGuiListener implements Listener {
    private static final ManagerGUI managerGUI = new ManagerGUI();
    private final String gui = ChatColor.DARK_GRAY + "Minerador";
    private final PickaxeObject MinerPick;

    public MinerGuiListener() {
        this.MinerPick = new PickaxeObject();
    }

    public void openGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, gui);

        List<String> loreBreakArea = new ArrayList<>();
            loreBreakArea.add("§eClique com o esquerdo para ativar");
            loreBreakArea.add("§eClique com o direito para abrir o menu de seleção");

        inv.setItem(10, managerGUI.createMenuItem(Material.LEVER, "§aQuebrar em Área", loreBreakArea));
        inv.setItem(12, managerGUI.createMenuItem(Material.TORCH, "§6Vagalume", null));
        inv.setItem(14, managerGUI.createMenuItem(Material.COMPOSTER, "§6Blacklist", null));
        inv.setItem(16, managerGUI.createMenuItem(Material.REDSTONE_TORCH, "§cVeinRadar", null));
        inv.setItem(26, managerGUI.createMenuItem(Material.BARRIER, "§cFechar", null));
        player.openInventory(inv);
    }
    public static boolean BreakBlocks = false;
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
            return;
        }
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals(gui)) {
            if (e.getSlot() == 10 && e.getCurrentItem().getType() == Material.LEVER) {
                if (e.isLeftClick()) {
                    if (BreakBlocks) {
                        sendMessageActionbar(player, "§2Quebrar em área ativado");
                        BreakBlocks = true;
                    } else {
                        sendMessageActionbar(player, "§cQuebrar em área desativado");
                        BreakBlocks = false;
                    }
                } else if (e.isRightClick()) {
                    BreakAreaGUI breakAreaGUI = new BreakAreaGUI();
                    breakAreaGUI.openGUI(player);
                    return;
                }
            }
            if (e.getSlot() == 12 && e.getCurrentItem().getType() == Material.TORCH) {

            }
            if (e.getSlot() == 14 && e.getCurrentItem().getType() == Material.COMPOSTER) {

            }
            if (e.getSlot() == 16 && e.getCurrentItem().getType() == Material.REDSTONE_TORCH) {

            }
            e.setCancelled(true);
            player.closeInventory();
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (MinerPick.isPickMiner(itemInHand)) {
            if (player.isSneaking() && event.getAction().toString().contains("RIGHT_CLICK")) {
                openGUI(player);
                event.setCancelled(true);
            }
        }
    }
}