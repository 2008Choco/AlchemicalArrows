package wtf.choco.arrows.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRecipeDiscoverEvent;
import org.jetbrains.annotations.NotNull;

public class ArrowRecipeDiscoverListener implements Listener {

    private final List<NamespacedKey> recipeKeys = new ArrayList<>();

    @EventHandler
    public void onDiscoverArrowRecipe(PlayerRecipeDiscoverEvent event) {
        if (!event.getRecipe().toString().equalsIgnoreCase("minecraft:arrow")) return;
        event.getPlayer().discoverRecipes(recipeKeys);
    }

    public void includeRecipeKey(@NotNull NamespacedKey key) {
    	if (recipeKeys.contains(key)) return;
        this.recipeKeys.add(key);
    }

    public void clearRecipeKeys() {
        this.recipeKeys.clear();
    }

}