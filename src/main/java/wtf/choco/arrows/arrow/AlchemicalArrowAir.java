package wtf.choco.arrows.arrow;

import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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
import org.bukkit.inventory.ItemStack;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.arrows.utils.ItemBuilder;

public class AlchemicalArrowAir extends AlchemicalArrow {
	
	private static final Random RANDOM = new Random();
	private static final int BREATH_RADIUS_LIMIT = 4;
	
	private int lastTick = 10;
	
	private final ItemStack item;
	private final NamespacedKey key;
	private final FileConfiguration config;
	
	public AlchemicalArrowAir(AlchemicalArrows plugin) {
		this.config = plugin.getConfig();
		this.key = new NamespacedKey(plugin, "air");
		this.item = ItemBuilder.of(Material.ARROW)
				.name(ChatColor.translateAlternateColorCodes('&', config.getString("Arrow.Air.Item.DisplayName", "&oAir Arrow")))
				.lore(config.getStringList("Arrow.Air.Item.Lore").stream()
						.map(s -> ChatColor.translateAlternateColorCodes('&', s))
						.collect(Collectors.toList())
				).build();
		
		this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Air.Skeleton.CanShoot", true));
		this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Air.AllowInfinity", false));
		this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Air.Skeleton.LootDropWeight", 10.0));
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
	
	@Override
	public boolean onShootFromPlayer(AlchemicalArrowEntity arrow, Player player) {
		return player.hasPermission("arrows.shoot.air");
	}
	
}