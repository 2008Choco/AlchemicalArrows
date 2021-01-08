package wtf.choco.arrows.util;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.registry.ArrowStateManager;

public final class ArrowUpdateTask extends BukkitRunnable {

    private static ArrowUpdateTask instance = null;

    private final List<AlchemicalArrowEntity> purgeBuffer = new ArrayList<>(16);
    private final ArrowStateManager stateManager;

    private ArrowUpdateTask(@NotNull ArrowStateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void run() {
        for (AlchemicalArrowEntity arrow : stateManager.getArrows()) {
            Arrow bukkitArrow = arrow.getArrow();
            if (!bukkitArrow.isValid()) {
                this.purgeBuffer.add(arrow);
                continue;
            }

            arrow.getImplementation().tick(arrow, bukkitArrow.getLocation());
        }

        if (purgeBuffer.size() >= 1) {
            this.purgeBuffer.forEach(stateManager::remove);
            this.purgeBuffer.clear();
        }
    }

    @NotNull
    public static ArrowUpdateTask startArrowUpdateTask(@NotNull AlchemicalArrows plugin) {
        Preconditions.checkNotNull(plugin, "Cannot pass null instance of plugin");

        if (instance == null) {
            instance = new ArrowUpdateTask(plugin.getArrowStateManager());
            instance.runTaskTimer(plugin, 0, 1);
        }

        return instance;
    }

}
