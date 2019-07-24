package com.rast.tntwars.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rast.tntwars.TNTWarsMain;

/**
 * This class is for the /setgameportal command and contains the command executor and auto tab events if relevant.
 *
 * @author Raster556
 */

public class SetPortalCount implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

        if (!TNTWarsMain.configEngine.developerMode) {
            sender.sendMessage(ChatColor.RED + "This feature is only available in Developer Mode! Use /developermode to toggle it");
            return true;
        }

        // We want sender to be player
        if (sender instanceof Player) {

            if (args.length < 1) {
                sender.sendMessage(ChatColor.DARK_RED + "Please enter a number for the per team portal count");
            }
            TNTWarsMain.configEngine.saveLocation("teamPortals", Integer.parseInt(args[0]));
            sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
                    + ChatColor.YELLOW + "Set each teams portal count to " + ChatColor.GOLD + "(" + TNTWarsMain.configEngine.teamPortals + ")");
            sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
                    + ChatColor.YELLOW + "Make sure you set all the portals or the game will error.");

        } else {
            sender.sendMessage(ChatColor.DARK_RED + "You must be a player to execute this command!");
        }
        return true;
    }
}