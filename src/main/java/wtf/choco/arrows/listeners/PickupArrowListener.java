package wtf.choco.arrows.listeners;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Arrow.PickupStatus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.jetbrains.annotations.NotNull;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.registry.ArrowRegistry;

public class PickupArrowListener implements Listener {

    private final ArrowRegistry arrowRegistry;

    public PickupArrowListener(@NotNull AlchemicalArrows plugin) {
        this.arrowRegistry = plugin.getArrowRegistry();
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPickupArrow(PlayerPickupArrowEvent event) {
        Arrow arrow = event.getArrow();
        AlchemicalArrowEntity alchemicalArrow = arrowRegistry.getAlchemicalArrow(arrow);
        if (alchemicalArrow == null) return;

        Player player = event.getPlayer();
        if ((arrow.getPickupStatus() == PickupStatus.CREATIVE_ONLY && player.getGameMode() != GameMode.CREATIVE)
                || arrow.getPickupStatus() == PickupStatus.DISALLOWED) {
            return;
        }

        event.setCancelled(true);
        arrow.remove();

        player.getInventory().addItem(alchemicalArrow.getImplementation().getItem());
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
    }

}