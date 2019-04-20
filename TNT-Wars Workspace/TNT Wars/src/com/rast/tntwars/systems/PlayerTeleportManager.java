package com.rast.tntwars.systems;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.rast.tntwars.TNTWarsMain;;

/**
 * This class is where all events are handled and listened for.
 * 
 * @author Raster556
 */

public class PlayerTeleportManager {
	
	// Methods to teleport player to these positions 
	public static void teleportLobby(Player player, boolean reset) {
		player.setGameMode(GameMode.ADVENTURE);
		if (reset) {
			resetPlayerStats(player);
		}
		player.teleport(TNTWarsMain.configEngine.lobbySpawn);
	}
	
	public static void teleportTeamOneIsland(Player player, boolean reset) {
		int spawnRadius = TNTWarsMain.configEngine.gameSpawnClearRadius;
		Location loc;
		loc = TNTWarsMain.configEngine.teamOneSpawn.clone();
		loc.add(0, spawnRadius-1, 0);
		WorldEditor.fillRegion(loc, spawnRadius, Material.AIR);
		player.setGameMode(GameMode.SURVIVAL);
		if (reset) {
			resetPlayerStats(player);
		}
		player.teleport(TNTWarsMain.configEngine.teamOneSpawn);
	}
	
	public static void teleportTeamTwoIsland(Player player, boolean reset) {
		int spawnRadius = TNTWarsMain.configEngine.gameSpawnClearRadius;
		Location loc;
		loc = TNTWarsMain.configEngine.teamTwoSpawn.clone();
		loc.add(0, spawnRadius-1, 0);
		WorldEditor.fillRegion(loc, spawnRadius, Material.AIR);
		player.setGameMode(GameMode.SURVIVAL);
		if (reset) {
			resetPlayerStats(player);
		}
		player.teleport(TNTWarsMain.configEngine.teamTwoSpawn);
	}
	
	public static void teleportTeamOneWaitRoom(Player player, boolean reset) {
		player.setGameMode(GameMode.ADVENTURE);
		if (reset) {
			resetPlayerStats(player);
		}
		player.teleport(TNTWarsMain.configEngine.teamOneWaitRoomSpawn);
	}
	
	public static void teleportTeamTwoWaitRoom(Player player, boolean reset) {
		player.setGameMode(GameMode.ADVENTURE);
		if (reset) {
			resetPlayerStats(player);
		}
		player.teleport(TNTWarsMain.configEngine.teamTwoWaitRoomSpawn);
	}
	
	// Reset the players health, hunger, and inventory
	private static void resetPlayerStats(Player player) {
		player.getInventory().clear();
		player.setHealth(20);
		player.setFoodLevel(20);
	}

}
