package com.rast.tntwars.systems;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.rast.tntwars.TNTWarsMain;

/**
 * This class handles the chest inventory used for players to get blocks
 * 
 * @author Raster556
 */

public class BlockMenu {

	public Inventory chestInventory;
	private List<ItemStack> items;
	private int size ;
	private int emptySpace;
	private ItemStack spacer = TNTWarsMain.itemStackStorage.spacer;
	
	public BlockMenu () {
		items = TNTWarsMain.configEngine.inventoryItems;
		size = items.size();
		if (size%9 == 0) {
			emptySpace = 0;
		} else {
			emptySpace = 9-size%9;
		}
		chestInventory = Bukkit.createInventory(null, size+emptySpace, ChatColor.GOLD + "Items");
		for (ItemStack item: items) {
			chestInventory.addItem(item);
		}
		
		for (int i = 0; i < emptySpace; i++) {
			ItemStack locSpacer = spacer;
			ItemMeta data = locSpacer.getItemMeta();
			data.setDisplayName(ChatColor.BLACK + "" + i);
			locSpacer.setItemMeta(data);
			chestInventory.addItem(locSpacer);
		}
	}
	
	public void openInventory (Player player) {
		player.openInventory(chestInventory);
	}
	
}
