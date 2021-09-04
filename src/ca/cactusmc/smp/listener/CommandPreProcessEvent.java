package ca.cactusmc.smp.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.google.common.collect.ImmutableList;

public class CommandPreProcessEvent implements Listener{
	
	public final ImmutableList<String> ALLOWED_CMDS = ImmutableList.of(
			"/team",
			"/t",
			"/lag",
			"/memory",
			"/tps",
			"/help",
			"/tm",
			"/tell",
			"/w",
			"/msg",
			"/teammsg",
			"/pl",
			"/plugins",
			"/me",
			"/?",
			"/colour",
			"/color",
			"/teamchat",
			"/tc",
			"/suicide",
			"/kill",
			"/links",
			"/list",
			"/online",
			"/stats",
			"/invite",
			"/inv",
			"/commands",
			"/rules",
			"/discord",
			"/whisper",
			"/m",
			"/vote",
			"/donate",
			"/buy",
			"/cactussmp:team",
			"/cactussmp:t",
			"/cactussmp:lag",
			"/cactussmp:memory",
			"/cactussmp:tps",
			"/cactussmp:help",
			"/cactussmp:tm",
			"/cactussmp:tell",
			"/cactussmp:w",
			"/cactussmp:msg",
			"/cactussmp:teammsg",
			"/cactussmp:pl",
			"/cactussmp:plugins",
			"/cactussmp:me",
			"/cactussmp:?",
			"/cactussmp:colour",
			"/cactussmp:color",
			"/cactussmp:teamchat",
			"/cactussmp:tc",
			"/cactussmp:suicide",
			"/cactussmp:kill",
			"/cactussmp:links",
			"/cactussmp:list",
			"/cactussmp:online",
			"/cactussmp:stats",
			"/cactussmp:invite",
			"/cactussmp:inv",
			"/cactussmp:commands",
			"/cactussmp:rules",
			"/discordsrv:discord",
			"/cactussmp:whisper",
			"/cactussmp:m",
			"/cactussmp:vote",
			"/cactussmp:donate",
			"/cactussmp:buy");
	public final ImmutableList<String> OVERRIDED = ImmutableList.of(
			"/tps",
			"/help",
			"/tm",
			"/tell",
			"/w",
			"/msg",
			"/teammsg",
			"/pl",
			"/plugins",
			"/me",
			"/?",
			"/kill",
			"/list");
	
	@EventHandler
	public void preProcessEvent(PlayerCommandPreprocessEvent e) {
		
		Player p = e.getPlayer();
		String cmd = e.getMessage().split(" ")[0].toLowerCase();
		
		if(OVERRIDED.contains(cmd)) {
			e.setMessage("/cactussmp:"+e.getMessage().replaceFirst("/", ""));
		}
		
		if(p.isOp()) return;
		
		if(!ALLOWED_CMDS.contains(cmd)) {
			p.sendMessage("§7» §cYou do not have permission to use this command.");
			e.setCancelled(true);
		}
	}
}
