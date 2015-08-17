package me.choco.arrows.api.methods;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Messages implements Listener{
	
	/**
	 * The default handler for AlchemicalArrows messages
	 * @param player - The player you'd like to send the message to
	 * @param message - The message in which you'd like to send
	 */
	public static void notification(Player player, String message){
		player.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + message);
	}//Close notification method
}//Close class