package com.rast.tntwars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.rast.tntwars.TNTWarsMain;

/**
 * This class is for the /endgame command and contains the command executor and auto tab events if relevant.
 * 
 * @author Raster556
 */

public class EndGame implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		TNTWarsMain.gameEnder.expelPlayers();
		return true;
	}

	
	
}
