package me.choco.arrows.api.methods;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class Arrows{
	static Plugin AA = Bukkit.getServer().getPluginManager().getPlugin("AlchemicalArrows");
	
	/**
	 * Create and return an Air Arrow object
	 * @param arrowCount - The count of arrows you'd like to create
	 * @return ItemStack
	 */
	public static ItemStack airArrow(int arrowCount){
		ItemStack airArrow = new ItemStack(Material.ARROW, arrowCount);
		ItemMeta airMeta = airArrow.getItemMeta();
		airMeta.setDisplayName(ChatColor.ITALIC + "Air Arrow");
		airArrow.setItemMeta(airMeta);
		return airArrow;
	}//Close airArrow method
	
	/**
	 * Create and return an Earth Arrow object
	 * @param arrowCount - The count of arrows you'd like to create
	 * @return ItemStack
	 */
	public static ItemStack earthArrow(int arrowCount){
		ItemStack earthArrow = new ItemStack(Material.ARROW, arrowCount);
		ItemMeta earthMeta = earthArrow.getItemMeta();
		earthMeta.setDisplayName(ChatColor.GRAY + "Earth Arrow");
		earthArrow.setItemMeta(earthMeta);
		return earthArrow;
	}//Close earthArrow method
	
	/**
	 * Create and return an Magic Arrow object
	 * @param arrowCount - The count of arrows you'd like to create
	 * @return ItemStack
	 */
	public static ItemStack magicArrow(int arrowCount){
		ItemStack magicArrow = new ItemStack(Material.ARROW, arrowCount);
		ItemMeta magicMeta = magicArrow.getItemMeta();
		magicMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Magic Arrow");
		magicArrow.setItemMeta(magicMeta);
		return magicArrow;
	}//Close magicArrow method
	
	/**
	 * Create and return an Ender Arrow object
	 * @param arrowCount - The count of arrows you'd like to create
	 * @return ItemStack
	 */
	public static ItemStack enderArrow(int arrowCount){
		ItemStack spectralArrow = new ItemStack(Material.ARROW, arrowCount);
		ItemMeta spectralMeta = spectralArrow.getItemMeta();
		spectralMeta.setDisplayName(ChatColor.DARK_PURPLE + "Spectral Arrow");
		spectralArrow.setItemMeta(spectralMeta);
		return spectralArrow;
	}//Close spectralArrow method
	
	/**
	 * Create and return an Life Arrow object
	 * @param arrowCount - The count of arrows you'd like to create
	 * @return ItemStack
	 */
	public static ItemStack lifeArrow(int arrowCount){
		ItemStack lifeArrow = new ItemStack(Material.ARROW, arrowCount);
		ItemMeta lifeMeta = lifeArrow.getItemMeta();
		lifeMeta.setDisplayName(ChatColor.GREEN + "Life Arrow");
		lifeArrow.setItemMeta(lifeMeta);
		return lifeArrow;
	}//Close lifeArrow method
	
	/**
	 * Create and return an Death Arrow object
	 * @param arrowCount - The count of arrows you'd like to create
	 * @return ItemStack
	 */
	public static ItemStack deathArrow(int arrowCount){
		ItemStack deathArrow = new ItemStack(Material.ARROW, arrowCount);
		ItemMeta deathMeta = deathArrow.getItemMeta();
		deathMeta.setDisplayName(ChatColor.BLACK + "Death Arrow");
		deathArrow.setItemMeta(deathMeta);
		return deathArrow;
	}//Close deathArrow method
	
	/**
	 * Create and return an Light Arrow object
	 * @param arrowCount - The count of arrows you'd like to create
	 * @return ItemStack
	 */
	public static ItemStack lightArrow(int arrowCount){
		ItemStack lightArrow = new ItemStack(Material.ARROW, arrowCount);
		ItemMeta lightMeta = lightArrow.getItemMeta();
		lightMeta.setDisplayName(ChatColor.YELLOW + "Light Arrow");
		lightArrow.setItemMeta(lightMeta);
		return lightArrow;
	}//Close lightArrow method
	
	/**
	 * Create and return an Darkness Arrow object
	 * @param arrowCount - The count of arrows you'd like to create
	 * @return ItemStack
	 */
	public static ItemStack darknessArrow(int arrowCount){
		ItemStack darknessArrow = new ItemStack(Material.ARROW, arrowCount);
		ItemMeta darknessMeta = darknessArrow.getItemMeta();
		darknessMeta.setDisplayName(ChatColor.DARK_GRAY + "Darkness Arrow");
		darknessArrow.setItemMeta(darknessMeta);
		return darknessArrow;
	}//Close darknessArrow method
	
	/**
	 * Create and return an FireArrow Arrow object
	 * @param arrowCount - The count of arrows you'd like to create
	 * @return ItemStack
	 */
	public static ItemStack fireArrow(int arrowCount){
		ItemStack fireArrow = new ItemStack(Material.ARROW, arrowCount);
		ItemMeta fireMeta = fireArrow.getItemMeta();
		fireMeta.setDisplayName(ChatColor.RED + "Fire Arrow");
		fireArrow.setItemMeta(fireMeta);
		return fireArrow;
	}//Close fireArrow method
	
	/**
	 * Create and return an Frost Arrow object
	 * @param arrowCount - The count of arrows you'd like to create
	 * @return ItemStack
	 */
	public static ItemStack frostArrow(int arrowCount){
		ItemStack frostArrow = new ItemStack(Material.ARROW, arrowCount);
		ItemMeta frostMeta = frostArrow.getItemMeta();
		frostMeta.setDisplayName(ChatColor.AQUA + "Frost Arrow");
		frostArrow.setItemMeta(frostMeta);
		return frostArrow;
	}//Close frostArrow method
	
	/**
	 * Create and return an Water Arrow object
	 * @param arrowCount - The count of arrows you'd like to create
	 * @return ItemStack
	 */
	public static ItemStack waterArrow(int arrowCount){
		ItemStack waterArrow = new ItemStack(Material.ARROW, arrowCount);
		ItemMeta waterMeta = waterArrow.getItemMeta();
		waterMeta.setDisplayName(ChatColor.BLUE + "Water Arrow");
		waterArrow.setItemMeta(waterMeta);
		return waterArrow;
	}//Close waterArrow method
	
	/**
	 * Create and return an Necrotic Arrow object
	 * @param arrowCount - The count of arrows you'd like to create
	 * @return ItemStack
	 */
	public static ItemStack necroticArrow(int arrowCount){
		ItemStack necroticArrow = new ItemStack(Material.ARROW, arrowCount);
		ItemMeta necroticMeta = necroticArrow.getItemMeta();
		necroticMeta.setDisplayName(ChatColor.DARK_GREEN + "Necrotic Arrow");
		necroticArrow.setItemMeta(necroticMeta);
		return necroticArrow;
	}//Close necroticArrow method
	
	/**
	 * Create and return an Confusion Arrow object
	 * @param arrowCount - The count of arrows you'd like to create
	 * @return ItemStack
	 */
	public static ItemStack confusionArrow(int arrowCount){
		ItemStack confusionArrow = new ItemStack(Material.ARROW, arrowCount);
		ItemMeta confusionMeta = confusionArrow.getItemMeta();
		confusionMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Confusion Arrow");
		confusionArrow.setItemMeta(confusionMeta);
		return confusionArrow;
	}//Close confusionArrow method
	
	/**
	 * Create and return an Magnetic Arrow object
	 * @param arrowCount - The count of arrows you'd like to create
	 * @return ItemStack
	 */
	public static ItemStack magneticArrow(int arrowCount){
		ItemStack magneticArrow = new ItemStack(Material.ARROW, arrowCount);
		ItemMeta magneticMeta = magneticArrow.getItemMeta();
		magneticMeta.setDisplayName(ChatColor.GRAY + "Magnetic Arrow");
		magneticArrow.setItemMeta(magneticMeta);
		return magneticArrow;
	}//Close magneticArrow method
}//Close class