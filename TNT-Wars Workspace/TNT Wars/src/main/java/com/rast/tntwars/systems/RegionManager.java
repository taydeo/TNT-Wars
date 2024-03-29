package com.rast.tntwars.systems;

import org.bukkit.Location;

import com.rast.tntwars.TNTWarsMain;

/**
 * This class has methods for detecting which region something is in based on location
 * 
 * @author Raster556
 */

public class RegionManager {

	private static final int[][] lr = TNTWarsMain.configEngine.lobbyRegion;
	private static final int[][] tor = TNTWarsMain.configEngine.teamOneIslandRegion;
	private static final int[][] ttr = TNTWarsMain.configEngine.teamTwoIslandRegion;
	
	public static String regionDetect (Location loc) {
		
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		
		// Test if the position is in the bounds of the regions
		if ((lr[0][0] <= x && lr[0][1] >= x) && (lr[1][0] <= y && lr[1][1] >= y) && (lr[2][0] <= z && lr[2][1] >= z)) {
			return "lobby";
		}
		
		if ((tor[0][0] <= x && tor[0][1] >= x) && (tor[1][0] <= y && tor[1][1] >= y) && (tor[2][0] <= z && tor[2][1] >= z)) {
			return "teamOne";
		}
		
		if ((ttr[0][0] <= x && ttr[0][1] >= x) && (ttr[1][0] <= y && ttr[1][1] >= y) && (ttr[2][0] <= z && ttr[2][1] >= z)) {
			return "teamTwo";
		}
		
		return "none";
		
	}
	
}
