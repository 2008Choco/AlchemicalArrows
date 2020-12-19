package wtf.choco.arrows.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.registry.ArrowRegistry;

/**
 * @deprecated This class exists only to convert from legacy arrows to the new arrows
 * with NamespacedKeys under the PersistentDataContainer of the ItemStack. This class
 * should be removed at a later date.
 */
@Deprecated
public final class LegacyArrowConvertionListener implements Listener {

    private final AlchemicalArrows plugin;

    public LegacyArrowConvertionListener(AlchemicalArrows plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        convertInventory(event.getPlayer().getInventory());
    }

    @EventHandler
    private void onOpenInventory(InventoryOpenEvent event) {
        convertInventory(event.getInventory());
    }

    private void convertInventory(Inventory inventory) {
        ItemStack[] contents = inventory.getContents();
        ArrowRegistry registry = plugin.getArrowRegistry();

        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item == null) {
                continue;
            }

            AlchemicalArrow arrow = get(registry, item);
            if (arrow == null) {
                continue;
            }

            contents[i] = arrow.createItemStack(item.getAmount());
        }

        inventory.setContents(contents);
    }

    // This will compare against the legacy item. We don't WANT to use AlchemicalArrow#matchesItem() here.
    private AlchemicalArrow get(ArrowRegistry registry, ItemStack item) {
        for (AlchemicalArrow arrow : registry.getRegisteredArrows()) {
            if (arrow.getItem().isSimilar(item)) {
                return arrow;
            }
        }

        return null;
    }

}
