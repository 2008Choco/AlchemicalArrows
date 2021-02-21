package wtf.choco.arrows.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.util.AAConstants;

public final class CraftingPermissionListener implements Listener {

    private final AlchemicalArrows plugin;

    public CraftingPermissionListener(AlchemicalArrows plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPrepareCraftingRecipe(PrepareItemCraftEvent event) {
        ItemStack item = event.getInventory().getResult();
        if (item == null || event.getViewers().isEmpty()) {
            return;
        }

        AlchemicalArrow type = plugin.getArrowRegistry().get(item);
        if (type == null || !type.getClass().getPackage().getName().startsWith("me.choco.arrows.arrow")) {
            return;
        }

        if (!AAConstants.PERMISSION_CRAFT_ARROW_PREDICATE.test(event.getViewers().get(0), type.getKey().getKey())) {
            event.getInventory().setResult(null);
        }
    }

}
