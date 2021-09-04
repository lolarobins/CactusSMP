package ca.cactusmc.smp;

import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

public class Whitelist {
	
	static FileConfiguration whitelist = CactusSMP.singleton.getWhitelistFile();
	
	public static boolean contains(UUID player) {
		if(whitelist.getStringList("whitelist").contains(player.toString())) return true;
		return false;
	}
	
	public static void add(UUID player) {
		List<String> wl = whitelist.getStringList("whitelist");
		wl.add(player.toString());
		whitelist.set("whitelist", wl);
		CactusSMP.singleton.saveWhitelistFile();
	}
	
	public static void remove(UUID player) {
		List<String> wl = whitelist.getStringList("whitelist");
		wl.remove(player.toString());
		whitelist.set("whitelist", wl);
		CactusSMP.singleton.saveWhitelistFile();
	}
}
