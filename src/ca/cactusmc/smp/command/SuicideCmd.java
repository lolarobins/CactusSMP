package ca.cactusmc.smp.command;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuicideCmd implements CommandExecutor {

	public static HashSet<UUID> suicides = new HashSet<UUID>();
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if(!(s instanceof Player)) return true;
		suicides.add(((Player) s).getUniqueId());
		((Player)s).setHealth(0);
		return true;
	}

}
