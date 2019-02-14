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

import wtf.choco.arrows.crafting.AlchemicalCauldron;
import wtf.choco.arrows.crafting.CauldronRecipe;

public class CauldronManager {

	private final Map<Block, AlchemicalCauldron> cauldrons = new HashMap<>();
	private final Map<NamespacedKey, CauldronRecipe> recipes = new HashMap<>();

	public void addAlchemicalCauldron(AlchemicalCauldron cauldron) {
		Preconditions.checkNotNull(cauldron, "Cannot add null alchemical cauldron");
		this.cauldrons.put(cauldron.getCauldronBlock(), cauldron);
	}

	public void removeAlchemicalCauldron(AlchemicalCauldron cauldron) {
		if (cauldron == null) return;
		this.cauldrons.remove(cauldron.getCauldronBlock());
	}

	public AlchemicalCauldron getAlchemicalCauldron(Block block) {
		return cauldrons.get(block);
	}

	public AlchemicalCauldron getAlchemicalCauldron(Location location) {
		return (location != null) ? getAlchemicalCauldron(location.getBlock()) : null;
	}

	public boolean isAlchemicalCauldron(Block block) {
		return cauldrons.containsKey(block);
	}

	public boolean isAlchemicalCauldron(Location location) {
		return location != null && cauldrons.containsKey(location.getBlock());
	}

	public Collection<AlchemicalCauldron> getAlchemicalCauldrons() {
		return Collections.unmodifiableCollection(cauldrons.values());
	}

	public void clearAlchemicalCauldrons() {
		this.cauldrons.clear();
	}

	public void registerCauldronRecipe(CauldronRecipe recipe) {
		Preconditions.checkNotNull(recipe, "Cannot register null recipe");
		this.recipes.put(recipe.getKey(), recipe);
	}

	public void unregisterCauldronRecipe(CauldronRecipe recipe) {
		this.recipes.remove(recipe.getKey());
	}

	public CauldronRecipe unregisterCauldronRecipe(NamespacedKey key) {
		return recipes.remove(key);
	}

	public CauldronRecipe getApplicableRecipe(Material catalyst, Map<Material, Integer> ingredients) {
		return recipes.values().stream()
				.filter(r -> r.getCatalyst() == catalyst && r.getExpectedYieldFromIngredients(ingredients) >= 1)
				.findFirst().orElse(null);
	}

	public Collection<CauldronRecipe> getRecipes() {
		return recipes.values(); // Intentionally mutable
	}

	public void clearRecipes() {
		this.recipes.clear();
	}

}