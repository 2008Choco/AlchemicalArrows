package wtf.choco.arrows.arrow.entity;

import org.bukkit.entity.Arrow;

import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;

public class ArrowEntityFused extends AlchemicalArrowEntity {
	
	private int fuse;
	private final int maxFuseTicks;
	
	public ArrowEntityFused(AlchemicalArrow type, Arrow arrow, int maxFuseTicks) {
		super(type, arrow);
		this.maxFuseTicks = maxFuseTicks;
		this.fuse = 0;
	}
	
	public int getMaxFuseTicks() {
		return maxFuseTicks;
	}
	
	public void setFuse(int fuse) {
		this.fuse = fuse;
	}
	
	public void tickFuse() {
		this.fuse++;
	}
	
	public int getFuse() {
		return fuse;
	}
	
	public boolean isFuseFinished() {
		return fuse >= maxFuseTicks;
	}
	
}