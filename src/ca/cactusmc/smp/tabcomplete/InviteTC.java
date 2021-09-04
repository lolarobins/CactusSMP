package ca.cactusmc.smp.tabcomplete;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import ca.cactusmc.smp.Invite;

public class InviteTC implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender s, Command c, String l, String[] args) {
		
		Player p = (Player) s;
		
		List<String> completions = new ArrayList<String>();
		if(args.length == 1) {
			completions.add("add");
			completions.add("remove");
		}
		if(args.length == 2 && args[0].equalsIgnoreCase("remove")) {
			for(Invite tmp : Invite.getActiveInvites(p.getUniqueId())) {
				completions.add(tmp.getInvited());
			}
		}
		return completions;
	}

}
