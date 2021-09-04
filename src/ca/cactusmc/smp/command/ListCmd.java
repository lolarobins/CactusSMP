package ca.cactusmc.smp.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.cactusmc.smp.SPlayer;
import net.md_5.bungee.api.ChatColor;

public class ListCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		String msg = "§7» §6Showing online players ("+Bukkit.getOnlinePlayers().size()+" of "+Bukkit.getMaxPlayers()+"):\n";
		int index = 0;
		for(Player tmp : Bukkit.getOnlinePlayers()) {
			SPlayer member = new SPlayer(tmp);
			String addedName;
			index++;
			addedName = ChatColor.of(member.getColour()) + tmp.getName();
			if(index == Bukkit.getOnlinePlayers().size()) {
				addedName = addedName+ChatColor.RESET+"§7";
			}else {
				addedName = addedName+ChatColor.RESET+"§7, ";
			}
			msg = msg + addedName;
		}
		s.sendMessage(msg);
		return true;
	}
	
}
