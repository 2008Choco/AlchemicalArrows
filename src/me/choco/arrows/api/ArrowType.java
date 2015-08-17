package me.choco.arrows.api;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import me.choco.arrows.AlchemicalArrows;

public enum ArrowType{
	AIR("Air", ChatColor.ITALIC + "Air"),
	EARTH("Earth", ChatColor.GRAY + "Earth"),
	MAGIC("Magic", ChatColor.LIGHT_PURPLE + "Magic"),
	ENDER("Ender", ChatColor.DARK_PURPLE + "Ender"),
	LIFE("Life", ChatColor.GREEN + "Life"),
	DEATH("Death", ChatColor.BLACK + "Death"),
	LIGHT("Light", ChatColor.YELLOW + "Light"),
	DARKNESS("Darkness", ChatColor.DARK_GRAY + "Darkness"),
	FIRE("Fire", ChatColor.RED + "Fire"),
	FROST("Frost", ChatColor.AQUA + "Frost"),
	WATER("Water", ChatColor.BLUE + "Water"),
	NECROTIC("Necrotic", ChatColor.DARK_GREEN + "Necrotic"),
	CONFUSION("Confusion", ChatColor.LIGHT_PURPLE + "Confusion"),
	MAGNETIC("Magnetic", ChatColor.GRAY + "Magnetic");
	
	static Plugin AA = Bukkit.getPluginManager().getPlugin("AlchemicalArrows");
	
	ArrowType(String meta, String displayTag){}
	
	public static ArrowType getArrowType(Arrow arrow){
		if (arrow.hasMetadata("Air")){
			return ArrowType.AIR;
		}
		if (arrow.hasMetadata("Earth")){
			return ArrowType.EARTH;
		}
		if (arrow.hasMetadata("Magic")){
			return ArrowType.MAGIC;
		}
		if (arrow.hasMetadata("Ender")){
			return ArrowType.ENDER;
		}
		if (arrow.hasMetadata("Life")){
			return ArrowType.LIFE;
		}
		if (arrow.hasMetadata("Death")){
			return ArrowType.DEATH;
		}
		if (arrow.hasMetadata("Light")){
			return ArrowType.LIGHT;
		}
		if (arrow.hasMetadata("Darkness")){
			return ArrowType.DARKNESS;
		}
		if (arrow.hasMetadata("Fire")){
			return ArrowType.FIRE;
		}
		if (arrow.hasMetadata("Frost")){
			return ArrowType.FROST;
		}
		if (arrow.hasMetadata("Water")){
			return ArrowType.WATER;
		}
		if (arrow.hasMetadata("Necrotic")){
			return ArrowType.NECROTIC;
		}
		if (arrow.hasMetadata("Confusion")){
			return ArrowType.CONFUSION;
		}
		if (arrow.hasMetadata("Magnetic")){
			return ArrowType.MAGNETIC;
		}
		else{
			return null;
		}
	}
	
	public static void setArrowType(Arrow arrow, ArrowType type){
		switch(type){
		case AIR:
			arrow.setMetadata("Air", new FixedMetadataValue(AA, arrow));
			arrow.setCustomName(ChatColor.ITALIC + "Air");
			break;
		case EARTH:
			arrow.setMetadata("Earth", new FixedMetadataValue(AA, arrow));
			arrow.setCustomName(ChatColor.GRAY + "Earth");
			break;
		case MAGIC:
			arrow.setMetadata("Magic", new FixedMetadataValue(AA, arrow));
			arrow.setCustomName(ChatColor.LIGHT_PURPLE + "Magic");
			break;
		case ENDER:
			arrow.setMetadata("Ender", new FixedMetadataValue(AA, arrow));
			arrow.setCustomName(ChatColor.DARK_PURPLE + "Ender");
			break;
		case LIFE:
			arrow.setMetadata("Life", new FixedMetadataValue(AA, arrow));
			arrow.setCustomName(ChatColor.GREEN + "Life");
			break;
		case DEATH:
			arrow.setMetadata("Death", new FixedMetadataValue(AA, arrow));
			arrow.setCustomName(ChatColor.BLACK + "Death");
			break;
		case LIGHT:
			arrow.setMetadata("Light", new FixedMetadataValue(AA, arrow));
			arrow.setCustomName(ChatColor.YELLOW + "Light");
			break;
		case DARKNESS:
			arrow.setMetadata("Darkness", new FixedMetadataValue(AA, arrow));
			arrow.setCustomName(ChatColor.DARK_GRAY + "Darkness");
			break;
		case FIRE:
			arrow.setMetadata("Fire", new FixedMetadataValue(AA, arrow));
			arrow.setCustomName(ChatColor.RED + "Fire");
			break;
		case FROST:
			arrow.setMetadata("Frost", new FixedMetadataValue(AA, arrow));
			arrow.setCustomName(ChatColor.AQUA + "Frost");
			break;
		case WATER:
			arrow.setMetadata("Water", new FixedMetadataValue(AA, arrow));
			arrow.setCustomName(ChatColor.BLUE + "Water");
			break;
		case NECROTIC:
			arrow.setMetadata("Necrotic", new FixedMetadataValue(AA, arrow));
			arrow.setCustomName(ChatColor.DARK_GREEN + "Necrotic");
			break;
		case CONFUSION:
			arrow.setMetadata("Confusion", new FixedMetadataValue(AA, arrow));
			arrow.setCustomName(ChatColor.LIGHT_PURPLE + "Confusion");
			break;
		case MAGNETIC:
			arrow.setMetadata("Magnetic", new FixedMetadataValue(AA, arrow));
			arrow.setCustomName(ChatColor.GRAY + "Magnetic");
			break;
		}
		arrow.setCustomNameVisible(AA.getConfig().getBoolean("DisplayArrowTags"));
		AlchemicalArrows.specializedArrows.add(arrow);
	}
}//Close ArrowType enum