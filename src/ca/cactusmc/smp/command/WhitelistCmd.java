package ca.cactusmc.smp.command;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import ca.cactusmc.smp.Whitelist;

public class WhitelistCmd implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if(s.isOp() || s instanceof ConsoleCommandSender) {
			if(args.length == 0) {
				s.sendMessage("Please specify.");
				return true;
			}
			try {
				UUID target = UUID.fromString(args[0]);
				if(Whitelist.contains(target)) {
					Whitelist.remove(target);
					s.sendMessage(args[0] + " removed.");
				}else {
					Whitelist.add(target);
					s.sendMessage(args[0] + " added.");
				}
			}catch(IllegalArgumentException e) {
				s.sendMessage("Invalid UUID.");
			}
		}
		return true;
	}
	
}
