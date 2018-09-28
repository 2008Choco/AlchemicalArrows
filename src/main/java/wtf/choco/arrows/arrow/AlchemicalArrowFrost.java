package wtf.choco.arrows.arrow;

import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.arrows.utils.ItemBuilder;

public class AlchemicalArrowFrost extends AlchemicalArrow {
	
	private static final PotionEffect SLOWNESS_EFFECT = new PotionEffect(PotionEffectType.SLOW, 100, 254);
	private static final PotionEffect ANTI_JUMP_EFFECT = new PotionEffect(PotionEffectType.JUMP, 100, 500);
	
	private final ItemStack item;
	private final NamespacedKey key;
	private final FileConfiguration config;
	
	public AlchemicalArrowFrost(AlchemicalArrows plugin) {
		this.config = plugin.getConfig();
		this.key = new NamespacedKey(plugin, "frost");
		this.item = new ItemBuilder(Material.ARROW)
				.setName(ChatColor.translateAlternateColorCodes('&', config.getString("Arrow.Frost.Item.DisplayName", "&bFrost Arrow")))
				.setLore(config.getStringList("Arrow.Frost.Item.Lore").stream()
						.map(s -> ChatColor.translateAlternateColorCodes('&', s))
						.collect(Collectors.toList())
				).build();
		
		this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Frost.Skeleton.CanShoot", true));
		this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Frost.AllowInfinity", false));
		this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Frost.Skeleton.LootDropWeight", 10.0));
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
	
	@Override
	public boolean onShootFromPlayer(AlchemicalArrowEntity arrow, Player player) {
		return player.hasPermission("arrows.shoot.frost");
	}
	
	private void freezeRadius(AlchemicalArrowEntity arrow, Location location) {
		double radius = Math.min(config.getDouble("Arrow.Frost.Effect.WaterFreezeRadius", 3.5), 7.0);
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