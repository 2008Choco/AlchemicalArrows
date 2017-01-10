package me.choco.arrows.registry;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;

public class ArrowRegistry {
	
	/** Used to keep track of all arrows currently active in the world */
	private final Map<UUID, AlchemicalArrow> arrows = Maps.newHashMap();
	private final Set<UUID> arrowsToPurge = new HashSet<>();
	
	/** Used to determine what ItemStack corresponds to what AlchemicalArrow type when registering arrows when shot */
	private static final BiMap<ItemStack, Class<? extends AlchemicalArrow>> ARROW_REGISTRY = HashBiMap.create(64);
	private static final Map<Class<? extends AlchemicalArrow>, AlchemicalArrow> INFORMATIONAL_INSTANCE_MAP = Maps.newHashMap();
	
	/** Register an Alchemical Arrow.
	 * <br>This is REQUIRED in order for AlchemicalArrows to recognize that you are in fact shooting an Alchemical Arrow
	 * @param item The item variation of the Alchemical Arrow
	 * @param clazz The class extending AlchemicalArrow
	 * @throws IllegalArgumentException Thrown if ItemStack parameter is not of Material type ARROW
	 */
	public static void registerAlchemicalArrow(ItemStack item, Class<? extends AlchemicalArrow> clazz){
		ItemStack duplicate = new ItemStack(item);
		duplicate.setAmount(1);
		
		if (ARROW_REGISTRY.containsKey(duplicate)){
			throw new IllegalArgumentException("ItemStack is already being used by class " + getArrowRegistry().get(duplicate).getName());
		}else if (!item.getType().equals(Material.ARROW)){
			throw new IllegalArgumentException("Arrow registry requires Material Enum type of ARROW. Given " + duplicate.getType()); 
		}
		for (Class<? extends AlchemicalArrow> refClazz : ARROW_REGISTRY.values()){
			if (refClazz.getSimpleName().replace("Arrow", "").equalsIgnoreCase(clazz.getSimpleName().replace("Arrow", ""))){
				throw new IllegalArgumentException("Class " + clazz.getSimpleName() + " is already in use in package " + refClazz.getName() + ". (Change your class name)");
			}
		}
		
		ARROW_REGISTRY.put(duplicate, clazz);
		INFORMATIONAL_INSTANCE_MAP.put(clazz, AlchemicalArrow.createNewArrow(clazz, null));
		if (!clazz.getPackage().getName().startsWith("me.choco.arrows.utils.arrows")){
			AlchemicalArrows.getPlugin().getLogger().info("Successfully registered external arrow (" + clazz.getName() + ")");
		}
	}
	
	/** Get the registry for all Alchemical Arrows. Mainly used for Alchemical Arrows registration purposes, but is free to use
	 * @return The registry of arrow classes and items
	 */
	public static BiMap<ItemStack, Class<? extends AlchemicalArrow>> getArrowRegistry(){
		return ImmutableBiMap.copyOf(ARROW_REGISTRY);
	}
	
	/** Register an Arrow extending the AlchemicalArrow class. Used to keep track of living arrow entities
	 * @param arrow The arrow to register
	 */
	public void registerAlchemicalArrow(AlchemicalArrow arrow){
		arrows.put(arrow.getArrow().getUniqueId(), arrow);
	}
	
	/** Unregister an Arrow extending the AlchemicalArrow class
	 * <br><b>NOTE: </b>This will prepare the arrow for deletion, and not immediately
	 * delete it until the {@link #purgeArrows()} method is called
	 * @param arrow The arrow to unregister
	 */
	public void unregisterAlchemicalArrow(AlchemicalArrow arrow){
		this.arrowsToPurge.add(arrow.getArrow().getUniqueId());
	}
	
	/** Unregister an Arrow extending the AlchemicalArrow class
	 * <br><b>NOTE: </b>This will prepare the arrow for deletion, and not immediately
	 * delete it until the {@link #purgeArrows()} method is called
	 * @param arrow The Arrow entity to unregister
	 */
	public void unregisterAlchemicalArrow(Arrow arrow){
		this.arrowsToPurge.add(arrow.getUniqueId());
	}
	
	/** Unregister an Arrow extending the AlchemicalArrow class.
	 * <br><b>NOTE: </b>This will prepare the arrow for deletion, and not immediately
	 * delete it until the {@link #purgeArrows()} method is called
	 * @param arrow The UUID of the arrow to unregister
	 */
	public void unregisterAlchemicalArrow(UUID uuid){
		this.arrowsToPurge.add(uuid);
	}
	
	/** Remove all arrows from the registry that were prepared for unregistration
	 */
	public void purgeArrows(){
		this.arrowsToPurge.forEach(u -> this.arrows.remove(u));
		this.arrowsToPurge.clear();
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
		return (ARROW_REGISTRY.containsKey(item));
	}
	
	/** Get the registered/tracked arrows from AlchemicalArrows. May be used to manipulate all current arrows
	 * @return The registered AlchemicalArrow HashMap
	 */
	public Map<UUID, AlchemicalArrow> getRegisteredArrows(){
		return ImmutableMap.copyOf(arrows);
	}
	
	/** Get an instance of an alchemical arrow to be used for INFORMATIONAL PURPOSES! 
	 * (i.e. to retrieve data from methods). 
	 * <br><b>NOTE: </b>The instance of AlchemicalArrow provides a null arrow parameter
	 * @param clazz - The arrow to create
	 * @return an instance of alchemical arrow
	 */
	public AlchemicalArrow getInformationalInstance(Class<? extends AlchemicalArrow> clazz) {
		return INFORMATIONAL_INSTANCE_MAP.get(clazz);
	}
	
	public void clearRegisteredArrows() {
		this.arrows.clear();
	}
	
	public void clearArrowRegistry() {
		ARROW_REGISTRY.clear();
		INFORMATIONAL_INSTANCE_MAP.clear();
	}
}