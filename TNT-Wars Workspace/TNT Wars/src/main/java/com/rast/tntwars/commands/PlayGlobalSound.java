package com.rast.tntwars.commands;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import com.rast.tntwars.systems.SoundPlayer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

/**
 * This class is for the /playglobalsound command and contains the command executor and auto tab events if relevant.
 * 
 * @author Raster556
 */

public class PlayGlobalSound implements TabCompleter, CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		
		Sound sound;
		
		// Convert sound player wants to format we can use
		switch (args[0]) {
		case "Ding":
			sound = Sound.BLOCK_NOTE_BLOCK_BELL;
			break;

		case "Portal_Break":
			sound = Sound.ENTITY_WITHER_AMBIENT;
			break;

		case "Game_Over":
			sound = Sound.ENTITY_WITHER_DEATH;
			break;
			
		case "Game_Start_Fail":
			sound = Sound.BLOCK_PISTON_CONTRACT;
			break;
			
		default:
			sender.sendMessage(ChatColor.DARK_RED + "Invaled Sound Name");
			sound = Sound.BLOCK_NOTE_BLOCK_SNARE;
			break;
		}
		
		// We need sender to be player in order to get world
		if (sender instanceof Player) {
			SoundPlayer.PlayGlobalSound(((Player) sender).getWorld(), sound, SoundCategory.MASTER, Float.MAX_VALUE, 1);
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "You must be a player to execute this command!");
		}
		return true;
	}

	// Auto Tab Event
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
		// Set tab list
		List<String> list = new ArrayList<>(3);
		if (args.length == 1) {
			list.add("Ding");
			list.add("Portal_Break");
			list.add("Game_Over");
		}
		return list;
	}
	
}
