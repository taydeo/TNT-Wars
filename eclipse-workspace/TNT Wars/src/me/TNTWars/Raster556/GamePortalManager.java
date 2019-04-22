package me.TNTWars.Raster556;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.plugin.Plugin;

public class GamePortalManager {

	Plugin plugin = TNTWars.plugin;
	public List<GamePortal> gamePortals = new ArrayList<GamePortal>();
	public int team1portals;
	public int team2portals;
	private List<String> portalIDs;

	public void onEnable() {
		reloadPortals();
		team1portals = portalIDs.size() / 2;
		team2portals = portalIDs.size() / 2;
	}

	public void reloadPortals() {
		gamePortals.clear();
		// Load Portals (after setting new portals you need to reload the plugin)
		plugin.reloadConfig();
		portalIDs = plugin.getConfig().getStringList("portalList");
		for (int i = 0; i < portalIDs.size(); i++) {
			GamePortal gp = new GamePortal();
			gp.island = Integer.parseInt(portalIDs.get(i).split(", ")[0]);
			gp.portalNumb = Integer.parseInt(portalIDs.get(i).split(", ")[1]);
			gp.sortedX = new int[] {
					plugin.getConfig().getInt("portal" + portalIDs.get(i).replaceAll(", ", "") + ".pos1.x"),
					plugin.getConfig().getInt("portal" + portalIDs.get(i).replaceAll(", ", "") + ".pos2.x") };
			gp.sortedY = new int[] {
					plugin.getConfig().getInt("portal" + portalIDs.get(i).replaceAll(", ", "") + ".pos1.y"),
					plugin.getConfig().getInt("portal" + portalIDs.get(i).replaceAll(", ", "") + ".pos2.y") };
			gp.sortedZ = new int[] {
					plugin.getConfig().getInt("portal" + portalIDs.get(i).replaceAll(", ", "") + ".pos1.z"),
					plugin.getConfig().getInt("portal" + portalIDs.get(i).replaceAll(", ", "") + ".pos2.z") };
			Arrays.sort(gp.sortedX);
			Arrays.sort(gp.sortedY);
			Arrays.sort(gp.sortedZ);
			gamePortals.add(gp);
		}
	}
}
