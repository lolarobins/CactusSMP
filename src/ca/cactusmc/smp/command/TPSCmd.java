package ca.cactusmc.smp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.cactusmc.smp.HUD;
import net.minecraft.server.MinecraftServer;

public class TPSCmd implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		String msg = "§7» §6TPS & Lag Information:"+"\n§7» §3Current TPS: §b"+MinecraftServer.TPS+"\n§7» §3Memory Usage: §b"+HUD.getMemoryPrecent();
		if(s instanceof Player) {
			msg=msg+"\n§7» §3Client Ping: §b"+((Player) s).getPing()+" ms";
		}
		s.sendMessage(msg);
		return true;
	}
	
}
