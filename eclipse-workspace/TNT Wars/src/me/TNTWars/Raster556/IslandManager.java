package me.TNTWars.Raster556;

import java.util.Arrays;

import org.bukkit.plugin.Plugin;

public class IslandManager {

	Plugin plugin = TNTWars.plugin;
	public int[] Is1sortedX;
	public int[] Is1sortedY;
	public int[] Is1sortedZ;
	public int[] Is2sortedX;
	public int[] Is2sortedY;
	public int[] Is2sortedZ;

	public void onEnable() {
		loadIslands();
	}

	public void loadIslands() {
		Is1sortedX = new int[] { plugin.getConfig().getInt("island1.pos1.x"),
				plugin.getConfig().getInt("island1.pos2.x") };
		Is1sortedY = new int[] { plugin.getConfig().getInt("island1.pos1.y"),
				plugin.getConfig().getInt("island1.pos2.y") };
		Is1sortedZ = new int[] { plugin.getConfig().getInt("island1.pos1.z"),
				plugin.getConfig().getInt("island1.pos2.z") };
		Arrays.sort(Is1sortedX);
		Arrays.sort(Is1sortedY);
		Arrays.sort(Is1sortedZ);

		Is2sortedX = new int[] { plugin.getConfig().getInt("island2.pos1.x"),
				plugin.getConfig().getInt("island2.pos2.x") };
		Is2sortedY = new int[] { plugin.getConfig().getInt("island2.pos1.y"),
				plugin.getConfig().getInt("island2.pos2.y") };
		Is2sortedZ = new int[] { plugin.getConfig().getInt("island2.pos1.z"),
				plugin.getConfig().getInt("island2.pos2.z") };
		Arrays.sort(Is2sortedX);
		Arrays.sort(Is2sortedY);
		Arrays.sort(Is2sortedZ);
	}

	public int testBounds(int x, int y, int z) {
		if (x >= Is1sortedX[0] && x <= Is1sortedX[1] && y >= Is1sortedY[0] && y <= Is1sortedY[1] && z >= Is1sortedZ[0]
				&& z <= Is1sortedZ[1]) {
			return 1;
		} else if (x >= Is2sortedX[0] && x <= Is2sortedX[1] && y >= Is2sortedY[0] && y <= Is2sortedY[1]
				&& z >= Is2sortedZ[0] && z <= Is2sortedZ[1]) {
			return 2;
		}
		return 0;
	}
}
