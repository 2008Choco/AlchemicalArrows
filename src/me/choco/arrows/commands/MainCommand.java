package me.choco.arrows.commands;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.methods.Messages;

public class MainCommand implements CommandExecutor, Listener{
	
	Plugin AA = Bukkit.getPluginManager().getPlugin("AlchemicalArrows");
	private static String APIVersion = "1.8.8-AA0.3c";
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (command.getName().equalsIgnoreCase("alchemicalarrows") || command.getName().equalsIgnoreCase("aa")){
			if (sender instanceof Player){
				Player player = (Player) sender;
				if (args.length == 0){
					Messages.notification(player, "Invalid Arguments!");
					return false;
				}//Close if there are no arguments
				if (args.length >= 1){
					if (args[0].equalsIgnoreCase("killallarrows")){
						if (player.hasPermission("arrows.command.killallarrows")){
							int arrowCount = AlchemicalArrows.specializedArrows.size();
							if (arrowCount >= 1){
								for (Iterator<Arrow> it = AlchemicalArrows.specializedArrows.iterator(); it.hasNext();){
									Arrow arrow = it.next();
									it.remove();
									AlchemicalArrows.specializedArrows.remove(arrow);
									arrow.remove();
								}//Close iterate through all arrows
								Messages.notification(player, "Successfully removed " + ChatColor.AQUA + arrowCount
										+ ChatColor.GRAY + " specialized arrows from the world, and wiped them from memory");
							}//Close if there's more than 0 arrows in the world
							else{
								Messages.notification(player, "No arrows in the world to remove");
							}//Close if there's 0 arrows in the world
						}//Close if permissions == true
						else{
							Messages.notification(player, "You do not have the sufficient permissions to run this command");
						}//Close if permissions == false
						return true;
					}//Close if args 1 is "killAllArrows"
					
					if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("info")){
						player.sendMessage(ChatColor.GOLD + "--------------------------------------------");
						player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Version: " + ChatColor.RESET + ChatColor.GRAY  + AA.getDescription().getVersion());
						player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "API Version: " + ChatColor.RESET + ChatColor.GRAY + APIVersion);
						player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Developer / Maintainer: " + ChatColor.RESET + ChatColor.GRAY + "2008Choco");
						player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Development Page: " + ChatColor.RESET + ChatColor.GRAY + "http://dev.bukkit.org/bukkit-plugins/alchemical-arrows");
						player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Report Bugs To: " + ChatColor.RESET + ChatColor.GRAY + "http://dev.bukkit.org/bukkit-plugins/alchemical-arrows/tickets");
						player.sendMessage("");
						player.sendMessage(ChatColor.GOLD + "--------------------------------------------");
						return true;
					}//Close if args 1 is "version"
					
					if (args[0].equalsIgnoreCase("reload")){
						if (player.hasPermission("arrows.command.reload")){
							AA.reloadConfig();
							Messages.notification(player, ChatColor.GREEN + "Successfully reloaded configuration");
						}//Close if permissions == true
						else{
							Messages.notification(player, "You do not have the sufficient permissions to run this command");
						}//Close if permissions == false
						return true;
					}//Close if args 1 is "reload"
				}//Close if there are more than one arguments
			}//Close if sender is a player
		}//Close if command is AlchemicalArrows
		return false;
	}//Close onCommand method
}//Close class