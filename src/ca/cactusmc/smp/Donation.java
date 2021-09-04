package ca.cactusmc.smp;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Donation {
	
	SPlayer player;
	
	public Donation(SPlayer splayer){
		player = splayer;
	}
	
	public void addTime(int days) {
		player.addToDonationTimer(TimeUnit.DAYS.toMillis(days));
		player.setDonator(true);
	}
	
	public void check() {
		if(player.getDonationTimer() == 0) return;
		if(player.getDonationTimer() < System.currentTimeMillis()) {
			if(player.getPlayer() != null) {
				player.getPlayer().sendMessage("§7» §cYour donator permissions have expired.");
			}
			player.resetDonationTimer();
			player.setDonator(false);
		}
	}
	
	public static void checkOnline() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			Donation d = new Donation(new SPlayer(p));
			d.check();
		}
	}
	
}
