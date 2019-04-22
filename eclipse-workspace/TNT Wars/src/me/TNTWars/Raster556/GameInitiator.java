package me.TNTWars.Raster556;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class GameInitiator {

	public boolean gameStarted = false;
	public Location island1;
	public Location island2;

	private World world = Bukkit.getServer().getWorld("map");

	private Plugin plugin = TNTWars.plugin;
	private ItemStack inventoryItem;
	private EventListener eventListener;
	private boolean countdown = false;
	private int count;
	private int taskID;

	public void onEnable() {
		plugin.reloadConfig();
		eventListener = TNTWars.el;
		inventoryItem = TNTWars.inventoryItem;
		island1 = (Location) plugin.getConfig().get("islandSpawn1");
		island2 = (Location) plugin.getConfig().get("islandSpawn2");

	}

	public void countdownRefresh() {
		if (countdown == false) {
			if (eventListener.team1Count > 0 && eventListener.team2Count > 0) {
				count = 30;
				countdown = true;
				taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

					@Override
					public void run() {
						if (count > 0) {
							Bukkit.broadcastMessage(ChatColor.AQUA + "Starting game in " + count + " seconds");
							world.playSound(eventListener.lobbySpawn, Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER,
									Float.MAX_VALUE, 1f);
							count--;
						} else if (eventListener.team1Count > 0 && eventListener.team2Count > 0) {

							gameStarted = true;

							for (Player player : eventListener.team1Query) {
								eventListener.team1Players.add(player);
								player.teleport(island1, TeleportCause.PLUGIN);
								player.setGameMode(GameMode.SURVIVAL);
								player.getInventory().addItem(inventoryItem);
							}

							for (Player player : eventListener.team2Query) {
								eventListener.team2Players.add(player);
								player.teleport(island2, TeleportCause.PLUGIN);
								player.setGameMode(GameMode.SURVIVAL);
								player.getInventory().addItem(inventoryItem);
							}
							world.playSound(eventListener.lobbySpawn, Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER,
									Float.MAX_VALUE, 2f);
							eventListener.team1Query.clear();
							eventListener.team2Query.clear();
							Bukkit.getScheduler().cancelTask(taskID);
						} else {
							world.playSound(eventListener.lobbySpawn, Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER,
									Float.MAX_VALUE, 0f);
							countdown = false;
							Bukkit.broadcastMessage(ChatColor.DARK_RED + "Not enought players to start match.");
							Bukkit.getScheduler().cancelTask(taskID);
						}
					}
				}, 0, 20);

			} else {
				world.playSound(eventListener.lobbySpawn, Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER,
						Float.MAX_VALUE, 0f);
				Bukkit.broadcastMessage(ChatColor.DARK_RED + "Waiting for 1 more player...");
			}
		}
	}
}
