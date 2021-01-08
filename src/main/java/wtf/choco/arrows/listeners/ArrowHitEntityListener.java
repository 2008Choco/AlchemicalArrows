package wtf.choco.arrows.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;

public final class ArrowHitEntityListener implements Listener {

    private final AlchemicalArrows plugin;

    public ArrowHitEntityListener(AlchemicalArrows plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAlchemicalArrowHitEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Arrow) || event.getEntity() instanceof Player) {
            return;
        }

        AlchemicalArrowEntity alchemicalArrow = plugin.getArrowStateManager().get((Arrow) event.getDamager());
        if (alchemicalArrow == null) {
            return;
        }

        AlchemicalArrow type = alchemicalArrow.getImplementation();
        type.hitEntityEventHandler(alchemicalArrow, event);
        type.onHitEntity(alchemicalArrow, event.getEntity());
    }

}
