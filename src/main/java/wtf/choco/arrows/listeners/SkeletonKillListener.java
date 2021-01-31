package wtf.choco.arrows.listeners;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.property.ArrowProperty;

public final class SkeletonKillListener implements Listener {

    private static final Random RANDOM = new Random();

    private final AlchemicalArrows plugin;

    public SkeletonKillListener(AlchemicalArrows plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onKillSkeleton(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Skeleton) || RANDOM.nextInt(100) >= plugin.getConfig().getDouble("Skeletons.LootDropChance", 15.0)) {
            return;
        }

        List<ItemStack> drops = event.getDrops();
        drops.removeIf(i -> i.getType() == Material.ARROW);

        ItemStack toDrop = getWeightedRandom();
        if (toDrop == null) {
            return;
        }

        drops.add(toDrop);
    }

    @Nullable
    public ItemStack getWeightedRandom() {
        double totalWeight = 0;

        Collection<AlchemicalArrow> arrows = plugin.getArrowRegistry().getRegisteredArrows();
        for (AlchemicalArrow arrow : arrows) {
            totalWeight += arrow.getProperties().getProperty(ArrowProperty.SKELETON_LOOT_WEIGHT).getAsDouble();
        }

        ItemStack item = null;
        double randomValue = RANDOM.nextDouble() * totalWeight;

        for (AlchemicalArrow arrow : arrows) {
            randomValue -= arrow.getProperties().getProperty(ArrowProperty.SKELETON_LOOT_WEIGHT).getAsDouble();

            if (randomValue <= 0) {
                item = arrow.createItemStack();
                break;
            }
        }

        if (item == null) {
            return null;
        }

        item.setAmount(RANDOM.nextInt(2) + 1);
        return item;
    }

}
