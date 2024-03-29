package com.rast.tntwars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.inventory.ItemStack;

import com.rast.tntwars.systems.DataConverters;
import com.rast.tntwars.systems.Portal;

/**
 * This class gets and sets all the config data 
 * It is also a data center for our data from the config
 * 
 * @author Raster556
 */

public class ConfigEngine {

	private final TNTWarsMain tntwars = TNTWarsMain.getInstance;

	// Public variables from config
	public ChatColor lobbyColor;
	public ChatColor spectatorColor;
	public ChatColor teamOneColor;
	public ChatColor teamTwoColor;

	public int gameStartTimer;
	public int gameEndTimer;
	public int gameSpawnClearRadius;
	public int teamPortals;
	
	public int minTeamSize;
	public int maxTeamSize;
	
	public String teamOneName;
	public String teamTwoName;
	
	public boolean destructibleWater;
	public boolean tntTeamGriefProtection;
	public boolean portalTeamGriefProtection;
	public boolean pistonBounding;
	public boolean developerMode;

	public int[][] lobbyRegion = new int[3][];
	public int[][] lobbyPortal = new int[3][];
	public int[][] teamOneIslandRegion = new int[3][];
	public int[][] teamTwoIslandRegion = new int[3][];

	public Location lobbySpawn;
	public Location teamOneWaitRoomSpawn;
	public Location teamTwoWaitRoomSpawn;
	public Location teamOneSpawn;
	public Location teamTwoSpawn;
	
	public World gameWorld;
	
	public List<ItemStack> inventoryItems;
	public final List<Portal> teamPortalList;

	public ConfigEngine() {
		tntwars.saveDefaultConfig();
		
		// Create region 2D arrays
		lobbyRegion[0] = new int[2];
		lobbyRegion[1] = new int[2];
		lobbyRegion[2] = new int[2];

		lobbyPortal[0] = new int[2];
		lobbyPortal[1] = new int[2];
		lobbyPortal[2] = new int[2];

		teamOneIslandRegion[0] = new int[2];
		teamOneIslandRegion[1] = new int[2];
		teamOneIslandRegion[2] = new int[2];

		teamTwoIslandRegion[0] = new int[2];
		teamTwoIslandRegion[1] = new int[2];
		teamTwoIslandRegion[2] = new int[2];
		
		// Create portals list
		teamPortalList = new ArrayList<>();
		
		// Load config data
		LoadConfigData();
	}

	// Load the config file and set the variables accordingly
	public void LoadConfigData() {
		// Time variables
		long startLoad = System.currentTimeMillis();
		tntwars.getLogger().info("Reloading config variables...");

		// Refresh and get the config file
		tntwars.reloadConfig();
		Configuration config = tntwars.getConfig();
		// Get our variables
		lobbyColor = DataConverters.convertColor(Objects.requireNonNull(config.getString("lobbyColor")));
		spectatorColor = DataConverters.convertColor(Objects.requireNonNull(config.getString("spectatorColor")));
		teamOneColor = DataConverters.convertColor(Objects.requireNonNull(config.getString("teamOneColor")));
		teamTwoColor = DataConverters.convertColor(Objects.requireNonNull(config.getString("teamTwoColor")));
		
		gameStartTimer = config.getInt("gameStartTimer");
		gameEndTimer = config.getInt("gameEndTimer");
		gameSpawnClearRadius = config.getInt("gameSpawnClearRadius");
		teamPortals = config.getInt("teamPortals");

		minTeamSize = config.getInt("minTeamSize");
		maxTeamSize = config.getInt("maxTeamSize");

		teamOneName = config.getString("teamOneName");
		teamTwoName = config.getString("teamTwoName");

		destructibleWater = config.getBoolean("destructibleWater");
		tntTeamGriefProtection = config.getBoolean("tntTeamGriefProtection");
		portalTeamGriefProtection = config.getBoolean("portalTeamGriefProtection");
		pistonBounding = config.getBoolean("pistonBounding");
		developerMode = config.getBoolean("developerMode");

		lobbySpawn = (Location) config.get("lobbySpawn");
		teamOneWaitRoomSpawn = (Location) config.get("teamOneWaitRoom");
		teamTwoWaitRoomSpawn = (Location) config.get("teamTwoWaitRoom");
		teamOneSpawn = (Location) config.get("teamOneSpawn");
		teamTwoSpawn = (Location) config.get("teamTwoSpawn");

		lobbyRegion = compressPositions(config, "lobby");
		lobbyPortal = compressPositions(config, "lobbyPortal");
		teamOneIslandRegion = compressPositions(config, "teamOneIsland");
		teamTwoIslandRegion = compressPositions(config, "teamTwoIsland");

		gameWorld = tntwars.getServer().getWorld(Objects.requireNonNull(config.getString("gameWorldName")));

		inventoryItems = (List<ItemStack>) config.get("gameItems");

		teamPortalList.clear();
		
		// Make team one portals and save them to a list
		for (int i = 0; i < teamPortals; i++) {
			Portal tmpPortal = new Portal();
			tmpPortal.portalNumb = i;
			tmpPortal.teamNumb = 1;
			tmpPortal.r = compressPositions(config, "teamOnePortal" + DataConverters.numbToText(i+1));
			teamPortalList.add(tmpPortal);
		}
		
		// Make team two portals and save them to a list
		for (int i = 0; i < teamPortals; i++) {
			Portal tmpPortal = new Portal();
			tmpPortal.portalNumb = i;
			tmpPortal.teamNumb = 2;
			tmpPortal.r = compressPositions(config, "teamTwoPortal" + DataConverters. numbToText(i+1));
			teamPortalList.add(tmpPortal);
		}
		
		tntwars.getLogger().info("Reloading config completed in (" + (System.currentTimeMillis() - startLoad) + "ms)");
		
	}

	// Compress the x y z data from the config to a 2D array
	private int[][] compressPositions(Configuration config, String location) {
		int [] pos1 = new int[] {config.getInt(location + ".pos1.x"), config.getInt(location + ".pos1.y"),
				config.getInt(location + ".pos1.z") };
		int [] pos2 = new int[] { config.getInt(location + ".pos2.x"), config.getInt(location + ".pos2.y"),
				config.getInt(location + ".pos2.z") };
		return positionToArrays(pos1, pos2);
	}
	
	// Compress the position data to a 2D array
	public int[][] positionToArrays(int[] pos1, int[] pos2) {
		int[][] temp = new int[3][];
		temp[0] = new int[] {pos1[0], pos2[0]};
		temp[1] = new int[] {pos1[1], pos2[1]};
		temp[2] = new int[] {pos1[2], pos2[2]};
		Arrays.sort(temp[0]);
		Arrays.sort(temp[1]);
		Arrays.sort(temp[2]);
		return temp;
	}
	
	// Save a location to the config
	public void saveLocation(String path, Object data) {
		if (data instanceof int[][]) {
			int[][] temp = (int[][]) data;
			tntwars.reloadConfig();
			tntwars.getConfig().set(path + ".pos1.x", temp[0][0]);
			tntwars.getConfig().set(path + ".pos1.y", temp[1][0]);
			tntwars.getConfig().set(path + ".pos1.z", temp[2][0]);
			tntwars.getConfig().set(path + ".pos2.x", temp[0][1]);
			tntwars.getConfig().set(path + ".pos2.y", temp[1][1]);
			tntwars.getConfig().set(path + ".pos2.z", temp[2][1]);
			tntwars.saveConfig();
			LoadConfigData();
		} else {
			tntwars.reloadConfig();
			tntwars.getConfig().set(path, data);
			tntwars.saveConfig();
			LoadConfigData();
		}
	}

}
