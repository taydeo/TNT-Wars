package com.rast.tntwars.systems;

import java.util.List;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * This class contains a method for playing a sound to all players in a world.
 * 
 * @author Raster556
 */

public class SoundPlayer {
	
	public static void PlayGlobalSound(World world, Sound sound, SoundCategory soundCategory, float volume, float pitch) {
		
		// Get all the players in the world
		List<Player> players = world.getPlayers();
		Player player;
		int size = players.size();
		
		// Play sound for each player
		for (Player value : players) {
			player = value;
			player.playSound(player.getLocation(), sound, soundCategory, volume, pitch);
		}
	}	
}
