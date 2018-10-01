package wtf.choco.arrows.api.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.crafting.AlchemicalCauldron;
import wtf.choco.arrows.crafting.CauldronRecipe;

public class CauldronCraftEvent extends Event implements Cancellable {
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	private boolean cancelled = false;
	private boolean consumeIngredients = true;
	private AlchemicalArrow result;
	
	private final AlchemicalCauldron cauldron;
	private final CauldronRecipe recipe;
	
	public CauldronCraftEvent(AlchemicalCauldron cauldron, CauldronRecipe recipe) {
		this.cauldron = cauldron;
		this.recipe = recipe;
		this.result = recipe.getResult();
	}
	
	public AlchemicalCauldron getCauldron() {
		return cauldron;
	}
	
	public CauldronRecipe getRecipe() {
		return recipe;
	}
	
	public void setResult(AlchemicalArrow result) {
		this.result = result;
	}
	
	public AlchemicalArrow getResult() {
		return result;
	}
	
	public void setConsumeIngredients(boolean consume) {
		this.consumeIngredients = consume;
	}
	
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