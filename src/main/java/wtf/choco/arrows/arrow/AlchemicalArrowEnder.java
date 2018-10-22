package wtf.choco.arrows.arrow;

import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.arrows.utils.ItemBuilder;

public class AlchemicalArrowEnder extends AlchemicalArrow {
	
	private final ItemStack item;
	private final NamespacedKey key;
	private final FileConfiguration config;
	
	public AlchemicalArrowEnder(AlchemicalArrows plugin) {
		this.config = plugin.getConfig();
		this.key = new NamespacedKey(plugin, "ender");
		this.item = ItemBuilder.of(Material.ARROW)
				.name(ChatColor.translateAlternateColorCodes('&', config.getString("Arrow.Ender.Item.DisplayName", "&5Ender Arrow")))
				.lore(config.getStringList("Arrow.Ender.Item.Lore").stream()
						.map(s -> ChatColor.translateAlternateColorCodes('&', s))
						.collect(Collectors.toList())
				).build();
		
		this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Ender.Skeleton.CanShoot", true));
		this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Ender.AllowInfinity", false));
		this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Ender.Skeleton.LootDropWeight", 10.0));
	}
	
	@Override
	public NamespacedKey getKey() {
		return key;
	}
	
	@Override
	public String getDisplayName() {
		return item.getItemMeta().getDisplayName();
	}
	
	@Override
	public ItemStack getItem() {
		return item.clone();
	}
	
	@Override
	public void tick(AlchemicalArrowEntity arrow, Location location) {
		location.getWorld().spawnParticle(Particle.PORTAL, location, 3, 0.1, 0.1, 0.1);
	}
	
	@Override
	public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
		Arrow bukkitArrow = arrow.getArrow();
		if (!(bukkitArrow.getShooter() instanceof LivingEntity)) return;
		
		this.swapLocations(bukkitArrow, (LivingEntity) bukkitArrow.getShooter(), player);
	}
	
	@Override
	public void onHitEntity(AlchemicalArrowEntity arrow, Entity entity) {
		Arrow bukkitArrow = arrow.getArrow();
		if (!(bukkitArrow.getShooter() instanceof LivingEntity) || !(entity instanceof LivingEntity) || entity.getType() == EntityType.ARMOR_STAND) return;
		
		this.swapLocations(bukkitArrow, (LivingEntity) bukkitArrow.getShooter(), (LivingEntity) entity);
	}
	
	@Override
	public void onHitBlock(AlchemicalArrowEntity arrow, Block block) {
		if (!config.getBoolean("Arrow.Ender.Effect.TeleportOnHitBlock")) return;
		
		ProjectileSource shooter = arrow.getArrow().getShooter();
		if (!(shooter instanceof LivingEntity)) return;
		
		arrow.getArrow().remove();
		Location teleportLocation = block.getLocation().add(0.5, 1, 0.5);
		((LivingEntity) shooter).teleport(teleportLocation);
	}
	
	@Override
	public boolean onShootFromPlayer(AlchemicalArrowEntity arrow, Player player) {
		return player.hasPermission("arrows.shoot.ender");
	}
	
	private void swapLocations(Arrow source, LivingEntity shooter, LivingEntity target) {
		source.setKnockbackStrength(0);
		
		Location targetLocation = target.getLocation();
		Vector targetVelocity = target.getVelocity();
		
		// Swap player locations
		target.teleport(shooter.getLocation());
		target.setVelocity(shooter.getVelocity());
		shooter.teleport(targetLocation);
		shooter.setVelocity(targetVelocity);
		
		// Play sounds and display particles
		World world = source.getWorld();
		world.playSound(source.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 3);
		world.playSound(target.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 3);
		world.spawnParticle(Particle.PORTAL, source.getLocation(), 50, 1, 1, 1);
		world.spawnParticle(Particle.PORTAL, target.getLocation(), 5, 1, 1, 1);
	}
	
}