package wtf.choco.arrows.listeners;

import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.jetbrains.annotations.NotNull;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.registry.ArrowStateManager;

public final class PickupArrowListener implements Listener {

    private final ArrowStateManager stateManager;

    public PickupArrowListener(@NotNull AlchemicalArrows plugin) {
    	this.stateManager = plugin.getArrowStateManager();
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPickupArrow(PlayerPickupArrowEvent event) {
        AbstractArrow arrow = event.getArrow();
        if (!(arrow instanceof Arrow)) return;

        AlchemicalArrowEntity alchemicalArrow = stateManager.get((Arrow) arrow);
        if (alchemicalArrow == null) return;

        event.getItem().setItemStack(alchemicalArrow.getImplementation().getItem());
    }

}