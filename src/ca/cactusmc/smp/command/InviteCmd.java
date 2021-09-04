package ca.cactusmc.smp.command;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.cactusmc.smp.Invite;
import ca.cactusmc.smp.SPlayer;

public class InviteCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if(!(s instanceof Player)) {
			s.sendMessage("Only players may send invites. Use command wl to whitelist.");
			return true;
		}
		Player p = (Player) s;
		if(args.length == 0) {
			if(Invite.getActiveInvites(p.getUniqueId()).size() == 0){
				p.sendMessage("§7» §cYou have no invites to view.");
			}else {
				String msg = "§7» §6Showing your active invites:\n";
				for(Invite invtmp : Invite.getActiveInvites(p.getUniqueId())) {
					msg = msg + "§7» §3"+invtmp.getInvited()+"§7 <"+timeParse(invtmp.getTimestamp())+">\n";
				}
				p.sendMessage(msg);
			}
			return true;
		}
		switch(args[0].toLowerCase()) {
		case "add":
			addCmd(p, args);
			break;
		case "remove":
			removeCmd(p, args);
			break;
		default:
			p.sendMessage("§7» §cSubcommand not recognized.");
			break;
		}
		return true;
	}
	
	void removeCmd(Player p, String[] args) {
		if(args.length == 1) {
			p.sendMessage("§7» §cPlease specify a player.");
			return;
		}
		if(!args[1].matches("^\\w{3,16}$")) {
			p.sendMessage("§7» §cSpecified username is invalid.");
			return;
		}
		for(Invite tmp : Invite.getActiveInvites(p.getUniqueId())) {
			if(tmp.getInvited().equalsIgnoreCase(args[1])) {
				tmp.remove();
				p.sendMessage("§7» §aSuccessfully removed invite for "+args[1]+".");
				return;
			}
		}
		p.sendMessage("§7» §cSpecified player could not be found in your invites.");
	}
	
	void addCmd(Player p, String[] args) {
		if(args.length == 1) {
			p.sendMessage("§7» §cPlease specify a player.");
			return;
		}
		if(Invite.getActiveInvites(p.getUniqueId()).size() > 2) {
			p.sendMessage("§7» §cYou may only invite a maximum of 3 players at a time.");
			return;
		}
		if(!args[1].matches("^\\w{3,16}$")) {
			p.sendMessage("§7» §cSpecified username is invalid.");
			return;
		}
		if(new Invite(args[1]).getInviter() != null) {
			p.sendMessage("§7» §cSpecified player has already received an invite.");
			return;
		}
		if(SPlayer.offlinePlayerFromName(args[1]) != null) {
			p.sendMessage("§7» §cSpecified player has already joined the server.");
			return;
		}
		p.sendMessage("§7» §aInvite created for player "+args[1]+".");
		new Invite(args[1], p.getUniqueId());
	}
	
	String timeParse(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("h:mma z, MMM d, yyyy.");
		return sdf.format(Date.from(Instant.ofEpochMilli(time)));
	}

}
