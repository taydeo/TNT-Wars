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
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamOnePortalOne":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamTwoPortalOne":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamOnePortalTwo":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamTwoPortalTwo":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamOnePortalThree":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamTwoPortalThree":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamOnePortalFour":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamTwoPortalFour":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamOnePortalFive":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamTwoPortalFive":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamOnePortalSix":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamTwoPortalSix":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamOnePortalSeven":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamTwoPortalSeven":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamOnePortalEight":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamTwoPortalEight":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamOnePortalNine":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamTwoPortalNine":
				savePortalRegion(args[0], reg, sender);
				break;
			case "TeamPortalCount": 
				if (args.length < 2) {
					sender.sendMessage(ChatColor.DARK_RED + "Please enter a number for the per team portal count");
					break;
				}
				TNTWarsMain.configEngine.saveLocation("teamPortals", Integer.parseInt(args[1]));
				sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
						+ ChatColor.YELLOW + "Set each teams portal count to " + ChatColor.GOLD + "(" + TNTWarsMain.configEngine.teamPortals + ")");
				sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
						+ ChatColor.YELLOW + "Make sure you set all the portals or the game will error.");
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
		List<String> list = new ArrayList<String>();
		if (args.length == 1) {
			list.add("TeamPortalCount");
			list.add("LobbyPortal");

			for (int i = 0; i < TNTWarsMain.configEngine.teamPortals; i++) {
				list.add("TeamOnePortal" + DataConverters.numbToText(i+1));
				list.add("TeamTwoPortal" + DataConverters.numbToText(i+1));
			}
		}

		return list;
	}
}
