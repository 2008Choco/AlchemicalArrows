package wtf.choco.arrows.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;

public final class ArrowHitGroundListener implements Listener {

    private final AlchemicalArrows plugin;

    public ArrowHitGroundListener(AlchemicalArrows plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAlchemicalArrowHitGround(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow)) {
            return;
        }

        Block block = event.getHitBlock();
        if (block == null) {
            return;
        }

        AlchemicalArrowEntity alchemicalArrow = plugin.getArrowStateManager().get(arrow);
        if (alchemicalArrow == null) {
            return;
        }

        AlchemicalArrow type = alchemicalArrow.getImplementation();
        type.hitGroundEventHandler(alchemicalArrow, event);
        type.onHitBlock(alchemicalArrow, block);
    }

}
