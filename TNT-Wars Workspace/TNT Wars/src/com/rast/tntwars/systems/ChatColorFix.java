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
		StringBuilder builder = new StringBuilder();
		builder.append('<');
		builder.append(ScoreBoardManager.getPlayerTeamColor(player));
		builder.append(player.getName());
		builder.append(ChatColor.WHITE);
		builder.append("> ");
		builder.append(msg);
		String text = builder.toString();
		Bukkit.broadcastMessage(text);
	}
	
}
