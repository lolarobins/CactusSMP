package ca.cactusmc.smp.command;

import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.cactusmc.smp.Donation;
import ca.cactusmc.smp.SPlayer;
import net.md_5.bungee.api.ChatColor;

public class RgdonationCmd implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender c, Command s, String l, String[] args) {
		if(s instanceof Player) return true;
		try {
			int days = Integer.parseInt(args[0]);
			UUID uuid = UUID.fromString(args[1]);
			SPlayer sp = new SPlayer(Bukkit.getOfflinePlayer(uuid));
			if(Bukkit.getOfflinePlayer(uuid) == null) {
				Bukkit.getServer().getLogger().log(Level.SEVERE, "Player not found. Failed to register donation for "+uuid);
				return true;
			}
			Donation d = new Donation(sp);
			d.addTime(days);
			Bukkit.broadcastMessage("§7» "+ChatColor.of(sp.getColour())+sp.getOfflinePlayer().getName()+ " §ahas purchased "+days+" days of donator.");
		}catch(IllegalArgumentException e) {
			Bukkit.getServer().getLogger().log(Level.SEVERE, "FAILED TO PARSE DONATION FOR "+args[1]);
			e.printStackTrace();
		}
		
		return true;
	}
}
