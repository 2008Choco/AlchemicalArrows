package wtf.choco.arrows.events;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.registry.ArrowRegistry;

public class ArrowHitPlayerListener implements Listener {
	
	private final AlchemicalArrows plugin;
	private final ArrowRegistry arrowRegistry;
	
	public ArrowHitPlayerListener(AlchemicalArrows plugin) {
		this.plugin = plugin;
		this.arrowRegistry = plugin.getArrowRegistry();
	}
	
	@EventHandler
	public void onAlchemicalArrowHitPlayer(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Arrow) || !(event.getEntity() instanceof Player)) return;
		
		Player player = (Player) event.getEntity();
		Arrow arrow = (Arrow) event.getDamager();
		AlchemicalArrowEntity alchemicalArrow = arrowRegistry.getAlchemicalArrow(arrow);
		
		if (alchemicalArrow == null || player.isBlocking()) return;
		
		/* WorldGuard Support */
		if (plugin.isWorldGuardSupported()) {
			// Check state of shooter
			if (arrow.getShooter() instanceof Player) {
				Player shooter = (Player) arrow.getShooter();
				ApplicableRegionSet shooterRegions = WGBukkit.getRegionManager(shooter.getWorld()).getApplicableRegions(shooter.getLocation());
				
				if (!shooter.hasPermission("arrows.worldguardoverride") && 
						shooterRegions.queryState(null, DefaultFlag.PVP) != null && shooterRegions.queryState(null, DefaultFlag.PVP) == State.DENY) {
					shooter.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "You cannot hit a player whilst protected by PvP");
					event.setCancelled(true);
					return;
				}
			}
			
			// Check state of damaged
			ApplicableRegionSet damagedRegions = WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
			if (!damagedRegions.testState(null, DefaultFlag.PVP) || event.isCancelled()) {
				if (arrow.getShooter() instanceof Player){
					((Player) arrow.getShooter()).sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "This player is protected from PvP");
				}
				
				event.setCancelled(true);
				return;
			}
		}
		
		// AlchemicalArrows arrow handling
		AlchemicalArrow type = alchemicalArrow.getImplementation();
		type.hitEntityEventHandler(alchemicalArrow, event);
		type.onHitPlayer(alchemicalArrow, player);
	}
	
}