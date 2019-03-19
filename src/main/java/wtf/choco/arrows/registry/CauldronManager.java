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

public class CauldronManager {

	private final Map<Block, AlchemicalCauldron> cauldrons = new HashMap<>();
	private final Map<NamespacedKey, CauldronRecipe> recipes = new HashMap<>();

	public void addAlchemicalCauldron(@NotNull AlchemicalCauldron cauldron) {
		Preconditions.checkNotNull(cauldron, "Cannot add null alchemical cauldron");
		this.cauldrons.put(cauldron.getCauldronBlock(), cauldron);
	}

	public void removeAlchemicalCauldron(@NotNull AlchemicalCauldron cauldron) {
		this.cauldrons.remove(cauldron.getCauldronBlock());
	}

	@Nullable
	public AlchemicalCauldron getAlchemicalCauldron(@NotNull Block block) {
		return cauldrons.get(block);
	}

	@Nullable
	public AlchemicalCauldron getAlchemicalCauldron(@NotNull Location location) {
		return (location != null) ? getAlchemicalCauldron(location.getBlock()) : null;
	}

	public boolean isAlchemicalCauldron(@NotNull Block block) {
		return cauldrons.containsKey(block);
	}

	public boolean isAlchemicalCauldron(@NotNull Location location) {
		return location != null && cauldrons.containsKey(location.getBlock());
	}

	@NotNull
	public Collection<AlchemicalCauldron> getAlchemicalCauldrons() {
		return Collections.unmodifiableCollection(cauldrons.values());
	}

	public void clearAlchemicalCauldrons() {
		this.cauldrons.clear();
	}

	public void registerCauldronRecipe(@NotNull CauldronRecipe recipe) {
		Preconditions.checkNotNull(recipe, "Cannot register null recipe");
		this.recipes.put(recipe.getKey(), recipe);
	}

	public void unregisterCauldronRecipe(@NotNull CauldronRecipe recipe) {
		this.recipes.remove(recipe.getKey());
	}

	@Nullable
	public CauldronRecipe unregisterCauldronRecipe(@NotNull NamespacedKey key) {
		return recipes.remove(key);
	}

	@Nullable
	public CauldronRecipe getApplicableRecipe(@NotNull Material catalyst, @NotNull Map<Material, Integer> ingredients) {
		return recipes.values().stream()
				.filter(r -> r.getCatalyst() == catalyst && r.getExpectedYieldFromIngredients(ingredients) >= 1)
				.findFirst().orElse(null);
	}

	@NotNull
	public Collection<CauldronRecipe> getRecipes() {
		return recipes.values(); // Intentionally mutable
	}

	public void clearRecipes() {
		this.recipes.clear();
	}

}