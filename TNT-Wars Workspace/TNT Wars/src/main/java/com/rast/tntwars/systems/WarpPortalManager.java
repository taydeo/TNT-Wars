package com.rast.tntwars.systems;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.rast.tntwars.TNTWarsMain;

import java.util.Objects;

/**
 * This class manages the warp portals for the game.
 * 
 * @author Raster556
 */

public class WarpPortalManager {

	public static boolean playerPortalSender (Player player) {
		
		// Get variables from player
		Location loc = player.getLocation();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		
		int[][] lp = TNTWarsMain.configEngine.lobbyPortal;
		
		// Ensure that the player did not somehow hack outside of the game to the portal and is part of the lobby
		if (Objects.equals(TNTWarsMain.playerGroupManager.playerMap.get(player.getName()), "lobby")) {
			
			// Test the bounds of lobby portal against the player's position
			if ((lp[0][0] <= x && lp[0][1] >= x) && (lp[1][0] <= y && lp[1][1] >= y) && (lp[2][0] <= z && lp[2][1] >= z)) {
				TNTWarsMain.gameInitializer.addPlayerToGame(player);
				player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, Float.MAX_VALUE, 1.4f);
				return true;
			}
			
		}
		return false;
	}
	
}
