package me.choco.arrows.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.ArrowType;
import me.choco.arrows.api.Methods;
import me.choco.arrows.utils.Messages;

public class GiveArrowsCommand extends Methods implements CommandExecutor{
	AlchemicalArrows plugin;
	public GiveArrowsCommand(AlchemicalArrows plugin){
		this.plugin = plugin;
	}
	
	Messages message = new Messages(plugin);
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args){
		FileConfiguration config = plugin.messages.getConfig();
		if (command.getName().equalsIgnoreCase("givearrow") || command.getName().equalsIgnoreCase("givearrows")){
			if (sender instanceof Player){
				Player player = (Player) sender;
				if (player.hasPermission("arrows.command.givearrow")){
					if (args.length == 0){
						message.sendMessage(player, config.getString("Commands.InvalidCommandSyntax"));
						message.sendMessage(player, "/givearrow <arrow> [count] [player]");
						return true;
					}
					
					if (args.length >= 1){
						int giveCount = 1;
						Player targetPlayer = player;
						if (args.length >= 2){
							if (isInteger(args[1])){
								giveCount = Integer.parseInt(args[1]);
								if (giveCount <= 0){
									message.sendMessage(player, config.getString("Commands.GiveArrow.InvalidInteger").replaceAll("%giveCount%", String.valueOf(giveCount)));
									return true;
								}
							}
							else{
								message.sendMessage(player, config.getString("Commands.GiveArrow.InvalidCountArgument").replaceAll("%giveCount%", String.valueOf(giveCount)));
								return true;
							}
						}
						if (args.length == 3){
							if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[2]))){
								targetPlayer = Bukkit.getPlayer(args[2]);
								message.sendMessage(player, config.getString("Commands.GiveArrow.Success").replaceAll("%giveCount%", String.valueOf(giveCount)).replaceAll("%arrowType%", args[0]).replaceAll("%player%", targetPlayer.getName()));
							}
							else{
								message.sendMessage(player, config.getString("Commands.PlayerNotOnline").replaceAll("%player%", args[2]));
								return true;
							}
						}
						switch(args[0]){
						case "air":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.ITALIC + "Air Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "earth":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.GRAY + "Earth Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "magic":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.LIGHT_PURPLE + "Magic Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "ender":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.DARK_PURPLE + "Ender Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "life":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.GREEN + "Life Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "death":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.BLACK + "Death Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "light":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.YELLOW + "Light Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "darkness":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.DARK_GRAY + "Darkness Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "fire":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.RED + "Fire Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "frost":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.AQUA + "Frost Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "water":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.BLUE + "Water Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "necrotic":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.DARK_GREEN + "Necrotic Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "confusion":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.LIGHT_PURPLE + "Confusion Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "magnetic":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.GRAY + "Magnetic Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						default:
							message.sendMessage(player, config.getString("Commands.GiveArrow.InvalidArrowType"));
							String arrowTypes = "";
							for (ArrowType type : ArrowType.values()){
								arrowTypes = arrowTypes + type.toString().toLowerCase() + ", ";
							}
							player.sendMessage(ChatColor.GRAY + arrowTypes);
							break;
						}
						return true;
					}
				}
				else{
					message.sendMessage(player, config.getString("Commands.NoPermission"));
					return true;
				}
			}
			else{
				if (args.length <= 0 || args.length <= 2){
					Bukkit.getLogger().info("You must use all arguments");
					Bukkit.getLogger().info("/givearrow <arrow> <count> <player>");
					return true;
				}
				if (args.length == 3){
					if (isInteger(args[1])){
						int giveCount = Integer.parseInt(args[1]);
						if (giveCount <= 0){
							Bukkit.getLogger().info("Invalid integer " + giveCount + ". Must be a minimum of 1");
							return true;
						}
						if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[2]))){
							Player targetPlayer = Bukkit.getPlayer(args[2]);
							Bukkit.getLogger().info("Given " + giveCount + " " + args[0] + " arrows to "
									+ targetPlayer.getName());
						}
						else{
							Bukkit.getLogger().info("That player (" + args[2] + ") is not online");
							return true;
						}
						Player targetPlayer = Bukkit.getPlayer(args[2]);
						switch(args[0]){
						case "air":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.ITALIC + "Air Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "earth":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.GRAY + "Earth Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "magic":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.LIGHT_PURPLE + "Magic Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "ender":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.DARK_PURPLE + "Ender Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "life":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.GREEN + "Life Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "death":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.BLACK + "Death Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "light":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.YELLOW + "Light Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "darkness":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.DARK_GRAY + "Darkness Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "fire":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.RED + "Fire Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "frost":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.AQUA + "Frost Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "water":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.BLUE + "Water Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "necrotic":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.DARK_GREEN + "Necrotic Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "confusion":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.LIGHT_PURPLE + "Confusion Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						case "magnetic":
							targetPlayer.getInventory().addItem(createSpecializedArrow(giveCount, ChatColor.GRAY + "Magnetic Arrow"));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
							break;
						default:
							Bukkit.getLogger().info("Invalid arrow type! Available arrow types:");
							String arrowTypes = "";
							for (ArrowType type : ArrowType.values()){
								arrowTypes = arrowTypes + type.toString().toLowerCase() + ", ";
							}
							Bukkit.getLogger().info(ChatColor.GRAY + arrowTypes);
							break;
						}
						return true;
					}
					else{
						Bukkit.getLogger().info(args[1] + " is not an integer");
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
}