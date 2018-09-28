package wtf.choco.arrows.events;

import java.util.Random;
import java.util.Set;

import com.google.common.collect.Iterables;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Arrow.PickupStatus;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.projectiles.BlockProjectileSource;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.event.AlchemicalArrowShootEvent;
import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.arrows.registry.ArrowRegistry;

public class ProjectileShootListener implements Listener {

	private static final Random RANDOM = new Random();
	
	private final FileConfiguration config;
	private final ArrowRegistry arrowRegistry;
	
	public ProjectileShootListener(AlchemicalArrows plugin) {
		this.config = plugin.getConfig();
		this.arrowRegistry = plugin.getArrowRegistry();
	}
	
	@EventHandler
	public void onShootArrow(ProjectileLaunchEvent event) {
		if (!(event.getEntity() instanceof Arrow)) return;
		Arrow arrow = (Arrow) event.getEntity();
		
		if (arrow.getShooter() instanceof Player) {
			Player player = (Player) arrow.getShooter();
			PlayerInventory inventory = player.getInventory();
			if (!inventory.contains(Material.ARROW)) return;
			
			// Register the arrow if it's in the arrow registry
			int arrowSlot = (isShotFromMainHand(player) ? inventory.first(Material.ARROW) : inventory.getHeldItemSlot());
			ItemStack arrowItem = inventory.getItem(arrowSlot);
			
			if (arrowItem == null || arrowItem.getType() != Material.ARROW) {
				arrowSlot = inventory.first(Material.ARROW);
				arrowItem = inventory.getItem(arrowSlot);
			}
			
			AlchemicalArrow type = ArrowRegistry.getCustomArrow(arrowItem);
			if (type == null) return;
			
			AlchemicalArrowEntity alchemicalArrow = type.createNewArrow(arrow);
			type.shootEventHandler(alchemicalArrow, event);
			if (!type.onShootFromPlayer(alchemicalArrow, player)) {
				event.setCancelled(true);
				return;
			}
			
			if (type.getProperties().getPropertyValue(ArrowProperty.ALLOW_INFINITY)) return;
			
			boolean shouldBePickupable = (player.getGameMode() != GameMode.CREATIVE);
			
			ItemStack mainHand = inventory.getItemInMainHand(), offHand = inventory.getItemInOffHand();
			if ((mainHand != null && mainHand.containsEnchantment(Enchantment.ARROW_INFINITE))
				|| (offHand != null && offHand.containsEnchantment(Enchantment.ARROW_INFINITE))) {
				
				shouldBePickupable = false;
				
				if (arrowItem.getAmount() > 1) {
					arrowItem.setAmount(arrowItem.getAmount() - 1);
				} else {
					inventory.setItem(arrowSlot, null); 
				}
			}
			
			AlchemicalArrowShootEvent aasEvent = new AlchemicalArrowShootEvent(alchemicalArrow, arrow.getShooter());
			Bukkit.getPluginManager().callEvent(aasEvent);
			if (aasEvent.isCancelled()) {
				event.setCancelled(true);
				return;
			}
			
			this.arrowRegistry.addAlchemicalArrow(alchemicalArrow);
			arrow.setPickupStatus(shouldBePickupable ? PickupStatus.ALLOWED : PickupStatus.CREATIVE_ONLY);
		}
		
		else if (arrow.getShooter() instanceof Skeleton && RANDOM.nextInt(100) < config.getDouble("Skeletons.ShootPercentage", 10.0)) {
			Set<AlchemicalArrow> arrows = ArrowRegistry.getRegisteredCustomArrows();
			AlchemicalArrow type = Iterables.get(arrows, RANDOM.nextInt(arrows.size()));
			if (type == null || !type.getProperties().getPropertyValue(ArrowProperty.SKELETONS_CAN_SHOOT)) return;
			
			AlchemicalArrowEntity alchemicalArrow = type.createNewArrow(arrow);
			if (!type.onShootFromSkeleton(alchemicalArrow, (Skeleton) arrow.getShooter())) {
				event.setCancelled(true);
				return;
			}
			
			AlchemicalArrowShootEvent aasEvent = new AlchemicalArrowShootEvent(alchemicalArrow, arrow.getShooter());
			Bukkit.getPluginManager().callEvent(aasEvent);
			if (aasEvent.isCancelled()) {
				event.setCancelled(true);
				return;
			}
			
			this.arrowRegistry.addAlchemicalArrow(alchemicalArrow);
		}
		
		else if (arrow.getShooter() instanceof BlockProjectileSource) {
//			TODO:
//			BlockProjectileSource bps = (BlockProjectileSource) arrow.getShooter();
//			plugin.getArrowRegistry().getAlchemicalArrow(arrow).onShootFromBlockSource(bps);
		}
	}
	
	private boolean isShotFromMainHand(Player player) {
		ItemStack mainHand = player.getInventory().getItemInMainHand();
		ItemStack offHand = player.getInventory().getItemInOffHand();
		
		return ((mainHand != null && mainHand.getType() == Material.BOW) || 
				(mainHand == null && offHand != null && offHand.getType() == Material.BOW));
	}
	
}