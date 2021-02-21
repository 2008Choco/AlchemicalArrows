package wtf.choco.arrows.listeners;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRecipeDiscoverEvent;
import org.jetbrains.annotations.NotNull;

public final class ArrowRecipeDiscoverListener implements Listener {

    private static final NamespacedKey ARROW_KEY = Material.ARROW.getKey();

    private final Set<NamespacedKey> recipeKeys = new HashSet<>();

    @EventHandler
    public void onDiscoverArrowRecipe(PlayerRecipeDiscoverEvent event) {
        if (!ARROW_KEY.equals(event.getRecipe())) {
            return;
        }

        event.getPlayer().discoverRecipes(recipeKeys);
    }

    public void includeRecipeKey(@NotNull NamespacedKey key) {
        this.recipeKeys.add(key);
    }

    public void clearRecipeKeys() {
        this.recipeKeys.clear();
    }

}
