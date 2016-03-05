package me.choco.arrows.utils.commands;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;

public class MainCmd implements CommandExecutor, TabCompleter{
	AlchemicalArrows plugin;
	public MainCmd(AlchemicalArrows plugin){
		this.plugin = plugin;
	}
	
	private String APIVersion = "1.9-AA2";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("alchemicalarrows")){
			if (args.length == 0){
				sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "/alchemicalarrows <killallarrows|version|reload>");
				return true;
			}
			if (args.length >= 1){
				if (args[0].equalsIgnoreCase("killallarrows")){
					if (sender.hasPermission("arrows.command.killallarrows")){
						int arrowCount = plugin.getArrowRegistry().getRegisteredArrows().size();
						if (arrowCount >= 1){
							Iterator<UUID> it = plugin.getArrowRegistry().getRegisteredArrows().keySet().iterator();
							while (it.hasNext()){
								AlchemicalArrow arrow = plugin.getArrowRegistry().getAlchemicalArrow(it.next());
								arrow.getArrow().remove();
								it.remove();
							}
							sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "Successfully removed " + arrowCount + " arrows");
							return true;
						}else{
							sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "No arrows found to replace");
							return true;
						}
					}else{
						sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "You have insufficient privileges to run this command");
						return true;
					}
				}
				
				else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("info")){
					sender.sendMessage(ChatColor.GOLD + "--------------------------------------------");
					sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Version: " + ChatColor.RESET + ChatColor.GRAY  + plugin.getDescription().getVersion());
					sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "API Version: " + ChatColor.RESET + ChatColor.GRAY + APIVersion);
					sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Developer / Maintainer: " + ChatColor.RESET + ChatColor.GRAY + "2008Choco");
					sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Development Page: " + ChatColor.RESET + ChatColor.GRAY + "http://dev.bukkit.org/bukkit-plugins/alchemical-arrows");
					sender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Report Bugs To: " + ChatColor.RESET + ChatColor.GRAY + "http://dev.bukkit.org/bukkit-plugins/alchemical-arrows/tickets");
					sender.sendMessage("");
					sender.sendMessage(ChatColor.GOLD + "--------------------------------------------");
					return true;
				}
				
				else if (args[0].equalsIgnoreCase("reload")){
					if (sender.hasPermission("arrows.command.reload")){
						plugin.reloadConfig();
						sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GREEN + "Configuration file successfully reloaded");
					}else{
						sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "You have insufficient privileges to run this command");
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (args.length == 1){
			return Arrays.asList("killallarrows", "version", "reload");
		}
		return null;
	}
}