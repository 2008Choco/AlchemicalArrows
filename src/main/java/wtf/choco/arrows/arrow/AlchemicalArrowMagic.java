package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;

public class AlchemicalArrowMagic extends AlchemicalArrowAbstract {

	public AlchemicalArrowMagic(AlchemicalArrows plugin) {
		super(plugin, "magic", c -> c.getString("Arrow.Magic.Item.DisplayName", "&dMagic Arrow"), c -> c.getStringList("Arrow.Magic.Item.Lore"));

		FileConfiguration config = plugin.getConfig();
		this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Magic.Skeleton.CanShoot", true));
		this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Magic.AllowInfinity", false));
		this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Magic.Skeleton.LootDropWeight", 10.0));
	}

	@Override
	public void tick(AlchemicalArrowEntity arrow, Location location) {
		location.getWorld().spawnParticle(Particle.SPELL_WITCH, location, 2, 0.1, 0.1, 0.1);
	}

	@Override
	public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
		this.launchEntity(arrow.getArrow(), player);
	}

	@Override
	public void onHitEntity(AlchemicalArrowEntity arrow, Entity entity) {
		if (!(entity instanceof LivingEntity)) return;
		this.launchEntity(arrow.getArrow(), (LivingEntity) entity);
	}

	private void launchEntity(Arrow source, LivingEntity entity) {
		Vector sourceVelocity = source.getVelocity();
		Vector targetVelocity = new Vector(sourceVelocity.getX() * 2, 0.75, sourceVelocity.getZ() * 2);

		if (Math.abs(targetVelocity.getX()) > 4 || Math.abs(targetVelocity.getY()) > 4 || Math.abs(targetVelocity.getZ()) > 4) {
			targetVelocity.normalize().multiply(4);
		}

		entity.setVelocity(targetVelocity);
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 2);
	}

}