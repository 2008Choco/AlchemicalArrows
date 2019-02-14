package wtf.choco.arrows.arrow;

import java.util.Collection;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;

public class AlchemicalArrowAir extends AlchemicalArrowAbstract {

	private static final Random RANDOM = new Random();
	private static final int BREATH_RADIUS_LIMIT = 4;

	private int lastTick = 10;

	private final FileConfiguration config;

	public AlchemicalArrowAir(AlchemicalArrows plugin) {
		super(plugin, "air", c -> c.getString("Arrow.Air.Item.DisplayName", "&oAir Arrow"), c -> c.getStringList("Arrow.Air.Item.Lore"));

		this.config = plugin.getConfig();
		this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Air.Skeleton.CanShoot", true));
		this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Air.AllowInfinity", false));
		this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Air.Skeleton.LootDropWeight", 10.0));
	}

	@Override
	public void tick(AlchemicalArrowEntity arrow, Location location) {
		World world = location.getWorld();
		world.spawnParticle(Particle.CLOUD, location, 1, 0.1, 0.1, 0.1, 0.01);

		// Validate in-tile underwater arrow
		if (!arrow.getArrow().isInBlock() || lastTick-- > 0) return;

		Block block = location.getBlock();
		BlockData data = block.getBlockData();
		if (block.getType() != Material.WATER || (data instanceof Waterlogged && !((Waterlogged) data).isWaterlogged())) return;

		double radius = Math.min(config.getDouble("Arrows.Air.Effect.BreatheRadius", 2.0), BREATH_RADIUS_LIMIT);
		if (radius <= 0.0) return;

		// Replenish air of nearby underwater entities
		Collection<Entity> nearbyEntities = world.getNearbyEntities(location, radius, radius, radius);
		if (nearbyEntities.size() <= 1) return;

		for (Entity entity : nearbyEntities) {
			if (!(entity instanceof LivingEntity)) continue;

			LivingEntity lEntity = (LivingEntity) entity;
			if (lEntity.getRemainingAir() >= lEntity.getMaximumAir() + 40) continue;

			lEntity.setRemainingAir(lEntity.getRemainingAir() - 40);
			if (lEntity.getType() == EntityType.PLAYER) {
				((Player) lEntity).playSound(lEntity.getLocation(), Sound.ENTITY_BOAT_PADDLE_WATER, 1, 0.5F);
			}

			this.lastTick = 20;
		}
	}

	@Override
	public void hitEntityEventHandler(AlchemicalArrowEntity arrow, EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof LivingEntity)) return;
		LivingEntity entity = (LivingEntity) event.getEntity();

		entity.damage(event.getFinalDamage(), event.getDamager());
		entity.setVelocity(entity.getVelocity().setY((RANDOM.nextDouble() * 2) + 1));
		entity.getWorld().playSound(entity.getLocation(), Sound.ITEM_BUCKET_EMPTY, 1, 2);

		event.setCancelled(true);
		arrow.getArrow().remove();
	}

}