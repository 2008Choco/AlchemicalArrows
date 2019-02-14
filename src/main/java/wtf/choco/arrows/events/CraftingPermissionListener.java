package wtf.choco.arrows.events;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.registry.ArrowRegistry;

public class CraftingPermissionListener implements Listener {

	@EventHandler
	public void onPrepareCraftingRecipe(PrepareItemCraftEvent event){
		ItemStack item = event.getInventory().getResult();
		if (item == null || event.getViewers().isEmpty()) return;

		HumanEntity player = event.getViewers().get(0);
		CraftingInventory inventory = event.getInventory();

		AlchemicalArrow type = ArrowRegistry.getCustomArrow(item);
		if (type == null || !type.getClass().getPackage().getName().startsWith("me.choco.arrows.arrow")) return;

		if (!player.hasPermission("arrows.craft." + type.getKey().getKey())) {
			inventory.setResult(null);
		}
	}

}