package wtf.choco.arrows.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;

public final class CrossbowLoadListener implements Listener {

    private final AlchemicalArrows plugin;

    public CrossbowLoadListener(AlchemicalArrows plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onLoadCrossbow(InventoryClickEvent event) {
        if (!plugin.getConfig().getBoolean("Inventory.CrossbowLoading", true)) {
            return;
        }

        ItemStack crossbow = event.getCurrentItem();
        if (crossbow == null || crossbow.getType() != Material.CROSSBOW) {
            return;
        }

        CrossbowMeta crossbowMeta = (CrossbowMeta) crossbow.getItemMeta();
        int allowedChargedProjectiles = crossbowMeta.hasEnchant(Enchantment.MULTISHOT) ? 3 : 1;
        if (crossbowMeta.getChargedProjectiles().size() >= allowedChargedProjectiles) {
            return;
        }

        ItemStack arrow = event.getCursor();
        if (arrow == null) {
            return;
        }

        AlchemicalArrow alchemicalArrow = plugin.getArrowRegistry().get(arrow);
        if (alchemicalArrow == null) {
            return;
        }

        HumanEntity player = event.getWhoClicked();
        if (player.getGameMode() == GameMode.CREATIVE) {
            player.sendMessage("This feature is disabled for creative mode players due to it being non-functional on the part of vanilla Minecraft. Sorry :(");
            return;
        }

        ItemStack arrowToLoad = arrow.clone();
        arrowToLoad.setAmount(1);
        crossbowMeta.addChargedProjectile(arrowToLoad);
        crossbow.setItemMeta(crossbowMeta);
        event.setCurrentItem(crossbow);

        event.setCancelled(true);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            arrow.setAmount(arrow.getAmount() - 1);

            player.setItemOnCursor(arrow.getAmount() <= 0 ? null : arrow);

            // Play a click sound
            if (player instanceof Player) {
                ((Player) player).playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.5F, 1.4F);
            }
        }, 1L);
    }

}
