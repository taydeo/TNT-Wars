package com.rast.tntwars.systems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * This class has methods that are used to send chat the way it would in 1.12 with team colors in chat.
 * 
 * @author Raster556
 */

public class ChatColorFix {

	public static void sendChat (Player player, String msg) {
		String text = "<" + ScoreBoardManager.getPlayerTeamColor(player) + player.getName() + ChatColor.WHITE + "> " + msg;
		Bukkit.broadcastMessage(text);
	}
	
}
