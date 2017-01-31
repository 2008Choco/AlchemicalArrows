package me.choco.arrows.utils.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.registry.ArrowRegistry;

public class GiveArrowCmd implements CommandExecutor, TabCompleter{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender.hasPermission("arrows.command.givearrow")) {
			sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "You have insufficient privileges to run this command");
			return true;
		}
		
		if (args.length >= 1){
			int giveCount = 1;
			Player targetPlayer = (sender instanceof Player) ? (Player) sender : null;
			
			if (args.length >= 2){
				try{
					giveCount = Integer.parseInt(args[1]);
					if (giveCount <= 0) giveCount = 1;
					else if (giveCount > 64) giveCount = 64;
				}catch(NumberFormatException e){
					sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "Invalid arrow count provided, " + args[1]);
					return true;
				}
			}
			
			if (args.length >= 3){
				targetPlayer = Bukkit.getPlayer(args[2]);
				
				if (targetPlayer == null){
					sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "Player " + args[2] + " is not currently online");
					return true;
				}
			}
			
			if (targetPlayer == null) {
				sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "You must specify a player in order to execute this command from the console");
				return true;
			}
			
			for (ItemStack arrow : ArrowRegistry.getArrowRegistry().keySet()){
				String arrowName = ArrowRegistry.getArrowRegistry().get(arrow).getSimpleName().replace("Arrow", "").toLowerCase();
				if (args[0].equalsIgnoreCase(arrowName)){
					ItemStack itemToGive = arrow.clone();
					itemToGive.setAmount(giveCount);
					targetPlayer.getInventory().addItem(itemToGive);
					sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "Successfully given " + giveCount + " of " + arrowName + " arrow " + (targetPlayer.getName().equals(sender.getName()) ? "" : "to " + targetPlayer.getName()));
				}
			}
			
			sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "Invalid arrow type \"" + args[0] + "\" given");
		}
		else {
			sender.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "/givearrow <arrow> [count] [player]");
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (args.length == 1){
			List<String> options = new ArrayList<>();
			for (Class<? extends AlchemicalArrow> clazz : ArrowRegistry.getArrowRegistry().values())
				options.add(clazz.getSimpleName().replace("Arrow", ""));
			return options;
		}
		return null;
	}
}