package me.choco.arrows.utils.arrows;

import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;

public class DarknessArrow extends AlchemicalArrow{
	public DarknessArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.DAMAGE_INDICATOR, getArrow().getLocation(), 1, 0.1, 0.1, 0.1, 0.1);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		PotionEffect blindness = PotionEffectType.BLINDNESS.createEffect(500, 1);
		player.addPotionEffect(blindness);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.darkness")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
	
	@Override
	public boolean allowInfinity() {
		return AlchemicalArrows.getPlugin().getConfig().getBoolean("Arrows.DarknessArrow.AllowInfinity");
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return AlchemicalArrows.getPlugin().getConfig().getBoolean("Arrows.DarknessArrow.SkeletonsCanShoot");
	}
}