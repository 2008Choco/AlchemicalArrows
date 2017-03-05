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

import me.choco.arrows.AlchemicalArrows;

/**
 * Represents the base of an alchemical arrow with special effects
 * upon hitting a block, entity or player.
 * 
 * @author Parker Hawke - 2008Choco
 */
public abstract class AlchemicalArrow {
	
	protected final Arrow arrow;
	
	public AlchemicalArrow(Arrow arrow){
		this.arrow = arrow;
	}
	
	/** 
	 * Get the {@link Arrow} that represents this AlchemicalArrow
	 * 
	 * @return The representing arrow
	 */
	public Arrow getArrow(){
		return arrow;
	}
	
	/** 
	 * Get the name of the arrow to be displayed in chat 
	 */
	public abstract String getName();
	
	/** 
	 * Called when the arrow hits the ground
	 * <br><b>Note:</b> "block" parameter may be null. Though rare, be cautious
	 * 
	 * @param block - The block the arrow lands on
	 */
	public void onHitGround(Block block){}
	
	/** 
	 * Called when the arrow hits a player
	 * 
	 * @param player - The player damaged by the arrow
	 */
	public void onHitPlayer(Player player){}
	
	/** 
	 * Called when the arrow hits any entity excluding Players
	 * 
	 * @param entity - The entity damaged by the arrow
	 */
	public void onHitEntity(Entity entity){}
	
	/** 
	 * Called at low priority when a player successfully shoots an arrow
	 * 
	 * @param player - The player that shot the arrow
	 */
	public void onShootFromPlayer(Player player){}
	
	/** 
	 * Called at a low priority when a {@link Skeleton} successfully shoots an arrow. 
	 * {@link #skeletonsCanShoot()} must return true for this method to be called
	 * 
	 * @param skeleton - The skeleton that shot the arrow
	 */
	public void onShootFromSkeleton(Skeleton skeleton){}
	
	/** 
	 * Called at a low priority when a {@link BlockProjectileSource} (i.e. Dispenser) 
	 * shoots an arrow
	 * 
	 * @param source - The block source that shot the arrow
	 */
	public void onShootFromBlockSource(BlockProjectileSource source){}
	
	/** 
	 * Called the instant before {@link #onHitPlayer(Player)} or {@link #onHitEntity(Entity)} 
	 * is called. Used to cancel events if necessary 
	 */
	public void hitEntityEventHandler(EntityDamageByEntityEvent event){}
	
	/** 
	 * Fired the instant before {@link #onHitGround(Block)} is called. Used to cancel
	 * events if necessary
	 */
	public void hitGroundEventHandler(ProjectileHitEvent event){}
	
	/** 
	 * Fired the instant before {@link #onShootFromPlayer(Player)}, {@link #onShootFromSkeleton(Skeleton)} 
	 * or {@link #onShootFromBlockSource(BlockProjectileSource)} is called. Used to cancel events if necessary 
	 */
	public void shootEventHandler(ProjectileLaunchEvent event){}
	
	/** 
	 * Called whilst the arrow is still alive. The main intention for this is to
	 * determine the arrows particle effects, however may be used for other reasons
	 * such as world-interactions whilst in motion
	 * 
	 * @param player - The player to display the particle to
	 */
	public void displayParticle(Player player){}
	
	/** 
	 * Whether skeletons are able to shoot this arrow or not. Defaults to true
	 * 
	 * @return true if skeletons can shoot this arrow or not
	 */
	public boolean skeletonsCanShoot(){ return true; }
	
	/** 
	 * The weight (chance) at which skeletons are able to drop this arrow. Set
	 * to 0 if skeletons should not drop the arrow. Defaults to 10.0
	 * 
	 * @return The drop weight of this arrow
	 */
	public double skeletonLootWeight(){ return 10.0; }
	
	/** 
	 * Whether the infinity enchantment is allowed to be used with this arrow
	 * 
	 * @return Whether infinity is allowed or not
	 */
	public boolean allowInfinity(){ return false; }
	
	private static final AlchemicalArrows plugin = AlchemicalArrows.getPlugin();
	
	/** 
	 * Create a new instance of an AlchemicalArrow
	 * 
	 * @param type - The type of arrow to create
	 * @param arrow - The original arrow managed by Bukkit's API
	 * 
	 * @return a new instance of the specified arrow type. null if there is an invalid constructor in the class
	 */
	public static <T extends AlchemicalArrow> T createNewArrow(Class<T> type, Arrow arrow){
		try{
			return type.getDeclaredConstructor(Arrow.class).newInstance(arrow);
		}catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e){
			plugin.getLogger().warning("Could not create new arrow of type " + type.getName() + ". Constructor must only have parameter of type Arrow.class");
			return null;
		}
	}
}