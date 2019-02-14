package wtf.choco.arrows.utils;

import com.google.common.base.Preconditions;

import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitRunnable;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.registry.ArrowRegistry;

public final class ArrowUpdateTask extends BukkitRunnable {

	private static ArrowUpdateTask instance = null;

	private final ArrowRegistry arrowRegistry;

	private ArrowUpdateTask(AlchemicalArrows plugin) {
		this.arrowRegistry = plugin.getArrowRegistry();
	}

	@Override
	public void run() {
		for (AlchemicalArrowEntity arrow : arrowRegistry.getAlchemicalArrows()) {
			Arrow bukkitArrow = arrow.getArrow();
			if (bukkitArrow.isDead() || !bukkitArrow.isValid()) {
				this.arrowRegistry.removeAlchemicalArrow(arrow);
				continue;
			}

			arrow.getImplementation().tick(arrow, bukkitArrow.getLocation());
		}

		if (this.arrowRegistry.getArrowsToPurge() > 0) {
			this.arrowRegistry.purgeArrows();
		}
	}

	public static ArrowUpdateTask startArrowUpdateTask(AlchemicalArrows plugin) {
		Preconditions.checkNotNull(plugin, "Cannot pass null instance of plugin");

		if (instance == null) {
			instance = new ArrowUpdateTask(plugin);
			instance.runTaskTimer(plugin, 0, 1);
		}

		return instance;
	}

}