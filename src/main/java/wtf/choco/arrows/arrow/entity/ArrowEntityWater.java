package wtf.choco.arrows.arrow.entity;

import org.bukkit.entity.Arrow;
import org.bukkit.util.Vector;

import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;

public final class ArrowEntityWater extends AlchemicalArrowEntity {

	private final Vector velocity;

	public ArrowEntityWater(AlchemicalArrow type, Arrow arrow, Vector initialVelocity) {
		super(type, arrow);
		this.velocity = initialVelocity;
	}

	public Vector getVelocity(double multiplier) {
		Vector toReturn = velocity.clone();
		this.velocity.multiply(multiplier);

		return toReturn;
	}

	public Vector getVelocity() {
		return velocity.clone();
	}

}