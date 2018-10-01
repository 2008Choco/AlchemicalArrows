package wtf.choco.arrows.crafting;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import com.google.common.base.Preconditions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Levelled;

public class AlchemicalCauldron {
	
	public static final long REQUIRED_BUBBLING_TICKS = 300L;
	
	private long heatingStartTime;
	private boolean heatingUp = false, bubbling = false;
	
	private final Map<Material, Integer> ingredientPotency = new EnumMap<>(Material.class);
	
	private final Block cauldronBlock, fireBlock;
	
	public AlchemicalCauldron(Block block) {
		Preconditions.checkArgument(block.getType() == Material.CAULDRON, "AlchemicalCauldron block type must be CAULDRON");
		
		this.cauldronBlock = block;
		this.fireBlock = block.getRelative(BlockFace.DOWN);
		
		this.heatingStartTime = (fireBlock.getType() == Material.FIRE) ? System.currentTimeMillis() : -1;
	}
	
	public Block getCauldronBlock() {
		return cauldronBlock;
	}
	
	public Block getFireBlock() {
		return fireBlock;
	}
	
	public boolean canHeatUp() {
		if (cauldronBlock.getType() != Material.CAULDRON) return false;
		
		Levelled cauldron = (Levelled) cauldronBlock.getBlockData();
		return cauldron.getLevel() == cauldron.getMaximumLevel() && fireBlock.getType() == Material.FIRE;
	}
	
	public boolean attemptToHeatUp() {
		if (heatingUp || bubbling || !canHeatUp()) return false;
		
		this.heatingStartTime = System.currentTimeMillis();
		this.heatingUp = true;
		return true;
	}
	
	public boolean isHeatingUp() {
		return heatingUp;
	}
	
	public void stopHeatingUp() {
		this.heatingStartTime = -1;
		this.heatingUp = false;
	}
	
	public long getHeatingStartTime() {
		return heatingStartTime;
	}
	
	public void setBubbling(boolean bubbling) {
		this.bubbling = bubbling;
	}
	
	public boolean isBubbling() {
		return bubbling;
	}
	
	public void addIngredient(Material material, int potency) {
		this.ingredientPotency.merge(material, potency, Integer::sum);
	}
	
	public void addIngredient(Material material) {
		this.addIngredient(material, 1);
	}
	
	public void removeIngredient(Material material, int potency) {
		if (!ingredientPotency.containsKey(material)) return;
		
		int current = ingredientPotency.get(material);
		if (potency >= current) {
			this.ingredientPotency.remove(material);
		} else {
			this.ingredientPotency.put(material, current - potency);
		}
	}
	
	public void removeIngredient(Material material) {
		this.ingredientPotency.remove(material);
	}
	
	public void removeIngredients(CauldronRecipe recipe) {
		for (Material material : recipe.getRecipeMaterials()) {
			int recipeCount = recipe.getIngredientCount(material);
			this.ingredientPotency.computeIfPresent(material, (m, current) -> (current - recipeCount <= 0) ? null : current - recipeCount);
		}
	}
	
	public boolean hasIngredient(Material material) {
		return ingredientPotency.containsKey(material);
	}
	
	public boolean hasIngredients() {
		return ingredientPotency.size() >= 1;
	}
	
	public int getIngredientPotency(Material material) {
		return ingredientPotency.getOrDefault(material, 0);
	}
	
	public Map<Material, Integer> getIngredients() {
		return new EnumMap<>(ingredientPotency);
	}
	
	public void clearIngredients() {
		this.ingredientPotency.clear();
	}
	
	@Override
	public int hashCode() {
		return 31 * (cauldronBlock == null ? 0 : cauldronBlock.hashCode());
	}
	
	@Override
	public boolean equals(Object object) {
		return object == this || (object instanceof AlchemicalCauldron
			&& Objects.equals(cauldronBlock, ((AlchemicalCauldron) object).cauldronBlock));
	}
	
}