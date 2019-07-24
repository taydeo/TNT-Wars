package com.rast.tntwars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.rast.tntwars.TNTWarsMain;

import net.md_5.bungee.api.ChatColor;

/**
 * This class is for the /getselector command and contains the command executor and auto tab events if relevant.
 * 
 * @author Raster556
 */

public class GetSelector implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		
		// We need to make sure it is a player because we need their inventory
		if (sender instanceof Player) {
			// Put the selector tool in their inventory
			((Player) sender).getInventory().addItem(TNTWarsMain.itemStackStorage.selectorItem);
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "You must be a player to execute this command!");
		}
		return true;
	}

}
