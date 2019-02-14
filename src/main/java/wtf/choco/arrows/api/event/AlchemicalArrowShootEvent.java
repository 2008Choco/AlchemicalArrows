package wtf.choco.arrows.api.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.projectiles.ProjectileSource;

import wtf.choco.arrows.api.AlchemicalArrowEntity;

/**
 * Called when an alchemical arrow has been shot. This event is cancellable
 *
 * @author Parker Hawke - 2008Choco
 */
public class AlchemicalArrowShootEvent extends AlchemicalArrowEvent implements Cancellable {

	private static final HandlerList HANDLERS = new HandlerList();

	private boolean cancelled = false;

	private final ProjectileSource shooter;

	/**
	 * Construct a new AlchemicalArrowShootEvent
	 *
	 * @param arrow the arrow that was shot
	 * @param shooter the source of the arrow
	 */
	public AlchemicalArrowShootEvent(AlchemicalArrowEntity arrow, ProjectileSource shooter) {
		super(arrow);
		this.shooter = shooter;
	}

	/**
	 * Get the projectile source that launched this arrow
	 *
	 * @return the shooter
	 */
	public ProjectileSource getShooter() {
		return shooter;
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