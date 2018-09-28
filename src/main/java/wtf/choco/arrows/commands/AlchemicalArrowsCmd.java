package wtf.choco.arrows.commands;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.registry.ArrowRegistry;

public class AlchemicalArrowsCmd implements CommandExecutor {
	
	public static final TabCompleter TAB_COMPLETER = (s, c, l, args) -> (args.length == 1) ? Arrays.asList("killallarrows", "version", "reload") : null;
	
	private final AlchemicalArrows plugin;
	
	public AlchemicalArrowsCmd(AlchemicalArrows plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length < 1) {
			sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "/alchemicalarrows <killallarrows|version|reload>");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("killallarrows")) {
			if (!sender.hasPermission("arrows.command.killallarrows")) {
				sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "You have insufficient privileges to run this command");
				return true;
			}
			
			ArrowRegistry arrowRegistry = plugin.getArrowRegistry();
			int arrowCount = arrowRegistry.getAlchemicalArrows().size();
			
			if (arrowCount == 0) {
				sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "No arrows found to remove");
				return true;
			}
			
			arrowRegistry.getAlchemicalArrows().forEach(a -> {
				arrowRegistry.removeAlchemicalArrow(a);
				a.getArrow().remove();
			});
			
			sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "Successfully removed " + arrowCount + " arrows");
		}
		
		else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("info")) {
			sender.sendMessage(ChatColor.GOLD + "--------------------------------------------");
			sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Version: " + ChatColor.GRAY  + plugin.getDescription().getVersion());
			sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Developer / Maintainer: " + ChatColor.GRAY + "2008Choco");
			sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Development Page: " + ChatColor.GRAY + "https://www.spigotmc.org/resources/alchemicalarrows.11693/");
			sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Report Bugs To: " + ChatColor.GRAY + "https://github.com/2008Choco/AlchemicalArrows/issues/");
			sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "New Version Available: " + (plugin.isNewVersionAvailable() ? ChatColor.GREEN + "Yes" : ChatColor.RED + "No"));
			sender.sendMessage(ChatColor.GOLD + "--------------------------------------------");
		}
		
		else if (args[0].equalsIgnoreCase("reload")) {
			if (!sender.hasPermission("arrows.command.reload")) {
				sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "You have insufficient privileges to run this command");
				return true;
			}
			
			this.plugin.reloadConfig();
			sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GREEN + "Configuration file successfully reloaded");
		}
		
		return true;
	}
	
}