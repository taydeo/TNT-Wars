package com.rast.tntwars;

import java.util.List;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.rast.tntwars.systems.ChatColorFix;
import com.rast.tntwars.systems.PlayerTeleportManager;
import com.rast.tntwars.systems.Portal;
import com.rast.tntwars.systems.RegionManager;
import com.rast.tntwars.systems.TNTExtentions;
import com.rast.tntwars.systems.WarpPortalManager;
import com.rast.tntwars.systems.GamePortalChecker;

/**
 * This class is where all events are handled and listened for.
 * 
 * @author Raster556
 */

public class EventListener implements Listener {
	
	private final TNTWarsMain tntwars = TNTWarsMain.getInstance;
	
	// When the player joins put them in the lobby and assign them the lobby group
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false, false));
		PlayerTeleportManager.teleportLobby(player, true);
		TNTWarsMain.playerGroupManager.addToGroup(player, "lobby");
	}
	
	// Remove player from all lists when they quit
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		TNTWarsMain.playerGroupManager.removeFromList(player);
	}
	
	// When food level changes
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}
	
	// When entity is damaged
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {

		// We only want players
		if (event.getEntityType() != EntityType.PLAYER) {
			return;
		}

		Player player = (Player) event.getEntity();
		String group = TNTWarsMain.playerGroupManager.getGroup(player);

		// If the player is in the lobby or the game has not started we do not want to damage the player
		if (Objects.equals(group, "lobby") || !TNTWarsMain.gameInitializer.gameStarted) {
			event.setCancelled(true);
		}
	}

	// When a player empties a bucket
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBucketEmpty(PlayerBucketEmptyEvent event) {
		if (TNTWarsMain.configEngine.portalTeamGriefProtection) {
			Location location = event.getBlockClicked().getLocation();
			location.add(event.getBlockFace().getDirection());

			// Stop the event if player is placing fluid in the portal
			for (Portal portal : TNTWarsMain.configEngine.teamPortalList) {
				if (portal.checkPortal(location, false)) {
					event.setCancelled(true);
				}
			}
		}
	}

	// When a dispenser dispenses something
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockDispense(BlockDispenseEvent event) {		
		if (TNTWarsMain.configEngine.portalTeamGriefProtection) {
			if (event.getItem().getType() == Material.WATER_BUCKET || event.getItem().getType() == Material.LAVA_BUCKET) {
				Location location = event.getBlock().getLocation();
				BlockFace targetFace = ((org.bukkit.material.Dispenser) event.getBlock().getState().getData()).getFacing();
				location.add(targetFace.getDirection());
				
				// Stop the event if dispenser is placing fluid in the portal
				for (Portal portal : TNTWarsMain.configEngine.teamPortalList) {
					if (portal.checkPortal(location, false)) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
		
	// Handle player death
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		String group = TNTWarsMain.playerGroupManager.getGroup(player);
		
		// Respawn the player in the next server tick to allow time for the packages to send
		Bukkit.getScheduler().runTask(tntwars, () -> {
			player.spigot().respawn();
			player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false, false));
			// Teleport player to proper area according to group.
			switch (group) {
			case "lobby":
			case "spectator":
				PlayerTeleportManager.teleportLobby(player, true);
				break;
			case "teamOne":
				PlayerTeleportManager.teleportTeamOneIsland(player, true);
				player.getInventory().addItem(TNTWarsMain.itemStackStorage.blockMenuItem);
				break;
			case "teamTwo":
				PlayerTeleportManager.teleportTeamTwoIsland(player, true);
				player.getInventory().addItem(TNTWarsMain.itemStackStorage.blockMenuItem);
				break;
			default:
				TNTWarsMain.playerGroupManager.addToGroup(player, "lobby");
				PlayerTeleportManager.teleportLobby(player, true);
				player.sendMessage(ChatColor.DARK_RED + "You seem to be in an invalid group so was moved to lobby! Please report this to the plugin developer.");
				break;
			}
		});
	}
	
	// When a player chats we want to apply the team color
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		Player player = event.getPlayer();
		ChatColorFix.sendChat(player, event.getMessage());
	}
	
	// When a player chats we want to apply the team color
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		WarpPortalManager.playerPortalSender(player);
	}
	
	// When a player does an action
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		// If the block chest is being used
		if (event.hasItem() && Objects.requireNonNull(event.getItem()).isSimilar(TNTWarsMain.itemStackStorage.blockMenuItem)) {
			event.setCancelled(true);
			TNTWarsMain.blockMenu.openInventory(player);
			player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, SoundCategory.MASTER, Float.MAX_VALUE, 1.2f);
		} 
		
		// If the selector is being used
				if (event.hasItem() && event.hasBlock() && Objects.requireNonNull(event.getItem()).isSimilar(TNTWarsMain.itemStackStorage.selectorItem)) {
					event.setCancelled(true);
					Block block = event.getClickedBlock();
					if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
						int[] loc = TNTWarsMain.selectorManager.setPos1(Objects.requireNonNull(block).getLocation());
						player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
								+ ChatColor.YELLOW + "Pos1 set to " + ChatColor.GOLD + "(" + loc[0] + ", " + loc[1] + ", "
								+ loc[2] + ")");
					} else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						int[] loc = TNTWarsMain.selectorManager.setPos2(Objects.requireNonNull(block).getLocation());
						player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "TNT Wars" + ChatColor.DARK_RED + "] "
								+ ChatColor.YELLOW + "Pos2 set to " + ChatColor.GOLD + "(" + loc[0] + ", " + loc[1] + ", "
								+ loc[2] + ")");
					}
				} 
		
		// If the player is interacting in their own region or is in the lobby cancel interact event
		if (event.hasBlock()) {
			String reg = RegionManager.regionDetect(Objects.requireNonNull(event.getClickedBlock()).getLocation());
			String group = TNTWarsMain.playerGroupManager.getGroup(player);
			if (reg.equals("lobby") || !reg.equals(group)) {
				event.setCancelled(true);
			}
		}
	}
	
	// When a block is placed
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		String reg = RegionManager.regionDetect(event.getBlock().getLocation());
		String group = TNTWarsMain.playerGroupManager.getGroup(player);
		
		// If the player is not placing a block in their own region or is in the lobby cancel interact event
		if (reg.equals("lobby") || !reg.equals(group)) {
			event.setCancelled(true);
		}
	}
	
	// When a player closes an inventory
	@EventHandler
	public void onInvenotryClose(InventoryCloseEvent event) {
		HumanEntity player = event.getPlayer();
		if (TNTWarsMain.blockMenu.chestInventory.getViewers().contains(player)) {
			((Player) player).playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, SoundCategory.MASTER, Float.MAX_VALUE, 1.2f);
		}
	}
	
	// When a player throws/drops an item
	@EventHandler
	public void onItemThrow(PlayerDropItemEvent event) {
		if (event.getItemDrop().getItemStack().isSimilar(TNTWarsMain.itemStackStorage.blockMenuItem)) {
			event.setCancelled(true);
		}
	}
	
	// When an entity spawns
	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent event) {
		Entity entity = event.getEntity();
		
		if (event.getEntity().getType() == EntityType.PRIMED_TNT) {
	
			// We want to assign an original region value to prevent team griefers
			entity.setMetadata("regOfPrimed", new FixedMetadataValue(tntwars, RegionManager.regionDetect(entity.getLocation())));
		}
	}
	
	// When an entity explodes
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		
		// Run our custom code
		TNTExtentions.tntExplodeEventHandle(event);
	}
	
	
	// When a block explodes
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onBlockExplode(BlockExplodeEvent event) {
		if (TNTWarsMain.gameEnder.gameEnded) {
			event.setCancelled(true);
		}
		
		// We want to check if a portal was blown up by the custom tnt
		List<Block> blocks = event.blockList();
		GamePortalChecker.portalCheck(blocks);
	}
	
	// When a block is pushed
	@EventHandler
	public void onPistonExtend(BlockPistonExtendEvent event) {
		
		// If piston bounding is enabled
		if (TNTWarsMain.configEngine.pistonBounding) {
			
			List<Block> blocks = event.getBlocks();
			Vector direction = event.getDirection().getDirection();
			// If the piston is pushing blocks into the void region then we stop the event
			for (Block block : blocks) {
				if (RegionManager.regionDetect(block.getLocation().add(direction)).equals("none")) {
					event.setCancelled(true);
					break;
				}
			}
		}
	}
	
	// When a portal is mad
		@EventHandler
		public void onPortalCreation(PortalCreateEvent event) {
			
			// If we are not in developer mode we have no reason to make portals
			if (!TNTWarsMain.configEngine.developerMode) {
				event.setCancelled(true);
			}
		}
	
	// When a player interacts with inventory
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		
		InventoryView view = event.getView();
		
		// If the block menu is open
		if (TNTWarsMain.blockMenu.chestInventory.getViewers().contains(view.getPlayer())) {
			
			// If our player is shift clicking from any inventory we want to stop the item transfer
			if (event.isShiftClick()) {
				event.setCancelled(true);
			}
			
			// Prevent player from adding and removing items directly from the top inventory
			if (event.getClickedInventory() == event.getView().getTopInventory()) {
				event.setCancelled(true);
				ItemStack item = Objects.requireNonNull(event.getCurrentItem()).clone();
				
				// If our player clicks on a spacer item then we do nothing
				if (item.getType() == TNTWarsMain.itemStackStorage.spacer.getType()) {
					((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_GLASS_BREAK, SoundCategory.MASTER, Float.MAX_VALUE, 2);
					return;
				} 
				
				// If player left clicks give them max stack size. If right click give one item.
				if (event.isLeftClick()) {
					item.setAmount(item.getMaxStackSize());
					((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_BAT_TAKEOFF, SoundCategory.MASTER, Float.MAX_VALUE, 1.3f);
					view.getBottomInventory().addItem(item);
				} else if (event.isRightClick()) {
					((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_BAT_TAKEOFF, SoundCategory.MASTER, Float.MAX_VALUE, 1.6f);
					view.getBottomInventory().addItem(item);
				}
			}
		}
	}
}
