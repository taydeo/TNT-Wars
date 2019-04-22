package me.Raster556;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.md_5.bungee.api.ChatColor;

public class EventManager implements Listener {
	 
	
	@EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
		List<String> lore = Arrays.asList("It is a TNT cannon");
		ItemStack item = new ItemStack(Material.TNT);
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(Enchantment.DURABILITY, 1, false);
		meta.setDisplayName(ChatColor.GREEN + "TNT Cannon");
		meta.setLore(lore);
		item.setItemMeta(meta);
    	
    	ItemStack handItem = event.getItemInHand();
    	Player player = event.getPlayer();
    	if (item.isSimilar(handItem)) {
    		player.sendMessage(ChatColor.AQUA + "You Placed A Cannon!");
    		Block block = event.getBlock();
    		Cannon cannon = new Cannon();
    		cannon.makeCannon(block, Bukkit.getPluginManager().getPlugin("TestPlugin"));
    		}
    }
    
   
	
}
