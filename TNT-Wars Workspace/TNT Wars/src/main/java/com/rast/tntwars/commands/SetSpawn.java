package com.rast.tntwars.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.rast.tntwars.TNTWarsMain;

/**
 * This class is for the /setspawn command and contains the command executor and auto tab events if relevant.
 * 
 * @author Raster556
 */

public class SetSpawn implements TabCompleter, CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
	
		if (!TNTWarsMain.configEngine.developerMode) {
			sender.sendMessage(ChatColor.RED + "This feature is only available in Developer Mode! Use /developermode to toggle it");
			return true;
		}
		
		// We want sender to be player
		if (sender instanceof Player) {
			Player player = (Player) sender;
			// Which region do we want to save to
			switch (args[0]) {
			case "Lobby":
				TNTWarsMain.configEngine.saveLocation("lobbySpawn", player.getLocation());
				sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
						+ ChatColor.YELLOW + "Lobby Spawn Set");
				break;
	
			case "Island":
				if (args.length < 2) {
					sender.sendMessage(ChatColor.DARK_RED + "Invaled Island Number");
					break;
				}
				
				/// Which region do we want to save to
				switch (args[1]) {
				case "One":
					TNTWarsMain.configEngine.saveLocation("teamOneSpawn", player.getLocation());
					sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
							+ ChatColor.YELLOW + "Team one spawn set");
					break;
	
				case "Two":
					TNTWarsMain.configEngine.saveLocation("teamTwoSpawn", player.getLocation());
					sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
							+ ChatColor.YELLOW + "Team two spawn set");
					break;
					
				default:
					sender.sendMessage(ChatColor.DARK_RED + "Invaled Island Number");
					break;
				}
				break;
				
			case "WaitRoom":
				if (args.length < 2) {
					sender.sendMessage(ChatColor.DARK_RED + "Invaled Island Number");
					break;
				}
				
				/// Which region do we want to save to
				switch (args[1]) {
				case "One":
					TNTWarsMain.configEngine.saveLocation("teamOneWaitRoom", player.getLocation());
					sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
							+ ChatColor.YELLOW + "Team one wait room spawn set");
					break;
	
				case "Two":
					TNTWarsMain.configEngine.saveLocation("teamTwoWaitRoom", player.getLocation());
					sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
							+ ChatColor.YELLOW + "Team two wait room spawn set");
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
	
	// Auto Tab Event
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
		// Set tab list
		List<String> list = new ArrayList<>();
		if (args.length == 1) {
			list.add("Lobby");
			list.add("Island");
			list.add("WaitRoom");
		} else if (args[0].equalsIgnoreCase("Island") || args[0].equalsIgnoreCase("WaitRoom")) {
			list.add("One");
			list.add("Two");
		}
		
		return list;
	}
	
}
