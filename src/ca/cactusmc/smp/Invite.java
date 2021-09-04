package ca.cactusmc.smp;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

public class Invite {
	
	static FileConfiguration inviteFile = CactusSMP.singleton.getInviteFile();
	String invited;
	UUID inviter;
	long timestamp;
	
	public Invite(String invited, UUID inviter) {
		timestamp = System.currentTimeMillis();
		this.invited = invited.toLowerCase();
		this.inviter = inviter;
		saveValues();
	}
	
	public Invite(String invited) {
		String path = "invites."+invited.toLowerCase();
		if(!inviteFile.contains(path)) return;
		timestamp = inviteFile.getLong(path+".timestamp");
		inviter = UUID.fromString(inviteFile.getString(path+".inviter"));
		this.invited = invited.toLowerCase();
	}
	
	public long getTimestamp() {return timestamp;}
	public String getInvited() {return invited;}
	public UUID getInviter() {return inviter;}
	
	void saveValues() {
		String path = "invites."+invited.toLowerCase();
		inviteFile.set(path+".timestamp", timestamp);
		inviteFile.set(path+".inviter", inviter.toString());
		CactusSMP.singleton.saveInviteFile();
	}
	
	public void remove() {
		String path = "invites."+invited.toLowerCase();
		inviteFile.set(path, null);
		CactusSMP.singleton.saveInviteFile();
	}
	
	public static HashSet<Invite> getActiveInvites(){
		HashSet<Invite> tmp = new HashSet<Invite>();
		if(inviteFile.getConfigurationSection("invites") == null) return tmp;
		Object[] invs = inviteFile.getConfigurationSection("invites").getKeys(false).toArray();
		for(Object invtmp : invs) {
			tmp.add(new Invite((String) invtmp));
		}
		return tmp;
	}
	
	public static HashSet<Invite> getActiveInvites(UUID inviter){
		HashSet<Invite> tmp = new HashSet<Invite>();
		for(Invite invtmp : getActiveInvites()) {
			if(invtmp.getInviter().equals(inviter)) tmp.add(invtmp);
		}
		return tmp;
	}
}
