package com.rast.tntwars.systems;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitScheduler;

import com.rast.tntwars.ConfigEngine;
import com.rast.tntwars.TNTWarsMain;

/**
 * This class is where the custom code for tnt is held.
 * 
 * @author Raster556
 */

public class TNTExtentions {
	
	private static final TNTWarsMain tntwars = TNTWarsMain.getInstance;
	private static final World mainWorld = TNTWarsMain.configEngine.gameWorld;
	private static final BukkitScheduler scheduler = Bukkit.getScheduler();
	private static final ConfigEngine configEngine = TNTWarsMain.configEngine;
	private static final boolean tntTeamGriefProtection = configEngine.tntTeamGriefProtection;
	private static final boolean destructableWater = configEngine.destructibleWater;
	private static final Material water = Material.WATER;
	private static final Material air = Material.AIR;
	private static final Material tnt = Material.TNT;
	private static final EntityType primedTNT = EntityType.PRIMED_TNT;
	
	// Custom code for TNT
	public static void tntExplodeEventHandle (EntityExplodeEvent event) {
		
		Entity entity = event.getEntity();
		Location location = entity.getLocation();
		
		// If the TNT is blowing up in the same region it was primed in we just want to start the other TNT around it.
		if (tntTeamGriefProtection && entity.getMetadata("regOfPrimed").get(0).asString().equals(RegionManager.regionDetect(location))) {
			
			event.setCancelled(true);
			List<Block> blocks = event.blockList();
			
			// Check to see if each block that was going to be blown up is TNT or not
			for (Block block : blocks) {
				if (block.getBlockData().getMaterial() == tnt) {
					
					// If block was TNT we set it to air and summon a primed TNT just like vanilla Minecraft does.
					TNTPrimed tntEntity = (TNTPrimed) block.getWorld().spawnEntity(block.getLocation(), primedTNT);
					tntEntity.setFuseTicks((int) Math.round(Math.random() * 20 + 10));
					block.setType(air);
				}
			}
		} else {
			// We want to disable default explosions if water destruction is enabled and is the special tnt
			if (destructableWater) {
				event.setCancelled(true);
				
				// We want to make a 3x3x3 of non water to make water not prevent TNT explosions
				WorldEditor.replaceRegion(location, 2, water, air);
				
				// Make new explosion at the original location a tick later to allow time for the game to register events
				scheduler.runTask(tntwars, () -> {
					if (mainWorld != null) {
						mainWorld.createExplosion(location, 4);
					}
				});
			} else {
				if (TNTWarsMain.gameEnder.gameEnded) {
					event.setCancelled(true);
				}
				
				// We want to see if the normal tnt has blown up a portal and run our code
				List<Block> blocks = event.blockList();
				GamePortalChecker.portalCheck(blocks);
			}
		}
	}
	
}
