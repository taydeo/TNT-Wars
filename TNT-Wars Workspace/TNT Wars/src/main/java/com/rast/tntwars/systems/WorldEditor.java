package com.rast.tntwars.systems;

import java.util.List;
import java.util.Objects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * This class contains methods for filling a cube with material or replacing material
 * 
 * @author Raster556
 */

public class WorldEditor {

	// Fill a region with the given radius to a material (radius is the center block plus blocks extended from it so a 3x3x3 has a radius of 2)
	public static void fillRegion (Location location, int radius, Material material) {
		int mRad = radius-1;
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		World world = location.getWorld();
		
		for (int i = x-mRad; i <= x+mRad; i++) {
			for (int j = y-mRad; j <= y+mRad; j++) {
				for (int k = z-mRad; k <= z+mRad; k++) {
					Objects.requireNonNull(world).getBlockAt(i, j, k).setType(material);
				}
			}
		}
	}
	
	// Replace a region with the given radius from a list of materials to a material (radius is the center block plus blocks extended from it so a 3x3x3 has a radius of 2)
	// This is is slow and it may be faster to use the method replaceRegion multiple times. 
	public static void replaceRegionMultiple (Location location, int radius, List<Material> fromMaterial, Material toMaterial) {
		int mRad = radius-1;
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		World world = location.getWorld();
		
		for (int i = x-mRad; i <= x+mRad; i++) {
			for (int j = y-mRad; j <= y+mRad; j++) {
				for (int k = z-mRad; k <= z+mRad; k++) {
					Block block = Objects.requireNonNull(world).getBlockAt(i, j, k);
					if (fromMaterial.contains(block.getType())) {
						block.setType(toMaterial);
					}
				}
			}
		}
	}
	
	// Replace a region with the given radius from a material to another material (radius is the center block plus blocks extended from it so a 3x3x3 has a radius of 2)
	public static void replaceRegion (Location location, int radius, Material fromMaterial, Material toMaterial) {
		int mRad = radius-1;
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		World world = location.getWorld();
		
		for (int i = x-mRad; i <= x+mRad; i++) {
			for (int j = y-mRad; j <= y+mRad; j++) {
				for (int k = z-mRad; k <= z+mRad; k++) {
					Block block = Objects.requireNonNull(world).getBlockAt(i, j, k);
					if (fromMaterial == block.getType()) {
						block.setType(toMaterial);
					}
				}
			}
		}
	}
}
