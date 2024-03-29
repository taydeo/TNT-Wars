package com.rast.tntwars.systems;

import org.bukkit.Location;

/**
 * This class manages the selector's positions and stores them.
 * 
 * @author Raster556
 */

public class SelectorManager {

	public final int[] pos1 = new int[3];
	public final int[] pos2 = new int[3];
	
	// Get Pos1
	public int[] setPos1 (Location location) {
		pos1[0] = location.getBlockX();
		pos1[1] = location.getBlockY();
		pos1[2] = location.getBlockZ();
		return pos1;
	}
	
	// Get Pos2
	public int[] setPos2 (Location location) {
		pos2[0] = location.getBlockX();
		pos2[1] = location.getBlockY();
		pos2[2] = location.getBlockZ();
		return pos2;
	}
}
