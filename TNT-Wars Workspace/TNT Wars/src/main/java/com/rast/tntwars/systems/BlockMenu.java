package com.rast.tntwars.systems;

import java.util.List;
import java.util.Objects;

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

	public final Inventory chestInventory;

	public BlockMenu () {
		List<ItemStack> items = TNTWarsMain.configEngine.inventoryItems;
		int size = items.size();
		int emptySpace;
		if (size %9 == 0) {
			emptySpace = 0;
		} else {
			emptySpace = 9- size %9;
		}
		chestInventory = Bukkit.createInventory(null, size + emptySpace, ChatColor.GOLD + "Items");
		for (ItemStack item: items) {
			chestInventory.addItem(item);
		}

		for (int i = 0; i < emptySpace; i++) {
			ItemMeta data = TNTWarsMain.itemStackStorage.spacer.getItemMeta();
			Objects.requireNonNull(data).setDisplayName(ChatColor.BLACK + "" + i);
			TNTWarsMain.itemStackStorage.spacer.setItemMeta(data);
			chestInventory.addItem(TNTWarsMain.itemStackStorage.spacer);
		}
	}

	public void openInventory (Player player) {
		player.openInventory(chestInventory);
	}
	
}
