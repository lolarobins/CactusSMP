package ca.cactusmc.smp;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SPlayer {
	
	static FileConfiguration playerData = CactusSMP.singleton.getPlayerFile();
	OfflinePlayer op;
	Player p;
	String colour;
	UUID inviter;
	int votes,kills,pdeaths,tdeaths;
	boolean donator, firstjoin, teamchat;
	boolean hud = true;
	long timestamp, offline, rank;
	
	public SPlayer(OfflinePlayer player) {
		op = player;
		p = null;
		String path = "players."+player.getUniqueId();
		if(!playerData.contains(path)) return;
		timestamp = playerData.getLong(path+".join-time");
		offline = playerData.getLong(path+".offline");
		rank = playerData.getLong(path+".donator-expire");
		colour = playerData.getString(path+".colour");
		donator = playerData.getBoolean(path+".donator");
		votes = playerData.getInt(path+".votes");
		kills = playerData.getInt(path+".kills");
		pdeaths = playerData.getInt(path+".pdeaths");
		tdeaths = playerData.getInt(path+".tdeaths");
		if(playerData.getString(path+".inviter")!=null) inviter = UUID.fromString(playerData.getString(path+".inviter"));
	}
	
	public SPlayer(Player player) {
		op = (OfflinePlayer) player;
		p = player;
		String path = "players."+player.getUniqueId();
		if(!playerData.contains(path)) {
			colour = new RandomColour().getHex();
			incrementUnique();
			timestamp = System.currentTimeMillis();
			offline = System.currentTimeMillis();
			saveValues();
			firstjoin = true;
		}
		timestamp = playerData.getLong(path+".join-time");
		offline = playerData.getLong(path+".offline");
		rank = playerData.getLong(path+".donator-expire");
		colour = playerData.getString(path+".colour");
		donator = playerData.getBoolean(path+".donator");
		votes = playerData.getInt(path+".votes");
		kills = playerData.getInt(path+".kills");
		pdeaths = playerData.getInt(path+".pdeaths");
		tdeaths = playerData.getInt(path+".tdeaths");
		hud = playerData.getBoolean(path+".settings.hud");
		teamchat = playerData.getBoolean(path+".settings.teamchat");
		if(playerData.getString(path+".inviter")!=null) inviter = UUID.fromString(playerData.getString(path+".inviter"));
	}
	
	public void saveValues() {
		String path = "players."+op.getUniqueId();
		playerData.set(path+".username", op.getName());
		playerData.set(path+".join-time", timestamp);
		playerData.set(path+".offline", offline);
		playerData.set(path+".donator-expire", rank);
		playerData.set(path+".colour", colour);
		playerData.set(path+".donator", donator);
		playerData.set(path+".votes", votes);
		playerData.set(path+".kills", kills);
		playerData.set(path+".pdeaths", pdeaths);
		playerData.set(path+".tdeaths", tdeaths);
		if(inviter!=null) playerData.set(path+".inviter", inviter.toString());
		playerData.set(path+".settings.hud", hud);
		playerData.set(path+".settings.teamchat", teamchat);
		CactusSMP.singleton.savePlayerFile();
	}
	
	public void setInviter(UUID inviter) {this.inviter = inviter; saveValues();}
	public void setColour(String colour) {this.colour = colour; saveValues();}
	public void addToDonationTimer(long time) {
		if(rank == 0) {
			rank = System.currentTimeMillis()+time;
		}else {
			rank = rank+time;
		}
		saveValues();
	}
	public void resetDonationTimer() {rank = 0; saveValues();}
	public void addVote() {votes++; saveValues();}
	public void resetVotes() {votes=0; saveValues();}
	public void addTotalDeath() {tdeaths++; saveValues();}
	public void addPlayerDeath() {pdeaths++; saveValues();}
	public void addKill() {kills++; saveValues();}
	public void setDonator(boolean donator) {this.donator = donator; saveValues();}
	public void setTeamChat(boolean teamChat) {teamchat = teamChat; saveValues();}
	public void offlineTimestamp() {offline = System.currentTimeMillis(); saveValues();}
	
	public Player getPlayer() {return p;}
	public OfflinePlayer getOfflinePlayer() {return op;}
	public STeam getTeam() {return STeam.getTeamBelongingToPlayer(op);}
	public UUID getInviter() {return inviter;}
	public int getVotes() {return votes;}
	public int getKills() {return kills;}
	public int getPlayerDeaths() {return pdeaths;}
	public int getTotalDeaths() {return tdeaths;}
	public String getKD() {
		if(pdeaths == 0) return getKills() + ".00";
		DecimalFormat df = new DecimalFormat("##.###");
		return df.format(kills/pdeaths);
	}
	public long getJoinTime() {return timestamp;}
	public long getLastOnline() {return offline;}
	public long getDonationTimer() {return rank;}
	public boolean isDonator() {return donator;}
	public boolean isNew() {return firstjoin;}
	public boolean inTeamChat() {return teamchat;}
	public String getColour() {return colour;}
	
	public static SPlayer offlinePlayerFromName(String lastUsername) {
		if(playerData.getConfigurationSection("players") == null) return null;
		Object[] players = playerData.getConfigurationSection("players").getKeys(false).toArray();
		for(Object tmp : players) {
			if(playerData.contains("players."+(String)tmp+".username") && playerData.getString("players."+(String)tmp+".username").equalsIgnoreCase(lastUsername)) return new SPlayer(Bukkit.getOfflinePlayer(UUID.fromString((String) tmp)));
		}
		return null;
	}
	
	public static HashSet<SPlayer> getAllOfflinePlayers(){
		HashSet<SPlayer> set = new HashSet<SPlayer>();
		Object[] players = playerData.getConfigurationSection("players").getKeys(false).toArray();
		for(Object tmp : players) {
			set.add(new SPlayer(Bukkit.getOfflinePlayer(UUID.fromString((String) tmp))));
		}
		return set;
	}
	
	public static int getUniquePlayers() {return playerData.getInt("unique");}
	
	private static void incrementUnique() {
		if(!playerData.contains("unique")) {
			playerData.set("unique", 0);
			CactusSMP.singleton.savePlayerFile();
		}
		int uniquePlayers = playerData.getInt("unique")+1;
		playerData.set("unique", uniquePlayers);
		CactusSMP.singleton.savePlayerFile();
	}
}
