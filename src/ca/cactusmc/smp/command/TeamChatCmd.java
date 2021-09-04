package ca.cactusmc.smp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.cactusmc.smp.SPlayer;
import ca.cactusmc.smp.STeam;

public class TeamChatCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
	
		if(!(s instanceof Player)) {
			s.sendMessage("Must be a player to execute command.");
			return true;
		}
		
		Player p = (Player) s;
		SPlayer sp = new SPlayer(p);
		STeam t = sp.getTeam();
		
		if(t == null) {
			s.sendMessage("§7» §cYou are not in a team.");
			return true;
		}
		
		if(sp.inTeamChat()) {
			s.sendMessage("§7» §7Team chat §cdisabled§7.");
			sp.setTeamChat(false);
		}else {
			s.sendMessage("§7» §7Team chat §aenabled§7.");
			sp.setTeamChat(true);
		}
		
		return true;
	}

}
