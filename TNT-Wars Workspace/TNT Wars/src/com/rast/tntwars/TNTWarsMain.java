package com.rast.tntwars;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rast.tntwars.commands.DeveloperMode;
import com.rast.tntwars.commands.EndGame;
import com.rast.tntwars.commands.GetSelector;
import com.rast.tntwars.commands.Leave;
import com.rast.tntwars.commands.PlayGlobalSound;
import com.rast.tntwars.commands.SetPortal;
import com.rast.tntwars.commands.SetRegion;
import com.rast.tntwars.commands.SetSpawn;
import com.rast.tntwars.commands.Spectate;
import com.rast.tntwars.systems.GameInitializer;
import com.rast.tntwars.systems.PlayerGroupManager;
import com.rast.tntwars.systems.PlayerTeleportManager;
import com.rast.tntwars.systems.ScoreBoardManager;
import com.rast.tntwars.systems.SelectorManager;
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
	public static SelectorManager selectorManager;
	
	// Runs when the plugin is enabled
	@Override
	public void onEnable() {
		getInstance = this;
		
		// Start the map world if it does not already exist and set auto-save to false
		World gameWorld = getServer().createWorld(new WorldCreator(getConfig().getString("gameWorldName")));
		gameWorld.setAutoSave(false);
		
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
		selectorManager = new SelectorManager();
		
		// Register Event Listener
		getServer().getPluginManager().registerEvents(eventListener, this);
		
		// Register Commands
		getCommand("getselector").setExecutor(new GetSelector());
		getCommand("playglobalsound").setExecutor(new PlayGlobalSound());
		getCommand("endgame").setExecutor(new EndGame());
		getCommand("spectate").setExecutor(new Spectate());
		getCommand("setspawn").setExecutor(new SetSpawn());
		getCommand("setregion").setExecutor(new SetRegion());
		getCommand("setportal").setExecutor(new SetPortal());
		getCommand("developermode").setExecutor(new DeveloperMode());
		getCommand("leave").setExecutor(new Leave());

		// Set players to lobby that already exist (used for /reload)
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false, false));
			PlayerTeleportManager.teleportLobby(player, true);
			TNTWarsMain.playerGroupManager.addToGroup(player, "lobby");
		}
		
		
	}
	
	// Runs when the plugin shuts down
	@Override
	public void onDisable() {
		// Unload the map without saving
		gameEnder.expelPlayersInstant();
		
		// Remove references for safety
		selectorManager = null;
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
