package com.rast.tntwars.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.rast.tntwars.TNTWarsMain;
import com.rast.tntwars.systems.PlayerTeleportManager;
import com.rast.tntwars.systems.ScoreBoardManager;

import java.util.Objects;

/**
 * This class is for the /spectate command and contains the command executor and auto tab events if relevant.
 * 
 * @author Raster556
 */

public class Spectate implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		
		// We need sender to be player in order to teleport them
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (Objects.equals(TNTWarsMain.playerGroupManager.getGroup(player), "spectator")) {
				PlayerTeleportManager.teleportLobby(player, true);
				TNTWarsMain.playerGroupManager.addToGroup(player, "lobby");
				Bukkit.broadcastMessage(ChatColor.GRAY + player.getName() + " joined the lobby");
			} else {
				player.setGameMode(GameMode.SPECTATOR);
				ScoreBoardManager.teamPlayerAdd(player, "spectator");
				TNTWarsMain.playerGroupManager.addToGroup(player, "spectator");
				Bukkit.broadcastMessage(ChatColor.BLUE + player.getName() + " is now spectating!");
				sender.sendMessage(ChatColor.BLUE + "Use "+ ChatColor.UNDERLINE + "/spectate" + ChatColor.RESET + ChatColor.BLUE + " to exit this mode");
			}
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "You must be a player to execute this command!");
		}
		return true;
	}
}
