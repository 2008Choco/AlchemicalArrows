package wtf.choco.arrows.crafting;

import com.google.common.base.Preconditions;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import org.jetbrains.annotations.NotNull;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.event.CauldronCraftEvent;
import wtf.choco.arrows.registry.CauldronManager;

public class CauldronUpdateTask extends BukkitRunnable {

    private static CauldronUpdateTask instance = null;

    private final CauldronManager cauldronManager;

    private CauldronUpdateTask(@NotNull AlchemicalArrows plugin) {
        this.cauldronManager = plugin.getCauldronManager();
    }

    @Override
    public void run() {
        for (AlchemicalCauldron cauldron : cauldronManager.getAlchemicalCauldrons()) {
            Block block = cauldron.getCauldronBlock();
            Location location = block.getLocation().add(0.5, 0.25, 0.5);
            World world = block.getWorld();

            // Unheat if invalid
            if (!cauldron.canHeatUp()) {
                cauldron.stopHeatingUp();
                cauldron.setBubbling(false);

                // Drop ingredients if any
                if (!cauldron.hasIngredients()) {
                    continue;
                }

                cauldron.getIngredients().forEach((m, a) -> world.dropItem(location, new ItemStack(m, a)));
                cauldron.clearIngredients();
                continue;
            }

            // Attempt to heat cauldron (if valid)
            if (!cauldron.isBubbling() && !cauldron.isHeatingUp() && !cauldron.attemptToHeatUp()) {
                continue;
            }

            // Prepare bubbling cauldrons
            if (cauldron.isHeatingUp()) {
                if (System.currentTimeMillis() - cauldron.getHeatingStartTime() < AlchemicalCauldron.REQUIRED_BUBBLING_MS) {
                    continue;
                }

                cauldron.stopHeatingUp();
                cauldron.setBubbling(true);
            }

            world.spawnParticle(Particle.WATER_BUBBLE, block.getLocation().add(0.5, 0.95, 0.5), 1, 0.175F, 0F, 0.175F, 0F);

            // Dissolve items in bubbling cauldrons
            world.getNearbyEntities(location, 0.5, 0.5, 0.5).stream()
                .filter(e -> e instanceof Item)
                .forEach(e -> {
                    Item item = (Item) e;
                    cauldron.addIngredient(item.getItemStack());
                    item.remove();

                    world.playSound(location, Sound.ENTITY_PLAYER_SPLASH, 1F, 2F);
                });

            // Attempt crafting recipes in bubbling cauldrons
            if (CauldronRecipe.CATALYSTS.size() == 0) {
                return;
            }

            for (Material catalyst : CauldronRecipe.CATALYSTS) {
                while (cauldron.hasIngredient(catalyst)) {
                    CauldronRecipe activeRecipe = cauldronManager.getApplicableRecipe(catalyst, cauldron.getIngredients());
                    if (activeRecipe == null) {
                        return;
                    }

                    CauldronCraftEvent ccEvent = new CauldronCraftEvent(cauldron, activeRecipe);
                    Bukkit.getPluginManager().callEvent(ccEvent);
                    if (ccEvent.isCancelled()) {
                        break;
                    }

                    ThreadLocalRandom random = ThreadLocalRandom.current();
                    Vector itemVelocity = new Vector(random.nextDouble() / 10.0, 0.2 + (random.nextDouble() / 2), random.nextDouble() / 10.0);

                    AlchemicalArrow result = ccEvent.getResult();
                    if (result != null) {
                        world.dropItem(block.getLocation().add(0.5, 1.1, 0.5), result.createItemStack()).setVelocity(itemVelocity);
                    }

                    if (ccEvent.shouldConsumeIngredients()) {
                        cauldron.removeIngredients(activeRecipe);
                        cauldron.removeIngredient(catalyst, 1);
                    }

                    world.playSound(location, Sound.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, 1F, 1.5F);
                }
            }
        }
    }

    @NotNull
    public static CauldronUpdateTask startTask(@NotNull AlchemicalArrows plugin) {
        Preconditions.checkNotNull(plugin, "Cannot start task with null plugin instance");

        if (instance == null) {
            instance = new CauldronUpdateTask(plugin);
            instance.runTaskTimer(plugin, 0, 10);
        }

        return instance;
    }

}