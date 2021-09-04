package ca.cactusmc.smp.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.cactusmc.smp.SPlayer;
import net.md_5.bungee.api.ChatColor;

public class MeCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if(args.length == 0) {
			s.sendMessage("§7» §cPlease enter a message.");
			return true;
		}
		String colour;
		if(!(s instanceof Player)) {
			colour = "#FF0000";
		}else {
			colour = new SPlayer((Player) s).getColour();
		}
		String msg = "";
		for(String tmp : args) {
			msg = msg+tmp+" ";
		}
		Bukkit.broadcastMessage(ChatColor.of(colour)+"* "+s.getName() + " "+msg+"*");
		return true;
	}

}
