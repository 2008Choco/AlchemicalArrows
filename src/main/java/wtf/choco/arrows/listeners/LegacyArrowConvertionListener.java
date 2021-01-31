package wtf.choco.arrows.listeners;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
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
    private void onClickItem(InventoryClickEvent event) {
        event.setCurrentItem(convertItemStack(event.getCurrentItem()));
    }

    @EventHandler
    private void onPickupItem(EntityPickupItemEvent event) {
        Item item = event.getItem();
        ItemStack stack = item.getItemStack();
        ItemStack converted = convertItemStack(stack);

        if (stack != converted) {
            item.setItemStack(converted);
        }
    }

    private ItemStack convertItemStack(ItemStack item) {
        if (item == null) {
            return null;
        }

        ArrowRegistry registry = plugin.getArrowRegistry();
        AlchemicalArrow arrow = get(registry, item);
        if (arrow == null) {
            return item;
        }

        return arrow.createItemStack(item.getAmount());
    }

    private void convertInventory(Inventory inventory) {
        ItemStack[] contents = inventory.getContents();

        for (int i = 0; i < contents.length; i++) {
            contents[i] = convertItemStack(contents[i]);
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
