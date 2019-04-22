package me.Broadcast.Raster556;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class BroadcastMain extends JavaPlugin implements PluginMessageListener{

	public static BroadcastMain instance;
	
	@Override
	public void onEnable() {
		instance = this;
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
	    this.getCommand("broadcast").setExecutor(new BroadcastCommand());
	}
	
	@Override
	public void onDisable() {
		instance = null;
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
	    if (!channel.equals("BungeeCord")) {
	        return;
	    }
	    ByteArrayDataInput in = ByteStreams.newDataInput(message);
	    
	    String subChannel = in.readUTF();
	    short len = in.readShort();
	    byte[] msgbytes = new byte[len];
	    in.readFully(msgbytes);
	    String broadcastMessage = null;
	    
	    DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
	    try {
			broadcastMessage = msgin.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}

	    if (subChannel.equals("Broadcast")) {
	    	Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.GREEN + broadcastMessage);
	    }
	    
	}
	
	public void sendBroadcast (Player player, String message) {
		  ByteArrayDataOutput out = ByteStreams.newDataOutput();
		  out.writeUTF("Forward");
		  out.writeUTF("Online");
		  out.writeUTF("Broadcast");

		  ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		  DataOutputStream msgout = new DataOutputStream(msgbytes);
		  try {
		  msgout.writeUTF(message);
		  msgout.writeShort(123);
		  } catch (IOException exception){
		  exception.printStackTrace();
		  }

		  out.writeShort(msgbytes.toByteArray().length);
		  out.write(msgbytes.toByteArray());

		  player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}

	public static BroadcastMain getInstance() {
		return instance;
	}
	
}
