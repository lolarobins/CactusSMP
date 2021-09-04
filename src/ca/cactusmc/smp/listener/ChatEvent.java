package ca.cactusmc.smp.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ca.cactusmc.smp.SPlayer;
import ca.cactusmc.smp.STeam;
import net.md_5.bungee.api.ChatColor;

public class ChatEvent implements Listener {
	
	@EventHandler
	public void chatEvent(AsyncPlayerChatEvent e) {
		
		Player p = e.getPlayer();
		SPlayer sp = new SPlayer(p);
		STeam t = sp.getTeam();
		String c = sp.getColour();
		
		if(sp.inTeamChat()) {
			if(t != null){
				String formatted = ChatColor.of(t.getColour()) + "Team §8»" + ChatColor.RESET + " " + ChatColor.of(sp.getColour()) + p.getName() + ChatColor.RESET + "§8: " + ChatColor.RESET + e.getMessage();
				
				for(Player tmp : Bukkit.getOnlinePlayers()) {
					if(new SPlayer(tmp).getTeam().getId().equalsIgnoreCase(t.getId())) tmp.sendMessage(formatted);
				}
				
				e.setCancelled(true);
			}else {
				p.sendMessage("§7» Team chat has been disabled because you're not in a team.");
				sp.setTeamChat(false);
			}
		}
		
		if(t != null) {
			e.setFormat(ChatColor.of(t.getColour()) + t.getId() + ChatColor.RESET + " " + ChatColor.of(c) + p.getName() + ChatColor.RESET + "§8: " + ChatColor.RESET + e.getMessage());
		}else {
			e.setFormat(ChatColor.of(c) + p.getName() + ChatColor.RESET + "§8: " + ChatColor.RESET + e.getMessage());
		}
		
	}
}
