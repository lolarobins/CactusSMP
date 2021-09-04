package ca.cactusmc.smp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PluginsCmd implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		
		String msg = "§7» §6CactusMC SMP Plugins:"
				+ "\n§7» §3CactusSMP: §fHandles majority of server's custom features."
				+ "\n§7» §3Dynmap: §fWeb based live server flat and isometric view map of the SMP world."
				+ "\n§7» §3DiscordSRV: §fDiscord proximity voice chat.";
		s.sendMessage(msg);
		
		return true;
	}

}
