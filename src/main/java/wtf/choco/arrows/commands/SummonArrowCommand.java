package wtf.choco.arrows.commands;

import static wtf.choco.arrows.AlchemicalArrows.CHAT_PREFIX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.bukkit.util.Vector;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.registry.ArrowRegistry;
import wtf.choco.arrows.registry.ArrowStateManager;
import wtf.choco.arrows.utils.CommandUtil;

public class SummonArrowCommand implements CommandExecutor {

	// /summonarrow <arrow> [x] [y] [z] [world] [velocityX] [velocityY] [velocityZ]

	public static final TabCompleter TAB_COMPLETER = (s, c, l, args) -> {
		if (args.length >= 6) return null;

		if (args.length == 1) {
			List<String> arguments = new ArrayList<>();

			for (AlchemicalArrow arrow : AlchemicalArrows.getInstance().getArrowRegistry().getRegisteredArrows()) {
				NamespacedKey key = arrow.getKey();
				if (key.toString().startsWith(args[0]) || key.getKey().startsWith(args[0])) {
					arguments.add(arrow.getKey().toString());
				}
			}

			return arguments;
		}

		if (args.length >= 2 && args.length < 5) {
			return Arrays.asList("~");
		}

		// At this point, only the world is left to complete
		return StringUtil.copyPartialMatches(args[4], Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.toList()), new ArrayList<>());
	};

	private final AlchemicalArrows plugin;
	private final ArrowRegistry arrowRegistry;
	private final ArrowStateManager stateManager;

	public SummonArrowCommand(AlchemicalArrows plugin) {
		this.plugin = plugin;
		this.arrowRegistry = plugin.getArrowRegistry();
		this.stateManager = plugin.getArrowStateManager();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("arrows.command.summonarrow")) {
			sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "You have insufficient permissions to execute this command");
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.GRAY + "Missing parameter. " + ChatColor.YELLOW + "/" + label + " <arrow>");
			return true;
		}

		String arrowId = CommandUtil.argToNamespace(args[0], plugin);
		if (arrowId == null) {
			sender.sendMessage(CHAT_PREFIX + "Invalid namespace. Pattern IDs should be formatted as " + ChatColor.YELLOW + "namespace:id"
					+ ChatColor.GRAY + " (for example, " + ChatColor.YELLOW + "alchemicalarrows:air" + ChatColor.GRAY + ")");
			return true;
		}

		AlchemicalArrow arrow = arrowRegistry.get(arrowId);
		if (arrow == null) {
			sender.sendMessage(CHAT_PREFIX + "Could not find an arrow with the ID " + ChatColor.YELLOW + arrowId);
			return true;
		}

		if (!(sender instanceof Player) && args.length < 5) { // No x, y, z or world provided from console... we've got a problem here
			sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Insufficient arguments provided (from the console). An x, y, z and world must be specified.");
			return true;
		}

		Entity entitySender = (sender instanceof Entity) ? (Entity) sender : null;
		double x = (entitySender != null && (args.length < 2 || args[1].equals("~"))) ? entitySender.getLocation().getX() : NumberUtils.toDouble(args[1], Integer.MIN_VALUE);
		double y = (entitySender != null && (args.length < 3 || args[1].equals("~"))) ? entitySender.getLocation().getY() : NumberUtils.toDouble(args[2], Integer.MIN_VALUE);
		double z = (entitySender != null && (args.length < 4 || args[1].equals("~"))) ? entitySender.getLocation().getZ() : NumberUtils.toDouble(args[3], Integer.MIN_VALUE);

		if (x == Integer.MIN_VALUE || y == Integer.MIN_VALUE || z == Integer.MIN_VALUE) {
			sender.sendMessage(CHAT_PREFIX + "Invalid " + ChatColor.YELLOW + "spawn coordinates " + ChatColor.GRAY + "were provided... were they proper numbers?");
			return true;
		}

		World world = (entitySender != null && args.length < 5) ? entitySender.getWorld() : Bukkit.getWorld(args[4]);
		if (world == null) {
			sender.sendMessage(CHAT_PREFIX + "Invalid " + ChatColor.YELLOW + "world name " + ChatColor.GRAY + "was provided... does it exist?");
			return true;
		}

		float velocityX = args.length >= 6 ? CommandUtil.clamp(NumberUtils.toFloat(args[5]), -4.0F, 4.0F) : 0.0F;
		float velocityY = args.length >= 7 ? CommandUtil.clamp(NumberUtils.toFloat(args[6]), -4.0F, 4.0F) : 0.0F;
		float velocityZ = args.length >= 8 ? CommandUtil.clamp(NumberUtils.toFloat(args[7]), -4.0F, 4.0F) : 0.0F;
		Vector direction = new Vector(velocityX, velocityY, velocityZ);

		Arrow bukkitArrow = world.spawnArrow(new Location(world, x, y, z), direction, (float) direction.length(), 0.0F);
		AlchemicalArrowEntity alchemicalArrow = arrow.createNewArrow(bukkitArrow);
		this.stateManager.add(alchemicalArrow);
		sender.sendMessage(CHAT_PREFIX + "Arrow successfully summoned at (" + x + ", " + y + ", " + z + ") in world \"" + world.getName() + "\" -> (" + velocityX + ", " + velocityY + ", " + velocityZ + ")");
		return true;
	}

}