package wtf.choco.arrows.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.registry.ArrowRegistry;

public class GiveArrowCmd implements CommandExecutor {
	
	public static final TabCompleter TAB_COMPLETER = (s, c, l, args) -> {
		if (args.length != 1) return null;
		
		List<String> arguments = new ArrayList<>();
		for (AlchemicalArrow arrow : ArrowRegistry.getRegisteredCustomArrows())
			arguments.add(arrow.getKey().toString());
		return arguments;
	};
	
	private final AlchemicalArrows plugin;
	
	public GiveArrowCmd(AlchemicalArrows plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("arrows.command.givearrow")) {
			sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "You have insufficient privileges to run this command");
			return true;
		}
		
		if (args.length < 1) {
			sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "/givearrow <arrow> [count] [player]");
			return true;
		}
		
		int giveCount = (args.length >= 2) ? clamp(NumberUtils.toInt(args[1], 1), 1, 64) : 1;
		Player targetPlayer = (sender instanceof Player) ? (Player) sender : null;
		
		if (args.length >= 3) {
			targetPlayer = Bukkit.getPlayer(args[2]);
			
			if (targetPlayer == null) {
				sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "Player " + args[2] + " is not currently online");
				return true;
			}
		}
		
		if (targetPlayer == null) {
			sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "You must specify a player in order to execute this command from the console");
			return true;
		}
		
		String arrowNamespace = args[0].toLowerCase();
		
		if (!arrowNamespace.contains(":")) {
			arrowNamespace = plugin.getName().toLowerCase() + ":" + arrowNamespace;
		}
		else if (arrowNamespace.startsWith(":") || arrowNamespace.split(":").length > 2) {
			sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "Invalid namespace. Pattern IDs should be "
					+ "formatted as " + ChatColor.YELLOW + "namespace:id" + ChatColor.GRAY + " (i.e. " + ChatColor.YELLOW
					+ "\"alchemicalarrows:air\"" + ChatColor.GRAY + ")");
			return true;
		}
		
		AlchemicalArrow arrow = ArrowRegistry.getCustomArrow(arrowNamespace);
		if (arrow == null) {
			sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "Invalid arrow type \"" + arrowNamespace + "\" given");
			return true;
		}
		
		ItemStack itemToGive = arrow.getItem();
		itemToGive.setAmount(giveCount);
		targetPlayer.getInventory().addItem(itemToGive);
		sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "Successfully given " + giveCount + " of " + arrow.getDisplayName() + ChatColor.GRAY + (targetPlayer == sender ? "" : " to " + targetPlayer.getName()));
		return true;
	}
	
	private int clamp(int value, int min, int max) {
		return (value < min ? min : (value > max ? max : value));
	}
	
}