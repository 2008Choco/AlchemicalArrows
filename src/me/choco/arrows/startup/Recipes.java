package me.choco.arrows.startup;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

import me.choco.arrows.api.Methods;

public class Recipes extends Methods implements Listener{
	
	static Plugin AA = Bukkit.getServer().getPluginManager().getPlugin("AlchemicalArrows");
	public static ArrayList<String> disabledArrows = new ArrayList<String>();

	ItemStack airArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.AirArrow.Crafts"), ChatColor.ITALIC + "Air Arrow");
	ItemStack earthArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.EarthArrow.Crafts"), ChatColor.GRAY + "Earth Arrow");
	ItemStack magicArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.MagicArrow.Crafts"), ChatColor.LIGHT_PURPLE + "Magic Arrow");
	ItemStack enderArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.EnderArrow.Crafts"), ChatColor.DARK_PURPLE + "Ender Arrow");
	ItemStack lifeArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.LifeArrow.Crafts"), ChatColor.GREEN + "Life Arrow");
	ItemStack deathArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.DeathArrow.Crafts"), ChatColor.BLACK + "Death Arrow");
	ItemStack lightArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.LightArrow.Crafts"), ChatColor.YELLOW + "Light Arrow");
	ItemStack darknessArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.DarknessArrow.Crafts"), ChatColor.DARK_GRAY + "Darkness Arrow");
	ItemStack fireArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.FireArrow.Crafts"), ChatColor.RED + "Fire Arrow");
	ItemStack frostArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.FrostArrow.Crafts"), ChatColor.AQUA + "Frost Arrow");
	ItemStack waterArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.WaterArrow.Crafts"), ChatColor.BLUE + "Water Arrow");
	ItemStack necroticArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.NecroticArrow.Crafts"), ChatColor.DARK_GREEN + "Necrotic Arrow");
	ItemStack confusionArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.ConfusionArrow.Crafts"), ChatColor.LIGHT_PURPLE + "Confusion Arrow");
	ItemStack magneticArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.MagneticArrow.Crafts"), ChatColor.GRAY + "Magnetic Arrow");
	
	public void enable(){
		createRecipe(airArrow, Material.FEATHER, AA.getConfig().getBoolean("ElementalArrows.AirArrow.Craftable"));
		createRecipe(earthArrow, Material.DIRT, AA.getConfig().getBoolean("ElementalArrows.EarthArrow.Craftable"));
		createRecipe(magicArrow, Material.BLAZE_POWDER, AA.getConfig().getBoolean("ElementalArrows.MagicArrow.Craftable"));
		createRecipe(enderArrow, Material.EYE_OF_ENDER, AA.getConfig().getBoolean("ElementalArrows.EnderArrow.Craftable"));
		createRecipe(lifeArrow, Material.SPECKLED_MELON, AA.getConfig().getBoolean("ElementalArrows.LifeArrow.Craftable"));
		createRecipe(deathArrow, Material.SKULL_ITEM, (byte) 1, AA.getConfig().getBoolean("ElementalArrows.DeathArrow.Craftable")); //TODO: Try with a long value
		createRecipe(lightArrow, Material.GLOWSTONE_DUST, AA.getConfig().getBoolean("ElementalArrows.LightArrow.Craftable"));
		createRecipe(darknessArrow, Material.COAL, AA.getConfig().getBoolean("ElementalArrows.DarknessArrow.Craftable"));
		createRecipe(darknessArrow, Material.COAL, (byte) 1, AA.getConfig().getBoolean("ElementalArrows.DarknessArrow.Craftable"));
		createRecipe(fireArrow, Material.FIREBALL, AA.getConfig().getBoolean("ElementalArrows.FireArrow.Craftable"));
		createRecipe(frostArrow, Material.SNOW_BALL, AA.getConfig().getBoolean("ElementalArrows.FrostArrow.Craftable"));
		createRecipe(waterArrow, Material.WATER_BUCKET, AA.getConfig().getBoolean("ElementalArrows.WaterArrow.Craftable"));
		createRecipe(necroticArrow, Material.ROTTEN_FLESH, AA.getConfig().getBoolean("ElementalArrows.NecroticArrow.Craftable"));
		createRecipe(confusionArrow, Material.POISONOUS_POTATO, AA.getConfig().getBoolean("ElementalArrows.ConfusionArrow.Craftable"));
		createRecipe(magneticArrow, Material.IRON_INGOT, AA.getConfig().getBoolean("ElementalArrows.MagneticArrow.Craftable"));
		
		if (!disabledArrows.isEmpty()){
			AA.getLogger().info("Disabled Crafting Recipes: " + disabledArrows.toString().trim());
		}
	}
	
	private static void createRecipe(ItemStack recipeItem, Material secondaryMaterial, boolean craftable){
		if (craftable){
			ShapelessRecipe recipe = new ShapelessRecipe(recipeItem).addIngredient(secondaryMaterial).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(recipe);
		}else{
			disabledArrows.add(recipeItem.getItemMeta().getDisplayName().replace(" Arrow", ", "));
		}
	}
	
	private static void createRecipe(ItemStack recipeItem, Material secondaryMaterial, byte byteData, boolean craftable){
		if (craftable){
			@SuppressWarnings("deprecation")
			ShapelessRecipe recipe = new ShapelessRecipe(recipeItem).addIngredient(secondaryMaterial, byteData).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(recipe);
		}else{
			disabledArrows.add(recipeItem.getItemMeta().getDisplayName().replace(" Arrow", ", "));
		}
	}
	
	@EventHandler
	public void onCraftPrepare(PrepareItemCraftEvent event){
		ItemStack result = event.getRecipe().getResult();
		if (event.getInventory().getContents().length > 0){
			if (isAlchemicalArrow(result)){
				for (ItemStack item : event.getInventory().getMatrix()){
					if (isAlchemicalArrow(item)){
						event.getInventory().setResult(new ItemStack(Material.AIR));
					}
				}
			}
			
			Player player = (Player) event.getInventory().getViewers().get(0);
			if (result.equals(airArrow)){
				checkPermission(player, "arrows.craft.air", event);
			}
			else if (result.equals(earthArrow)){
				checkPermission(player, "arrows.craft.earth", event);
			}
			else if (result.equals(magicArrow)){
				checkPermission(player, "arrows.craft.magic", event);
			}
			else if (result.equals(enderArrow)){
				checkPermission(player, "arrows.craft.ender", event);
			}
			else if (result.equals(lifeArrow)){
				checkPermission(player, "arrows.craft.life", event);
			}
			else if (result.equals(deathArrow)){
				checkPermission(player, "arrows.craft.death", event);
			}
			else if (result.equals(lightArrow)){
				checkPermission(player, "arrows.craft.light", event);
			}
			else if (result.equals(darknessArrow)){
				checkPermission(player, "arrows.craft.darkness", event);
			}
			else if (result.equals(fireArrow)){
				checkPermission(player, "arrows.craft.fire", event);
			}
			else if (result.equals(frostArrow)){
				checkPermission(player, "arrows.craft.frost", event);
			}
			else if (result.equals(waterArrow)){
				checkPermission(player, "arrows.craft.water", event);
			}
			else if (result.equals(necroticArrow)){
				checkPermission(player, "arrows.craft.necrotic", event);
			}
			else if (result.equals(confusionArrow)){
				checkPermission(player, "arrows.craft.confusion", event);
			}
			else if (result.equals(magneticArrow)){
				checkPermission(player, "arrows.craft.magnetic", event);
			}
		}
	}
	
	private boolean isAlchemicalArrow(ItemStack item){
		if (item == null)
			return false;
		if (!item.hasItemMeta())
			return false;
		if (item.getItemMeta().equals(airArrow.getItemMeta()) || item.getItemMeta().equals(earthArrow.getItemMeta()) || item.equals(magicArrow.getItemMeta()) || 
				item.getItemMeta().equals(enderArrow.getItemMeta()) || item.getItemMeta().equals(lifeArrow.getItemMeta()) || item.getItemMeta().equals(deathArrow.getItemMeta()) || 
				item.getItemMeta().equals(lightArrow.getItemMeta()) || item.getItemMeta().equals(darknessArrow.getItemMeta()) || item.getItemMeta().equals(fireArrow.getItemMeta()) || 
				item.getItemMeta().equals(frostArrow.getItemMeta()) || item.getItemMeta().equals(waterArrow.getItemMeta()) || item.getItemMeta().equals(necroticArrow.getItemMeta()) || 
				item.getItemMeta().equals(confusionArrow.getItemMeta()) || item.getItemMeta().equals(magneticArrow.getItemMeta()))
			return true;
		return false;
	}

	private void checkPermission(Player player, String permission, PrepareItemCraftEvent event){
		if (!player.hasPermission(permission)){
			event.getInventory().setResult(new ItemStack(Material.AIR));
		}
	}
}