package me.TNTWars.Raster556;

import java.util.Arrays;

import org.bukkit.plugin.Plugin;

public class LobbyPortal {

	Plugin plugin = TNTWars.plugin;
	public int[] sortedX;
	public int[] sortedY;
	public int[] sortedZ;

	public void onEnable() {
		loadPortal();
	}

	public void loadPortal() {
		sortedX = new int[] { plugin.getConfig().getInt("lobbyPortal.pos1.x"),
				plugin.getConfig().getInt("lobbyPortal.pos2.x") };
		sortedY = new int[] { plugin.getConfig().getInt("lobbyPortal.pos1.y"),
				plugin.getConfig().getInt("lobbyPortal.pos2.y") };
		sortedZ = new int[] { plugin.getConfig().getInt("lobbyPortal.pos1.z"),
				plugin.getConfig().getInt("lobbyPortal.pos2.z") };
		Arrays.sort(sortedX);
		Arrays.sort(sortedY);
		Arrays.sort(sortedZ);
	}

	public boolean testBounds(int x, int y, int z) {
		if (x >= sortedX[0] && x <= sortedX[1] && y >= sortedY[0] && y <= sortedY[1] && z >= sortedZ[0]
				&& z <= sortedZ[1]) {
			return true;
		}
		return false;
	}

}
