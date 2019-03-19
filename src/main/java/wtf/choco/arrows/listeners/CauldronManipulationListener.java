package wtf.choco.arrows.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.crafting.AlchemicalCauldron;
import wtf.choco.arrows.registry.CauldronManager;

public class CauldronManipulationListener implements Listener {

	private final AlchemicalArrows plugin;

	public CauldronManipulationListener(@NotNull AlchemicalArrows plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlaceCauldron(BlockPlaceEvent event) {
		Block block = event.getBlock();
		if (block.getType() != Material.CAULDRON || !plugin.getConfig().getBoolean("Crafting.CauldronCrafting")) return;

		this.plugin.getCauldronManager().addAlchemicalCauldron(new AlchemicalCauldron(block));
	}

	@EventHandler
	public void onDestroyCauldron(BlockBreakEvent event) {
		Block block = event.getBlock();
		if (block.getType() != Material.CAULDRON || !plugin.getConfig().getBoolean("Crafting.CauldronCrafting")) return;

		CauldronManager manager = plugin.getCauldronManager();
		AlchemicalCauldron cauldron = manager.getAlchemicalCauldron(block);
		if (cauldron == null) return;

		manager.removeAlchemicalCauldron(cauldron);

		// Drop cauldron ingredients, if any
		if (!cauldron.hasIngredients()) return;

		World world = block.getWorld();
		Location location = block.getLocation().add(0.5, 0.25, 0.5);
		cauldron.getIngredients().forEach((m, a) -> world.dropItem(location, new ItemStack(m, a)));
		cauldron.clearIngredients();
	}

}