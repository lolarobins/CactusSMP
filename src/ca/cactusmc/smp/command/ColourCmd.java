package ca.cactusmc.smp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.cactusmc.smp.RandomColour;
import ca.cactusmc.smp.SPlayer;
import net.md_5.bungee.api.ChatColor;

public class ColourCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		
		if(!(s instanceof Player)) {
			s.sendMessage("Must be a player to execute command.");
			return true;
		}
		
		Player p = (Player) s;
		SPlayer sp = new SPlayer(p);
		
		if(sp.isDonator() && args.length != 0) {
			if(!TeamCmd.validHexCode(args[0])) {
				s.sendMessage("§7» §cSpecified hex code is invalid. Hex code must follow format #000000.");
			}else {
				sp.setColour(args[0]);
				s.sendMessage("§7» Your colour has been changed to "+ChatColor.of(sp.getColour())+sp.getColour()+"§r.");
				p.setPlayerListName(ChatColor.of(sp.getColour())+p.getName());
			}
			return true;
		}
		
		sp.setColour(new RandomColour().getHex());
		s.sendMessage("§7» Your colour has been randomly changed to "+ChatColor.of(sp.getColour())+sp.getColour()+"§r.");
		p.setPlayerListName(ChatColor.of(sp.getColour())+p.getName());
		
		return true;
	}

}
