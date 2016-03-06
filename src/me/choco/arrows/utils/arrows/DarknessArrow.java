package me.choco.arrows.utils.arrows;

import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;

public class DarknessArrow extends AlchemicalArrow{
	public DarknessArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.DAMAGE_INDICATOR, getArrow().getLocation(), 3, 0.1, 0.1, 0.1, 0.1);
	}
	
	/* TODO
	 * REWRITE AN IDEA FOR THIS
	 */
	
	@SuppressWarnings("deprecation")
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.darkness")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
}