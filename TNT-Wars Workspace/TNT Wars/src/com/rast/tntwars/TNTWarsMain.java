package com.rast.tntwars;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rast.tntwars.commands.EndGame;
import com.rast.tntwars.commands.GetSelector;
import com.rast.tntwars.commands.PlayGlobalSound;
import com.rast.tntwars.systems.GameInitializer;
import com.rast.tntwars.systems.PlayerGroupManager;
import com.rast.tntwars.systems.PlayerTeleportManager;
import com.rast.tntwars.systems.ScoreBoardManager;
import com.rast.tntwars.systems.BlockMenu;
import com.rast.tntwars.systems.GameEnder;

/**
 * This class is the main class of the TNT Wars plugin.
 * 
 * @author Raster556
 */

public class TNTWarsMain extends JavaPlugin{
	
	public static TNTWarsMain getInstance;
	public static ConfigEngine configEngine;
	
	public static EventListener eventListener;
	public static PlayerGroupManager playerGroupManager;
	public static ScoreBoardManager scoreBoardManager;
	public static GameInitializer gameInitializer;
	public static StaticItemStackStorage itemStackStorage;
	public static BlockMenu blockMenu;
	public static GameEnder gameEnder;
	
	// Runs when the plugin is enabled
	@Override
	public void onEnable() {
		getInstance = this;
		
		getServer().createWorld(new WorldCreator("map"));
		// Setup config
		configEngine = new ConfigEngine();
		
		// Make new instances for non static classes
		eventListener = new EventListener();
		playerGroupManager = new PlayerGroupManager();
		scoreBoardManager = new ScoreBoardManager();
		gameInitializer = new GameInitializer();
		itemStackStorage = new StaticItemStackStorage();
		blockMenu = new BlockMenu();
		gameEnder = new GameEnder();
		
		// Register Event Listener
		getServer().getPluginManager().registerEvents(eventListener, this);
		
		// Register Commands
		getCommand("getselector").setExecutor(new GetSelector());
		getCommand("playglobalsound").setExecutor(new PlayGlobalSound());
		getCommand("endgame").setExecutor(new EndGame());
		
		// Set players to lobby that already exist (used for /reload)
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false, false));
			PlayerTeleportManager.teleportLobby(player, true);
			TNTWarsMain.playerGroupManager.addToGroup(player, "lobby");
		}
		
		// Grab a temporary item menu to load it in memory
		@SuppressWarnings("unused")
		ItemStack tempBlockMenu = itemStackStorage.blockMenuItem;
		
	}
	
	// Runs when the plugin shuts down
	@Override
	public void onDisable() {
		
		// Remove references for safety
		gameEnder = null;
		blockMenu = null;
		itemStackStorage = null;
		gameInitializer = null;
		scoreBoardManager = null;
		playerGroupManager = null;
		eventListener = null;
		getInstance = null;
		
	}
}
