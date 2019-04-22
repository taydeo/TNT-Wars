package me.Raster556;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Cannon {
	
	public void makeCannon(Block block, Plugin plugin) {
		
		Location loc = new Location(block.getWorld(), block.getX(), block.getY() + 1, block.getZ());
		Location loc2 = new Location(block.getWorld(), block.getX()+.5, block.getY() - 0.25, block.getZ()+.5);
		Location loc3 = new Location(block.getWorld(), block.getX()+.5, block.getY() +1.5, block.getZ()+.5);
		block.setType(Material.BRICKS);
		loc.getBlock().setType(Material.STONE_SLAB);
		ArmorStand stand = (ArmorStand) block.getWorld().spawnEntity(loc2, EntityType.ARMOR_STAND);
		stand.setHelmet(new ItemStack(Material.TNT));
		stand.setGravity(false);
		stand.setVisible(false);
		block.getWorld().playSound(loc3, Sound.ENTITY_TNT_PRIMED, 1, 1);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				TNTPrimed tnt = (TNTPrimed) block.getWorld().spawnEntity(loc3, EntityType.PRIMED_TNT);
				tnt.setVelocity(new Vector(Math.random()*2-1, 2, Math.random()*2-1));
				block.getWorld().playSound(loc3, Sound.ENTITY_WITHER_SHOOT, 1, 2);
			}
		}.runTaskLater(plugin, 20*2);

		new BukkitRunnable() {
			@Override
			public void run() {
				TNTPrimed tnt = (TNTPrimed) block.getWorld().spawnEntity(loc3, EntityType.PRIMED_TNT);
				tnt.setVelocity(new Vector(Math.random()*2-1, 2, Math.random()*2-1));
				block.getWorld().playSound(loc3, Sound.ENTITY_WITHER_SHOOT, 1, 2);
			}
		}.runTaskLater(plugin, (long) (20*2.5));
		
		new BukkitRunnable() {
			@Override
			public void run() {
				TNTPrimed tnt = (TNTPrimed) block.getWorld().spawnEntity(loc3, EntityType.PRIMED_TNT);
				tnt.setVelocity(new Vector(Math.random()*2-1, 2, Math.random()*2-1));
				block.getWorld().playSound(loc3, Sound.ENTITY_WITHER_SHOOT, 1, 2);
			}
		}.runTaskLater(plugin, 20*3);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				TNTPrimed tnt = (TNTPrimed) block.getWorld().spawnEntity(loc3, EntityType.PRIMED_TNT);
				tnt.setVelocity(new Vector(Math.random()*2-1, 2, Math.random()*2-1));
				block.getWorld().playSound(loc3, Sound.ENTITY_WITHER_SHOOT, 1, 2);
			}
		}.runTaskLater(plugin, (long) (20*3.5));
		
		new BukkitRunnable() {
			@Override
			public void run() {
				TNTPrimed tnt = (TNTPrimed) block.getWorld().spawnEntity(loc3, EntityType.PRIMED_TNT);
				tnt.setVelocity(new Vector(Math.random()*2-1, 2, Math.random()*2-1));
				block.getWorld().playSound(loc3, Sound.ENTITY_WITHER_SHOOT, 1, 2);
			}
		}.runTaskLater(plugin, 20*4);

		new BukkitRunnable() {
			@Override
			public void run() {
				TNTPrimed tnt = (TNTPrimed) block.getWorld().spawnEntity(loc3, EntityType.PRIMED_TNT);
				tnt.setVelocity(new Vector(Math.random()*2-1, 2, Math.random()*2-1));
				block.getWorld().playSound(loc3, Sound.ENTITY_WITHER_SHOOT, 1, 2);
			}
		}.runTaskLater(plugin, (long) (20*4.5));
		
		new BukkitRunnable() {
			@Override
			public void run() {
				TNTPrimed tnt = (TNTPrimed) block.getWorld().spawnEntity(loc3, EntityType.PRIMED_TNT);
				tnt.setVelocity(new Vector(Math.random()*2-1, 2, Math.random()*2-1));
				block.getWorld().playSound(loc3, Sound.ENTITY_WITHER_SHOOT, 1, 2);
			}
		}.runTaskLater(plugin, 20*5);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				TNTPrimed tnt = (TNTPrimed) block.getWorld().spawnEntity(loc3, EntityType.PRIMED_TNT);
				tnt.setVelocity(new Vector(Math.random()*2-1, 2, Math.random()*2-1));
				block.getWorld().playSound(loc3, Sound.ENTITY_WITHER_SHOOT, 1, 2);
				stand.remove(); 
			}
		}.runTaskLater(plugin, (long) (20*5.5));
	
	}
}
