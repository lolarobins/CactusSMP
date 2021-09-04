package ca.cactusmc.smp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class STeam {
	
	static FileConfiguration teamData = CactusSMP.singleton.getTeamFile();
	Set<OfflinePlayer> members = new HashSet<OfflinePlayer>();
	Set<OfflinePlayer> invites = new HashSet<OfflinePlayer>();
	OfflinePlayer leader;
	UUID sessionID = UUID.randomUUID();
	String id;
	String colour;
	
	public STeam(String id, OfflinePlayer leader, boolean create) {
		if(!create) return;
		
		this.id = id;
		this.leader = leader;
		members.add(leader);
		colour = "#0ac900";
		
		for(String tmp : STeam.playerInvitedToIDs(leader.getPlayer())) {
			STeam tmpT = new STeam(tmp);
			tmpT.removeInvite(leader, false);
			tmpT.saveValues();
		}
		Bukkit.getLogger().log(Level.INFO, "Team "+this.id+" ("+members.size()+" members) has been created.");
		saveValues();
	}
	
	public STeam(String id) {
		String idLC = id.toLowerCase();
		if(teamData.getConfigurationSection("teams") == null) return;
		Map<String, String> caseMap = new HashMap<String, String>();
		Object[] teams = teamData.getConfigurationSection("teams").getKeys(false).toArray();
		for(Object tmp : teams) {
			String tmpS = (String) tmp;
			caseMap.put(tmpS.toLowerCase(), tmpS);
		}
		if(caseMap.containsKey(idLC)) {
			String path = "teams."+caseMap.get(idLC);
			colour = teamData.getString(path+".colour");
			this.id = caseMap.get(idLC);
			leader = Bukkit.getOfflinePlayer(UUID.fromString(teamData.getString(path+".leader")));
			for(String tmp : teamData.getStringList(path+".members")) {
				members.add(Bukkit.getOfflinePlayer(UUID.fromString(tmp)));
			}
			for(String tmp : teamData.getStringList(path+".invites")) {
				invites.add(Bukkit.getOfflinePlayer(UUID.fromString(tmp)));
			}
		}
	}
	
	public void saveValues() {
		String path = "teams."+id;
		List<String> tmpList = new ArrayList<String>();
		List<String> tmpList2 = new ArrayList<String>();
		teamData.set(path+".leader", leader.getUniqueId().toString());
		for(OfflinePlayer tmp : members) {
			tmpList.add(tmp.getUniqueId().toString());
		}
		for(OfflinePlayer tmp : invites) {
			tmpList2.add(tmp.getUniqueId().toString());
		}
		teamData.set(path+".members", tmpList);
		teamData.set(path+".invites", tmpList2);
		teamData.set(path+".colour", colour);
		CactusSMP.singleton.saveTeamFile();
	}
	
	public void remove() {
		for(Player tmp : getOnlinePlayers()) {
			tmp.sendMessage("§7» §cYour team has been abandoned.");
			new SPlayer(tmp).setTeamChat(false);
		}
		Bukkit.getLogger().log(Level.INFO, "Team "+this.id+" ("+members.size()+" members) has been deleted.");
		teamData.set("teams."+this.id, null);
		CactusSMP.singleton.saveTeamFile();
	}
	
	public void sendInvite(Player inviter, Player target) {
		if(Bukkit.getPlayer(target.getUniqueId()) == null) return;
		for(Player p : getOnlinePlayers()) {
			p.sendMessage("§7» "+ChatColor.of(new SPlayer(target).getColour()) + target.getName() + " §r§7has been invited to your team.");
		}
		target.sendMessage("\n§7» "+ChatColor.of(new SPlayer(inviter).getColour()) + inviter.getName() + "§r §7has invited you to join team "+ChatColor.of(colour)+id+"§7.");
		TextComponent msg = new TextComponent("§7» §2Click here to join§7.\n ");
		msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team join "+getId()));
		target.spigot().sendMessage(msg);
		invites.add(target);
		saveValues();
	}
	
	public void join(Player player) {
		for(Player tmp : getOnlinePlayers()) {
			tmp.sendMessage("§7» "+ChatColor.of(new SPlayer(player).getColour()) + player.getName() + " §r§7has joined your team.");
		}
		player.sendMessage("§7» You have successfully joined " + ChatColor.of(colour)+id+"§7.");
		addMember(player);
		for(String tmp : STeam.playerInvitedToIDs(player)) {
			STeam tmpT = new STeam(tmp);
			tmpT.removeInvite(player, false);
		}
	}
	
	public void leave(Player player) {
		player.sendMessage("§7» You have left your team.");
		removeMember(player);
		for(Player tmp : getOnlinePlayers()) {
			tmp.sendMessage("§7» "+ChatColor.of(new SPlayer(player).getColour()) + player.getName() + " §r§7has left your team.");
		}
	}
	
	public void kick(OfflinePlayer player) {
		if(player.isOnline()) {
			((Player) player).sendMessage("§7» You have been kicked from your team.");
		}
		removeMember(player);
		for(Player tmp : getOnlinePlayers()) {
			tmp.sendMessage("§7» "+ChatColor.of(new SPlayer(player).getColour()) + player.getName() + " §r§7has been kicked from your team.");
		}
	}
	
	public void removeInvite(OfflinePlayer target, boolean notify) {
		if(target.isOnline() && notify) {
			((Player) target).sendMessage("§7» Your invite to "+ChatColor.of(colour)+id+"§7 has been revoked.");
		}
		List<String> tmp = new ArrayList<String>(); 
		for(String str : teamData.getStringList("teams."+id+".invites")) if(!target.getUniqueId().toString().equalsIgnoreCase(str)) tmp.add(str);
		teamData.set("teams."+id+".invites", tmp);
		CactusSMP.singleton.saveTeamFile();
		loadFromFile(id);
	}
	
	public void removeMember(OfflinePlayer member) {
		List<String> tmp = new ArrayList<String>(); 
		for(String str : teamData.getStringList("teams."+id+".members")) if(!member.getUniqueId().toString().equalsIgnoreCase(str)) tmp.add(str);
		teamData.set("teams."+id+".members", tmp);
		CactusSMP.singleton.saveTeamFile();
		loadFromFile(id);
	}
	
	public List<Player> getOnlinePlayers() {
		List<Player> players = new ArrayList<Player>();
		for(OfflinePlayer tmp : members) {
			if(Bukkit.getPlayer(tmp.getUniqueId()) != null) players.add(tmp.getPlayer());
		}
		return players;
	}
	
	public OfflinePlayer getLeader() {return leader;}
	public Set<OfflinePlayer> getMembers(){return members;}
	public Set<OfflinePlayer> getInvites(){return invites;}
	public String getId() {return id;}
	public String getColour() {return colour;}
	
	public Set<OfflinePlayer> getMembersIgnoreLeader() {
		Set<OfflinePlayer> tmp = members;
		tmp.remove(leader);
		return tmp;
	}

	public void setLeader(OfflinePlayer leader) {this.leader = leader; saveValues();}
	public void addMember(OfflinePlayer member) {this.members.add(member); saveValues();}
	public void setId(String id) {this.id = id; saveValues();}
	public void setColour(String colour) {this.colour = colour; saveValues();}
	
	void loadFromFile(String id) {
		String idLC = id.toLowerCase();
		if(teamData.getConfigurationSection("teams") == null) return;
		Map<String, String> caseMap = new HashMap<String, String>();
		Object[] teams = teamData.getConfigurationSection("teams").getKeys(false).toArray();
		for(Object tmp : teams) {
			String tmpS = (String) tmp;
			caseMap.put(tmpS.toLowerCase(), tmpS);
		}
		if(caseMap.containsKey(idLC)) {
			String path = "teams."+caseMap.get(idLC);
			colour = teamData.getString(path+".colour");
			id = caseMap.get(idLC);
			leader = Bukkit.getOfflinePlayer(UUID.fromString(teamData.getString(path+".leader")));
			for(String tmp : teamData.getStringList(path+".members")) {
				members.add(Bukkit.getOfflinePlayer(UUID.fromString(tmp)));
			}
			for(String tmp : teamData.getStringList(path+".invites")) {
				invites.add(Bukkit.getOfflinePlayer(UUID.fromString(tmp)));
			}
		}
	}
	
	public static STeam getTeamBelongingToPlayer(OfflinePlayer player) {
		if(teamData.getConfigurationSection("teams") == null) return null;
		Object[] teams = teamData.getConfigurationSection("teams").getKeys(false).toArray();
		for(Object tmp : teams) {
			if(teamData.getStringList("teams."+(String)tmp+".members").contains(player.getUniqueId().toString())) return new STeam((String) tmp);
		}
		return null;
	}
	
	public static List<String> playerInvitedToIDs(Player player){
		List<String> tmpList = new ArrayList<String>();
		if(teamData.getConfigurationSection("teams") == null) return tmpList;
		Object[] teams = teamData.getConfigurationSection("teams").getKeys(false).toArray();
		for(Object tmp : teams) {
			if(teamData.getStringList("teams."+(String)tmp+".invites").contains(player.getUniqueId().toString())) tmpList.add((String)tmp);
		}
		return tmpList;
	}
	
	public static boolean idExists(String id) {
		if(teamData.getConfigurationSection("teams") == null) return false;
		Object[] teams = teamData.getConfigurationSection("teams").getKeys(false).toArray();
		for(Object tmp : teams) {
			if(((String) tmp).equalsIgnoreCase(id)) return true;
		}
		return false;
	}
}
