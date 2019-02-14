package wtf.choco.arrows.commands;

import static wtf.choco.arrows.AlchemicalArrows.CHAT_PREFIX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.registry.ArrowRegistry;

public class AlchemicalArrowsCmd implements CommandExecutor {

	private static final List<String> DEFAULT_COMPLETIONS = Arrays.asList("killallarrows", "version", "reload");
	public static final TabCompleter TAB_COMPLETER = (s, c, l, args) -> (args.length > 0) ? StringUtil.copyPartialMatches(args[0], DEFAULT_COMPLETIONS, new ArrayList<>()) : null;

	private final AlchemicalArrows plugin;

	public AlchemicalArrowsCmd(AlchemicalArrows plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.GRAY + "Missing parameter. " + ChatColor.YELLOW + "/" + label + " <reload|version|killallarrows>");
			return true;
		}

		if (args[0].equalsIgnoreCase("killallarrows")) {
			if (!sender.hasPermission("arrows.command.killallarrows")) {
				sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "You have insufficient permissions to execute this command");
				return true;
			}

			ArrowRegistry arrowRegistry = plugin.getArrowRegistry();
			int arrowCount = arrowRegistry.getAlchemicalArrows().size();

			if (arrowCount == 0) {
				sender.sendMessage(CHAT_PREFIX + "No alchemical arrows were found in the world");
				return true;
			}

			arrowRegistry.getAlchemicalArrows().forEach(a -> {
				arrowRegistry.removeAlchemicalArrow(a);
				a.getArrow().remove();
			});

			sender.sendMessage(CHAT_PREFIX + "Successfully removed " + ChatColor.YELLOW + arrowCount + ChatColor.GRAY + " alchemical arrows from the world");
		}

		else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("info")) {
			sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.STRIKETHROUGH + "--------------------------------------------");
			sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Version: " + ChatColor.GRAY  + plugin.getDescription().getVersion());
			sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Developer / Maintainer: " + ChatColor.GRAY + "2008Choco" + ChatColor.YELLOW + "( https://choco.gg/ )");
			sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Development Page: " + ChatColor.GRAY + "https://www.spigotmc.org/resources/alchemicalarrows.11693/");
			sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Report Bugs To: " + ChatColor.GRAY + "https://github.com/2008Choco/AlchemicalArrows/issues/");
			sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "New Version Available: " + (plugin.isNewVersionAvailable() ? ChatColor.GREEN + "Yes" : ChatColor.RED + "No"));
			sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.STRIKETHROUGH + "--------------------------------------------");
		}

		else if (args[0].equalsIgnoreCase("reload")) {
			if (!sender.hasPermission("arrows.command.reload")) {
				sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "You have insufficient permissions to execute this command");
				return true;
			}

			this.plugin.reloadConfig();
			sender.sendMessage(CHAT_PREFIX + ChatColor.GREEN + "AlchemicalArrows configuration successfully reloaded");
		}

		else {
			sender.sendMessage(CHAT_PREFIX + ChatColor.RED + "Invalid command syntax! " + ChatColor.GRAY + "Unknown parameter " + ChatColor.AQUA + args[0] + ChatColor.GRAY + ". " + ChatColor.YELLOW + "/" + label + " <reload|version|killallarrows>");
		}

		return true;
	}

}