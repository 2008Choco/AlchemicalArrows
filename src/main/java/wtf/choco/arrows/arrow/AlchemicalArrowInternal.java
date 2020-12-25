package wtf.choco.arrows.arrow;

import java.util.stream.Collectors;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.utils.CommandUtil;
import wtf.choco.arrows.utils.ItemBuilder;

// For internal use. More convenient arrow construction for configuration-based arrows
abstract class AlchemicalArrowInternal extends AlchemicalArrow {

    private final NamespacedKey key;
    private final ItemStack item;
    private final String name;

    protected AlchemicalArrowInternal(AlchemicalArrows plugin, String key, String defaultName, int defaultModelData) {
    	FileConfiguration config = plugin.getConfig();

        this.key = new NamespacedKey(plugin, key.toLowerCase());
        String nameRaw = config.getString("Arrow." + key + ".Item.DisplayName", defaultName);
        this.name = (nameRaw != null) ? ChatColor.translateAlternateColorCodes('&', nameRaw) : nameRaw; // Never null anyways

        // Update arrow item colour
        Color colour = null;
        String colourRaw = config.getString("Arrow." + key + ".Colour");
        if (colourRaw != null) {
           String[] rgbSplitRaw = colourRaw.split("\\s*,\\s*");
           if (rgbSplitRaw.length != 3) {
                plugin.getLogger().warning("Attempted to set colour with invalid RGB. Expected 'r,g,b', received '" + colourRaw + "'. Arrow = " + key + ". Ignoring...");
            }

           int r = CommandUtil.clamp(NumberUtils.toInt(rgbSplitRaw[0], Integer.MIN_VALUE), 0, 255);
           int g = CommandUtil.clamp(NumberUtils.toInt(rgbSplitRaw[1], Integer.MIN_VALUE), 0, 255);
           int b = CommandUtil.clamp(NumberUtils.toInt(rgbSplitRaw[2], Integer.MIN_VALUE), 0, 255);

           colour = Color.fromRGB(r, g, b);
        }

        Color colourFinal = colour; // Stupid lambdas -,-
        ItemBuilder itemBuilder = ItemBuilder.of(colour != null ? Material.TIPPED_ARROW : Material.ARROW)
                .name(name)
                .lore(config.getStringList("Arrow." + key + ".Item.Lore").stream()
                    .map(s -> ChatColor.translateAlternateColorCodes('&', s))
                    .collect(Collectors.toList())
                )
                .modelData(config.getInt("Arrow." + key + ".Item.CustomModelData", defaultModelData));

        if (colour != null) {
            itemBuilder.specific(PotionMeta.class, m -> {
                m.setColor(colourFinal);
                m.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            });
        }

        this.item = itemBuilder.build();
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

}