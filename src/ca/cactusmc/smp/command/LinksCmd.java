package ca.cactusmc.smp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LinksCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		
		String msg = "§7» §6CactusMC SMP Links:"
				+ "\n§7» §3Dynmap: §fhttp://smp.cactusmc.ca:8123/"
				+ "\n§7» §3Discord: §fhttp://discord.cactusmc.ca/"
				+ "";
		s.sendMessage(msg);
		
		return true;
	}

}
