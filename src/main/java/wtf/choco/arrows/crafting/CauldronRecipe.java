package wtf.choco.arrows.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Preconditions;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import wtf.choco.arrows.api.AlchemicalArrow;

/**
 * Represents a recipe that may be crafted in an {@link AlchemicalCauldron}.
 *
 * @author Parker Hawke - Choco
 */
public class CauldronRecipe implements Keyed {

    protected static final List<Material> CATALYSTS = new ArrayList<>();

    private final NamespacedKey key;
    private final AlchemicalArrow result;
    private final Material catalyst;
    private final Map<Material, Integer> ingredients = new EnumMap<>(Material.class);

    /**
     * Construct a new CauldronRecipe with a unique ID, {@link AlchemicalArrow} result, catalyst and a set
     * of required ingredients (and quantities).
     *
     * @param key the unique recipe key
     * @param result the result of the recipe
     * @param catalyst the {@link Material} required to trigger this recipe in the cauldron
     * @param ingredients the set of ingredients and their amounts required for this recipe
     */
    public CauldronRecipe(@NotNull NamespacedKey key, @NotNull AlchemicalArrow result, @NotNull Material catalyst, @NotNull Map<Material, Integer> ingredients) {
        this(key, result, catalyst);
        this.ingredients.putAll(ingredients);
    }

    /**
     * Construct a new CauldronRecipe with a unique ID, {@link AlchemicalArrow} result, catalyst and a set
     * of required ingredients where the quantities are assumed to be 1.
     *
     * @param key the unique recipe key
     * @param result the result of the recipe
     * @param catalyst the {@link Material} required to trigger this recipe in the cauldron
     * @param ingredients the set of ingredients (quantities default to 1)
     */
    public CauldronRecipe(@NotNull NamespacedKey key, @NotNull AlchemicalArrow result, @NotNull Material catalyst, @NotNull Material... ingredients) {
        this(key, result, catalyst);

        Preconditions.checkState(ingredients.length > 0, "Recipes contain at least one ingredient (excluding the catalyst)");
        for (Material ingredient : ingredients) {
            this.ingredients.put(ingredient, 1);
        }
    }

    /**
     * Construct a new CauldronRecipe with a unique ID, {@link AlchemicalArrow} result, catalyst and a single
     * ingredient.
     *
     * @param key the unique recipe key
     * @param result the result of the recipe
     * @param catalyst the {@link Material} required to trigger this recipe in the cauldron
     * @param ingredient the recipe ingredient
     */
    public CauldronRecipe(@NotNull NamespacedKey key, @NotNull AlchemicalArrow result, @NotNull Material catalyst, @NotNull Material ingredient) {
        this(key, result, catalyst);
        this.ingredients.put(ingredient, 1);
    }

    /**
     * Construct a new CauldronRecipe with a unique ID, {@link AlchemicalArrow} result and a catalyst.
     *
     * @param key the unique recipe key
     * @param result the result of the recipe
     * @param catalyst the {@link Material} required to trigger this recipe in the cauldron
     */
    private CauldronRecipe(@NotNull NamespacedKey key, @NotNull AlchemicalArrow result, @NotNull Material catalyst) {
        Preconditions.checkNotNull(key, "Namespaced key must not be null");

        this.key = key;
        this.result = result;
        this.catalyst = catalyst;

        if (!CATALYSTS.contains(catalyst)) {
            CATALYSTS.add(catalyst);
        }
    }

    @Override
    public NamespacedKey getKey() {
        return key;
    }

    /**
     * Get the result of this recipe.
     *
     * @return the result
     */
    @NotNull
    public AlchemicalArrow getResult() {
        return result;
    }

    /**
     * Get the catalyst, the {@link Material} required to trigger this recipe in the cauldron.
     *
     * @return the catalyst
     */
    @NotNull
    public Material getCatalyst() {
        return catalyst;
    }

    /**
     * Add an ingredient and its required amount to this recipe.
     *
     * @param material the {@link Material} to add
     * @param amount the amount of material required
     *
     * @return this instance. Allows for chained method calls
     */
    @NotNull
    public CauldronRecipe addIngredient(@NotNull Material material, int amount) {
        this.ingredients.put(material, amount);
        return this;
    }

    /**
     * Check whether or not the specified {@link Material} is an ingredient for this recipe.
     *
     * @param material the material to check
     *
     * @return true if a required ingredient, false otherwise
     */
    public boolean isIngredient(@NotNull Material material) {
        return ingredients.containsKey(material);
    }

    /**
     * Get the amount of the specified ingredient required for this recipe.
     *
     * @param material the {@link Material} whose amount to retrieve
     *
     * @return the ingredient's required amount. 0 if not an ingredient
     */
    public int getIngredientCount(@NotNull Material material) {
        return ingredients.getOrDefault(material, 0);
    }

    /**
     * Get an unmodifiable set of all required ingredients.
     *
     * @return the required ingredients
     */
    @NotNull
    public Set<Material> getRecipeMaterials() {
        return Collections.unmodifiableSet(ingredients.keySet());
    }

    /**
     * Get the expected yield (i.e. quantity of result) that may be produced such that the provided
     * ingredients will not be over-consumed.
     *
     * @param availableIngredients the ingredients available in the cauldron.
     *
     * @return the recipe yield
     */
    public int getExpectedYieldFromIngredients(@NotNull Map<Material, Integer> availableIngredients) {
        int yield = 0;
        boolean initialFind = true;

        for (Entry<Material, Integer> requiredIngredient : ingredients.entrySet()) {
            Material requiredMaterial = requiredIngredient.getKey();
            if (!availableIngredients.containsKey(requiredMaterial)) return 0;

            int requiredCount = requiredIngredient.getValue();
            int availableCount = availableIngredients.get(requiredMaterial);
            yield = initialFind ? availableCount / requiredCount : Math.min(availableCount / requiredCount, yield);

            initialFind = false;
        }

        return yield;
    }

}