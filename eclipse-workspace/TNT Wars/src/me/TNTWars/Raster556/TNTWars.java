package me.TNTWars.Raster556;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

// Main Class
public class TNTWars extends JavaPlugin {

	public static Plugin plugin;
	public static ItemStack selectorTool;
	public static ItemStack inventoryItem;
	public static CommandListener cl;
	public static EventListener el;
	public static GamePortalManager gpm;
	public static IslandManager im;
	public static LobbyPortal lp;
	public static GameEndManager gem;
	public static GameInitiator gi;
	public static World gameWorld;

	// Fired when plugin is first enabled
	@Override
	public void onEnable() {
		plugin = this;
		plugin.saveDefaultConfig();
		gameWorld = this.getServer().createWorld(new WorldCreator("map"));
		gameWorld.setAutoSave(false);

		// Create selection tool
		List<String> lore = new ArrayList<String>();
		lore.add("[Left Click] for Pos1");
		lore.add("[Right Click] for Pos2");
		selectorTool = new ItemStack(Material.GOLDEN_PICKAXE);
		ItemMeta meta = selectorTool.getItemMeta();
		meta.addEnchant(Enchantment.DURABILITY, 1, false);
		meta.setDisplayName("TNT War Arena Selector");
		meta.setLore(lore);
		selectorTool.setItemMeta(meta);

		// Create inventory tool
		lore.clear();
		lore.add("Get items for game");
		inventoryItem = new ItemStack(Material.CHEST);
		meta.setDisplayName("Chest O' Items");
		meta.setLore(lore);
		inventoryItem.setItemMeta(meta);

		// Initiate Classes
		el = new EventListener();
		cl = new CommandListener();
		gpm = new GamePortalManager();
		im = new IslandManager();
		lp = new LobbyPortal();
		gem = new GameEndManager();
		gi = new GameInitiator();

		// Initiate Classes
		el.onEnable();
		el.setupScoreboard();
		cl.onEnable();
		gpm.onEnable();
		im.onEnable();
		lp.onEnable();
		gem.onEnable();
		gi.onEnable();

		// Setup Event Listener
		getServer().getPluginManager().registerEvents(el, this);

		// Register Commands
		this.getCommand("selectorTool").setExecutor(cl);
		this.getCommand("setIsland").setExecutor(cl);
		this.getCommand("createPortal").setExecutor(cl);
		this.getCommand("deletePortal").setExecutor(cl);
		this.getCommand("setLobby").setExecutor(cl);
		this.getCommand("setLobbyPortal").setExecutor(cl);
		this.getCommand("setLobbySpawn").setExecutor(cl);
		this.getCommand("setIslandSpawn").setExecutor(cl);
		this.getCommand("setSetupMode").setExecutor(cl);
		this.getCommand("setQueSpawn").setExecutor(cl);
		this.getCommand("lobby").setExecutor(cl);
		this.getCommand("spectate").setExecutor(cl);
		this.getCommand("endGame").setExecutor(cl);
	}

	// Fired when plugin is disabled
	@Override
	public void onDisable() {

	}
}
