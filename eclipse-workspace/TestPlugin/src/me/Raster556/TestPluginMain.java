package me.Raster556;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class TestPluginMain extends JavaPlugin {
	
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	this.getCommand("cookies").setExecutor(new PluginCommands());
    	this.getCommand("cannon").setExecutor(new PluginCommands());
    	getServer().getPluginManager().registerEvents(new EventManager(), this);
    	
    	List<String> lore = Arrays.asList("It is a TNT cannon");
		ItemStack item = new ItemStack(Material.TNT);
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(Enchantment.DURABILITY, 1, false);
		meta.setDisplayName(ChatColor.GREEN + "TNT Cannon");
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		NamespacedKey key = new NamespacedKey(this, "Cannon");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		
		recipe.shape("TST", "TBT", "TTT");
		recipe.setIngredient('T', Material.TNT);
		recipe.setIngredient('S', Material.STONE_SLAB);
		recipe.setIngredient('B', Material.BRICKS);
		
		Bukkit.addRecipe(recipe);
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }
   
}
