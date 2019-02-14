package wtf.choco.arrows.arrow;

import java.util.concurrent.ThreadLocalRandom;

import com.sk89q.worldguard.bukkit.WGBukkit;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;

public class AlchemicalArrowLife extends AlchemicalArrowAbstract {

	private static final PotionEffect REGENERATION_EFFECT = new PotionEffect(PotionEffectType.REGENERATION, 300, 2);
	private static final int GROWTH_RADIUS_LIMIT = 5;
	private static final Material[] GROWABLE_MATERIALS = {
		Material.ALLIUM, Material.AZURE_BLUET, Material.BLUE_ORCHID,
		Material.DANDELION, Material.GRASS, Material.ORANGE_TULIP,
		Material.OXEYE_DAISY, Material.PINK_TULIP, Material.POPPY,
		Material.RED_TULIP, Material.WHITE_TULIP,
	};

	private final AlchemicalArrows plugin;
	private final FileConfiguration config;

	public AlchemicalArrowLife(AlchemicalArrows plugin) {
		super(plugin, "life", c -> c.getString("Arrow.Life.Item.DisplayName", "&aLife Arrow"), c -> c.getStringList("Arrow.Life.Item.Lore"));

		this.plugin = plugin;
		this.config = plugin.getConfig();

		this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Life.Skeleton.CanShoot", true));
		this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Life.AllowInfinity", false));
		this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Life.Skeleton.LootDropWeight", 10.0));
	}

	@Override
	public void tick(AlchemicalArrowEntity arrow, Location location) {
		location.getWorld().spawnParticle(Particle.HEART, location, 1, 0.1, 0.1, 0.1);
	}

	@Override
	public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
		player.addPotionEffect(REGENERATION_EFFECT);
	}

	@Override
	public void onHitEntity(AlchemicalArrowEntity arrow, Entity entity) {
		if (!(entity instanceof LivingEntity)) return;
		LivingEntity lEntity = (LivingEntity) entity;

		lEntity.addPotionEffect(REGENERATION_EFFECT);
	}

	@Override
	public void onHitBlock(AlchemicalArrowEntity arrow, Block block) {
		int radius = Math.min(config.getInt("Arrow.Life.Effect.FloralRadius", 2), GROWTH_RADIUS_LIMIT);
		if (radius <= 0) return;

		// WorldGuard check
		ProjectileSource shooter = arrow.getArrow().getShooter();
		if (plugin.isWorldGuardSupported() && shooter instanceof Player
				&& !WGBukkit.getPlugin().canBuild((Player) shooter, block)) {
			return;
		}

		// Generate flowers around the block at variable radius
		boolean found = false;
		ThreadLocalRandom random = ThreadLocalRandom.current();

		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				if (random.nextInt(5) > 1) continue; // 40%

				found = false;
				Block relative = block.getRelative(x, radius, z);

				while (relative.isEmpty()) { // Get highest air within radius
					Block down = relative.getRelative(BlockFace.DOWN);
					if (down.getType() == Material.GRASS_BLOCK) {
						found = true;
						break;
					}

					relative = down;
				}

				if (!found) {
					continue;
				}

				relative.setType(GROWABLE_MATERIALS[random.nextInt(GROWABLE_MATERIALS.length)]);
			}
		}
	}

	@Override
	public void hitEntityEventHandler(AlchemicalArrowEntity arrow, EntityDamageByEntityEvent event) {
		arrow.getArrow().setKnockbackStrength(0);
		event.setDamage(0);
	}

}