package wtf.choco.arrows.arrow;

import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.utils.ItemBuilder;

// For internal use. More convenient arrow construction for configuration-based arrows
abstract class AlchemicalArrowAbstract extends AlchemicalArrow {

    private final NamespacedKey key;
    private final ItemStack item;
    private final String name;

    protected AlchemicalArrowAbstract(AlchemicalArrows plugin, String key, String defaultName) {
    	FileConfiguration config = plugin.getConfig();

        this.key = new NamespacedKey(plugin, key.toLowerCase());
        String nameRaw = config.getString("Arrow." + key + ".Item.DisplayName", defaultName);
        this.name = (nameRaw != null) ? ChatColor.translateAlternateColorCodes('&', nameRaw) : nameRaw; // Never null anyways
        this.item = ItemBuilder.of(Material.ARROW)
                .name(name)
                .lore(config.getStringList("Arrow." + key + ".Item.Lore").stream()
                    .map(s -> ChatColor.translateAlternateColorCodes('&', s))
                    .collect(Collectors.toList())
                ).build();
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