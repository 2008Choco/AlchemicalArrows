package me.choco.arrows.arrows;

import java.util.Iterator;

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
import me.choco.arrows.utils.ConfigOption;

public class NecroticArrow extends AlchemicalArrow {
	
	private static final ItemStack ROTTEN_FLESH = new ItemStack(Material.ROTTEN_FLESH);
	
	public NecroticArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public String getName() {
		return "Necrotic";
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.ITEM_CRACK, arrow.getLocation(), 2, 0.1, 0.1, 0.1, 0.1, ROTTEN_FLESH);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		Iterator<Entity> nearbyEntities = player.getNearbyEntities(50, 10, 50).iterator();
		while (nearbyEntities.hasNext()){
			Entity entity = nearbyEntities.next();
			if (entity instanceof Monster)
				((Monster) entity).setTarget(player);
		}
	}
	
	@Override
	public void hitEntityEventHandler(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Damageable){
			Damageable damager = (Damageable) event.getDamager();
			damager.setHealth(damager.getHealth() + event.getDamage() / 2);
		}
	}
	
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.necrotic")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
	
	@Override
	public boolean allowInfinity() {
		return ConfigOption.NECROTIC_ALLOW_INFINITY;
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return ConfigOption.NECROTIC_SKELETONS_CAN_SHOOT;
	}
	
	@Override
	public double skeletonLootWeight() {
		return ConfigOption.NECROTIC_SKELETON_LOOT_WEIGHT;
	}
}