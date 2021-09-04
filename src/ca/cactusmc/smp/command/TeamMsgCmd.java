package ca.cactusmc.smp.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.cactusmc.smp.SPlayer;
import ca.cactusmc.smp.STeam;
import net.md_5.bungee.api.ChatColor;

public class TeamMsgCmd implements CommandExecutor {

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
		
		if(args.length == 0) {
			s.sendMessage("§7» §cPlease enter a message.");
			return true;
		}
		
		String msg = "";
		
		for(String tmp : args) {
			msg=msg+tmp+" ";
		}
		
		String formatted = ChatColor.of(t.getColour()) + "Team §8»" + ChatColor.RESET + " " + ChatColor.of(sp.getColour()) + p.getName() + ChatColor.RESET + "§8: " + ChatColor.RESET + msg;
		
		for(Player tmp : Bukkit.getOnlinePlayers()) {
			if(new SPlayer(tmp).getTeam().getId().equalsIgnoreCase(t.getId())) tmp.sendMessage(formatted);
		}
		
		return true;
	}

}
