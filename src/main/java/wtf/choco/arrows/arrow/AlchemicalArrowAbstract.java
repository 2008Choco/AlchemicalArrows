package wtf.choco.arrows.arrow;

import java.util.List;
import java.util.function.Function;
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

    protected AlchemicalArrowAbstract(AlchemicalArrows plugin, String key, Function<FileConfiguration, String> nameFunction, Function<FileConfiguration, List<String>> loreFunction) {
        this.key = new NamespacedKey(plugin, key);
        this.name = ChatColor.translateAlternateColorCodes('&', nameFunction.apply(plugin.getConfig()));
        this.item = ItemBuilder.of(Material.ARROW)
                .name(name)
                .lore(loreFunction.apply(plugin.getConfig()).stream()
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
