package wtf.choco.arrows.arrow;

import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.arrows.utils.ItemBuilder;

public class AlchemicalArrowGrapple extends AlchemicalArrow {
	
	private final ItemStack item;
	private final NamespacedKey key;
	private final FileConfiguration config;
	
	public AlchemicalArrowGrapple(AlchemicalArrows plugin) {
		this.config = plugin.getConfig();
		this.key = new NamespacedKey(plugin, "grapple");
		this.item = new ItemBuilder(Material.ARROW)
				.setName(ChatColor.translateAlternateColorCodes('&', config.getString("Arrow.Grapple.Item.DisplayName", "&eGrapple Arrow")))
				.setLore(config.getStringList("Arrow.Grapple.Item.Lore").stream()
						.map(s -> ChatColor.translateAlternateColorCodes('&', s))
						.collect(Collectors.toList())
				).build();
		
		this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Grapple.Skeleton.CanShoot", true));
		this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Grapple.AllowInfinity", false));
		this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Grapple.Skeleton.LootDropWeight", 10.0));
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
		location.getWorld().spawnParticle(Particle.CRIT, location, 3, 0.1, 0.1, 0.1, 0.1);
	}
	
	@Override
	public void onHitBlock(AlchemicalArrowEntity arrow, Block block) {
		Arrow bukkitArrow = arrow.getArrow();
		if (!(bukkitArrow.getShooter() instanceof LivingEntity)) return;
		LivingEntity shooter = (LivingEntity) bukkitArrow.getShooter();
		
		Vector grappleVelocity = bukkitArrow.getLocation().toVector().subtract(shooter.getLocation().toVector()).normalize();
		grappleVelocity.multiply(Math.max(config.getDouble("Arrow.Grapple.Effect.GrappleForce", 2.5), 4.0));
		
		shooter.setVelocity(grappleVelocity);
		bukkitArrow.getWorld().playSound(bukkitArrow.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 2);
		bukkitArrow.remove();
	}
	
	@Override
	public boolean onShootFromPlayer(AlchemicalArrowEntity arrow, Player player) {
		return player.hasPermission("arrows.shoot.grapple");
	}
	
}