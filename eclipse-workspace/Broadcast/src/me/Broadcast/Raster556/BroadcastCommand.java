package me.Broadcast.Raster556;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastCommand implements CommandExecutor{

	BroadcastMain plugin = BroadcastMain.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length > 0) {
				StringBuilder string = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					string.append(args[i]);
					string.append(' ');
				}
				String message = string.toString();
				plugin.sendBroadcast(player, message);
			} else {
				player.sendMessage(ChatColor.DARK_RED + "Must have text in message");
			}
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "Must be a player to send message");
		}
		return true;
	}

	
	
}
