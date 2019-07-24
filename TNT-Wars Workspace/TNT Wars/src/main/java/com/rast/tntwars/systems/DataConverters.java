package com.rast.tntwars.systems;

import org.bukkit.ChatColor;

/**
 * This class has methods to convert data from one type to another.
 * 
 * @author Raster556
 */

public class DataConverters {
	// Convert number to text
	public static String numbToText (int numb) {
		switch (numb) {
		case 2:
			return "Two";
		case 3:
			return "Three";
		case 4:
			return "Four";
		case 5:
			return "Five";
		case 6:
			return "Six";
		case 7:
			return "Seven";
		case 8:
			return "Eight";
		case 9:
			return "Nine";
		default:
			return "One";
		}
	}
	

	// Convert our color from a string to an enum
	public static ChatColor convertColor(String color) {
		switch (color) {
		case "AQUA":
			return ChatColor.AQUA;
		case "BLACK":
			return ChatColor.BLACK;
		case "BLUE":
			return ChatColor.BLUE;
		case "DARK_AQUA":
			return ChatColor.DARK_AQUA;
		case "DARK_BLUE":
			return ChatColor.DARK_BLUE;
		case "DARK_GRAY":
			return ChatColor.DARK_GRAY;
		case "DARK_GREEN":
			return ChatColor.DARK_GREEN;
		case "DARK_PURPLE":
			return ChatColor.DARK_PURPLE;
		case "DARK_RED":
			return ChatColor.DARK_RED;
		case "GOLD":
			return ChatColor.GOLD;
		case "GRAY":
			return ChatColor.GRAY;
		case "GREEN":
			return ChatColor.GREEN;
		case "LIGHT_PURPLE":
			return ChatColor.LIGHT_PURPLE;
		case "RED":
			return ChatColor.RED;
		case "YELLOW":
			return ChatColor.YELLOW;
		default:
			return ChatColor.WHITE;
		}

	}
}
