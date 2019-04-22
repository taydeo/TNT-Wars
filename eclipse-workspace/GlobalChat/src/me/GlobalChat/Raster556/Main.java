package me.GlobalChat.Raster556;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {

	@Override
	public void onEnable () {
		getProxy().registerChannel("my:channel");
	}
	
	@Override
	public void onDisable () {
		
	}
	
	private void sendCustomData(ProxiedPlayer player, String data1, int data2) {
	    if (ProxyServer.getInstance().getPlayers() == null || ProxyServer.getInstance().getPlayers().isEmpty()) {
	      return;
	    }
	    ByteArrayDataOutput out = ByteStreams.newDataOutput();
	    out.writeUTF("MySubChannel"); // the channel could be whatever you want
	    out.writeUTF(data1); // this data could be whatever you want
	    out.writeInt(data2); // this data could be whatever you want

	    player.getServer().sendData("my:channel", out.toByteArray()); // we send the data to the server
	  }
}