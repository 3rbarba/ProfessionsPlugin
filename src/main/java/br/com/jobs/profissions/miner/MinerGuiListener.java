package br.com.jobs.profissions.miner;
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

        inv.setItem(10, managerGUI.createMenuItem(Material.LEVER, "§aQuebrar Área", loreBreakArea));
        inv.setItem(13, managerGUI.createMenuItem(Material.IRON_PICKAXE, "§6Escolher Nível", loreBreakArea));
        inv.setItem(16, managerGUI.createMenuItem(Material.BARRIER, "§cFechar", loreBreakArea));
        player.openInventory(inv);

    }

    static boolean BreakArea;
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
            return;
        }
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals(gui)) {
            if (e.getSlot() == 1 && e.getCurrentItem().getType() == Material.LEVER) {
                /* todo arrumar isso */
                if (e.isLeftClick()) {
                    if (!BreakArea) {
                        sendMessageActionbar(player, "§2Quebrar em área ativado");
                        BreakArea = true;
                    } else {
                        BreakArea = false;
                        sendMessageActionbar(player, "§cQuebrar em área desativado");
                    }
                } else if (e.isRightClick()) {
                    BreakAreaGUI breakAreaGUI = new BreakAreaGUI();
                    breakAreaGUI.openGUI(player);
                    return;
                }
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