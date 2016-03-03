package me.choco.arrows.api;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Methods{
	public static ItemStack createSpecializedArrow(int arrowCount, String displayName){
		ItemStack arrow = new ItemStack(Material.ARROW, arrowCount);
		ItemMeta arrowMeta = arrow.getItemMeta();
		arrowMeta.setDisplayName(displayName);
		arrow.setItemMeta(arrowMeta);
		return arrow;	
	}
}