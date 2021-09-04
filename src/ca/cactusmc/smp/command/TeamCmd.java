package ca.cactusmc.smp.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.cactusmc.smp.SPlayer;
import ca.cactusmc.smp.STeam;
import net.md_5.bungee.api.ChatColor;

public class TeamCmd implements CommandExecutor {
	
	public static final Pattern HEXPATTERN = Pattern.compile("^#([a-fA-F0-9]{6})$");
	
	public static boolean validHexCode(final String colorCode) {
        Matcher matcher = HEXPATTERN.matcher(colorCode);
        return matcher.matches();
    }
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if(!(s instanceof Player)) {
			s.sendMessage("Must be a player to execute command.");
			return true;
		}
		Player p = (Player) s;
		SPlayer sp = new SPlayer(p);
		STeam t = sp.getTeam();
		if(args.length == 0) {
			teamInfoSubCmd(s, t);
			return true;
		}
		switch(args[0].toLowerCase()) {
		case "create":
			createSubCmd(s, p, t, args);
			return true;
		case "c":
			createSubCmd(s,p,t,args);
			return true;
		case "leave":
			leaveSubCmd(s, p, t);
			return true;
		case "invite":
			inviteSubCmd(s, p, t, args);
			return true;
		case "inv":
			inviteSubCmd(s,p,t,args);
			return true;
		case "join":
			joinSubCmd(s, p, t, args);
			return true;
		case "kick":
			kickSubCmd(s, p, t, args);
			return true;
		case "colour":
			colourSubCmd(s, p, t, args);
			return true;
		case "color":
			colourSubCmd(s, p, t, args);
			return true;
		default:
			s.sendMessage("§7» §cSubcommand not recognized.");
			return true;
		}
	}
	
	void teamInfoSubCmd(CommandSender s, STeam t) {
		if(t == null) {
			s.sendMessage("§7» §cYou are not in a team.");
			return;
		}
		SPlayer leader = new SPlayer(t.getLeader());
		String msg = "§7» §6Team info for " + ChatColor.of(t.getColour()) + t.getId() + "§r§6:"
			+ "\n";
		if(t.getLeader().isOnline()) {
			msg = msg+"§7» §3Leader: " + ChatColor.of(leader.getColour()) + t.getLeader().getName();
		}else {
			msg = msg+"§7» §3Leader: " + ChatColor.of(leader.getColour()) + ChatColor.STRIKETHROUGH + t.getLeader().getName();
		}
		if(t.getMembersIgnoreLeader().size() != 0) msg = msg+"\n§7» §3Members: ";
		int index = 0;
		for(OfflinePlayer tmp : t.getMembersIgnoreLeader()) {
			SPlayer member = new SPlayer(tmp);
			String addedName;
			index++;
			addedName = tmp.getName();
			if(!tmp.isOnline()) {
				addedName = "§8" + addedName;
			}else {
				addedName = ChatColor.of(member.getColour()) + addedName;
			}
			if(index == t.getMembersIgnoreLeader().size()) {
				addedName = addedName+ChatColor.RESET+"§7";
			}else {
				addedName = addedName+ChatColor.RESET+"§7, ";
			}
			msg = msg + addedName;
		}
		if(t.getInvites().size() != 0) msg = msg+"\n§7» §3Invites: ";
		index = 0;
		for(OfflinePlayer tmp : t.getInvites()) {
			SPlayer member = new SPlayer(tmp);
			String addedName;
			index++;
			addedName = tmp.getName();
			if(!tmp.isOnline()) {
				addedName = "§8" + addedName;
			}else {
				addedName = ChatColor.of(member.getColour()) + addedName;
			}
			if(index == t.getInvites().size()) {
				addedName = addedName+ChatColor.RESET+"§7";
			}else {
				addedName = addedName+ChatColor.RESET+"§7, ";
			}
			msg = msg + addedName;
		}
		s.sendMessage(msg);
	}
	
	void colourSubCmd(CommandSender s, Player p, STeam t, String[] args) {
		if(t == null) {
			s.sendMessage("§7» §cYou are not in a team.");
			return;
		}
		if(t.getLeader().getUniqueId() != p.getUniqueId()) {
			s.sendMessage("§7» §cYou must be the leader of a team to set a colour.");
			return;
		}
		if(!new SPlayer(p).isDonator()) {
			s.sendMessage("§7» §cYou must donate to the server to change your team's colour.");
			return;
		}
		if(args.length == 1) {
			s.sendMessage("§7» §cPlease specify a colour.");
			return;
		}
		if(!validHexCode(args[1])) {
			s.sendMessage("§7» §cSpecified hex code is invalid. Hex code must follow format #000000.");
			return;
		}
		s.sendMessage("§7» §aTeam colour set to §r"+ChatColor.of(args[1])+args[1].toUpperCase()+"§r§a.");
		t.setColour(args[1]);
	}
	
	void joinSubCmd(CommandSender s, Player p, STeam t, String[] args) {
		if(t != null) {
			s.sendMessage("§7» §cYou are already in a team.");
			return;
		}
		if(args.length == 1) {
			s.sendMessage("§7» §cPlease specify a team to join.");
			return;
		}
		if(!STeam.idExists(args[1])) {
			s.sendMessage("§7» §cSpecified team does not exist.");
			return;
		}
		STeam targTeam = new STeam(args[1]);
		if(targTeam.getInvites().contains(p)) {
			targTeam.join(p);
			return;
		}
		s.sendMessage("§7» §cYou must receive an invitation to join this team.");
		return;
	}
	
	void leaveSubCmd(CommandSender s, Player p, STeam t) {
		if(t == null) {
			s.sendMessage("§7» §cYou are not in a team.");
			return;
		}
		if(t.getLeader().getUniqueId() == p.getUniqueId()) {
			t.remove();
			return;
		}else {
			t.leave(p);
			return;
		}
	}
	
	void kickSubCmd(CommandSender s, Player p, STeam t, String[] args) {
		if(t == null) {
			s.sendMessage("§7» §cYou are not in a team.");
			return;
		}
		if(t.getLeader().getUniqueId() != p.getUniqueId()) {
			s.sendMessage("§7» §cYou must be the leader of a team to kick members.");
			return;
		}
		if(args.length == 1) {
			s.sendMessage("§7» §cPlease specify a player to kick.");
			return;
		}
		for(OfflinePlayer tmp : t.getMembersIgnoreLeader()) {
			if(tmp.getName().equalsIgnoreCase(args[1])) {
				t.kick(tmp);
				return;
			}
		}
		s.sendMessage("§7» §cCould not find specified player.");
		return;
	}
	
	void createSubCmd(CommandSender s, Player p, STeam t, String[] args) {
		if(t != null) {
			s.sendMessage("§7» §cYou must leave your current team before creating a new one.");
			return;
		}
		if(args.length == 1) {
			s.sendMessage("§7» §cPlease specify a name for your team.");
			return;
		}
		if(STeam.idExists(args[1])) {
			s.sendMessage("§7» §cTeam name has already already taken.");
			return;
		}
		if(!args[1].matches("^[a-zA-Z0-9_]+$")) {
			s.sendMessage("§7» §cTeam names must be alphanumeric with the exception of underscores.");
			return;
		}
		STeam newTeam = new STeam(args[1], (OfflinePlayer) p, true);
		newTeam.saveValues();
		s.sendMessage("§7» §aTeam " + ChatColor.of(newTeam.getColour()) + newTeam.getId() + "§r§a has been successfully created.");
	}
	
	void inviteSubCmd(CommandSender s, Player p, STeam t, String[] args) {
		if(t == null) {
			s.sendMessage("§7» §cYou are not in a team.");
			return;
		}
		if(t.getLeader().getUniqueId() != p.getUniqueId()) {
			s.sendMessage("§7» §cYou must be the leader of a team to invite players.");
			return;
		}
		if(args.length == 1) {
			s.sendMessage("§7» §cPlease specify a player to invite.");
			return;
		}
		for(OfflinePlayer tmp : t.getInvites()) {
			SPlayer tmpSP = new SPlayer(tmp);
			if(tmp.getName().equalsIgnoreCase(args[1])) {
				t.removeInvite(tmp, true);
				s.sendMessage("§7» §7Invite for "+ChatColor.of(tmpSP.getColour())+tmp.getName()+"§r §7has been removed.");
				return;
			}
		}
		Player tp = Bukkit.getPlayer(args[1]);
		if(tp == null) {
			s.sendMessage("§7» §cCould not find specified player.");
			return;
		}
		SPlayer tsp = new SPlayer(tp);
		if(tsp.getTeam() != null) {
			s.sendMessage("§7» §cPlayer is already in a team.");
			return;
		}
		t.sendInvite(p, tp);
	}
}
