package wtf.choco.arrows.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.registry.ArrowStateManager;

public final class ArrowHitEntityListener implements Listener {

    private final ArrowStateManager stateManager;

    public ArrowHitEntityListener(@NotNull AlchemicalArrows plugin) {
        this.stateManager = plugin.getArrowStateManager();
    }

    @EventHandler
    public void onAlchemicalArrowHitEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Arrow) || event.getEntity() instanceof Player) return;

        Arrow arrow = (Arrow) event.getDamager();
        Entity entity = event.getEntity();

        AlchemicalArrowEntity alchemicalArrow = stateManager.get(arrow);
        if (alchemicalArrow == null) return;

        AlchemicalArrow type = alchemicalArrow.getImplementation();
        type.hitEntityEventHandler(alchemicalArrow, event);
        type.onHitEntity(alchemicalArrow, entity);
    }

}