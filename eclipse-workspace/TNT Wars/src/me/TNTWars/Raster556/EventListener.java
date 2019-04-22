package me.TNTWars.Raster556;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

public class EventListener implements Listener {

	// Initiate variables
	private Plugin plugin = TNTWars.plugin;

	private ItemStack inventoryItem;
	private ItemStack selector;
	private IslandManager islandManager;
	private LobbyPortal lobbyPortal;
	private GameInitiator gameInitiator;
	private CommandListener commandListener;
	private GamePortalManager gamePM;
	private GameEndManager gameEM;
	private boolean moveDebounce;

	private World world = plugin.getServer().getWorld("map");
	
	public Location que1Spawn;
	public Location que2Spawn;
	public Location lobbySpawn;
	public Inventory inv = Bukkit.createInventory(null, 18, "Item Stash");

	public int[] pos1;
	public int[] pos2;

	// Variables for player managment
	public List<Player> lobbyPlayers = new ArrayList<Player>();
	public List<Player> team1Players = new ArrayList<Player>();
	public List<Player> team2Players = new ArrayList<Player>();
	public List<Player> team1Query = new ArrayList<Player>();
	public List<Player> team2Query = new ArrayList<Player>();
	public List<Player> spectatingPlayers = new ArrayList<Player>();
	public int team1Count = 0;
	public int team2Count = 0;

	Random rand = new Random(System.currentTimeMillis());

	ScoreboardManager sbm = Bukkit.getScoreboardManager();
	Scoreboard sb = sbm.getMainScoreboard();

	// Run when class is created
	public void onEnable() {
		plugin.reloadConfig();
		inventoryItem = TNTWars.inventoryItem;
		selector = TNTWars.selectorTool;
		islandManager = TNTWars.im;
		lobbyPortal = TNTWars.lp;
		commandListener = TNTWars.cl;
		gamePM = TNTWars.gpm;
		gameInitiator = TNTWars.gi;
		gameEM = TNTWars.gem;

		que1Spawn = (Location) plugin.getConfig().get("Que1");
		que2Spawn = (Location) plugin.getConfig().get("Que2");
		lobbySpawn = (Location) plugin.getConfig().get("lobbySpawn");

		inv.addItem(new ItemStack(Material.TNT));
		inv.addItem(new ItemStack(Material.WATER_BUCKET));
		inv.addItem(new ItemStack(Material.BUCKET));
		inv.addItem(new ItemStack(Material.STONE_BRICKS));
		inv.addItem(new ItemStack(Material.REDSTONE_BLOCK));
		inv.addItem(new ItemStack(Material.REDSTONE));
		inv.addItem(new ItemStack(Material.REPEATER));
		inv.addItem(new ItemStack(Material.COMPARATOR));
		inv.addItem(new ItemStack(Material.REDSTONE_TORCH));
		inv.addItem(new ItemStack(Material.PISTON));
		inv.addItem(new ItemStack(Material.STICKY_PISTON));
		inv.addItem(new ItemStack(Material.SLIME_BLOCK));
		inv.addItem(new ItemStack(Material.DISPENSER));
		inv.addItem(new ItemStack(Material.OBSERVER));
		inv.addItem(new ItemStack(Material.STONE_BUTTON));
		inv.addItem(new ItemStack(Material.LEVER));
		inv.addItem(new ItemStack(Material.STONE_BRICK_SLAB));
		inv.addItem(new ItemStack(Material.IRON_PICKAXE));
	}

	public void setupScoreboard() {
		if (sb.getTeam("Lobby") != null) {
			sb.getTeam("Lobby").unregister();
			Team team = sb.registerNewTeam("Lobby");
			team.setColor(ChatColor.GRAY);
		} else {
			Team team = sb.registerNewTeam("Lobby");
			team.setColor(ChatColor.GRAY);
		}

		if (sb.getTeam("Spectators") != null) {
			sb.getTeam("Spectators").unregister();
			Team team = sb.registerNewTeam("Spectators");
			team.setColor(ChatColor.BLUE);
		} else {
			Team team = sb.registerNewTeam("Spectators");
			team.setColor(ChatColor.BLUE);
		}

		if (sb.getTeam("Team1") != null) {
			sb.getTeam("Team1").unregister();
			Team team = sb.registerNewTeam("Team1");
			team.setColor(ChatColor.DARK_AQUA);
		} else {
			Team team = sb.registerNewTeam("Team1");
			team.setColor(ChatColor.DARK_AQUA);
		}

		if (sb.getTeam("Team2") != null) {
			sb.getTeam("Team2").unregister();
			Team team = sb.registerNewTeam("Team2");
			team.setColor(ChatColor.GOLD);
		} else {
			Team team = sb.registerNewTeam("Team2");
			team.setColor(ChatColor.GOLD);
		}

		for (Player player : Bukkit.getOnlinePlayers()) {
			lobbyPlayers.add(player);
			sb.getTeam("Lobby").addEntry(player.getName());
			player.setGameMode(GameMode.ADVENTURE);
			player.teleport(lobbySpawn);
			player.setHealth(20);
			player.setFoodLevel(20);
			player.getInventory().clear();
		}

	}

	// When a player empties a bucket
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBucketEmpty(PlayerBucketEmptyEvent event) {
		Location location = event.getBlockClicked().getLocation();
		location.add(event.getBlockFace().getDirection());
		for (GamePortal portal : gamePM.gamePortals) {
			if (portal.testBounds(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
				event.setCancelled(true);
			}
		}
	}

	// When a player clicks something
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerIeract(BlockDispenseEvent event) {
		if (event.getItem().getType() == Material.WATER_BUCKET || event.getItem().getType() == Material.LAVA_BUCKET) {
			Location location = event.getBlock().getLocation();
			BlockFace targetFace = ((org.bukkit.material.Dispenser) event.getBlock().getState().getData()).getFacing();
			location.add(targetFace.getDirection());
			for (GamePortal portal : gamePM.gamePortals) {
				if (portal.testBounds(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
					event.setCancelled(true);
				}
			}
		}
	}

	// When a player clicks something
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {

		Player player = event.getPlayer();

		// If player used the Selector
		if (event.hasItem() && inventoryItem.isSimilar(event.getItem())) {
			event.setCancelled(true);
			player.openInventory(inv);
		}

		// If player used the Selector
		if (event.hasItem() && selector.isSimilar(event.getItem())) {
			event.setCancelled(true);
			Block block = event.getClickedBlock();
			if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
				pos1 = new int[] { block.getX(), block.getY(), block.getZ() };
				player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
						+ ChatColor.YELLOW + "Pos1 set to " + ChatColor.GOLD + "(" + pos1[0] + ", " + pos1[1] + ", "
						+ pos1[2] + ")");
			} else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				pos2 = new int[] { block.getX(), block.getY(), block.getZ() };
				player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
						+ ChatColor.YELLOW + "Pos2 set to " + ChatColor.GOLD + "(" + pos2[0] + ", " + pos2[1] + ", "
						+ pos2[2] + ")");
			}
		}
	}

	// When a player clicks inventory
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (inv.getViewers().contains(event.getWhoClicked())) {
			event.setCancelled(true);
			if (event.getClickedInventory() == event.getView().getTopInventory()) {
				ItemStack item = new ItemStack(event.getCurrentItem().getType());
				if (event.getClick().isLeftClick()) {
					item.setAmount(item.getMaxStackSize());
					event.getView().getBottomInventory().addItem(item);
				} else if (event.getClick().isRightClick()) {
					event.getView().getBottomInventory().addItem(item);
				}
			}
		}
	}

	// When a player clicks inventory
	@EventHandler
	public void onInventoryDrag(PlayerDropItemEvent event) {
		if (event.getItemDrop().getItemStack().isSimilar(inventoryItem)) {
			event.setCancelled(true);
		}
	}

	// When a player breaks a block
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Location location = event.getBlock().getLocation();
		int island = islandManager.testBounds(location.getBlockX(), location.getBlockY(), location.getBlockZ());
		if (island == 0 && commandListener.setupMode == false) {
			event.setCancelled(true);
		} else if (island == 1 && !team1Players.contains(player)) {
			event.setCancelled(true);
		} else if (island == 2 && !team2Players.contains(player)) {
			event.setCancelled(true);
		}
	}

	// When a player places a block
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Location location = event.getBlock().getLocation();

		int island = islandManager.testBounds(location.getBlockX(), location.getBlockY(), location.getBlockZ());
		if (island == 0 && commandListener.setupMode == false) {
			event.setCancelled(true);
		} else if (island == 1 && !team1Players.contains(player)) {
			event.setCancelled(true);
		} else if (island == 2 && !team2Players.contains(player)) {
			event.setCancelled(true);
		}
	}

	// When player moves
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (spectatingPlayers.contains(event.getPlayer())) {
			return;
		}
		if (moveDebounce == true) {
			return;
		}
		moveDebounce = true;
		Player player = event.getPlayer();
		if (lobbyPortal.testBounds(player.getLocation().getBlockX(), player.getLocation().getBlockY(),
				player.getLocation().getBlockZ()) && sb.getTeam("Lobby").hasEntry(player.getName())) {
			sb.getTeam("Lobby").removeEntry(player.getName());
			if (team1Count < team2Count) {
				team1Count++;
				if (gameInitiator.gameStarted) {
					player.teleport(gameInitiator.island1, TeleportCause.PLUGIN);
					team1Players.add(player);
					player.setGameMode(GameMode.SURVIVAL);
					player.getInventory().addItem(inventoryItem);
				} else {
					player.teleport(que1Spawn, TeleportCause.PLUGIN);
					gameInitiator.countdownRefresh();
					team1Query.add(player);
				}
				lobbyPlayers.remove(player);
				sb.getTeam("Team1").addEntry(player.getName());
				Bukkit.broadcastMessage(ChatColor.DARK_AQUA + player.getName() + " joined the cyan team!");
			} else if (team1Count > team2Count) {
				team2Count++;
				if (gameInitiator.gameStarted) {
					player.teleport(gameInitiator.island2, TeleportCause.PLUGIN);
					team2Players.add(player);
					player.setGameMode(GameMode.SURVIVAL);
					player.getInventory().addItem(inventoryItem);
				} else {
					player.teleport(que2Spawn, TeleportCause.PLUGIN);
					gameInitiator.countdownRefresh();
					team2Query.add(player);
				}
				lobbyPlayers.remove(player);
				sb.getTeam("Team2").addEntry(player.getName());
				Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + " joined the orange team!");
			} else if (team1Count == team2Count) {
				if (rand.nextBoolean()) {
					team1Count++;
					if (gameInitiator.gameStarted) {
						player.teleport(gameInitiator.island1, TeleportCause.PLUGIN);
						team1Players.add(player);
						player.setGameMode(GameMode.SURVIVAL);
						player.getInventory().addItem(inventoryItem);
					} else {
						player.teleport(que1Spawn, TeleportCause.PLUGIN);
						gameInitiator.countdownRefresh();
						team1Query.add(player);
					}
					lobbyPlayers.remove(player);
					sb.getTeam("Team1").addEntry(player.getName());
					Bukkit.broadcastMessage(ChatColor.DARK_AQUA + player.getName() + " joined the cyan team!");
				} else {
					team2Count++;
					if (gameInitiator.gameStarted) {
						player.teleport(gameInitiator.island2, TeleportCause.PLUGIN);
						team2Players.add(player);
						player.setGameMode(GameMode.SURVIVAL);
						player.getInventory().addItem(inventoryItem);
					} else {
						player.teleport(que2Spawn, TeleportCause.PLUGIN);
						gameInitiator.countdownRefresh();
						team2Query.add(player);
					}
					lobbyPlayers.remove(player);
					sb.getTeam("Team2").addEntry(player.getName());
					Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + " joined the orange team!");
				}
			}
		}
		moveDebounce = false;
	}

	// When an enitity such as tnt explodes
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		Location location = event.getEntity().getLocation();

		event.setCancelled(true);

		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();

		if (event.getEntity().getMetadata("origionalIsland").get(0).asInt() == islandManager.testBounds(x, y, z)) {
			List<Block> blocks = event.blockList();
			for (Block block : blocks) {
				if (block.getBlockData().getMaterial() == Material.TNT) {
					TNTPrimed entity = (TNTPrimed) block.getWorld().spawnEntity(block.getLocation(),
							EntityType.PRIMED_TNT);
					entity.setFuseTicks((int) Math.round(Math.random() * 20 + 10));
					block.setType(Material.AIR);
				}
			}
		} else {
			location.add(-1, -1, -1);
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					for (int k = 0; k < 3; k++) {
						Block block = location.getBlock();
						if (block.getType() != Material.BEDROCK && block.getType() != Material.OBSIDIAN
								&& block.getType() != Material.NETHER_PORTAL) {
							block.setType(Material.AIR);
						}
						location.add(1, 0, 0);
					}
					location.add(-3, 1, 0);
				}
				location.add(0, -3, 1);
			}
			Bukkit.getScheduler().runTask(plugin, new Runnable() {
				@Override
				public void run() {
					location.add(1, 1, -2);
					location.getWorld().createExplosion(location, 4);
				}
			});
		}
	}

	// When an enitity such as tnt explodes
	@EventHandler
	public void onBlockExplode(BlockExplodeEvent event) {
		List<Block> blocks = event.blockList();
		for (Block block : blocks) {
			if (block.getType() == Material.NETHER_PORTAL) {
				for (GamePortal portal : gamePM.gamePortals) {
					if (portal.destroyed == false && portal.testBounds(block.getX(), block.getY(), block.getZ())) {
						portal.destroyed = true;
						if (portal.island == 1) {
							Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Cyan" + ChatColor.YELLOW + "'s No."
									+ portal.portalNumb + " portal has been destroyed!");
							world.playSound(lobbySpawn, Sound.ENTITY_WITHER_AMBIENT, SoundCategory.MASTER, Float.MAX_VALUE,
									1f);
						} else {
							Bukkit.broadcastMessage(ChatColor.GOLD + "Orange" + ChatColor.YELLOW + "'s No."
									+ portal.portalNumb + " portal has been destroyed!");
							world.playSound(lobbySpawn, Sound.ENTITY_WITHER_AMBIENT, SoundCategory.MASTER, Float.MAX_VALUE,
									1f);
						}
						gameEM.testPortals();
					}
				}
			}
		}
	}

	// When a block is pushed
	@EventHandler
	public void onPistonExtend(BlockPistonExtendEvent event) {
		List<Block> blocks = event.getBlocks();
		Vector direction = event.getDirection().getDirection();
		int X = direction.getBlockX();
		int Y = direction.getBlockY();
		int Z = direction.getBlockZ();

		for (Block block : blocks) {
			if (islandManager.testBounds(block.getX() + X, block.getY() + Y, block.getZ() + Z) == 0) {
				event.setCancelled(true);
				break;
			}
		}
	}

	// When a portal is made
	@EventHandler
	public void onPortalCreation(PortalCreateEvent event) {
		if (commandListener.setupMode == false) {
			event.setCancelled(true);
		}
	}

	// When a player dies
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run() {
				player.spigot().respawn();
				if (team1Players.contains(player)) {
					Location loc = gameInitiator.island1;
					player.getInventory().addItem(inventoryItem);
					Location location = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
					location.add(-1, 1, -1);
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							for (int k = 0; k < 3; k++) {
								Block block = location.getBlock();
								if (block.getType() != Material.BEDROCK && block.getType() != Material.OBSIDIAN) {
									block.setType(Material.AIR);
								}
								location.add(1, 0, 0);
							}
							location.add(-3, 1, 0);
						}
						location.add(0, -3, 1);
					}
					loc.add(0, 0.5, 0);
					player.teleport(loc);
				} else if (team2Players.contains(player)) {
					Location loc = gameInitiator.island2;
					player.getInventory().addItem(inventoryItem);
					Location location = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
					location.add(-1, 1, -1);
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							for (int k = 0; k < 3; k++) {
								Block block = location.getBlock();
								if (block.getType() != Material.BEDROCK && block.getType() != Material.OBSIDIAN) {
									block.setType(Material.AIR);
								}
								location.add(1, 0, 0);
							}
							location.add(-3, 1, 0);
						}
						location.add(0, -3, 1);
					}
					loc.add(0, 0.5, 0);
					player.teleport(loc);
				}
			}
		});
	}

	// When an enitity such as tnt explodes
	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent event) {
		Entity entity = event.getEntity();

		if (event.getEntity().getType() == EntityType.PRIMED_TNT) {
			Location location = entity.getLocation();
			entity.setMetadata("origionalIsland", new FixedMetadataValue(plugin,
					islandManager.testBounds(location.getBlockX(), location.getBlockY(), location.getBlockZ())));
		}
	}

	// When a player joins
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		lobbyPlayers.add(player);
		sb.getTeam("Lobby").addEntry(player.getName());
		player.setGameMode(GameMode.ADVENTURE);
		player.teleport(lobbySpawn);
		player.setHealth(20);
		player.setFoodLevel(20);
		player.getInventory().clear();
	}

	// When a player joins
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (!lobbyPlayers.contains(player)) {
			if (team1Players.contains(player) || team1Query.contains(player)) {
				team1Count--;
			}
			if (team2Players.contains(player) || team2Query.contains(player)) {
				team2Count--;
			}
			lobbyPlayers.add(player);
			team1Players.remove(player);
			spectatingPlayers.remove(player);
			team2Players.remove(player);
			team1Query.remove(player);
			team1Query.remove(player);
			sb.getTeam("Lobby").addEntry(player.getName());
			player.setGameMode(GameMode.ADVENTURE);
			player.teleport(lobbySpawn);
			player.setHealth(20);
			player.setFoodLevel(20);
			player.getInventory().clear();
		}
	}

	// When food level changes
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}

	// When health changes
	@EventHandler
	public void onHealthChange(EntityDamageEvent event) {

		if (event.getEntityType() != EntityType.PLAYER) {
			return;
		}

		Player player = (Player) event.getEntity();

		if (lobbyPlayers.contains(player) || team1Query.contains(player) || team2Query.contains(player)) {
			event.setCancelled(true);
		} else if (player.getHealth() - event.getDamage() < 1) {
			player.getInventory().clear();
		}
	}

	// When a player chats
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (event.isAsynchronous()) {
			event.setCancelled(true);
			String name = event.getPlayer().getName();
			Bukkit.broadcastMessage(
					"<" + sb.getEntryTeam(name).getColor() + name + ChatColor.WHITE + "> " + event.getMessage());
		}
	}
}
