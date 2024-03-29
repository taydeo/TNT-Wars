package com.rast.tntwars.systems;

import java.util.Objects;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.rast.tntwars.TNTWarsMain;

/**
 * This class manages the score boards and player team assignments.
 * 
 * the team names for declaring the team in each method are; lobby, teamOne, teamTwo, and spectator.
 * 
 * @author Raster556
 */

public class ScoreBoardManager {
	
	private static final TNTWarsMain tntwars = TNTWarsMain.getInstance;
	
	private static final Scoreboard scoreboard = Objects.requireNonNull(tntwars.getServer().getScoreboardManager()).getMainScoreboard();
	
	private static Team lobby;
	private static Team teamOne;
	private static Team teamTwo;
	private static Team spectator;

	public ScoreBoardManager () {
		Set<Team> teams = scoreboard.getTeams();
		
		// Get team colors
		ChatColor lobbyColor = TNTWarsMain.configEngine.lobbyColor;
		ChatColor teamOneColor = TNTWarsMain.configEngine.teamOneColor;
		ChatColor teamTwoColor = TNTWarsMain.configEngine.teamTwoColor;
		ChatColor spectatorColor = TNTWarsMain.configEngine.spectatorColor;
		
		// Unregister all previous teams for cleanup
		for (Team team : teams) {
			team.unregister();
		}
		
		// Register new teams
		lobby = scoreboard.registerNewTeam("Lobby");
		teamOne = scoreboard.registerNewTeam("Team_1");
		teamTwo = scoreboard.registerNewTeam("Team_2");
		spectator = scoreboard.registerNewTeam("Spector");
		
		// Set team colors
		lobby.setColor(lobbyColor);
		teamOne.setColor(teamOneColor);
		teamTwo.setColor(teamTwoColor);
		spectator.setColor(spectatorColor);		
	}
		
	// Add players to a team and remove them from the other teams
	public static void teamPlayerAdd (Player player, String team) {
		
		String name = player.getName();
		
		switch (team) {
		case "lobby": 
			teamOne.removeEntry(name);
			teamTwo.removeEntry(name);
			spectator.removeEntry(name);
			lobby.addEntry(name);
			break;
		case "teamOne": 
			lobby.removeEntry(name);
			teamTwo.removeEntry(name);
			spectator.removeEntry(name);
			teamOne.addEntry(name);
			break;
		case "teamTwo": 
			lobby.removeEntry(name);
			teamOne.removeEntry(name);
			teamTwo.addEntry(name);
			spectator.removeEntry(name);
			break;
		case "spectator": 
			lobby.removeEntry(name);
			teamOne.removeEntry(name);
			teamTwo.removeEntry(name);
			spectator.addEntry(name);
			break;
		default:
			break;
		}
	}
		
	// Remove player from all teams
	public static void teamRemovePlayer (Player player) {
			
			String name = player.getName();
			
			lobby.removeEntry(name);
			teamOne.removeEntry(name);
			teamTwo.removeEntry(name);
			spectator.removeEntry(name);
	}
	
	// Gets player's team color
	public static ChatColor getPlayerTeamColor (Player player) {
			
			String name = player.getName();
			return Objects.requireNonNull(scoreboard.getEntryTeam(name)).getColor();
	}
	
}
