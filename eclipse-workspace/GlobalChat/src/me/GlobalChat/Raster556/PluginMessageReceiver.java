package me.GlobalChat.Raster556;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageReceiver implements Listener
{

    @EventHandler
    public void on(PluginMessageEvent event)
    {
        if ( !event.getTag().equalsIgnoreCase( "my:channel" ) )
        {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput( event.getData() );
        String subChannel = in.readUTF();
        if ( subChannel.equalsIgnoreCase( "OtherSubchannel" ) )
        {
            // Do things without need of a server
           
            //------------------------------------------------------------------------
            // This is if you want to do things with server
            // We can't know if the sender is a player or a server, that's why we check it
            if ( event.getSender() instanceof Server )
            {
                Server server = (Server) event.getSender();
                // Do things with need of a server
                return;
            }
            if ( event.getSender() instanceof ProxiedPlayer )
            {
                ProxiedPlayer sender = (ProxiedPlayer) event.getSender();
                Server server = sender.getServer();
                // Do things with need of a server
            }
        }
    }

}
