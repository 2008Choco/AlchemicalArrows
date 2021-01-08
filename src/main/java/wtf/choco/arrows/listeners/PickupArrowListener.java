package wtf.choco.arrows.listeners;

import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupArrowEvent;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;

public final class PickupArrowListener implements Listener {

    private final AlchemicalArrows plugin;

    public PickupArrowListener(AlchemicalArrows plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPickupArrow(PlayerPickupArrowEvent event) {
        AbstractArrow arrow = event.getArrow();
        if (!(arrow instanceof Arrow)) {
            return;
        }

        AlchemicalArrowEntity alchemicalArrow = plugin.getArrowStateManager().get((Arrow) arrow);
        if (alchemicalArrow == null) {
            return;
        }

        event.getItem().setItemStack(alchemicalArrow.getImplementation().createItemStack());
    }

}
