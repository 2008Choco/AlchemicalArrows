package wtf.choco.arrows.integration.alchema;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import wtf.choco.alchema.api.event.CauldronRecipeRegisterEvent;
import wtf.choco.alchema.crafting.CauldronRecipeRegistry;

public final class AlchemaRecipeIntegrationListener implements Listener {

    private final PluginIntegrationAlchema integration;

    AlchemaRecipeIntegrationListener(@NotNull PluginIntegrationAlchema integration) {
        this.integration = integration;
    }

    @EventHandler
    private void onRegisterAlchemaRecipe(CauldronRecipeRegisterEvent event) {
        CauldronRecipeRegistry recipeRegistry = event.getRecipeRegistry();
        this.integration.getRecipes().forEach(recipeRegistry::registerCauldronRecipe);
    }

}
