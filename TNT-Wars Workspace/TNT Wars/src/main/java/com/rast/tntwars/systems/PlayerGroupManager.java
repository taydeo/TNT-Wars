package com.rast.tntwars.systems;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.bukkit.entity.Player;

import com.rast.tntwars.TNTWarsMain;

/**
 * This is where the players are put in a hashmap with a group assigned to them.
 * All player hashmap methods are handled here.
 * 
 * Group names are associated with the team names declared in the com.rast.tntwars.systems.ScoreBoardManager class.
 * 
 * @author Raster556
 */

public class PlayerGroupManager {
	
	private final TNTWarsMain tntwars = TNTWarsMain.getInstance;
	
	public final Map<String,String> playerMap = new HashMap<>();
	public int teamOneCount = 0;
	public int teamTwoCount = 0;
	
	// Used to add/replace the player in the map with a group value
	public void addToGroup (Player player, String group) {
		String name = player.getName();
		String oldGroup = playerMap.get(name);
		playerMap.put(name, group);
		
		// If we are adding players to the a team we increment the team's counter by one.
		if (Objects.equals(group, "teamOne")) {
			teamOneCount++;
		} else if (Objects.equals(group, "teamTwo")) {
			teamTwoCount++;
		}
		
		if (Objects.equals(oldGroup, "teamOne")) {
			teamOneCount--;
		} else if (Objects.equals(oldGroup, "teamTwo")) {
			teamTwoCount--;
		}
		
		ScoreBoardManager.teamPlayerAdd(player, group);
		
	}
	
	// Used to remove a player from the map to save memory
	public void removeFromList (Player player) {
		String name = player.getName();
		String group = playerMap.get(name);
		
		// If we are subtracting players from the a team we increment the team's counter by negative one.
				if (Objects.equals(group, "teamOne")) {
					teamOneCount--;
				} else if (Objects.equals(group, "teamTwo")) {
					teamTwoCount--;
				}
				
		playerMap.remove(name);
		ScoreBoardManager.teamRemovePlayer(player);
	}
	
	// Used to get the group that a player is in
	public String getGroup (Player player) {
		String name = player.getName();
		
		// If player does not exist in the map we put them in the lobby
		if (!playerMap.containsKey(name)) {
			tntwars.getLogger().warning("Making new player in the list because we could not find " + name);
			playerMap.put(name, "lobby");
		}
		return playerMap.get(name);
	}
	
}
