package com.rast.tntwars.systems;

import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import com.rast.tntwars.TNTWarsMain;

/**
 * This class contains methods for starting the game.
 * 
 * @author Raster556
 */

public class GameInitializer {

	private TNTWarsMain tntwars = TNTWarsMain.getInstance;
	
	public boolean gameStarted = false;
	private boolean countdown = false;
	
	int teamOneCount = TNTWarsMain.playerGroupManager.teamOneCount;
	int teamTwoCount = TNTWarsMain.playerGroupManager.teamTwoCount;
	
	int taskID;
	int timeInSeconds;
	
	
	// Add a player to the game
	public void addPlayerToGame (Player player) {
		teamOneCount = TNTWarsMain.playerGroupManager.teamOneCount;
		teamTwoCount = TNTWarsMain.playerGroupManager.teamTwoCount;
		int maxTeamSize = TNTWarsMain.configEngine.maxTeamSize;
		
		// Ensure that teams are not full
		if (teamOneCount >= maxTeamSize && teamTwoCount >= maxTeamSize) {
			player.sendMessage(ChatColor.DARK_RED + "Teams are full!");
			return;
		}
		
		// Balance the way we put players in teams and do a straw pull when the teams are equally full
		if (teamOneCount < teamTwoCount) {
			TNTWarsMain.playerGroupManager.addToGroup(player, "teamOne");
			teamOneJoin(player);
		} else if (teamOneCount > teamTwoCount) {
			TNTWarsMain.playerGroupManager.addToGroup(player, "teamTwo");
			PlayerTeleportManager.teleportTeamTwoIsland(player, true);
			teamTwoJoin(player);
		} else {
			Random rand = new Random(System.currentTimeMillis());
			boolean strawPull = rand.nextBoolean();
			
			if (strawPull) {
				TNTWarsMain.playerGroupManager.addToGroup(player, "teamOne");
				PlayerTeleportManager.teleportTeamOneIsland(player, true);
				teamOneJoin(player);
			} else {
				TNTWarsMain.playerGroupManager.addToGroup(player, "teamTwo");
				PlayerTeleportManager.teleportTeamTwoIsland(player, true);
				teamTwoJoin(player);
			}
		}
	}
	
	// Join a player to team one
	private void teamOneJoin(Player player) {
		String name = player.getName();
		if (gameStarted) {
			PlayerTeleportManager.teleportTeamOneIsland(player, false);
			player.getInventory().addItem(TNTWarsMain.itemStackStorage.blockMenuItem);
		} else {
			PlayerTeleportManager.teleportTeamOneWaitRoom(player, true);
			if (checkStartGameConditions()) {
				startGame();
			} else {
				Bukkit.broadcastMessage(ChatColor.DARK_RED + "Waiting for more players...");
			}
		}
		Bukkit.broadcastMessage(TNTWarsMain.configEngine.teamOneColor + name + " has joined the " + TNTWarsMain.configEngine.teamOneName + " team!");
	}
	
	// Join a player to team two
	private void teamTwoJoin (Player player) {
		String name = player.getName();
		if (gameStarted) {
			PlayerTeleportManager.teleportTeamTwoIsland(player, false);
			player.getInventory().addItem(TNTWarsMain.itemStackStorage.blockMenuItem);
		} else {
			PlayerTeleportManager.teleportTeamTwoWaitRoom(player, true);
			if (checkStartGameConditions()) {
				startGame();
			} else {
				Bukkit.broadcastMessage(ChatColor.DARK_RED + "Waiting for more players...");
			}
		}
		Bukkit.broadcastMessage(TNTWarsMain.configEngine.teamTwoColor + name + " has joined the " + TNTWarsMain.configEngine.teamTwoName + " team!");
	}
	
	// Make sure teams are filled to the minimum amount
	private boolean checkStartGameConditions () {
		teamOneCount = TNTWarsMain.playerGroupManager.teamOneCount;
		teamTwoCount = TNTWarsMain.playerGroupManager.teamTwoCount;
		tntwars.getLogger().info("Checking start conditions...");
		int minTeamSize = TNTWarsMain.configEngine.minTeamSize;
		if (teamOneCount >= minTeamSize && teamTwoCount >= minTeamSize) {
			return true;
		} else {
			return false;
		}
	}
	
	// Attempt to start the game
	private void startGame() {
		teamOneCount = TNTWarsMain.playerGroupManager.teamOneCount;
		teamTwoCount = TNTWarsMain.playerGroupManager.teamTwoCount;
		if (gameStarted || countdown) {
			tntwars.getLogger().info("Failed to start game because countoun in progress or game has started.");
			return;
		}
		
		countdown = true;
		timeInSeconds = TNTWarsMain.configEngine.gameStartTimer;
		
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(tntwars, new Runnable() {
			
			@Override
			public void run() {
				if (timeInSeconds > 0) {
					
					Bukkit.broadcastMessage(ChatColor.AQUA + "Starting game in " + timeInSeconds + " seconds");
					SoundPlayer.PlayGlobalSound(TNTWarsMain.configEngine.gameWorld, Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, Float.MAX_VALUE, 1);
					timeInSeconds--;
					
				} else if (checkStartGameConditions()){
					
					Map<String,String> playerMap = TNTWarsMain.playerGroupManager.playerMap;
					
					for (Map.Entry<String,String> entry : playerMap.entrySet()) {
						String name = entry.getKey();
						Player player = Bukkit.getPlayer(name);
						String group = entry.getValue();
						if (group == "teamOne") {
							PlayerTeleportManager.teleportTeamOneIsland(player, false);
							player.getInventory().addItem(TNTWarsMain.itemStackStorage.blockMenuItem);
						} else if (group == "teamTwo") {
							PlayerTeleportManager.teleportTeamTwoIsland(player, false);
							player.getInventory().addItem(TNTWarsMain.itemStackStorage.blockMenuItem);
						}
					}
					
					TitleManager.Title(ChatColor.AQUA, ChatColor.AQUA, "Game has begun!", "");
					SoundPlayer.PlayGlobalSound(TNTWarsMain.configEngine.gameWorld, Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, Float.MAX_VALUE, 2);
					SoundPlayer.PlayGlobalSound(TNTWarsMain.configEngine.gameWorld, Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, Float.MAX_VALUE, 1.5f);
					gameStarted = true;
					Bukkit.getScheduler().cancelTask(taskID);
					
				} else {
					countdown = false;
					Bukkit.broadcastMessage(ChatColor.DARK_RED + "Not enough players to start game.");
					SoundPlayer.PlayGlobalSound(TNTWarsMain.configEngine.gameWorld, Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, Float.MAX_VALUE, 0.7f);
					SoundPlayer.PlayGlobalSound(TNTWarsMain.configEngine.gameWorld, Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, Float.MAX_VALUE, 0.55f);
					SoundPlayer.PlayGlobalSound(TNTWarsMain.configEngine.gameWorld, Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, Float.MAX_VALUE, 0.5f);
					Bukkit.getScheduler().cancelTask(taskID);
				}
				
			}
		}, 0, 20);
	}
}
