package me.choco.arrows.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.BlockIterator;

import me.choco.arrows.AlchemicalArrows;

public class ArrowHitGround implements Listener{
	
	AlchemicalArrows plugin;
	public ArrowHitGround(AlchemicalArrows plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onHitGround(ProjectileHitEvent event){
		if (!(event.getEntity() instanceof Arrow)) return;
		
		Arrow arrow = (Arrow) event.getEntity();
		if (plugin.getArrowRegistry().isAlchemicalArrow(arrow)){
			BlockIterator it = new BlockIterator(arrow.getWorld(), arrow.getLocation().toVector(), arrow.getVelocity().normalize(), 0, 4);
			
			Block hitBlock = null;
			while (it.hasNext()){
				hitBlock = it.next();
				if (hitBlock.getType().isSolid()) break;
			}
			
			plugin.getArrowRegistry().getAlchemicalArrow(arrow).onHitGround(hitBlock);
		}
	}
}