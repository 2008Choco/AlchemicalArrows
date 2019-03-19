package wtf.choco.arrows.commands;

import static wtf.choco.arrows.AlchemicalArrows.CHAT_PREFIX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.registry.ArrowRegistry;

public class GiveArrowCommand implements CommandExecutor {

	public static final TabCompleter TAB_COMPLETER = (s, c, l, args) -> {
		if (args.length != 1) return null;

		List<String> arguments = new ArrayList<>();
		for (AlchemicalArrow arrow : ArrowRegistry.getRegisteredCustomArrows()) {
			NamespacedKey key = arrow.getKey();
			if (key.toString().startsWith(args[0]) || key.getKey().startsWith(args[0])) {
				arguments.add(arrow.getKey().toString());
			}
		}

		return arguments;
	};

	private final AlchemicalArrows plugin;

	public GiveArrowCommand(@NotNull AlchemicalArrows plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("arrows.command.givearrow")) {
			sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "You have insufficient permissions to execute this command");
			return true;
		}

		if (args.length < 1) {
			sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.GRAY + "Missing parameter. " + ChatColor.YELLOW + "/" + label + " <arrow> [count] [player]");
			return true;
		}

		int giveCount = (args.length >= 2) ? clamp(NumberUtils.toInt(args[1], 1), 1, 64) : 1;
		List<Player> targets = (sender instanceof Player) ? Arrays.asList((Player) sender) : Collections.EMPTY_LIST;

		if (args.length >= 3) {
			try {
				List<Player> potentialTargets = Bukkit.selectEntities(sender, args[2]).stream()
						.filter(e -> e.getType() == EntityType.PLAYER)
						.map(e -> (Player) e).collect(Collectors.toList());

				if (potentialTargets.size() == 0) {
					sender.sendMessage(CHAT_PREFIX + "Malformed player selector. You must specify either an online player name, UUID or selector (i.e. @p, @a, @s or @r)");
					return true;
				}

				targets = potentialTargets;
			} catch (IllegalArgumentException e) {
				sender.sendMessage(CHAT_PREFIX + "Malformed player selector. You must specify either a player name, UUID or selector (i.e. @p, @a, @s or @r)");
				return true;
			}
		}

		if (targets.isEmpty()) {
			sender.sendMessage(CHAT_PREFIX + "A player name must be specified in order to execute this command from the console");
			return true;
		}

		String arrowNamespace = args[0].toLowerCase();

		if (!arrowNamespace.contains(":")) {
			arrowNamespace = plugin.getName().toLowerCase() + ":" + arrowNamespace;
		} else if (arrowNamespace.startsWith(":") || arrowNamespace.split(":").length > 2) {
			sender.sendMessage(CHAT_PREFIX + "Invalid namespace. Pattern IDs should be formatted as " + ChatColor.YELLOW + "namespace:id"
					+ ChatColor.GRAY + " (for example, " + ChatColor.YELLOW + "alchemicalarrows:air" + ChatColor.GRAY + ")");
			return true;
		}

		AlchemicalArrow arrow = ArrowRegistry.getCustomArrow(arrowNamespace);
		if (arrow == null) {
			sender.sendMessage(CHAT_PREFIX + "Could not find an arrow with the ID " + ChatColor.YELLOW + arrowNamespace);
			return true;
		}

		ItemStack itemToGive = arrow.getItem().clone();
		itemToGive.setAmount(giveCount);
		targets.forEach(p -> p.getInventory().addItem(itemToGive));

		if (targets.size() == 1) {
			Player target = targets.get(0);
			sender.sendMessage(CHAT_PREFIX + "Successfully given " + ChatColor.GREEN + giveCount + ChatColor.GRAY + " of " + arrow.getDisplayName() + ChatColor.GRAY + (target == sender ? "" : " to " + target.getName()));
		} else {
			sender.sendMessage(CHAT_PREFIX + "Successfully given " + ChatColor.GREEN + giveCount + ChatColor.GRAY + " of " + arrow.getDisplayName() + ChatColor.GRAY + " to " + targets.size() + " players");
		}

		return true;
	}

	private int clamp(int value, int min, int max) {
		return (value < min ? min : (value > max ? max : value));
	}

}