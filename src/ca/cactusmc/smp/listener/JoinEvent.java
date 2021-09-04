package ca.cactusmc.smp.listener;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ca.cactusmc.smp.Donation;
import ca.cactusmc.smp.Invite;
import ca.cactusmc.smp.SPlayer;
import ca.cactusmc.smp.Whitelist;
import net.md_5.bungee.api.ChatColor;

public class JoinEvent implements Listener{
 
	@EventHandler
	public void joinEvent(PlayerJoinEvent e) {
	
		Player p = e.getPlayer();
		SPlayer sp = new SPlayer(p);
		
		e.setJoinMessage("§7» "+ChatColor.of(sp.getColour()) + p.getName() + ChatColor.RESET + "§7 joined the game");
		
		if(sp.isNew()) {
			Invite inv = new Invite(e.getPlayer().getName());
			if(inv.getInviter() != null) sp.setInviter(inv.getInviter());
			if(sp.getInviter() != null) {
				OfflinePlayer inviter = Bukkit.getOfflinePlayer(sp.getInviter());
				SPlayer sinviter = new SPlayer(inviter);
				e.setJoinMessage("§7» "+ChatColor.of(sp.getColour()) + p.getName() + ChatColor.RESET + " §b#" + SPlayer.getUniquePlayers() + "§7, invited by " + ChatColor.of(sinviter.getColour()) + inviter.getName()+"§7, has joined for the first time");
				inv.remove();
				Whitelist.add(p.getUniqueId());
			}else {
				e.setJoinMessage("§7» "+ChatColor.of(sp.getColour()) + p.getName() + ChatColor.RESET + " §b#" + SPlayer.getUniquePlayers() + "§7 has joined for the first time");
			}
			p.sendMessage("§7» §3Welcome to §aCactus§2MC§3's SMP Server!\n \n§7» §3Please join our Discord server by checking §b/links§3. You may view other commands by using §b/help§3.\n \n§7» §3Enjoy your stay!\n ");
		}
		
		p.setPlayerListName(ChatColor.of(sp.getColour())+p.getName());
		
		new Donation(sp).check();
		
		if(sp.inTeamChat()) {
			if(sp.getTeam() == null) {
				sp.setTeamChat(false);
			}else {
				p.sendMessage("§7» §7Notice: You have joined in team chat. Please use /teamchat to disable it.");
			}
		}
	}
}
