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
import com.rast.tntwars.systems.DataConverters;

/**
 * This class is for the /setgameportal command and contains the command executor and auto tab events if relevant.
 * 
 * @author Raster556
 */

public class SetPortal implements TabCompleter, CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

		if (!TNTWarsMain.configEngine.developerMode) {
			sender.sendMessage(ChatColor.RED + "This feature is only available in Developer Mode! Use /developermode to toggle it");
			return true;
		}

		// We want sender to be player
		if (sender instanceof Player) {

			// We want to save the portal region
			int[][] reg = TNTWarsMain.configEngine.positionToArrays(TNTWarsMain.selectorManager.pos1, TNTWarsMain.selectorManager.pos2);

			// Which region do we want to save to
			switch (args[0]) {
			case "LobbyPortal":
				case "TeamOnePortalOne":
				case "TeamTwoPortalNine":
				case "TeamOnePortalNine":
				case "TeamTwoPortalEight":
				case "TeamOnePortalEight":
				case "TeamTwoPortalSeven":
				case "TeamOnePortalSeven":
				case "TeamTwoPortalSix":
				case "TeamOnePortalSix":
				case "TeamTwoPortalFive":
				case "TeamOnePortalFive":
				case "TeamTwoPortalFour":
				case "TeamOnePortalFour":
				case "TeamTwoPortalThree":
				case "TeamOnePortalThree":
				case "TeamTwoPortalTwo":
				case "TeamOnePortalTwo":
				case "TeamTwoPortalOne":
					savePortalRegion(args[0], reg, sender);
				break;
				default:
				sender.sendMessage(ChatColor.DARK_RED + "Invaled Portal Name");
				break;
			}
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "You must be a player to execute this command!");
		}
		return true;
	}

	private void savePortalRegion (String region, int[][] reg, CommandSender sender) {
		region = Character.toLowerCase(region.charAt(0)) + region.substring(1);
		TNTWarsMain.configEngine.saveLocation(region, reg);
		sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
				+ ChatColor.YELLOW + "Region set to " + ChatColor.GOLD + "(" + reg[0][0] + ", " + reg[1][0] + ", "
				+ reg[2][0] + "), (" + reg[0][0] + ", " + reg[1][0] + ", "
				+ reg[2][0] + ")");
	}

	// Auto Tab Event
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

		// Set tab list
		List<String> list = new ArrayList<>();
		if (args.length == 1) {
			list.add("LobbyPortal");

			for (int i = 0; i < TNTWarsMain.configEngine.teamPortals; i++) {
				list.add("TeamOnePortal" + DataConverters.numbToText(i+1));
				list.add("TeamTwoPortal" + DataConverters.numbToText(i+1));
			}
		}

		return list;
	}
}
