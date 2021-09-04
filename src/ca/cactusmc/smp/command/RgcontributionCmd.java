package ca.cactusmc.smp.command;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.cactusmc.smp.SPlayer;
import net.md_5.bungee.api.ChatColor;

public class RgcontributionCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender c, Command s, String l, String[] args) {
		if(s instanceof Player) return true;
		try {
			UUID uuid = UUID.fromString(args[0]);
			SPlayer sp = new SPlayer(Bukkit.getOfflinePlayer(uuid));
			Bukkit.broadcastMessage("§7» "+ChatColor.of(sp.getColour())+sp.getOfflinePlayer().getName()+ " §ahas made a contribution to the server.");
		}catch(IllegalArgumentException e) {
			Bukkit.broadcastMessage("§7» §aAn unknown player has made a contribution to the server");
		}catch(NullPointerException e) {
			Bukkit.broadcastMessage("§7» §aAn unknown player has made a contribution to the server");
		}
		return true;
	}

}
