package ca.cactusmc.smp.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import ca.cactusmc.smp.SPlayer;
import ca.cactusmc.smp.command.SuicideCmd;

public class DeathEvent implements Listener {
	
	@EventHandler
	public void playerDeath(PlayerDeathEvent e) {
		
		Player p = e.getEntity();
		SPlayer sp = new SPlayer(p);
		
		if(SuicideCmd.suicides.contains(p.getUniqueId())) {
			e.setDeathMessage(p.getName() + " took their own life.");
			SuicideCmd.suicides.remove(p.getUniqueId());
			return;
		}
		
		sp.addTotalDeath();
		
		if(p.getKiller() != null) {
			Player k = p.getKiller();
			SPlayer sk = new SPlayer(k);
			sk.addKill();
			sp.addPlayerDeath();
		}
		
	}
}
