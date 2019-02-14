package wtf.choco.arrows.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.registry.ArrowRegistry;

public class ArrowHitEntityListener implements Listener {

	private final ArrowRegistry arrowRegistry;

	public ArrowHitEntityListener(AlchemicalArrows plugin) {
		this.arrowRegistry = plugin.getArrowRegistry();
	}

	@EventHandler
	public void onAlchemicalArrowHitEntity(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Arrow) || event.getEntity() instanceof Player) return;

		Arrow arrow = (Arrow) event.getDamager();
		Entity entity = event.getEntity();

		AlchemicalArrowEntity alchemicalArrow = arrowRegistry.getAlchemicalArrow(arrow);
		if (alchemicalArrow == null) return;

		AlchemicalArrow type = alchemicalArrow.getImplementation();
		type.hitEntityEventHandler(alchemicalArrow, event);
		type.onHitEntity(alchemicalArrow, entity);
	}

}