package me.choco.arrows.api;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.BlockProjectileSource;

import com.google.common.base.Preconditions;

import me.choco.arrows.AlchemicalArrows;

public abstract class AlchemicalArrow {
	
	protected final Arrow arrow;
	public AlchemicalArrow(Arrow arrow){
		this.arrow = arrow;
	}
	
	/** Get the base arrow linked to this AlchemicalArrow
	 * @return The arrow
	 */
	public Arrow getArrow(){
		return arrow;
	}
	
	/** Get the name of the arrow used to be displayed in chat */
	public abstract String getName();
	
	/** This method will be called when the arrow hits the ground
	 * <br><b><i>Note: "block" parameter may on the odd occasion be null. Though rare, be cautious</i></b>
	 * @param block The block the arrow lands on
	 */
	public void onHitGround(Block block){}
	
	/** This method will be called when the arrow hits a player
	 * @param player The player damaged by the arrow
	 */
	public void onHitPlayer(Player player){}
	
	/** This method will be called when the arrow hits an entity (excluding Players)
	 * @param entity The entity damaged by the arrow
	 */
	public void onHitEntity(Entity entity){}
	
	/** This method is fired at a low priority when a player successfully shoots an arrow
	 * @param player The player that shot the arrow
	 */
	public void onShootFromPlayer(Player player){}
	
	/** This method is fired at a low priority when a Skeleton successfully shoots an arrow
	 * <br>{@link #skeletonsCanShoot()} must return true for this method to be called</b>
	 * @param skeleton
	 */
	public void onShootFromSkeleton(Skeleton skeleton){}
	
	/** This method is fired at a low priority when a BlockProjectileSource (i.e. Dispenser) shoots an arrow
	 * @param source The block source that shot the arrow
	 */
	public void onShootFromBlockSource(BlockProjectileSource source){}
	
	/** Fired the instant before onHitPlayer() or onHitEntity() is called. Used to cancel events if necessary */
	public void hitEntityEventHandler(EntityDamageByEntityEvent event){}
	
	/** Fired the instant before onHitBlock() is called. Used to cancel events if necessary */
	public void hitGroundEventHandler(ProjectileHitEvent event){}
	
	/** Fired the instant before onShootFromPlayer(), onShootFromSkeleton(), or onShootFromBlockSource() is called. Used to cancel events if necessary */
	public void shootEventHandler(ProjectileLaunchEvent event){}
	
	/** This method will be called whilst the arrow is still alive
	 * <br> The main intention for this is to determine the arrows particle effects, 
	 * however may be used for other reasons, such as world-interactions whilst in motion
	 * @param player The player to display the partcile to
	 */
	public void displayParticle(Player player){}
	
	/** Whether skeletons are able to shoot this arrow or not. Defaults to true
	 * @return Whether skeletons may shoot this arrow or not
	 */
	public boolean skeletonsCanShoot(){ return true; }
	
	/** Whether the infinity enchantment is allowed to be used with this arrow
	 * @return Whether infinity is allowed or not
	 */
	public boolean allowInfinity(){ return false; }
	
	private static final AlchemicalArrows plugin = AlchemicalArrows.getPlugin();
	
	/** Create a new instance of an AlchemicalArrow
	 * @param type - The type of arrow to create
	 * @param arrow - The original arrow managed by Bukkit's API
	 * @return a new instance of the specified arrow type. null if there is an invalid constructor in the class
	 */
	public static <T extends AlchemicalArrow> T createNewArrow(Class<T> type, Arrow arrow){
		Preconditions.checkNotNull(arrow, "The provided arrow cannot be null");
		
		try{
			return type.getDeclaredConstructor(Arrow.class).newInstance(arrow);
		}catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e){
			plugin.getLogger().warning("Could not create new arrow of type " + type.getName() + ". Constructor must only have parameter of type Arrow.class");
			return null;
		}
	}
}