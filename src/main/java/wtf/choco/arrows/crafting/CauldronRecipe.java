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

public class CauldronRecipe implements Keyed {

	protected static final List<Material> CATALYSTS = new ArrayList<>();

	private final NamespacedKey key;
	private final AlchemicalArrow result;
	private final Material catalyst;
	private final Map<Material, Integer> ingredients = new EnumMap<>(Material.class);

	public CauldronRecipe(@NotNull NamespacedKey key, @NotNull AlchemicalArrow result, @NotNull Material catalyst, @NotNull Map<Material, Integer> ingredients) {
		this(key, result, catalyst);
		this.ingredients.putAll(ingredients);
	}

	public CauldronRecipe(@NotNull NamespacedKey key, @NotNull AlchemicalArrow result, @NotNull Material catalyst, @NotNull Material... ingredients) {
		this(key, result, catalyst);

		Preconditions.checkState(ingredients.length > 0, "Recipes contain at least one ingredient (excluding the catalyst)");
		for (Material ingredient : ingredients) {
			this.ingredients.put(ingredient, 1);
		}
	}

	public CauldronRecipe(@NotNull NamespacedKey key, @NotNull AlchemicalArrow result, @NotNull Material catalyst, @NotNull Material ingredient) {
		this(key, result, catalyst);
		this.ingredients.put(ingredient, 1);
	}

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

	@NotNull
	public AlchemicalArrow getResult() {
		return result;
	}

	@NotNull
	public Material getCatalyst() {
		return catalyst;
	}

	@NotNull
	public CauldronRecipe addIngredient(@NotNull Material material, int amount) {
		this.ingredients.put(material, amount);
		return this;
	}

	public boolean isIngredient(@NotNull Material material) {
		return ingredients.containsKey(material);
	}

	public int getIngredientCount(@NotNull Material material) {
		return ingredients.getOrDefault(material, 0);
	}

	@NotNull
	public Set<Material> getRecipeMaterials() {
		return Collections.unmodifiableSet(ingredients.keySet());
	}

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