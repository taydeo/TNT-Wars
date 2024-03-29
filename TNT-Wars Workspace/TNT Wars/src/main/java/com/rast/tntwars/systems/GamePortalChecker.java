package com.rast.tntwars.systems;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.rast.tntwars.TNTWarsMain;

/**
 * This class is where we detect when a portal is broke and register the events and code we need.
 * 
 * @author Raster556
 */

public class GamePortalChecker {

	private static final Material netherPortal = Material.NETHER_PORTAL;
	private static final List<Portal> teamPortalList = TNTWarsMain.configEngine.teamPortalList;
	private static final World world = TNTWarsMain.configEngine.gameWorld;
	
	public static void portalCheck(List<Block> blocks) {
		for (Block block : blocks) {
			if (block.getType() == netherPortal) {
				for (Portal portal : teamPortalList) {
					if (!portal.destroyed && portal.checkPortal(block.getLocation(), true)) {
						SoundPlayer.PlayGlobalSound(world, Sound.ENTITY_WITHER_AMBIENT, SoundCategory.AMBIENT, Float.MAX_VALUE, 1);
						if (portal.teamNumb == 1) {
							Bukkit.broadcastMessage(TNTWarsMain.configEngine.teamOneColor + TNTWarsMain.configEngine.teamOneName + ChatColor.YELLOW + "'s No."
									+ (portal.portalNumb+1) + " portal has been destroyed!");
						} else {
							Bukkit.broadcastMessage(TNTWarsMain.configEngine.teamTwoColor + TNTWarsMain.configEngine.teamTwoName + ChatColor.YELLOW + "'s No."
									+ (portal.portalNumb+1) + " portal has been destroyed!");
						}
						TNTWarsMain.gameEnder.testPortals();
					}
				}
			}
		}
	}
	
}
