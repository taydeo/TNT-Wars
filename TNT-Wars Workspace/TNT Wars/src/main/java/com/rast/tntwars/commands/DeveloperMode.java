package com.rast.tntwars.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.rast.tntwars.TNTWarsMain;

/**
 * This class is for the /developermode command and contains the command executor and auto tab events if relevant.
 * 
 * @author Raster556
 */

public class DeveloperMode implements TabCompleter, CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
	
		if (args.length == 1) {
			// Which state do we want to save it as
			switch (args[0]) {
			case "True":
				if (TNTWarsMain.configEngine.developerMode) {
					sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
							+ ChatColor.YELLOW + "Developer Mode already set to  " + ChatColor.GOLD + "(True)");
				} else {
					TNTWarsMain.configEngine.saveLocation("developerMode", true);
					sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
							+ ChatColor.YELLOW + "Developer Mode set to " + ChatColor.GOLD + "(" + TNTWarsMain.configEngine.developerMode + ")");
				}
				break;
	
			case "False":
				if (!TNTWarsMain.configEngine.developerMode) {
					sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
							+ ChatColor.YELLOW + "Developer Mode already set to  " + ChatColor.GOLD + "(False)");
				} else {
					TNTWarsMain.configEngine.saveLocation("developerMode", false);
					sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
							+ ChatColor.YELLOW + "Developer Mode set to " + ChatColor.GOLD + "(" + TNTWarsMain.configEngine.developerMode + ")");
				}
				break;
				
			default:
				sender.sendMessage(ChatColor.DARK_RED + "Use True or False");
				break;
			}
		} else {
			TNTWarsMain.configEngine.saveLocation("developerMode", !TNTWarsMain.configEngine.developerMode);
			sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
					+ ChatColor.YELLOW + "Developer Mode set to " + ChatColor.GOLD + "(" + TNTWarsMain.configEngine.developerMode + ")");
		}
			
	return true;
	}
	
	// Auto Tab Event
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
		// Set tab list
		List<String> list = new ArrayList<>(2);
		if (args.length == 1) {
			list.add("True");
			list.add("False");
		}
		return list;
	}
	
}
