package wtf.choco.arrows.events;

import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.registry.ArrowRegistry;

public class ArrowHitGroundListener implements Listener {
	
	private final ArrowRegistry arrowRegistry;
	
	public ArrowHitGroundListener(AlchemicalArrows plugin) {
		this.arrowRegistry = plugin.getArrowRegistry();
	}
	
	@EventHandler
	public void onAlchemicalArrowHitGround(ProjectileHitEvent event) {
		if (!(event.getEntity() instanceof Arrow)) return;
		if (event.getHitBlock() == null) return;
		
		Arrow arrow = (Arrow) event.getEntity();
		AlchemicalArrowEntity alchemicalArrow = arrowRegistry.getAlchemicalArrow(arrow);
		if (alchemicalArrow == null) return;
		
		AlchemicalArrow type = alchemicalArrow.getImplementation();
		type.hitGroundEventHandler(alchemicalArrow, event);
		type.onHitBlock(alchemicalArrow, event.getHitBlock());
	}
	
}