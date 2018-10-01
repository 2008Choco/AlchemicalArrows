package wtf.choco.arrows.crafting;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Preconditions;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import wtf.choco.arrows.api.AlchemicalArrow;

public class CauldronRecipe implements Keyed {
	
	private final NamespacedKey key;
	private final AlchemicalArrow result;
	private final Map<Material, Integer> ingredients;
	
	public CauldronRecipe(NamespacedKey key, AlchemicalArrow result, EnumMap<Material, Integer> ingredients) {
		Preconditions.checkNotNull(key, "Namespaced key must not be null");
		
		this.key = key;
		this.result = result;
		this.ingredients = ingredients.clone();
	}
	
	public CauldronRecipe(NamespacedKey key, AlchemicalArrow result, Material... ingredients) {
		Preconditions.checkNotNull(key, "Namespaced key must not be null");
		
		this.key = key;
		this.result = result;
		this.ingredients = new EnumMap<>(Material.class);
		
		for (Material ingredient : ingredients) {
			this.ingredients.put(ingredient, 1);
		}
	}
	
	public CauldronRecipe(NamespacedKey key, AlchemicalArrow result, Material ingredient) {
		Preconditions.checkNotNull(key, "Namespaced key must not be null");
		
		this.key = key;
		this.result = result;
		this.ingredients = new EnumMap<>(Material.class);
		this.ingredients.put(ingredient, 1);
	}
	
	@Override
	public NamespacedKey getKey() {
		return key;
	}
	
	public AlchemicalArrow getResult() {
		return result;
	}
	
	public CauldronRecipe addIngredient(Material material, int amount) {
		this.ingredients.put(material, amount);
		return this;
	}
	
	public boolean isIngredient(Material material) {
		return ingredients.containsKey(material);
	}
	
	public int getIngredientCount(Material material) {
		return ingredients.getOrDefault(material, 0);
	}
	
	public Set<Material> getRecipeMaterials() {
		return Collections.unmodifiableSet(ingredients.keySet());
	}
	
	public int getExpectedYieldFromIngredients(Map<Material, Integer> availableIngredients) {
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