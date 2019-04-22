package me.Raster556;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class PluginCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (sender instanceof Player) {
    		Player player = (Player) sender;
    		if (command.getName().equalsIgnoreCase("cookies")) {
				Inventory inv = player.getInventory();
    			if (args.length >= 1) {
	    			inv.addItem(new ItemStack(Material.COOKIE, Integer.parseInt(args[0])));
    			} else {
	    			inv.addItem(new ItemStack(Material.COOKIE));
    			}
    		}
    		
    		if (command.getName().equalsIgnoreCase("cannon")) {
    			List<String> lore = Arrays.asList("It is a TNT cannon");
				Inventory inv = player.getInventory();
				ItemStack item = new ItemStack(Material.TNT);
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.DURABILITY, 1, false);
				meta.setDisplayName(ChatColor.GREEN + "TNT Cannon");
				meta.setLore(lore);
				item.setItemMeta(meta);
				inv.addItem(item);
    		}
    	} else {
    		sender.sendMessage(ChatColor.DARK_RED + "You must be a player to send this command!");
    	}
    	return true;
    }
}
