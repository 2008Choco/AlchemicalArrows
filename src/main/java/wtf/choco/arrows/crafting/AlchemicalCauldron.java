package wtf.choco.arrows.crafting;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import com.google.common.base.Preconditions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Levelled;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import wtf.choco.arrows.api.AlchemicalArrow;

/**
 * Represents a special cauldron that may be used to craft {@link AlchemicalArrow}s. These
 * cauldrons require a source of heat, therefore a lit flame must be present below the cauldron
 * before it may be used to conjure recipes.
 *
 * @author Parker Hawke - 2008Choco
 */
public class AlchemicalCauldron {

    public static final long REQUIRED_BUBBLING_MS = 300L;

    private long heatingStartTime;
    private boolean heatingUp = false, bubbling = false;

    private final Map<Material, Integer> ingredientPotency = new EnumMap<>(Material.class);

    private final Block cauldronBlock, fireBlock;

    public AlchemicalCauldron(@NotNull Block block) {
        Preconditions.checkArgument(block.getType() == Material.CAULDRON, "AlchemicalCauldron block type must be CAULDRON");

        this.cauldronBlock = block;
        this.fireBlock = block.getRelative(BlockFace.DOWN);

        this.heatingStartTime = (fireBlock.getType() == Material.FIRE) ? System.currentTimeMillis() : -1;
    }

    /**
     * Get the block at which the cauldron resides.
     *
     * @return the cauldron block
     */
    @NotNull
    public Block getCauldronBlock() {
        return cauldronBlock;
    }

    /**
     * Get the block used to ignite the cauldron (below, Y - 1, {@link #getCauldronBlock()}).
     *
     * @return the fire block
     */
    @NotNull
    public Block getFireBlock() {
        return fireBlock;
    }

    /**
     * Check whether or not this cauldron may be heated up.
     *
     * @return true if heating is possible, false otherwise
     */
    public boolean canHeatUp() {
        if (cauldronBlock.getType() != Material.CAULDRON) {
            return false;
        }

        Levelled cauldron = (Levelled) cauldronBlock.getBlockData();
        return cauldron.getLevel() == cauldron.getMaximumLevel() && fireBlock.getType() == Material.FIRE;
    }

    /**
     * Attempt to heat this cauldron.
     *
     * @return true if the attempt is successful and heating has started, false otherwise
     */
    public boolean attemptToHeatUp() {
        if (heatingUp || bubbling || !canHeatUp()) {
            return false;
        }

        this.heatingStartTime = System.currentTimeMillis();
        this.heatingUp = true;
        return true;
    }

    /**
     * Check whether or not this cauldron is currently heating up.
     *
     * @return true if heating up, false otherwise
     */
    public boolean isHeatingUp() {
        return heatingUp;
    }

    /**
     * Stop this cauldron from heating up.
     */
    public void stopHeatingUp() {
        this.heatingStartTime = -1;
        this.heatingUp = false;
    }

    /**
     * Get the time in millis (according to {@link System#currentTimeMillis()}) at which this
     * cauldron started heating up. If the cauldron is not heating up (i.e. {@link #isHeatingUp()}
     * == false), this will return -1.
     *
     * @return the heating start time. -1 if the cauldron is not heating up
     */
    public long getHeatingStartTime() {
        return heatingStartTime;
    }

    /**
     * Set whether or not this cauldron is bubbling.
     *
     * @param bubbling the new bubbling state
     */
    public void setBubbling(boolean bubbling) {
        this.bubbling = bubbling;
    }

    /**
     * Check whether or not this cauldron is bubbling.
     *
     * @return true if bubbling, false otherwise
     */
    public boolean isBubbling() {
        return bubbling;
    }

    /**
     * Add an ingredient to this cauldron with the specified potency (quantity).
     *
     * @param material the ingredient to add
     * @param potency the amount of the ingredient to add
     */
    public void addIngredient(@NotNull Material material, int potency) {
        this.ingredientPotency.merge(material, potency, Integer::sum);
    }

    /**
     * Add an ingredient to this cauldron according to the specified item. The potency to be added
     * will be equivalent to the item's quantity (i.e. {@link ItemStack#getAmount()})
     *
     * @param item the ingredient to add
     */
    public void addIngredient(@NotNull ItemStack item) {
    	Preconditions.checkArgument(item != null, "Cannot add null item to cauldron");
    	this.addIngredient(item.getType(), item.getAmount());
    }

    /**
     * Add a single (1) ingredient to this cauldron.
     *
     * @param material the ingredient to add
     */
    public void addIngredient(@NotNull Material material) {
        this.addIngredient(material, 1);
    }

    /**
     * Remove the specified amount of the specified ingredient from this cauldron. If the ingredient
     * is not present, this method will fail silently.
     *
     * @param material the ingredient to remove
     * @param potency the amount of the ingredient to remove
     */
    public void removeIngredient(@NotNull Material material, int potency) {
        if (!ingredientPotency.containsKey(material)) {
            return;
        }

        int current = ingredientPotency.get(material);
        if (potency >= current) {
            this.ingredientPotency.remove(material);
        } else {
            this.ingredientPotency.put(material, current - potency);
        }
    }

    /**
     * Remove all of the specified ingredient from this cauldron. If the ingredient is not present,
     * this method will fail silently.
     *
     * @param material the ingredient to remove
     */
    public void removeIngredient(@NotNull Material material) {
        this.ingredientPotency.remove(material);
    }

    /**
     * Remove the ingredients listed by the provided {@link CauldronRecipe}.
     *
     * @param recipe the recipe whose ingredients should be removed
     */
    public void removeIngredients(@NotNull CauldronRecipe recipe) {
        for (Material material : recipe.getRecipeMaterials()) {
            int recipeCount = recipe.getIngredientCount(material);
            this.ingredientPotency.computeIfPresent(material, (m, current) -> (current - recipeCount <= 0) ? null : current - recipeCount);
        }
    }

    /**
     * Check whether or not this cauldron has the specified ingredient.
     *
     * @param material the ingredient to check
     *
     * @return true if present with a quantity of at least one, false otherwise
     */
    public boolean hasIngredient(@NotNull Material material) {
        return ingredientPotency.containsKey(material);
    }

    /**
     * Check whether or not this cauldron has ANY ingredients.
     *
     * @return true if at least one ingredient is present, false otherwise
     */
    public boolean hasIngredients() {
        return ingredientPotency.size() >= 1;
    }

    /**
     * Get the potency of the specified ingredient. If not present, 0 is returned instead.
     *
     * @param material the ingredient whose potency to query
     *
     * @return the ingredient potency. 0 if not present
     */
    public int getIngredientPotency(@NotNull Material material) {
        return ingredientPotency.getOrDefault(material, 0);
    }

    /**
     * Get the ingredients present in this cauldron mapped to their quantities. Changes made to this
     * Map will not affect the contents of this cauldron.
     *
     * @return the ingredients
     */
    @NotNull
    public Map<Material, Integer> getIngredients() {
        return new EnumMap<>(ingredientPotency);
    }

    /**
     * Clear all ingredients from this cauldron.
     */
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