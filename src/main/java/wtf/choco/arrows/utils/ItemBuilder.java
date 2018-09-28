package wtf.choco.arrows.utils;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * A class to assist those in creating ItemStacks in the confines of a single line of code
 * This class removes the need to get ItemMeta
 * 
 * @author Parker Hawke - 2008Choco
 * @since 3/30/2016 - March 30th, 2016
 */
public class ItemBuilder {
	
	private final ItemStack item;
	private final ItemMeta meta;
	
	/**
	 * Construct a new ItemBuilder with the provided material
	 * 
	 * @param material the item's material
	 */
	public ItemBuilder(Material material) {
		this(new ItemStack(material));
	}
	
	/**
	 * Construct a new ItemBuilder based upon a pre-existing ItemStack instance. The
	 * constructed instance will be a clone of the provided ItemStack object
	 * 
	 * @param item the existing ItemStack
	 */
	public ItemBuilder(ItemStack item) {
		this.item = item.clone();
		this.meta = item.getItemMeta();
	}
	
	/**
	 * Set the item's name
	 * 
	 * @param name the new name to set
	 * 
	 * @return this instance. Allows for chained calls
	 */
	public ItemBuilder setName(String name) {
		this.meta.setDisplayName(name);
		return this;
	}
	
	/**
	 * Set the item's lore
	 * 
	 * @param lore the new lore to set
	 * 
	 * @return this instance. Allows for chained calls
	 */
	public ItemBuilder setLore(List<String> lore) {
		this.meta.setLore(lore);
		return this;
	}
	
	/**
	 * Set the item's amount
	 * 
	 * @param amount the new amount to set
	 * 
	 * @return this instance. Allows for chained calls
	 */
	public ItemBuilder setAmount(int amount) {
		this.item.setAmount(amount);
		return this;
	}
	
	/**
	 * Add {@link ItemFlag}s to the item
	 * 
	 * @param flags the flags to add to the item
	 * 
	 * @return this instance. Allows for chained calls
	 */
	public ItemBuilder addFlags(ItemFlag... flags) {
		this.meta.addItemFlags(flags);
		return this;
	}
	
	/**
	 * Add an {@link Enchantment} with a given level to the item
	 * 
	 * @param enchantment the enchantment to add
	 * @param level the level of the enchantment
	 * 
	 * @return this instance. Allows for chained calls
	 */
	public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
		this.meta.addEnchant(enchantment, level, true);
		return this;
	}
	
	/**
	 * Set the item's unbreakable state
	 * 
	 * @param unbreakable the new unbreakable state
	 * 
	 * @return this instance. Allows for chained calls
	 */
	public ItemBuilder setUnbreakable(boolean unbreakable) {
		this.meta.setUnbreakable(unbreakable);
		return this;
	}
	
	/**
	 * Apply a function from a specific implementation of ItemMeta. If the type of
	 * ItemMeta used in this ItemBuilder is not of the type passed to the function,
	 * the method call will be ignored and this ItemBuilder will continue without
	 * any errors.
	 * 
	 * @param metaType the type of meta to apply methods from
	 * @param metaFunction the meta application method
	 * @param <T> the type of meta
	 * 
	 * @return this instance. Allows for chained calls
	 */
	public <T extends ItemMeta> ItemBuilder applyCustomMeta(Class<T> metaType, Consumer<T> metaFunction) {
		if (!metaType.isInstance(meta)) return this;
		
		metaFunction.accept(metaType.cast(meta));
		return this;
	}
	
	/**
	 * Build the ItemStack and return the result
	 * 
	 * @return the resulting ItemStack
	 */
	public ItemStack build() {
		this.item.setItemMeta(meta);
		return item;
	}
	
}