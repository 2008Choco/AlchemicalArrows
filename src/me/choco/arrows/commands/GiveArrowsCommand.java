package me.choco.arrows.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.choco.arrows.api.ArrowType;
import me.choco.arrows.api.methods.Arrows;
import me.choco.arrows.api.methods.Messages;

public class GiveArrowsCommand implements CommandExecutor, Listener{
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args){
		if (command.getName().equalsIgnoreCase("givearrow") || command.getName().equalsIgnoreCase("givearrows")){
			if (sender instanceof Player){
				Player player = (Player) sender;
				if (player.hasPermission("arrows.command.givearrow")){
					if (args.length == 0){
						Messages.notification(player, "Invalid Arguments!");
						Messages.notification(player, "/givearrow <arrow> [count] [player]");
						return true;
					}//Close if there's 0 arguments
					
					if (args.length >= 1){
						int giveCount = 1;
						Player targetPlayer = player;
						if (args.length == 2){
							giveCount = Integer.parseInt(args[1]);
							if (giveCount <= 0){
								Messages.notification(player, "Invalid integer " + giveCount + ". Must be a minimum of 1");
								return true;
							}//Close if giveCount == 0
						}//Close if there's a 3rd argument
						if (args.length == 3){
							if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[2]))){
								targetPlayer = Bukkit.getPlayer(args[2]);
								Messages.notification(player, "Given " + giveCount + " " + args[0] + " arrows to "
										+ targetPlayer.getName());
							}//Close if player is online
							else{
								Messages.notification(player, "That player (" + args[2] + ") is not online");
							}//Close if player is not online
						}//Close if a player is specified
						switch(args[0]){
						case "air":
							targetPlayer.getInventory().addItem(Arrows.airArrow(giveCount));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
							break;
						case "earth":
							targetPlayer.getInventory().addItem(Arrows.earthArrow(giveCount));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
							break;
						case "magic":
							targetPlayer.getInventory().addItem(Arrows.magicArrow(giveCount));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
							break;
						case "ender":
							targetPlayer.getInventory().addItem(Arrows.enderArrow(giveCount));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
							break;
						case "life":
							targetPlayer.getInventory().addItem(Arrows.lifeArrow(giveCount));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
							break;
						case "death":
							targetPlayer.getInventory().addItem(Arrows.deathArrow(giveCount));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
							break;
						case "light":
							targetPlayer.getInventory().addItem(Arrows.lightArrow(giveCount));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
							break;
						case "darkness":
							targetPlayer.getInventory().addItem(Arrows.darknessArrow(giveCount));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
							break;
						case "fire":
							targetPlayer.getInventory().addItem(Arrows.fireArrow(giveCount));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
							break;
						case "frost":
							targetPlayer.getInventory().addItem(Arrows.frostArrow(giveCount));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
							break;
						case "water":
							targetPlayer.getInventory().addItem(Arrows.waterArrow(giveCount));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
							break;
						case "necrotic":
							targetPlayer.getInventory().addItem(Arrows.necroticArrow(giveCount));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
							break;
						case "confusion":
							targetPlayer.getInventory().addItem(Arrows.confusionArrow(giveCount));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
							break;
						case "magnetic":
							targetPlayer.getInventory().addItem(Arrows.magneticArrow(giveCount));
							targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
							break;
						default:
							Messages.notification(player, "Invalid arrow type! Available arrow types:");
							String arrowTypes = "";
							for (ArrowType type : ArrowType.values()){
								arrowTypes = arrowTypes + type.toString().toLowerCase() + ", ";
							}
							player.sendMessage(ChatColor.GRAY + arrowTypes);
							break;
						}//Close switch statement
						return true;
					}//Close if there are more than one arguments
				}//Close if permissions == true
				else{
					Messages.notification(player, "You do not have the sufficient permissions to run this command");
					return true;
				}//Close if perissions == false
			}//Close if sender is player
			else{
				if (args.length <= 0 || args.length <= 2){
					Bukkit.getLogger().info("You must use all arguments");
					Bukkit.getLogger().info("/givearrow <arrow> <count> <player>");
					return true;
				}//Close if there's 0 arguments
				if (args.length == 3){
					int giveCount = Integer.parseInt(args[1]);
					if (giveCount <= 0){
						Bukkit.getLogger().info("Invalid integer " + giveCount + ". Must be a minimum of 1");
						return true;
					}//Close if giveCount == 0
					if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[2]))){
						Player targetPlayer = Bukkit.getPlayer(args[2]);
						Bukkit.getLogger().info("Given " + giveCount + " " + args[0] + " arrows to "
								+ targetPlayer.getName());
					}//Close if player is online
					else{
						Bukkit.getLogger().info("That player (" + args[2] + ") is not online");
						return true;
					}//Close if player is not online
					Player targetPlayer = Bukkit.getPlayer(args[2]);
					switch(args[0]){
					case "air":
						targetPlayer.getInventory().addItem(Arrows.airArrow(giveCount));
						targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						break;
					case "earth":
						targetPlayer.getInventory().addItem(Arrows.earthArrow(giveCount));
						targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						break;
					case "magic":
						targetPlayer.getInventory().addItem(Arrows.magicArrow(giveCount));
						targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						break;
					case "spectral":
						targetPlayer.getInventory().addItem(Arrows.enderArrow(giveCount));
						targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						break;
					case "life":
						targetPlayer.getInventory().addItem(Arrows.lifeArrow(giveCount));
						targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						break;
					case "death":
						targetPlayer.getInventory().addItem(Arrows.deathArrow(giveCount));
						targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						break;
					case "light":
						targetPlayer.getInventory().addItem(Arrows.lightArrow(giveCount));
						targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						break;
					case "darkness":
						targetPlayer.getInventory().addItem(Arrows.darknessArrow(giveCount));
						targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						break;
					case "fire":
						targetPlayer.getInventory().addItem(Arrows.fireArrow(giveCount));
						targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						break;
					case "frost":
						targetPlayer.getInventory().addItem(Arrows.frostArrow(giveCount));
						targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						break;
					case "water":
						targetPlayer.getInventory().addItem(Arrows.waterArrow(giveCount));
						targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						break;
					case "necrotic":
						targetPlayer.getInventory().addItem(Arrows.necroticArrow(giveCount));
						targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						break;
					case "confusion":
						targetPlayer.getInventory().addItem(Arrows.confusionArrow(giveCount));
						targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						break;
					case "magnetic":
						targetPlayer.getInventory().addItem(Arrows.magneticArrow(giveCount));
						targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						break;
					default:
						Bukkit.getLogger().info("Invalid arrow type! Available arrow types:");
						String arrowTypes = "";
						for (ArrowType type : ArrowType.values()){
							arrowTypes = arrowTypes + type.toString().toLowerCase() + ", ";
						}
						Bukkit.getLogger().info(ChatColor.GRAY + arrowTypes);
						break;
					}//Close switch statement
					return true;
				}//Close if there are more than one arguments
			}//Close if sender is console / command block
		}//Close if command is "/givearrow"
		return false;
	}//Close onCommand method
}//Close class