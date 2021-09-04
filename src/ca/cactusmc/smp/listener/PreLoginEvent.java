package ca.cactusmc.smp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import ca.cactusmc.smp.Invite;
import ca.cactusmc.smp.Whitelist;

public class PreLoginEvent implements Listener {

	@EventHandler
	public void preLogin(AsyncPlayerPreLoginEvent e) {
		if(new Invite(e.getName()).getInviter() == null && !Whitelist.contains(e.getUniqueId())) {
			e.disallow(Result.KICK_WHITELIST, "§cYou are not whitelisted on this server.\n \n§cPlease apply @ whitelist.cactusmc.ca,\n§cor have a friend use '§o/invite "+e.getName()+"§c' in game.\n \n§cFor additional help, join our Discord @ discord.cactusmc.ca.");
		}
	}
	
}
