package wtf.choco.arrows.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import wtf.choco.arrows.crafting.AlchemicalCauldron;
import wtf.choco.arrows.crafting.CauldronRecipe;

/**
 * Manages instances of {@link AlchemicalCauldron} in any given world.
 *
 * @author Parker Hawke - Choco
 */
public class CauldronManager {

    private final Map<Block, AlchemicalCauldron> cauldrons = new HashMap<>();
    private final Map<NamespacedKey, CauldronRecipe> recipes = new HashMap<>();

    public void addAlchemicalCauldron(@NotNull AlchemicalCauldron cauldron) {
        Preconditions.checkNotNull(cauldron, "Cannot add null alchemical cauldron");
        this.cauldrons.put(cauldron.getCauldronBlock(), cauldron);
    }

    /**
     * Remove an {@link AlchemicalCauldron} from the world.
     *
     * @param cauldron the cauldron to remove
     */
    public void removeAlchemicalCauldron(@NotNull AlchemicalCauldron cauldron) {
        this.cauldrons.remove(cauldron.getCauldronBlock());
    }

    /**
     * Get an {@link AlchemicalCauldron} at the specified {@link Block}. If no cauldron is
     * present, null is returned.
     *
     * @param block the block from which to get a cauldron
     *
     * @return the alchemical cauldron at the block. null if none
     */
    @Nullable
    public AlchemicalCauldron getAlchemicalCauldron(@NotNull Block block) {
        return cauldrons.get(block);
    }

    /**
     * Get an {@link AlchemicalCauldron} at the specified {@link Location}. If no cauldron is
     * present, null is returned.
     *
     * @param location the location at which to get a cauldron
     *
     * @return the alchemical cauldron at the location. null if none
     */
    @Nullable
    public AlchemicalCauldron getAlchemicalCauldron(@NotNull Location location) {
        return (location != null) ? getAlchemicalCauldron(location.getBlock()) : null;
    }

    /**
     * Get an unmodifiable collection of all {@link AlchemicalCauldron}s in this manager.
     *
     * @return all cauldrons
     */
    @NotNull
    public Collection<AlchemicalCauldron> getAlchemicalCauldrons() {
        return Collections.unmodifiableCollection(cauldrons.values());
    }

    /**
     * Clear all alchemical cauldrons from the world.
     */
    public void clearAlchemicalCauldrons() {
        this.cauldrons.clear();
    }

    /**
     * Register a {@link CauldronRecipe} to be used by any {@link AlchemicalCauldron}.
     *
     * @param recipe the recipe to register
     */
    public void registerCauldronRecipe(@NotNull CauldronRecipe recipe) {
        Preconditions.checkNotNull(recipe, "Cannot register null recipe");
        this.recipes.put(recipe.getKey(), recipe);
    }

    /**
     * Unregister a {@link CauldronRecipe}. Upon unregistration, cauldrons will not longer be able
     * to execute its recipe.
     *
     * @param recipe the recipe to unregister
     */
    public void unregisterCauldronRecipe(@NotNull CauldronRecipe recipe) {
        this.recipes.remove(recipe.getKey());
    }

    /**
     * Unregister a {@link CauldronRecipe} associated with the provided ID. Upon unregistration,
     * cauldrons will no longer be able to execute its recipe.
     *
     * @param key the key of the recipe to unregister
     *
     * @return the unregistered recipe. null if none
     */
    @Nullable
    public CauldronRecipe unregisterCauldronRecipe(@NotNull NamespacedKey key) {
        return recipes.remove(key);
    }

    /**
     * Get the {@link CauldronRecipe} that applies given a catalyst and a set of ingredients. If
     * no recipe can consume the ingredients and catalyst, null is returned. If more than one recipe
     * is valid, the first is selected.
     *
     * @param catalyst the catalyst material
     * @param ingredients the set of ingredients mapped to their quantity
     *
     * @return the cauldron recipe that applies. null if none
     */
    @Nullable
    public CauldronRecipe getApplicableRecipe(@NotNull Material catalyst, @NotNull Map<Material, Integer> ingredients) {
        return recipes.values().stream()
                .filter(r -> r.getCatalyst() == catalyst && r.getExpectedYieldFromIngredients(ingredients) >= 1)
                .findFirst().orElse(null);
    }

    /**
     * Get a collection of all registered recipes. Changes made to the returned collection will be
     * reflected internally to this instance.
     *
     * @return the collection of registered recipes
     */
    @NotNull
    public Collection<CauldronRecipe> getRecipes() {
        return recipes.values(); // Intentionally mutable
    }

    /**
     * Clear all recipes in the manager.
     */
    public void clearRecipes() {
        this.recipes.clear();
    }

}