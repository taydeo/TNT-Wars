package me.TNTWars.Raster556;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class CommandListener implements CommandExecutor {

	// Initiate variables
	private Plugin plugin = TNTWars.plugin;
	private EventListener listener;
	private ItemStack selector;
	public Boolean setupMode;
	private GameEndManager gem;

	// Run when class is created
	public void onEnable() {
		plugin.reloadConfig();
		setupMode = plugin.getConfig().getBoolean("setupMode");
		listener = TNTWars.el;
		selector = TNTWars.selectorTool;
		gem = TNTWars.gem;
	}

	// When a command is issued from the list in TNTWars this is called
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		// If this is a player using the command
		if (sender instanceof Player) {
			String name = command.getName();
			Player player = (Player) sender;

			// If the command is 'Selection'
			if (name.equalsIgnoreCase("selectorTool")) {
				if (player.hasPermission("TNTWars.admin.selection")) {
					Inventory inv = player.getInventory();
					inv.addItem(selector);
				} else {
					player.sendMessage(ChatColor.RED + "You are lacking permisssion for TNTWars.admin.selection");
				}
			}

			// If the command is 'SetSetupMode'
			if (name.equalsIgnoreCase("setsetupmode")) {
				if (player.hasPermission("TNTWars.admin.selection")) {
					if (setupMode != Boolean.parseBoolean(args[0])) {
						setupMode = Boolean.parseBoolean(args[0]);
						plugin.reloadConfig();
						plugin.getConfig().set("setupMode", setupMode);
						plugin.saveConfig();
						sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED
								+ "] " + ChatColor.YELLOW + "Setup Mode set to " + ChatColor.GOLD + "(" + setupMode
								+ ")");
					} else {
						sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED
								+ "] " + ChatColor.YELLOW + "Setup Mode is already set to " + ChatColor.GOLD + "("
								+ setupMode + ")");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You are lacking permisssion for TNTWars.admin.selection");
				}
			}

			// If the command is 'Lobby'
			if (name.equalsIgnoreCase("lobby") && gem.gameEnded == false) {
				if (!listener.lobbyPlayers.contains(player)) {
					if (listener.team1Players.contains(player) || listener.team1Query.contains(player)) {
						listener.team1Count--;
					}
					if (listener.team2Players.contains(player) || listener.team2Query.contains(player)) {
						listener.team2Count--;
					}
					listener.lobbyPlayers.add(player);
					listener.team1Players.remove(player);
					listener.spectatingPlayers.remove(player);
					listener.team2Players.remove(player);
					listener.team1Query.remove(player);
					listener.team1Query.remove(player);
					listener.sb.getTeam("Lobby").addEntry(player.getName());
					player.setGameMode(GameMode.ADVENTURE);
					player.teleport(listener.lobbySpawn);
					player.setHealth(20);
					player.setFoodLevel(20);
					player.getInventory().clear();
				}
			}

			// If the command is 'Spectate'
			if (name.equalsIgnoreCase("spectate") && gem.gameEnded == false) {
				if (!listener.spectatingPlayers.contains(player)) {
					if (listener.team1Players.contains(player) || listener.team1Query.contains(player)) {
						listener.team1Count--;
					}
					if (listener.team2Players.contains(player) || listener.team2Query.contains(player)) {
						listener.team2Count--;
					}
					listener.lobbyPlayers.remove(player);
					listener.team1Players.remove(player);
					listener.team2Players.remove(player);
					listener.team1Query.remove(player);
					listener.team1Query.remove(player);
					listener.spectatingPlayers.add(player);
					listener.sb.getTeam("Spectators").addEntry(player.getName());
					player.setGameMode(GameMode.SPECTATOR);
					player.sendMessage(ChatColor.BLUE + "You have entered spectator mode. Use /spectate to exit");
					player.getInventory().clear();
				} else {
					listener.spectatingPlayers.remove(player);
					listener.lobbyPlayers.add(player);
					listener.sb.getTeam("Lobby").addEntry(player.getName());
					player.setGameMode(GameMode.ADVENTURE);
					player.teleport(listener.lobbySpawn);
					player.setHealth(20);
					player.setFoodLevel(20);
					player.getInventory().clear();
				}
			}

			if ((name.equalsIgnoreCase("setIsland") || name.equalsIgnoreCase("createPortal")
					|| name.equalsIgnoreCase("deletePortal") || name.equalsIgnoreCase("setLobby")
					|| name.equalsIgnoreCase("setLobbyPortal") || name.equalsIgnoreCase("setLobbySpawn")
					|| name.equalsIgnoreCase("setIslandSpawn") || name.equalsIgnoreCase("setQueSpawn")
					|| name.equalsIgnoreCase("setIsland")) && setupMode == false) {

				player.sendMessage(ChatColor.RED + "Setup Mode is turned off. Do '/setSetupMode true' to turn it on.");
				return true;
			}

			// If the command is 'SetLobby'
			if (name.equalsIgnoreCase("setlobby")) {
				if (player.hasPermission("TNTWars.admin.selection")) {

					// Get the positions from selector and save them to the config
					int[] pos1 = listener.pos1;
					int[] pos2 = listener.pos2;
					plugin.reloadConfig();
					plugin.getConfig().set("lobby.pos1.x", pos1[0]);
					plugin.getConfig().set("lobby.pos1.y", pos1[1]);
					plugin.getConfig().set("lobby.pos1.z", pos1[2]);
					plugin.getConfig().set("lobby.pos2.x", pos2[0]);
					plugin.getConfig().set("lobby.pos2.y", pos2[1]);
					plugin.getConfig().set("lobby.pos2.z", pos2[2]);
					sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
							+ ChatColor.YELLOW + "Lobby set to " + ChatColor.GOLD + "(" + pos1[0] + ", " + pos1[1]
							+ ", " + pos1[2] + "), (" + pos2[0] + ", " + pos2[1] + ", " + pos2[2] + ")");
					plugin.saveConfig();
				} else {
					player.sendMessage(ChatColor.RED + "You are lacking permisssion for TNTWars.admin.selection");
				}
			}

			// If the command is 'EndGame'
			if (name.equalsIgnoreCase("endGame")) {
				if (player.hasPermission("TNTWars.admin.selection")) {
					gem.expelPlayers();
				} else {
					player.sendMessage(ChatColor.RED + "You are lacking permisssion for TNTWars.admin.selection");
				}
			}

			// If the command is 'CreatePortal'
			if (name.equalsIgnoreCase("createPortal")) {
				if (player.hasPermission("TNTWars.admin.selection")) {
					if (args[0] == null && args[1] == null) {
						return false;
					}

					// Get the positions from selector and save them to the config (requires reload)
					int[] pos1 = listener.pos1;
					int[] pos2 = listener.pos2;
					plugin.reloadConfig();
					List<String> list = plugin.getConfig().getStringList("portalList");
					if (list == null) {
						list = new ArrayList<String>();
					}
					if (!(list.contains(args[0] + ", " + args[1]))) {
						list.add(args[0] + ", " + args[1]);
					}
					plugin.getConfig().set("portal" + args[0] + args[1] + ".pos1.x", pos1[0]);
					plugin.getConfig().set("portal" + args[0] + args[1] + ".pos1.y", pos1[1]);
					plugin.getConfig().set("portal" + args[0] + args[1] + ".pos1.z", pos1[2]);
					plugin.getConfig().set("portal" + args[0] + args[1] + ".pos2.x", pos2[0]);
					plugin.getConfig().set("portal" + args[0] + args[1] + ".pos2.y", pos2[1]);
					plugin.getConfig().set("portal" + args[0] + args[1] + ".pos2.z", pos2[2]);
					plugin.getConfig().set("portalList", list);
					sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
							+ ChatColor.YELLOW + "Portal " + args[0] + args[1] + " set to " + ChatColor.GOLD + "("
							+ pos1[0] + ", " + pos1[1] + ", " + pos1[2] + "), (" + pos2[0] + ", " + pos2[1] + ", "
							+ pos2[2] + ")");
					plugin.saveConfig();
				} else {
					player.sendMessage(ChatColor.RED + "You are lacking permisssion for TNTWars.admin.selection");
				}
			}

			// If the command is 'SetLobbyPortal'
			if (name.equalsIgnoreCase("setLobbyPortal")) {

				if (player.hasPermission("TNTWars.admin.selection")) {

					// Get the positions from selector and save them to the config (requires reload)
					int[] pos1 = listener.pos1;
					int[] pos2 = listener.pos2;
					plugin.reloadConfig();
					plugin.getConfig().set("lobbyPortal.pos1.x", pos1[0]);
					plugin.getConfig().set("lobbyPortal.pos1.y", pos1[1]);
					plugin.getConfig().set("lobbyPortal.pos1.z", pos1[2]);
					plugin.getConfig().set("lobbyPortal.pos2.x", pos2[0]);
					plugin.getConfig().set("lobbyPortal.pos2.y", pos2[1]);
					plugin.getConfig().set("lobbyPortal.pos2.z", pos2[2]);
					sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
							+ ChatColor.YELLOW + "Set lobby portal to " + ChatColor.GOLD + "(" + pos1[0] + ", "
							+ pos1[1] + ", " + pos1[2] + "), (" + pos2[0] + ", " + pos2[1] + ", " + pos2[2] + ")");
					plugin.saveConfig();
				} else {
					player.sendMessage(ChatColor.RED + "You are lacking permisssion for TNTWars.admin.selection");
				}
			}

			// If the command is 'DeletePortal' (requires reload)
			if (name.equalsIgnoreCase("deletePortal")) {

				if (player.hasPermission("TNTWars.admin.selection")) {
					if (args[0] == null && args[1] == null) {
						return false;
					}
					plugin.reloadConfig();
					List<String> list = plugin.getConfig().getStringList("portalList");
					if (list == null) {
						list = new ArrayList<String>();
					}
					list.remove(args[0] + ", " + args[1]);
					plugin.getConfig().set("portal" + args[0] + args[1] + ".pos1.x", null);
					plugin.getConfig().set("portal" + args[0] + args[1] + ".pos1.y", null);
					plugin.getConfig().set("portal" + args[0] + args[1] + ".pos1.z", null);
					plugin.getConfig().set("portal" + args[0] + args[1] + ".pos2.x", null);
					plugin.getConfig().set("portal" + args[0] + args[1] + ".pos2.y", null);
					plugin.getConfig().set("portal" + args[0] + args[1] + ".pos2.z", null);
					plugin.getConfig().set("portalList", list);
					sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
							+ ChatColor.YELLOW + "Portal " + args[0] + args[1] + " was deleted.");
					plugin.saveConfig();
				} else {
					player.sendMessage(ChatColor.RED + "You are lacking permisssion for TNTWars.admin.selection");
				}
			}

			// If the command is 'SetIsland' [island numb]
			if (name.equalsIgnoreCase("setisland")) {

				if (player.hasPermission("TNTWars.admin.selection")) {

					// Get the positions form the selector and save them to the config
					plugin.reloadConfig();
					int[] pos1 = listener.pos1;
					int[] pos2 = listener.pos2;

					if (Integer.parseInt(args[0]) == 1) {
						plugin.getConfig().set("island1.pos1.x", pos1[0]);
						plugin.getConfig().set("island1.pos1.y", pos1[1]);
						plugin.getConfig().set("island1.pos1.z", pos1[2]);
						plugin.getConfig().set("island1.pos2.x", pos2[0]);
						plugin.getConfig().set("island1.pos2.y", pos2[1]);
						plugin.getConfig().set("island1.pos2.z", pos2[2]);
						sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED
								+ "] " + ChatColor.YELLOW + "Island1 set to " + ChatColor.GOLD + "(" + pos1[0] + ", "
								+ pos1[1] + ", " + pos1[2] + "), (" + pos2[0] + ", " + pos2[1] + ", " + pos2[2] + ")");
					} else if (Integer.parseInt(args[0]) == 2) {
						plugin.getConfig().set("island2.pos1.x", pos1[0]);
						plugin.getConfig().set("island2.pos1.y", pos1[1]);
						plugin.getConfig().set("island2.pos1.z", pos1[2]);
						plugin.getConfig().set("island2.pos2.x", pos2[0]);
						plugin.getConfig().set("island2.pos2.y", pos2[1]);
						plugin.getConfig().set("island2.pos2.z", pos2[2]);
						sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED
								+ "] " + ChatColor.YELLOW + "Island2 set to " + ChatColor.GOLD + "(" + pos1[0] + ", "
								+ pos1[1] + ", " + pos1[2] + "), (" + pos2[0] + ", " + pos2[1] + ", " + pos2[2] + ")");
					} else {
						return false;
					}
					plugin.saveConfig();
				} else {
					player.sendMessage(ChatColor.RED + "You are lacking permisssion for TNTWars.admin.selection");
				}
			}

			// If the command is 'SetIslandSpawn' [island numb]
			if (name.equalsIgnoreCase("setislandspawn")) {
				if (player.hasPermission("TNTWars.admin.selection")) {

					// Get the positions form the selector and save them to the config
					plugin.reloadConfig();
					Location loc = player.getLocation();
					if (Integer.parseInt(args[0]) == 1) {
						plugin.getConfig().set("islandSpawn1", loc);
						sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED
								+ "] " + ChatColor.YELLOW + "Island Spawn 1 set to " + ChatColor.GOLD + "("
								+ loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")");
					} else if (Integer.parseInt(args[0]) == 2) {
						plugin.getConfig().set("islandSpawn2", loc);
						sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED
								+ "] " + ChatColor.YELLOW + "Island Spawn 2 set to " + ChatColor.GOLD + "("
								+ loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")");
					} else {
						return false;
					}
					plugin.saveConfig();
				} else {
					player.sendMessage(ChatColor.RED + "You are lacking permisssion for TNTWars.admin.selection");
				}
			}

			// If the command is 'SetQueSpawn' [que numb]
			if (name.equalsIgnoreCase("setQueSpawn")) {
				if (player.hasPermission("TNTWars.admin.selection")) {

					// Get the positions form the selector and save them to the config
					plugin.reloadConfig();
					Location loc = player.getLocation();
					if (Integer.parseInt(args[0]) == 1) {
						plugin.getConfig().set("Que1", loc);
						sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED
								+ "] " + ChatColor.YELLOW + "Que Spawn 1 set to " + ChatColor.GOLD + "("
								+ loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")");
					} else if (Integer.parseInt(args[0]) == 2) {
						plugin.getConfig().set("Que2", loc);
						sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED
								+ "] " + ChatColor.YELLOW + "Que Spawn 2 set to " + ChatColor.GOLD + "("
								+ loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")");
					} else {
						return false;
					}
					plugin.saveConfig();
				} else {
					player.sendMessage(ChatColor.RED + "You are lacking permisssion for TNTWars.admin.selection");
				}
			}

			// If the command is 'setLobbySpawn'
			if (name.equalsIgnoreCase("setlobbyspawn")) {
				if (player.hasPermission("TNTWars.admin.selection")) {

					// Get the positions form the selector and save them to the config
					plugin.reloadConfig();
					Location loc = player.getLocation();
					plugin.getConfig().set("lobbySpawn", loc);
					sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
							+ ChatColor.YELLOW + "Lobby Spawn set to " + ChatColor.GOLD + "(" + loc.getBlockX() + ", "
							+ loc.getBlockY() + ", " + loc.getBlockZ() + ")");
					plugin.saveConfig();
				} else {
					player.sendMessage(ChatColor.RED + "You are lacking permisssion for TNTWars.admin.selection");
				}
			}
		} else {

			// You must be a player to send these commands
			sender.sendMessage(ChatColor.DARK_RED + "You must be a player to send this command!");
		}
		return true;
	}
}
