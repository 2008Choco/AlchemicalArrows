package wtf.choco.arrows.api.event;

import org.bukkit.event.Event;

import wtf.choco.arrows.api.AlchemicalArrowEntity;

/**
 * An abstract event that involves an {@link AlchemicalArrowEntity}
 *
 * @author Parker Hawke - 2008Choco
 */
public abstract class AlchemicalArrowEvent extends Event {

	private final AlchemicalArrowEntity arrow;

	protected AlchemicalArrowEvent(AlchemicalArrowEntity arrow) {
		this.arrow = arrow;
	}

	/**
	 * Get the alchemical arrow entity involved in this event
	 *
	 * @return the involved arrow
	 */
	public AlchemicalArrowEntity getArrow() {
		return arrow;
	}

}