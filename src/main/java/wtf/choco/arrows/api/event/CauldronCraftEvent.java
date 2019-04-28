package wtf.choco.arrows.api.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.crafting.AlchemicalCauldron;
import wtf.choco.arrows.crafting.CauldronRecipe;

/**
 * Called when an {@link AlchemicalCauldron} has successfully prepared a crafting recipe.
 *
 * @author Parker Hawke - Choco
 */
public class CauldronCraftEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled = false;
    private boolean consumeIngredients = true;
    private AlchemicalArrow result;

    private final AlchemicalCauldron cauldron;
    private final CauldronRecipe recipe;

    public CauldronCraftEvent(@NotNull AlchemicalCauldron cauldron, @NotNull CauldronRecipe recipe) {
        this.cauldron = cauldron;
        this.recipe = recipe;
        this.result = recipe.getResult();
    }

    /**
     * Get the {@link AlchemicalCauldron} involved in this event.
     *
     * @return the cauldron
     */
    @NotNull
    public AlchemicalCauldron getCauldron() {
        return cauldron;
    }

    /**
     * Get the recipe that was prepared for this event.
     *
     * @return the recipe
     */
    @NotNull
    public CauldronRecipe getRecipe() {
        return recipe;
    }

    /**
     * Set the resulting {@link AlchemicalArrow} of the cauldron crafting process. Null if none.
     *
     * @param result the result to set
     */
    public void setResult(@Nullable AlchemicalArrow result) {
        this.result = result;
    }

    /**
     * Get the resulting {@link AlchemicalArrow} for this cauldron crafting process.
     *
     * @return the result
     */
    @Nullable
    public AlchemicalArrow getResult() {
        return result;
    }

    /**
     * Set whether or not the ingredients should be consumed from the cauldron.
     *
     * @param consume whether or not ingredients should be consumed
     */
    public void setConsumeIngredients(boolean consume) {
        this.consumeIngredients = consume;
    }

    /**
     * Check whether or not ingredients should be consumed from the cauldron.
     *
     * @return true if ingredients are to be consumed, false otherwise
     */
    public boolean shouldConsumeIngredients() {
        return consumeIngredients;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}