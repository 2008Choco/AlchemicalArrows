package me.choco.arrows.utils.arrows;

import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;

public class NecroticArrow extends AlchemicalArrow{
	public NecroticArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		//TODO
	}
	
	@Override
	public void onHitPlayer(Player player) {
		List<Entity> nearbyEntities = player.getNearbyEntities(50, 10, 50);
		for (Iterator<Entity> it = nearbyEntities.iterator(); it.hasNext();){
			Entity entity = it.next();
			if (entity instanceof Monster)
				((Monster)entity).setTarget(player);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.necrotic")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
	
	@Override
	public boolean allowInfinity() {
		return AlchemicalArrows.getPlugin().getConfig().getBoolean("Arrows.NecroticArrow.AllowInfinity");
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return AlchemicalArrows.getPlugin().getConfig().getBoolean("Arrows.NecroticArrow.SkeletonsCanShoot");
	}
}