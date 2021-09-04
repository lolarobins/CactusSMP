package ca.cactusmc.smp.command;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.cactusmc.smp.SPlayer;
import net.md_5.bungee.api.ChatColor;

public class StatsCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if(args.length == 0) {
			try{
				runStats(s, new SPlayer((Player) s));
			}catch(ClassCastException e) {
				s.sendMessage("stats <player>");
			}
			return true;
		}
		if(Bukkit.getPlayer(args[0]) == null) {
			if(SPlayer.offlinePlayerFromName(args[0]) == null) {
				s.sendMessage("§7» §cCould not find player.");
			}else {
				runStatsOffline(s, SPlayer.offlinePlayerFromName(args[0]));
			}
		}else {
			runStats(s, new SPlayer(Bukkit.getPlayer(args[0])));
		}
		return true;
	}
	
	String timeParse(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("h:mma z, MMM d, yyyy.");
		return sdf.format(Date.from(Instant.ofEpochMilli(time)));
	}
	
	void runStats(CommandSender s, SPlayer target) {
		String msg = "§7» §6Showing player stats for "+ChatColor.of(target.getColour())+target.getOfflinePlayer().getName()+"§6:\n"+
				"§7» §3Joined: §f"+timeParse(target.getJoinTime()) + "\n";
		if(target.getTeam()!=null) msg=msg+"§7» §3Team: "+ChatColor.of(target.getTeam().getColour())+target.getTeam().getId()+"\n";
		if(target.getInviter()!=null) msg=msg+"§7» §3Inviter: "+ChatColor.of(new SPlayer(Bukkit.getOfflinePlayer(target.getInviter())).getColour()) + Bukkit.getOfflinePlayer(target.getInviter()).getName() + "\n";
		msg = msg+"§7» §3PvP K/D: §f"+target.getKD()+" §7<§a"+target.getKills()+"§7:§c"+target.getPlayerDeaths()+"§7>\n"
				+ "§7» §3Total Deaths: §f"+target.getTotalDeaths();
		s.sendMessage(msg);
	}
	
	void runStatsOffline(CommandSender s, SPlayer target) {
		String msg = "§7» §6Showing player stats for "+ChatColor.of(target.getColour())+target.getOfflinePlayer().getName()+"§6:\n"
				+ "§7» §3Joined: §f"+timeParse(target.getJoinTime()) + "\n"
				+ "§7» §3Last Online: §f"+timeParse(target.getLastOnline()) + "\n";
		if(target.getTeam()!=null) msg=msg+"§7» §3Team: "+ChatColor.of(target.getTeam().getColour())+target.getTeam().getId()+"\n";
		if(target.getInviter()!=null) msg=msg+"§7» §3Inviter: "+ChatColor.of(new SPlayer(Bukkit.getOfflinePlayer(target.getInviter())).getColour()) + Bukkit.getOfflinePlayer(target.getInviter()).getName() + "\n";
		msg = msg+"§7» §3PvP K/D: §f"+target.getKD()+" §7<§a"+target.getKills()+"§7:§c"+target.getPlayerDeaths()+"§7>\n"
				+ "§7» §3Total Deaths: §f"+target.getTotalDeaths();
		s.sendMessage(msg);
	}
}
