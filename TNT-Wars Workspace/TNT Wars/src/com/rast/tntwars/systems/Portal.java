package com.rast.tntwars.systems;

import org.bukkit.Location;

/**
 * This class is for the portals used as targets and stores a multitude of variables.
 * 
 * @author Raster556
 */

public class Portal {
	public int portalNumb;
	public int teamNumb;
	public boolean destroyed = false;
	public int[][] r;
	
	public boolean checkPortal(Location loc, boolean destroy) {
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		
		// Test if the position is in the bounds of the regions
		if ((r[0][0] <= x && r[0][1] >= x) && (r[1][0] <= y && r[1][1] >= y) && (r[2][0] <= z && r[2][1] >= z)) {
			destroyed = destroy;
			return true;
		} else {
			return false;
		}
	}
}
