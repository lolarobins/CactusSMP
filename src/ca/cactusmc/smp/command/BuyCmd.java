package ca.cactusmc.smp.command;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.cactusmc.smp.SPlayer;

public class BuyCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if(!(s instanceof Player)) return true;
		String status = "";
		SPlayer p = new SPlayer((Player) s);
		if(!p.isDonator()) {
			status = "Not a donator";
		}else {
			status = "Donator §7<"+timeParse(p.getDonationTimer())+">";
		}
		String msg = "§7» §6Donation Info: \n"
				+ "§7» §3Server Store: §fhttp://shop.cactusmc.ca/\n"
				+ "§7» §3Donation Status: §f" + status;
		s.sendMessage(msg);
		return true;
	}

	String timeParse(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("h:mma z, MMM d, yyyy.");
		return sdf.format(Date.from(Instant.ofEpochMilli(time)));
	}
}
