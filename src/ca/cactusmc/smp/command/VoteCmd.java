package ca.cactusmc.smp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.cactusmc.smp.SPlayer;
import ca.cactusmc.smp.listener.VoteEvent;

public class VoteCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if(!(s instanceof Player)) return true;
		SPlayer sp = new SPlayer((Player)s);
		s.sendMessage("§7» §6Please vote at the links below:\n"
				+ "§7» §fhttps://bit.ly/CMCSMP-TOPG\n"
				+ "§7» §fhttps://bit.ly/CMCSMP-PMC\n"
				+ "§7» §fhttps://bit.ly/CMCSMP-MSL\n"
				+ "§7» §fhttps://bit.ly/CMCSMP-MS\n"
				+ "§7» §3You have §b"+(VoteEvent.VOTES_UNTIL_DONATOR-sp.getVotes())+"§3 votes left until you receive §b" + VoteEvent.ADDED_DAYS + "§3 days of donator.");
		return true;
	}
	
}
