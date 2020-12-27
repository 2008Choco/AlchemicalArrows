package wtf.choco.arrows.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import wtf.choco.alchema.api.event.CauldronRecipeRegisterEvent;
import wtf.choco.alchema.crafting.CauldronRecipe;
import wtf.choco.alchema.crafting.CauldronRecipeRegistry;

public final class AlchemaRecipeIntegrationListener implements Listener {

    private final List<CauldronRecipe> recipes = new ArrayList<>();

    @EventHandler
    private void onRegisterAlchemaRecipe(CauldronRecipeRegisterEvent event) {
        CauldronRecipeRegistry recipeRegistry = event.getRecipeRegistry();
        this.recipes.forEach(recipeRegistry::registerCauldronRecipe);
    }

    public void addRecipe(CauldronRecipe recipe) {
        this.recipes.add(recipe);
    }

    public void clearRecipes() {
        this.recipes.clear();
    }

}
