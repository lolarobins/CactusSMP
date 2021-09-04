package ca.cactusmc.smp.listener;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import ca.cactusmc.smp.SPlayer;
import net.md_5.bungee.api.ChatColor;

public class LeaveEvent implements Listener {
	
	@EventHandler
	public void leaveEvent(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		SPlayer sp = new SPlayer((OfflinePlayer) p);
		
		e.setQuitMessage("§7» " + ChatColor.of(sp.getColour()) + p.getName() + ChatColor.RESET + "§7 left the game");
		sp.offlineTimestamp();
	}
}
