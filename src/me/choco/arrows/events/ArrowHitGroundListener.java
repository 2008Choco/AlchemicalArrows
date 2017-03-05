package me.choco.arrows.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.BlockIterator;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.registry.ArrowRegistry;

public class ArrowHitGroundListener implements Listener {
	
	private final ArrowRegistry arrowRegistry;
	
	public ArrowHitGroundListener(AlchemicalArrows plugin){
		this.arrowRegistry = plugin.getArrowRegistry();
	}
	
	@EventHandler
	public void onHitGround(ProjectileHitEvent event){
		if (!(event.getEntity() instanceof Arrow)) return;
		
		Arrow arrow = (Arrow) event.getEntity();
		if (!this.arrowRegistry.isAlchemicalArrow(arrow)) return;
		
		BlockIterator it = new BlockIterator(arrow.getWorld(), arrow.getLocation().toVector(), arrow.getVelocity().normalize(), 0, 4);
		
		Block hitBlock = null;
		while (it.hasNext()){
			hitBlock = it.next();
			if (hitBlock.getType().isSolid()) break;
		}
		
		AlchemicalArrow aarrow = this.arrowRegistry.getAlchemicalArrow(arrow);
		aarrow.hitGroundEventHandler(event);
		aarrow.onHitGround(hitBlock);
	}
}