package com.rast.tntwars.systems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.rast.tntwars.TNTWarsMain;

/**
 * This class contains methods for detecting the end game conditions and manages shutting down the game.
 * 
 * @author Raster556
 */

public class GameEnder {
	
	private static World world = TNTWarsMain.configEngine.gameWorld;
	private TNTWarsMain tntwars = TNTWarsMain.getInstance;
	public boolean gameEnded = false;
	
	// Test each portal for a team to lose all its portals
	public void testPortals() {
		if (gameEnded ) {
			return;
		}
		int team1PortalTotal = 0;
		int team2PortalTotal = 0;
		
		// Check each portal if it is alive and which team it belongs to for the count
		for (Portal portal : TNTWarsMain.configEngine.teamPortalList) {
			if (!portal.destroyed) {
				switch (portal.teamNumb) {
				case 1:
					team1PortalTotal++;
					break;

				case 2:
					team2PortalTotal++;
					break;

				default:
					break;
				}
			}

		}
		
		// If for some reason there is a tie we end the game in a tie.
		if (team1PortalTotal == 0 && team2PortalTotal == 0) {
			Bukkit.broadcastMessage(ChatColor.YELLOW + "The Game is a Tie!");
			SoundPlayer.PlayGlobalSound(world, Sound.ENTITY_WITHER_HURT, SoundCategory.AMBIENT, Float.MAX_VALUE, 0.9f);
			expelPlayers();
		} else if (team2PortalTotal == 0) {
			Bukkit.broadcastMessage(TNTWarsMain.configEngine.teamOneColor + TNTWarsMain.configEngine.teamOneName + ChatColor.GREEN + " team won the game!");
			SoundPlayer.PlayGlobalSound(world, Sound.ENTITY_WITHER_DEATH, SoundCategory.AMBIENT, Float.MAX_VALUE, 1);
			expelPlayers();
			
		} else if (team1PortalTotal == 0) {
			Bukkit.broadcastMessage(TNTWarsMain.configEngine.teamTwoColor + TNTWarsMain.configEngine.teamTwoName + ChatColor.GREEN + " team won the game!");
			SoundPlayer.PlayGlobalSound(world, Sound.ENTITY_WITHER_DEATH, SoundCategory.AMBIENT, Float.MAX_VALUE, 1);
			expelPlayers();
		}
	}
	
	
	// Unload maps
	public void unloadMap(String mapname) {
		if (Bukkit.getServer().unloadWorld(Bukkit.getServer().getWorld(mapname), false)) {
			Bukkit.broadcastMessage("Successfully unloaded map");
		} else {
			Bukkit.broadcastMessage("Failed to unload map");
		}
	}
	
	// Kick players and restart map after delay time
	public void expelPlayers() {
		gameEnded = true;
		Bukkit.broadcastMessage(ChatColor.BLUE + "Game will restart in " + TNTWarsMain.configEngine.gameEndTimer + " seconds...");
		
		// Put each player in spectator mode
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			ScoreBoardManager.teamPlayerAdd(player, "Spectator");
			player.setGameMode(GameMode.SPECTATOR);
			player.getInventory().clear();
		}
		
		// Restart server without saving world after so much time
		Bukkit.getScheduler().runTaskLater(tntwars, new Runnable() {

			@Override
			public void run() {
				// Kick the players from the world so the world can unload without any issue
				for (Player player : Bukkit.getServer().getOnlinePlayers()) {
					player.kickPlayer("The game is restarting...");
				}
				
				Bukkit.getScheduler().runTask(tntwars, new Runnable() {
					
					@Override
					public void run() {
						unloadMap("map");
						Bukkit.getServer().spigot().restart();
					}
				});
			}
		}, TNTWarsMain.configEngine.gameEndTimer*20);
	}
}