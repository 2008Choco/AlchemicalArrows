package wtf.choco.arrows.arrow;

import java.util.stream.Collectors;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.util.ItemBuilder;
import wtf.choco.arrows.util.MathUtil;

// For internal use. More convenient arrow construction for configuration-based arrows
public abstract class ConfigurableAlchemicalArrow extends AlchemicalArrow {

    private String name;
    private ItemStack item;

    private final String defaultName;
    private final int defaultModelData;

    private final AlchemicalArrows plugin;
    private final NamespacedKey key;

    protected ConfigurableAlchemicalArrow(AlchemicalArrows plugin, String key, String defaultName, int defaultModelData) {
        this.plugin = plugin;
        this.key = new NamespacedKey(plugin, key.toLowerCase());

        this.defaultName = defaultName;
        this.defaultModelData = defaultModelData;

        this.reload();
    }

    @Override
    public NamespacedKey getKey() {
        return key;
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public ItemStack getItem() {
        return item.clone();
    }

    @Override
    public boolean onShootFromPlayer(AlchemicalArrowEntity arrow, Player player) {
        return player.hasPermission("arrows.shoot." + key.getKey());
    }

    public void reload() {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection arrowSection = config.getConfigurationSection("Arrow." + key);

        String nameRaw = arrowSection.getString("Item.DisplayName", defaultName);
        this.name = (nameRaw != null) ? ChatColor.translateAlternateColorCodes('&', nameRaw) : nameRaw; // Never null anyways

        // Update arrow item colour
        Color colour = null;
        String colourRaw = arrowSection.getString("Colour");
        if (colourRaw != null) {
           String[] rgbSplitRaw = colourRaw.split("\\s*,\\s*");
           if (rgbSplitRaw.length != 3) {
               this.plugin.getLogger().warning("Attempted to set colour with invalid RGB. Expected 'r,g,b', received '" + colourRaw + "'. Arrow = " + key + ". Ignoring...");
           }

           int r = MathUtil.clamp(NumberUtils.toInt(rgbSplitRaw[0], 0), 0, 255);
           int g = MathUtil.clamp(NumberUtils.toInt(rgbSplitRaw[1], 0), 0, 255);
           int b = MathUtil.clamp(NumberUtils.toInt(rgbSplitRaw[2], 0), 0, 255);

           colour = Color.fromRGB(r, g, b);
        }

        Color colourFinal = colour; // Stupid lambdas -,-
        ItemBuilder itemBuilder = ItemBuilder.of(colour != null ? Material.TIPPED_ARROW : Material.ARROW)
            .name(name)
            .lore(arrowSection.getStringList("Item.Lore").stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .collect(Collectors.toList())
            )
            .modelData(arrowSection.getInt("Item.CustomModelData", defaultModelData));

        if (colour != null) {
            itemBuilder.specific(PotionMeta.class, meta -> {
                meta.setColor(colourFinal);
                meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            });
        }

        this.item = itemBuilder.build();
    }

}
