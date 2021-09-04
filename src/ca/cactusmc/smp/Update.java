package ca.cactusmc.smp;

import org.bukkit.Bukkit;

public class Update {
	
	public static void runUpdates() {
		run2sec();
		run10sec();
		run1min();
	}
	
	private static void run2sec() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(CactusSMP.singleton, new Runnable() {
			@Override
			public void run() {
				HUD.updatePing();
				HUD.updateHeaderFooter();
			}
		}, 10, 40);
	}
	
	private static void run10sec() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(CactusSMP.singleton, new Runnable() {
			@Override
			public void run() {
				HUD.incrementHeaderFooter();
			}
		}, 10, 200);
	}
	
	private static void run1min() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(CactusSMP.singleton, new Runnable() {
			@Override
			public void run() {
				Donation.checkOnline();
			}
		}, 10, 1200);
	}

}
