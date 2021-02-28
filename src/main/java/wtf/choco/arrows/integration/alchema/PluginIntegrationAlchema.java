package wtf.choco.arrows.integration.alchema;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import wtf.choco.alchema.crafting.CauldronIngredientMaterial;
import wtf.choco.alchema.crafting.CauldronRecipe;
import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.util.AAConstants;
import wtf.choco.commons.integration.PluginIntegration;

public final class PluginIntegrationAlchema implements PluginIntegration {

    private final Plugin alchemaPlugin;
    private final List<@NotNull CauldronRecipe> recipes = new ArrayList<>();

    public PluginIntegrationAlchema(@NotNull Plugin alchemaPlugin) {
        this.alchemaPlugin = alchemaPlugin;
    }

    @NotNull
    @Override
    public Plugin getIntegratedPlugin() {
        return alchemaPlugin;
    }

    @Override
    public void load() { }

    @Override
    public void enable() {
        AlchemicalArrows alchemicalArrows = AlchemicalArrows.getInstance();
        FileConfiguration config = alchemicalArrows.getConfig();

        if (config.getBoolean(AAConstants.CONFIG_CRAFTING_ALCHEMA_INTEGRATION_ENABLED, true)) {
            Bukkit.getPluginManager().registerEvents(new AlchemaRecipeIntegrationListener(this), alchemicalArrows);
        }
    }

    @Override
    public void disable() {
        this.recipes.clear();
    }

    /**
     * Add a new {@link CauldronRecipe} to be registered to Alchema on enable.
     *
     * @param key the recipe key
     * @param arrow the arrow for which to create a recipe
     * @param yield the recipe result yield
     * @param arrowsRequired the amount of arrows required in this recipe
     * @param ingredient the additional ingredient to craft the recipe
     */
    public void addRecipe(@NotNull NamespacedKey key, @NotNull AlchemicalArrow arrow, int yield, int arrowsRequired, @NotNull Material ingredient) {
        Preconditions.checkArgument(key != null, "key must not be null");
        Preconditions.checkArgument(arrow != null, "arrow must not be null");
        Preconditions.checkArgument(ingredient != null, "ingredient must not be null");

        this.recipes.add(CauldronRecipe.builder(key, arrow.createItemStack(yield))
            .addIngredient(new CauldronIngredientMaterial(Material.ARROW, arrowsRequired))
            .addIngredient(new CauldronIngredientMaterial(ingredient))
            .build()
        );
    }

    List<CauldronRecipe> getRecipes() {
        return recipes;
    }

}
