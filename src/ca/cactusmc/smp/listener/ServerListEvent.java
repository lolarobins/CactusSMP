package ca.cactusmc.smp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListEvent implements Listener {
	
	@EventHandler
	public void serverList(ServerListPingEvent e) {
		e.setMotd("§8» §aCactus§2MC \n§8» §71.17.1 Whitelisted SMP");
	}
}
