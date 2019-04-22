package me.TNTWars.Raster556;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GameEndManager {

	public boolean gameEnded = false;

	private Plugin plugin = TNTWars.plugin;
	private EventListener listener;
	private GamePortalManager gamePM;
	private int team1PortalTotal;
	private int team2PortalTotal;

	private World world = Bukkit.getServer().getWorld("map");

	public void onEnable() {
		listener = TNTWars.el;
		gamePM = TNTWars.gpm;
	}

	public void testPortals() {
		team1PortalTotal = gamePM.team1portals;
		team2PortalTotal = gamePM.team2portals;
		for (GamePortal portal : gamePM.gamePortals) {
			if (portal.destroyed) {
				switch (portal.island) {
				case 1:
					team1PortalTotal--;
					break;

				case 2:
					team2PortalTotal--;
					break;

				default:
					break;
				}
			}

		}
		if (team1PortalTotal == 0 && team2PortalTotal == 0) {
			Bukkit.broadcastMessage(ChatColor.YELLOW + "The Game is a Tie!");
			expelPlayers();
			world.playSound(listener.lobbySpawn, Sound.ENTITY_WITHER_HURT, SoundCategory.MASTER, Float.MAX_VALUE, 1f);
		} else if (team1PortalTotal == 0) {
			world.playSound(listener.lobbySpawn, Sound.ENTITY_WITHER_DEATH, SoundCategory.MASTER, Float.MAX_VALUE, 1f);
			Bukkit.broadcastMessage(ChatColor.GOLD + "Orange" + ChatColor.GREEN + " team won the game!");
			expelPlayers();
		} else if (team2PortalTotal == 0) {
			world.playSound(listener.lobbySpawn, Sound.ENTITY_WITHER_DEATH, SoundCategory.MASTER, Float.MAX_VALUE, 1f);
			Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Cyan" + ChatColor.GREEN + " team won the game!");
			expelPlayers();
		}
	}

	// Unload maps
	public static void unloadMap(String mapname) {
		if (Bukkit.getServer().unloadWorld(Bukkit.getServer().getWorld(mapname), false)) {
			Bukkit.broadcastMessage("Successfully unloaded map");
		} else {
			Bukkit.broadcastMessage("Failed to unload map");
		}
	}

	public void expelPlayers() {
		gameEnded = true;
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			listener.lobbyPlayers.remove(player);
			listener.team1Players.remove(player);
			listener.team2Players.remove(player);
			listener.team1Query.remove(player);
			listener.team2Query.remove(player);
			listener.spectatingPlayers.add(player);
			listener.sb.getTeam("Spectators").addEntry(player.getName());
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage(ChatColor.BLUE + "Map is resetting...");
			player.getInventory().clear();
		}
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

			@Override
			public void run() {
				for (Player player : Bukkit.getServer().getOnlinePlayers()) {
					player.kickPlayer("Game is reseting...");
				}
				unloadMap("map");
				Bukkit.getServer().spigot().restart();
			}
		}, 100);
	}
}
