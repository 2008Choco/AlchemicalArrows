package me.choco.arrows.utils.arrows;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;

public class NecroticArrow extends AlchemicalArrow{
	
	private final ItemStack rottenFlesh = new ItemStack(Material.ROTTEN_FLESH);
	
	public NecroticArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.ITEM_CRACK, getArrow().getLocation(), 2, 0.1, 0.1, 0.1, 0.1, rottenFlesh);
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
	
	@Override
	public void hitEntityEventHandler(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Damageable){
			Damageable damager = (Damageable) event.getDamager();
			damager.setHealth(damager.getHealth() + event.getDamage()/2);
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