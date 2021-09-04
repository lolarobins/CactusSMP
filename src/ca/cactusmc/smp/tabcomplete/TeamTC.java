package ca.cactusmc.smp.tabcomplete;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import ca.cactusmc.smp.SPlayer;
import ca.cactusmc.smp.STeam;

public class TeamTC implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender s, Command c, String l, String[] args) {
		
		List<String> completions = new ArrayList<String>();
		Player p = null;
		STeam t = null;
		
		if(s instanceof Player) {
			p = (Player) s;
			t = new SPlayer(p).getTeam();
		
		}
		switch(args.length) {
		case 1:
			completions.add("create");
			completions.add("leave");
			completions.add("colour");
			completions.add("invite");
			completions.add("join");
			completions.add("kick");
			break;
		case 2:
			switch(args[0].toLowerCase()) {
			case "invite":
				if(t == null) break;
				if(t.getLeader() != p) break;
				return null;
			case "create":
				completions.add("<name>");
				break;
			case "colour":
				completions.add("<#hexcode>");
				break;
			case "kick":
				if(t == null) break;
				if(t.getLeader() != p) break;
				for(OfflinePlayer tmp : t.getMembersIgnoreLeader()) {
					completions.add(tmp.getName());
				}
				break;
			case "join":
				if(t != null) break;
				completions.addAll(STeam.playerInvitedToIDs(p));
				break;
			}
			break;
		}
		
		return completions;
	}
}
