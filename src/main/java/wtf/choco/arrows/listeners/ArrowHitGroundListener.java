package wtf.choco.arrows.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.jetbrains.annotations.NotNull;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.registry.ArrowStateManager;

public final class ArrowHitGroundListener implements Listener {

    private final ArrowStateManager stateManager;

    public ArrowHitGroundListener(@NotNull AlchemicalArrows plugin) {
        this.stateManager = plugin.getArrowStateManager();
    }

    @EventHandler
    public void onAlchemicalArrowHitGround(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow)) return;

        Block hit = event.getHitBlock();
        if (hit == null) return;

        Arrow arrow = (Arrow) event.getEntity();
        AlchemicalArrowEntity alchemicalArrow = stateManager.get(arrow);
        if (alchemicalArrow == null) return;

        AlchemicalArrow type = alchemicalArrow.getImplementation();
        type.hitGroundEventHandler(alchemicalArrow, event);
        type.onHitBlock(alchemicalArrow, hit);
    }

}