package me.choco.arrows.api;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Methods{	
	
	public static void notification(Player player, String message){
		player.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + message);
	}//Close notification method
	
	public static ItemStack createSpecializedArrow(int arrowCount, String displayName){
		ItemStack arrow = new ItemStack(Material.ARROW, arrowCount);
		ItemMeta arrowMeta = arrow.getItemMeta();
		arrowMeta.setDisplayName(displayName);
		arrow.setItemMeta(arrowMeta);
		return arrow;
	}//Close airArrow method
}//Close class