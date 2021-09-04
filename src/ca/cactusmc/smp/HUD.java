package ca.cactusmc.smp;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.MinecraftServer;

public class HUD {
	
	static int headerFooterCycle;
	static ScoreboardManager sbm;
	static Scoreboard sb;
	static Objective ping;
	
	public static void registerScoreboards() {
		sbm = Bukkit.getScoreboardManager();
		sb = sbm.getNewScoreboard();
		ping = sb.registerNewObjective("ping", "dummy", "ping", RenderType.INTEGER);
		ping.setDisplaySlot(DisplaySlot.PLAYER_LIST);
	}
	
	public static void updatePing() {
		ScoreboardManager sbm = Bukkit.getScoreboardManager();
		Scoreboard sb = sbm.getNewScoreboard();
		Objective ping = sb.registerNewObjective("ping", "dummy", "ping", RenderType.INTEGER);
		ping.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		for(Player p : Bukkit.getOnlinePlayers()) {
			
			Score pingScore = ping.getScore(p.getName());
			pingScore.setScore(p.getPing());
			p.setScoreboard(sb);
			
		}
	}
	
	public static String getMemoryPrecent() {
		Runtime r = Runtime.getRuntime();
		DecimalFormat df = new DecimalFormat("##.##%");
		double t = r.totalMemory();
		double f = r.freeMemory();
		double m = r.maxMemory();
		double p = (t-f)/m;
		return df.format(p);
	}
	
	// Every 10s
	public static void incrementHeaderFooter() {
		headerFooterCycle++;
	}
	
	// Update every 2s to refresh TPS, memory
	public static void updateHeaderFooter() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			String header = "§aCactus§2SMP";
			String footer = "";
			switch(headerFooterCycle) {
			case 0:
				footer = "§3Dynmap: §bsmp.cactusmc.ca:8123";
				break;
			case 1:
				footer = "§3IP: §bsmp.cactusmc.ca";
				break;
			case 2:
				footer = "§3Discord: §bdiscord.cactusmc.ca";
				break;
			case 3:
				footer = "§3TPS: §b"+MinecraftServer.TPS;
				break;
			case 4:
				footer = "§3TPS: §b"+MinecraftServer.TPS;
				break;
			case 5:
				footer = "§3TPS: §b"+MinecraftServer.TPS;
				break;
			case 6:
				footer = "§3Memory Usage: §b"+getMemoryPrecent();
				break;
			case 7:
				footer = "§3Memory Usage: §b"+getMemoryPrecent();
				break;
			default:
				footer = "§3Memory Usage: §b"+getMemoryPrecent();
				headerFooterCycle = 0;
				break;
			}
			p.setPlayerListHeaderFooter(header, footer);
			p.setPlayerListName(ChatColor.of(new SPlayer(p).getColour())+p.getName());
		}
	}
}
