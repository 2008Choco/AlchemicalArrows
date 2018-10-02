package wtf.choco.arrows.events;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRecipeDiscoverEvent;

public class ArrowRecipeDiscoverListener implements Listener {
	
	private final Set<NamespacedKey> alchemicalArrowRecipeKeys = new HashSet<>();
	
	@EventHandler
	public void onDiscoverArrowRecipe(PlayerRecipeDiscoverEvent event) {
		if (!event.getRecipe().toString().equalsIgnoreCase("minecraft:arrow")) return;
		event.getPlayer().discoverRecipes(alchemicalArrowRecipeKeys);
	}
	
	public void includeRecipeKey(NamespacedKey key) {
		this.alchemicalArrowRecipeKeys.add(key);
	}
	
	public void clearRecipeKeys() {
		this.alchemicalArrowRecipeKeys.clear();
	}
	
}