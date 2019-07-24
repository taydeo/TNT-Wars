package com.rast.tntwars.systems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * This class has methods that are used to send title messages to players
 * 
 * @author Raster556
 */

public class TitleManager {
	public static void Title (ChatColor color1, ChatColor color2, String message1, String message2) {
		// Send a title to each player in the server
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.sendTitle(color1 + message1 , color2 + message2, 10, 40, 10);
		}
	}
}
