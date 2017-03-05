package me.choco.arrows.utils.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.registry.ArrowRegistry;
import me.choco.arrows.utils.ConfigOption;

public class MainCmd implements CommandExecutor, TabCompleter {
	
	private static final String API_VERSION = "1.10-AA2";
	
	private AlchemicalArrows plugin;
	
	public MainCmd(AlchemicalArrows plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length >= 1){
			if (args[0].equalsIgnoreCase("killallarrows")){
				if (!sender.hasPermission("arrows.command.killallarrows")) {
					sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "You have insufficient privileges to run this command");
					return true;
				}
				
				ArrowRegistry arrowRegistry = plugin.getArrowRegistry();
				int arrowCount = arrowRegistry.getRegisteredArrows().size();
				if (arrowCount >= 1){
					for (AlchemicalArrow arrow : arrowRegistry.getRegisteredArrows().values()) {
						arrowRegistry.unregisterAlchemicalArrow(arrow);
						arrow.getArrow().remove();
					}
					
					sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "Successfully removed " + arrowCount + " arrows");
				}else{
					sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "No arrows found to replace");
				}
			}
			
			else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("info")){
				sender.sendMessage(ChatColor.GOLD + "--------------------------------------------");
				sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Version: " + ChatColor.GRAY  + plugin.getDescription().getVersion());
				sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "API Version: " + ChatColor.GRAY + API_VERSION);
				sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Developer / Maintainer: " + ChatColor.GRAY + "2008Choco");
				sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Development Page: " + ChatColor.GRAY + "http://dev.bukkit.org/bukkit-plugins/alchemical-arrows");
				sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Report Bugs To: " + ChatColor.GRAY + "http://dev.bukkit.org/bukkit-plugins/alchemical-arrows/tickets");
				sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "New Version Available: " + ChatColor.GRAY + (plugin.isNewVersionAvailable() ? "Yes" : "No"));
				sender.sendMessage(ChatColor.GOLD + "--------------------------------------------");
			}
			
			else if (args[0].equalsIgnoreCase("reload")){
				if (!sender.hasPermission("arrows.command.reload")) {
					sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "You have insufficient privileges to run this command");
					return true;
				}
				
				plugin.reloadConfig();
				ConfigOption.loadConfigurationValues(plugin);
				sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GREEN + "Configuration file successfully reloaded");
			}
		}
		else {
			sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "/alchemicalarrows <killallarrows|version|reload>");
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (args.length == 1){
			return Arrays.asList("killallarrows", "version", "reload");
		}
		return null;
	}
}