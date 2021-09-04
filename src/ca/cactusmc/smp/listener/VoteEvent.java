package ca.cactusmc.smp.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import ca.cactusmc.smp.Donation;
import ca.cactusmc.smp.SPlayer;


public class VoteEvent implements Listener {

	public static final int VOTES_UNTIL_DONATOR = 20;
	public static final int ADDED_DAYS = 7;
	
	@EventHandler
	public void voteEvent(VotifierEvent e) {
		Vote v = e.getVote();
		Bukkit.getServer().getConsoleSender().sendMessage("VOTE RECEIVED - "+v.getUsername() + " "+v.getServiceName() + " " + v.getTimeStamp());
		SPlayer p = SPlayer.offlinePlayerFromName(v.getUsername());
		if(p == null) return;
		Player pl = Bukkit.getPlayer(p.getOfflinePlayer().getUniqueId());
		p.addVote();
		if(pl != null) {
			pl.sendMessage("§7» §6Received vote from "+v.getServiceName() +".");
		}
		if(p.getVotes() == VOTES_UNTIL_DONATOR) {
			Donation donation = new Donation(p);
			donation.addTime(ADDED_DAYS);
			p.resetVotes();
			Bukkit.broadcastMessage("§7» §a"+p.getOfflinePlayer().getName()+" has been awarded "+ADDED_DAYS+" days of donator for casting "+VOTES_UNTIL_DONATOR+" votes.");
		}
	}
}
