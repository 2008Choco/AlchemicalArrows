package me.choco.arrows.registry;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.inventory.ItemStack;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;

public class ArrowRegistry {
	
	/** Used to keep track of all arrows currently active in the world */
	private HashMap<UUID, AlchemicalArrow> arrows = new HashMap<>();
	
	/** Used to determine what ItemStack corresponds to what AlchemicalArrow type when registering arrows when shot */
	private static HashMap<ItemStack, Class<? extends AlchemicalArrow>> arrowRegistry = new HashMap<>();
	
	/** Register an Alchemical Arrow.
	 * <br>This is REQUIRED in order for AlchemicalArrows to recognize that you are in fact shooting an Alchemical Arrow
	 * @param item The item variation of the Alchemical Arrow
	 * @param clazz The class extending AlchemicalArrow
	 * @throws IllegalArgumentException Thrown if ItemStack parameter is not of Material type ARROW
	 */
	public static void registerAlchemicalArrow(ItemStack item, Class<? extends AlchemicalArrow> clazz){
		ItemStack duplicate = new ItemStack(item);
		duplicate.setAmount(1);
		
		if (getArrowRegistry().containsKey(duplicate)){
			throw new IllegalArgumentException("ItemStack is already being used by class " + getArrowRegistry().get(duplicate).getName());
		}else if (!item.getType().equals(Material.ARROW)){
			throw new IllegalArgumentException("Arrow registry requires Material Enum type of ARROW. Given " + duplicate.getType()); 
		}
		for (Class<? extends AlchemicalArrow> refClazz : getArrowRegistry().values()){
			if (refClazz.getSimpleName().replace("Arrow", "").equalsIgnoreCase(clazz.getSimpleName().replace("Arrow", ""))){
				throw new IllegalArgumentException("Class " + clazz.getSimpleName() + " is already in use in package " + refClazz.getName() + ". (Change your class name)");
			}
		}
		
		arrowRegistry.put(duplicate, clazz);
		if (!clazz.getPackage().getName().startsWith("me.choco.arrows.utils.arrows")){
			AlchemicalArrows.getPlugin().getLogger().info("Successfully registered external arrow (" + clazz.getSimpleName() + ") from package " + clazz.getPackage().getName());
		}
	}
	
	/** Get the registry for all Alchemical Arrows. Mainly used for Alchemical Arrows registration purposes, but is free to use
	 * @return The registry of arrow classes and items
	 */
	public static HashMap<ItemStack, Class<? extends AlchemicalArrow>> getArrowRegistry(){
		return arrowRegistry;
	}
	
	/** Register an Arrow extending the AlchemicalArrow class. Used to keep track of living arrow entities
	 * @param arrow The arrow to register
	 */
	public void registerAlchemicalArrow(AlchemicalArrow arrow){
		arrows.put(arrow.getArrow().getUniqueId(), arrow);
	}
	
	/** Unregister an Arrow extending the AlchemicalArrow class
	 * @param arrow The arrow to unregister
	 * @deprecated This method should not be used as Alchemical Arrows automatically unregisters this automatically
	 * <br>ConcurrentModificationExceptions may occur if this method is used
	 */
	@Deprecated
	public void unregisterAlchemicalArrow(AlchemicalArrow arrow){
		arrows.remove(arrow.getArrow().getUniqueId());
	}
	
	/** Unregister an Arrow extending the AlchemicalArrow class
	 * @param arrow The Arrow entity to unregister
	 * @deprecated This method should not be used as Alchemical Arrows automatically unregisters this automatically
	 * <br>ConcurrentModificationExceptions may occur if this method is used
	 */
	@Deprecated
	public void unregisterAlchemicalArrow(Arrow arrow){
		arrows.remove(arrow.getUniqueId());
	}
	
	/** Unregister an Arrow extending the AlchemicalArrow class
	 * @param arrow The UUID of the arrow to unregister
	 * @deprecated This method should not be used as Alchemical Arrows automatically unregisters this automatically
	 * <br>ConcurrentModificationExceptions may occur if this method is used
	 */
	@Deprecated
	public void unregisterAlchemicalArrow(UUID uuid){
		arrows.remove(uuid);
	}
	
	/** Get a registered Arrow tracked by Alchemical Arrows
	 * @param arrow The arrow to get an instance of
	 * @return An instance of a class extending AlchemicalArrow
	 */
	public AlchemicalArrow getAlchemicalArrow(Arrow arrow){
		return (arrows.get(arrow.getUniqueId()));
	}
	
	/** Get a registered Arrow tracked by Alchemical Arrows
	 * @param uuid The UUID to get an instance of 
	 * @return An instance of a class extending AlchemicalArrow
	 */
	public AlchemicalArrow getAlchemicalArrow(UUID uuid){
		return (arrows.get(uuid));
	}
	
	/** Check whether an Arrow is registered or not
	 * @param arrow The arrow to reference
	 * @return Whether it is registered/tracked or not
	 */
	public boolean isAlchemicalArrow(Arrow arrow){
		return (arrows.containsKey(arrow.getUniqueId()));
	}
	
	/** Check whether an Arrow is registered or not
	 * @param uuid The UUID to reference
	 * @return Whether it is registered/tracked or not
	 */
	public boolean isAlchemicalArrow(UUID uuid){
		return (arrows.containsKey(uuid));
	}
	
	/** Check whether an ItemStack is a registered arrow or not
	 * @param item - The item to check
	 * @return Whether it is an arrow or not
	 */
	public boolean isAlchemicalArrow(ItemStack item){
		return (arrowRegistry.containsKey(item));
	}
	
	/** Get the registered/tracked arrows from AlchemicalArrows. May be used to manipulate all current arrows
	 * @return The registered AlchemicalArrow HashMap
	 */
	public HashMap<UUID, AlchemicalArrow> getRegisteredArrows(){
		return arrows;
	}
}