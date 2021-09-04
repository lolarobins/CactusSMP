package ca.cactusmc.smp.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.cactusmc.smp.SPlayer;
import net.md_5.bungee.api.ChatColor;

public class MessageCmd implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		
		if(args.length == 0) {
			s.sendMessage("§7» §cPlease specify a player.");
			return true;
		
		}
		
		Player t = Bukkit.getPlayer(args[0]);
		
		if(t == null) {
			s.sendMessage("§7» §cPlayer not recognized.");
			return true;
		}
		
		if(args.length == 1) {
			s.sendMessage("§7» §cPlease enter a message.");
			return true;
		}
		
		String msgToSend = "";
		args[0] = "";
		
		for(String tmp : args) {
			msgToSend = msgToSend + tmp + " ";
		}
		
		String senderColour = "";
		Player p = null;
		
		if(!(s instanceof Player)) {
			senderColour = "#FF0000";
		}else {
			p = (Player) s;
			senderColour = new SPlayer(p).getColour();
		}
		
		String format = ChatColor.of(senderColour) + s.getName()+" §r§7>§r "+ChatColor.of(new SPlayer(t).getColour()) +t.getName() + "§r§8:§r"+msgToSend;
		s.sendMessage(format);
		t.sendMessage(format);
		
		return true;
	}

}
