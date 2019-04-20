package com.rast.tntwars;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

/**
 * This class creates and stores the custom items for our game.
 * 
 * @author Raster556
 */

public class StaticItemStackStorage {
	
	public ItemStack selectorItem;
	public ItemStack blockMenuItem;
	public ItemStack spacer;
	
	public StaticItemStackStorage () {
		selectorItem = GenerateSelectorItem();
		blockMenuItem = GenerateBlockMenuItem();
		spacer = GenerateSpacerItem();
	}
	
	
	// Generate the selector item
	private static ItemStack GenerateSelectorItem () {
		ItemStack tempItem = new ItemStack(Material.GOLDEN_PICKAXE);
		ItemMeta tempMetaData = tempItem.getItemMeta();
		tempMetaData.setDisplayName(ChatColor.GOLD + "Region Selector");
		tempMetaData.addEnchant(Enchantment.DURABILITY, 5, true);
		tempItem.setItemMeta(tempMetaData);
		return tempItem;
	}
	
	// Generate the block menu item
	private static ItemStack GenerateBlockMenuItem () {
		ItemStack tempItem = new ItemStack(Material.CHEST);
		ItemMeta tempMetaData = tempItem.getItemMeta();
		tempMetaData.setDisplayName(ChatColor.GOLD + "Items");
		tempItem.setItemMeta(tempMetaData);
		return tempItem;
	}
	
	// Generate a inventory spacer
	private static ItemStack GenerateSpacerItem () {
		ItemStack tempItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		return tempItem;
	}
	
}
