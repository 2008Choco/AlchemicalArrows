package me.choco.arrows.utils.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.utils.ArrowRegistry;

public class GiveArrowCmd implements CommandExecutor, TabCompleter{
	
	AlchemicalArrows plugin;
	public GiveArrowCmd(AlchemicalArrows plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player player = (Player) sender;
			if (player.hasPermission("arrows.command.givearrow")){
				if (args.length == 0){
					player.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "/givearrow <arrow> [count] [player]");
					return true;
				}
				
				else if (args.length >= 1){
					int giveCount = 1;
					Player targetPlayer = player;
					if (args.length >= 2){
						try{
							giveCount = Integer.parseInt(args[1]);
							if (giveCount <= 0) giveCount = 1;
							else if (giveCount > 64) giveCount = 64;
						}catch(NumberFormatException e){
							player.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "Invalid arrow count provided, " + args[1]);
							return true;
						}
					}
					
					if (args.length >= 3){
						if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[2]))){
							targetPlayer = Bukkit.getPlayer(args[2]);
						}else{
							player.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "Player " + args[2] + " is not currently online");
							return true;
						}
					}
					
					for (ItemStack arrow : ArrowRegistry.getArrowRegistry().keySet()){
						String arrowName = ArrowRegistry.getArrowRegistry().get(arrow).getSimpleName().replace("Arrow", "").toLowerCase();
						if (args[0].equalsIgnoreCase(arrowName)){
							ItemStack itemToGive = new ItemStack(Material.ARROW);
							itemToGive.setItemMeta(arrow.getItemMeta());
							itemToGive.setAmount(giveCount);
							targetPlayer.getInventory().addItem(itemToGive);
							player.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "Successfully given " + giveCount + " of " + arrowName + " arrow " + (targetPlayer.getName().equals(player.getName()) ? "" : "to " + targetPlayer.getName()));
							return true;
						}
					}
					player.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "Invalid arrow type \"" + args[0] + "\" given");
					return true;
				}
			}
			else{
				player.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "You have insufficient privileges to run this command");
				return true;
			}
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