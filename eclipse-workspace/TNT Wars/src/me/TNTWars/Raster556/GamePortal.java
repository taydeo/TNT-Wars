package me.TNTWars.Raster556;

import org.bukkit.plugin.Plugin;

public class GamePortal {

	public int island;
	public int portalNumb;
	public int[] sortedX;
	public int[] sortedY;
	public int[] sortedZ;
	public boolean destroyed = false;

	Plugin plugin = TNTWars.plugin;

	public boolean testBounds(int x, int y, int z) {
		if (x >= sortedX[0] && x <= sortedX[1] && y >= sortedY[0] && y <= sortedY[1] && z >= sortedZ[0]
				&& z <= sortedZ[1]) {
			return true;
		}
		return false;
	}

}
