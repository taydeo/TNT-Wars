package com.rast.tntwars.systems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.rast.tntwars.TNTWarsMain;

import java.util.Objects;

/**
 * This class contains methods for detecting the end game conditions and manages shutting down the game.
 * 
 * @author Raster556
 */

public class GameEnder {
	
	private static final World world = TNTWarsMain.configEngine.gameWorld;
	private final TNTWarsMain tntwars = TNTWarsMain.getInstance;
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
			TitleManager.Title(ChatColor.YELLOW, ChatColor.DARK_GRAY, "Game is a tie!", "nobody won the game");
			SoundPlayer.PlayGlobalSound(world, Sound.ENTITY_WITHER_HURT, SoundCategory.AMBIENT, Float.MAX_VALUE, 0.9f);
			expelPlayers();
		} else if (team2PortalTotal == 0) {
			TitleManager.Title(TNTWarsMain.configEngine.teamOneColor, ChatColor.DARK_GRAY, TNTWarsMain.configEngine.teamOneName + " has won the game!", "");
			SoundPlayer.PlayGlobalSound(world, Sound.ENTITY_WITHER_DEATH, SoundCategory.AMBIENT, Float.MAX_VALUE, 1);
			expelPlayers();
			
		} else if (team1PortalTotal == 0) {
			TitleManager.Title(TNTWarsMain.configEngine.teamTwoColor, ChatColor.DARK_GRAY, TNTWarsMain.configEngine.teamTwoName + " has won the game!", "");
			SoundPlayer.PlayGlobalSound(world, Sound.ENTITY_WITHER_DEATH, SoundCategory.AMBIENT, Float.MAX_VALUE, 1);
			expelPlayers();
		}
	}
	
	
	// Unload maps
	public void unloadMap(String mapname) {
		if (Bukkit.getServer().unloadWorld(Objects.requireNonNull(Bukkit.getServer().getWorld(mapname)), false)) {
			Bukkit.broadcastMessage("Successfully unloaded map");
		} else {
			Bukkit.broadcastMessage("Failed to unload map");
		}
	}
	
	// Kick players and restart map after delay time
	public void expelPlayers() {
		gameEnded = true;
		Bukkit.broadcastMessage(ChatColor.AQUA + "Game will restart in " + TNTWarsMain.configEngine.gameEndTimer + " seconds...");
		
		// Put each player in spectator mode
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			TNTWarsMain.playerGroupManager.addToGroup(player, "spectator");
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage(ChatColor.BLUE + "Use "+ ChatColor.UNDERLINE + "/spectate" + ChatColor.RESET + ChatColor.BLUE + " to exit this mode");
			player.getInventory().clear();
		}
		
		// Restart server without saving world after so much time
		Bukkit.getScheduler().runTaskLater(tntwars, () -> {
			// Kick the players from the world so the world can unload without any issue
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				player.kickPlayer("The game is restarting...");
			}

			Bukkit.getScheduler().runTask(tntwars, () -> {
				unloadMap(TNTWarsMain.configEngine.gameWorld.getName());
				Bukkit.getServer().spigot().restart();
			});
		}, TNTWarsMain.configEngine.gameEndTimer*20);
	}
	
	// Kick players and restart map instantly (when the world does a stop or a reset for some odd reason)
	public void expelPlayersInstant() {

		// Kick the players from the world so the world can unload without any issue
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.kickPlayer("The game is restarting...");
		}
		unloadMap(TNTWarsMain.configEngine.gameWorld.getName());
	}
}
