package br.com.jobs.profissions;
import br.com.jobs.Jobs;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import static br.com.jobs.utils.TextUtils.*;
import static br.com.jobs.utils.messages.MessagesHandle.*;

public class GuiConfigYML {
    private final File file;
    private FileConfiguration fileConfiguration;

    public GuiConfigYML() {
        file = new File(Jobs.getInstance().getDataFolder(), "ConfigGUI.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                warnLoggers(Msg_Command_ReloadConfigGuiYML_Create_Error);
                e.printStackTrace();
            }
            infoLoggers(Msg_Command_ReloadConfigGuiYML_Create);
        }

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        loadConfig();
    }

    public FileConfiguration getConfig() {
        return fileConfiguration;
    }


    public void saveConfig() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            warnLoggers(Msg_Command_ReloadConfigGuiYML_Error);
            e.printStackTrace();
        }
    }


    public void reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        infoLoggers(Msg_ReloadConfigGuiYML);
    }


    private void loadConfig() {
        FileConfiguration config = getConfig();

        if (config.getConfigurationSection("gui") == null) {
            ConfigurationSection guiSection = config.createSection("gui");
            guiSection.set("title", "Seleção de Profissões");

            ConfigurationSection items = guiSection.createSection("items");

            createItem(items, "archer", 12, "BOW", "§aArqueiro", "§7Uma profissão focada em ataques de longa distância.", "§7Especialista em combate com arco.");
            createItem(items, "enchanter", 13, "ENCHANTING_TABLE", "§bEncantador", "§7Você é o mestre da magia.", "§7Encante itens como nenhum outro.");
            createItem(items, "lumberjack", 14, "IRON_AXE", "§6Lenhador", "§7Profissão ideal para cortar árvores", "§7Ajude a coletar madeira para sobrevivência.");
            createItem(items, "farmer", 20, "WHEAT", "§2Fazendeiro", "§7Agricultura para todos.", "§7Cultive e colha para sustentar seu time.");
            createItem(items, "fisherman", 21, "FISHING_ROD", "§3Pescador", "§7A pesca é uma arte.", "§7Conquiste os mares.");
            createItem(items, "miner", 22, "DIAMOND_PICKAXE", "§9Mineiro", "§7Subterrâneo, o reino dos mineiros.", "§7Encontre minerais valiosos.");
            createItem(items, "tank", 23, "DIAMOND_CHESTPLATE", "§eTanque", "§7Sustente o dano pelo grupo.", "§7Perfeito para defesa.");
            createItem(items, "agility", 24, "FEATHER", "§fAgilidade", "§7Movimento rápido é sua vantagem.", "§7Evite ataques com rapidez.");
            createItem(items, "warrior", 30, "IRON_SWORD", "§cGuerreiro", "§7Lute de frente.", "§7Profissão para combatentes.");
            createItem(items, "alchemist", 31, "BREWING_STAND", "§5Alquimista", "§7Misture líquidos misteriosos.", "§7Proporcione novas possibilidades ao time.");
            createItem(items, "excavator", 32, "GOLDEN_SHOVEL", "§6Escavador", "§7Remova areia e cascalho rápido.", "§7Descubra tesouros enterrados.");

            saveConfig();
        }
    }


    private void createItem(ConfigurationSection parent, String key, int slot, String material, String displayName, String... lore) {
        ConfigurationSection itemSection = parent.createSection(key);
        itemSection.set("slot", slot);
        itemSection.set("material", material);
        itemSection.set("display_name", displayName);
        itemSection.set("lore", lore);
    }
}