package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;

public class AlchemicalArrowFrost extends AlchemicalArrowAbstract {

	public static final ArrowProperty<Double> PROPERTY_WATER_FREEZE_RADIUS = new ArrowProperty<>(new NamespacedKey(AlchemicalArrows.getInstance(), "water_freeze_radius"), Double.class, 3.5);

	private static final PotionEffect SLOWNESS_EFFECT = new PotionEffect(PotionEffectType.SLOW, 100, 254);
	private static final PotionEffect ANTI_JUMP_EFFECT = new PotionEffect(PotionEffectType.JUMP, 100, 500);
	private static final double WATER_FREEZE_RADIUS_LIMIT = 7.0;

	public AlchemicalArrowFrost(AlchemicalArrows plugin) {
		super(plugin, "frost", c -> c.getString("Arrow.Frost.Item.DisplayName", "&bFrost Arrow"), c -> c.getStringList("Arrow.Frost.Item.Lore"));

		FileConfiguration config = plugin.getConfig();
		this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Frost.Skeleton.CanShoot", true));
		this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Frost.AllowInfinity", false));
		this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Frost.Skeleton.LootDropWeight", 10.0));
		this.properties.setProperty(PROPERTY_WATER_FREEZE_RADIUS, Math.min(config.getDouble("Arrow.Frost.Effect.WaterFreezeRadius", PROPERTY_WATER_FREEZE_RADIUS.getDefaultValue()), WATER_FREEZE_RADIUS_LIMIT));
	}

	@Override
	public void tick(AlchemicalArrowEntity arrow, Location location) {
		location.getWorld().spawnParticle(Particle.SNOW_SHOVEL, location, 3, 0.1, 0.1, 0.1, 0.01);

		if (location.getBlock().getType() != Material.WATER) return;
		this.freezeRadius(arrow, location);
	}

	@Override
	public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
		player.addPotionEffect(SLOWNESS_EFFECT);
		player.addPotionEffect(ANTI_JUMP_EFFECT);
	}

	@Override
	public void onHitEntity(AlchemicalArrowEntity arrow, Entity entity) {
		if (!(entity instanceof LivingEntity)) return;
		LivingEntity lEntity = (LivingEntity) entity;

		lEntity.addPotionEffect(SLOWNESS_EFFECT);
		lEntity.addPotionEffect(ANTI_JUMP_EFFECT);
	}

	@Override
	public void onHitBlock(AlchemicalArrowEntity arrow, Block block) {
		this.freezeRadius(arrow, block.getLocation());
	}

	private void freezeRadius(AlchemicalArrowEntity arrow, Location location) {
		double radius = properties.getPropertyValue(PROPERTY_WATER_FREEZE_RADIUS).doubleValue();
		if (radius <= 0.0) return;

		// Center the location
		location.setX(location.getBlockX() + 0.5);
		location.setZ(location.getBlockZ() + 0.5);

		boolean blocksChanged = false;

		// Slowly fill ice from outer radius to center
		for (double currentRadius = radius; currentRadius >= 0; currentRadius -= 0.5) {
			for (double theta = 0; theta < Math.PI * 2; theta += Math.PI / 16) {
				double x = Math.cos(theta) * currentRadius;
				double z = Math.sin(theta) * currentRadius;

				location.add(x, 0, z);

				Block block = location.getBlock();
				if (block.getType() == Material.WATER) {
					location.getBlock().setType(Material.FROSTED_ICE);
					blocksChanged = true;
				}

				location.subtract(x, 0, z);
			}
		}

		if (blocksChanged) {
			arrow.getArrow().remove();
		}
	}

}