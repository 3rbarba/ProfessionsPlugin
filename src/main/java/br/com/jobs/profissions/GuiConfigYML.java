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

            createItem(items, "archer", 12, "BOW", "&aArqueiro", "Uma profissão focada em ataques de longa distância.", "Especialista em combate com arco.");
            createItem(items, "enchanter", 13, "ENCHANTING_TABLE", "&bEncantador", "Você é o mestre da magia.", "Encante itens como nenhum outro.");
            createItem(items, "lumberjack", 14, "IRON_AXE", "&6Lenhador", "Profissão ideal para cortar árvores", "Ajude a coletar madeira para sobrevivência.");
            createItem(items, "farmer", 20, "WHEAT", "&2Fazendeiro", "Agricultura para todos.", "Cultive e colha para sustentar seu time.");
            createItem(items, "fisherman", 21, "FISHING_ROD", "&3Pescador", "A pesca é uma arte.", "Conquiste os mares.");
            createItem(items, "miner", 22, "DIAMOND_PICKAXE", "&9Mineiro", "Subterrâneo, o reino dos mineiros.", "Encontre minerais valiosos.");
            createItem(items, "tank", 23, "DIAMOND_CHESTPLATE", "&eTanque", "Sustente o dano pelo grupo.", "Perfeito para defesa.");
            createItem(items, "agility", 24, "FEATHER", "&fAgilidade", "Movimento rápido é sua vantagem.", "Evite ataques com rapidez.");
            createItem(items, "warrior", 30, "IRON_SWORD", "&cGuerreiro", "Lute de frente.", "Profissão para combatentes.");
            createItem(items, "alchemist", 31, "BREWING_STAND", "&5Alquimista", "&7Misture líquidos misteriosos.", "Proporcione novas possibilidades ao time.");
            createItem(items, "excavator", 32, "GOLDEN_SHOVEL", "&6Escavador", "Remova areia e cascalho rápido.", "Descubra tesouros enterrados.");
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