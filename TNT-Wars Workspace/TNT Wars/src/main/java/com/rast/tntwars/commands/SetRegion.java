package com.rast.tntwars.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.rast.tntwars.TNTWarsMain;

/**
 * This class is for the /setregion command and contains the command executor and auto tab events if relevant.
 * 
 * @author Raster556
 */

public class SetRegion implements TabCompleter, CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
	
		if (!TNTWarsMain.configEngine.developerMode) {
			sender.sendMessage(ChatColor.RED + "This feature is only available in Developer Mode! Use /developermode to toggle it");
			return true;
		}
		
		// We want sender to be player
		if (sender instanceof Player) {
			// Which region do we want to save to
			switch (args[0]) {
			case "Lobby":
				
				// We want to save the lobby region
				int[][] reg = TNTWarsMain.configEngine.positionToArrays(TNTWarsMain.selectorManager.pos1, TNTWarsMain.selectorManager.pos2);
				TNTWarsMain.configEngine.saveLocation("lobby", reg);
				break;
	
			case "Island":
				
				/// Which region do we want to save to
				switch (args[1]) {
				case "One":
					saveIslandOne(sender);
					break;
	
				case "Two":
					saveIslandTwo(sender);
					break;
					
				default:
					sender.sendMessage(ChatColor.DARK_RED + "Invaled Island Number");
					break;
				}
				break;
				
			default:
				sender.sendMessage(ChatColor.DARK_RED + "Invaled Region Name");
				break;
			}
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "You must be a player to execute this command!");
		}
	return true;
	}

	private void saveIslandOne (CommandSender sender) {
		// We want to save the island one region
		int[][] reg = TNTWarsMain.configEngine.positionToArrays(TNTWarsMain.selectorManager.pos1, TNTWarsMain.selectorManager.pos2);
		TNTWarsMain.configEngine.saveLocation("teamOneIsland", reg);
		sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
				+ ChatColor.YELLOW + "Region set to " + ChatColor.GOLD + "(" + reg[0][0] + ", " + reg[1][0] + ", "
				+ reg[2][0] + "), (" + reg[0][0] + ", " + reg[1][0] + ", "
				+ reg[2][0] + ")");
	}
	
	private void saveIslandTwo (CommandSender sender) {
		// We want to save the island two region
		int[][] reg = TNTWarsMain.configEngine.positionToArrays(TNTWarsMain.selectorManager.pos1, TNTWarsMain.selectorManager.pos2);
		TNTWarsMain.configEngine.saveLocation("teamTwoIsland", reg);
		sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
				+ ChatColor.YELLOW + "Region set to " + ChatColor.GOLD + "(" + reg[0][0] + ", " + reg[1][0] + ", "
				+ reg[2][0] + "), (" + reg[0][0] + ", " + reg[1][0] + ", "
				+ reg[2][0] + ")");
	}
	
	// Auto Tab Event
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
		// Set tab list
		List<String> list = new ArrayList<>(2);
		if (args.length == 1) {
			list.add("Lobby");
			list.add("Island");
		}
		
		if (args.length == 2 && Objects.equals(args[0], "Island")) {
			list.add("One");
			list.add("Two");
		}
		return list;
	}
	
}
