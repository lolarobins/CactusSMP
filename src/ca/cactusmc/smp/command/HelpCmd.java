package ca.cactusmc.smp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.ImmutableList;

public class HelpCmd implements CommandExecutor {

	final ImmutableList<String> HELP_LIST = ImmutableList.of(
			"§3/colour:§f Change the colour of your name.",
			"§3/discord:§f Link your discord account for proximity chat.",
			"§3/donate:§f Donate to the server or view the expiration time of your perks.",
			"§3/help:§f View available server commands.",
			"§3/invite:§f Invite another player to the server.",
			"§3/links:§f View links for the server's Dynmap, and Discord.",
			"§3/list:§f Shows a list of online players.",
			"§3/me:§f Describe an action.",
			"§3/msg:§f Send a message to another online player.",
			"§3/plugins:§f View a list of server plugins.",
			"§3/rules:§f List of rules to be followed.",
			"§3/stats:§f Displays server-side player statistics.",
			"§3/suicide:§f Kills your player without server-side statistics being affected.",
			"§3/team:§f Manage and join teams.",
			"§3/teamchat:§f Toggle team chat mode.",
			"§3/teammsg:§f Send a message to your team.",
			"§3/tps:§f View info on server TPS, memory, and client ping.",
			"§3/vote:§f View a list of websites to vote for the server."
			);
	
	final int COMMANDS_PER_PAGE = 6;
	final int TOTAL_PAGES = (int) Math.ceil(HELP_LIST.size()/COMMANDS_PER_PAGE)+1;
	
	String getHelpAtIndex(int index) {
		index--;
		String cmds = "";
		for(int i = 0; i<COMMANDS_PER_PAGE; i++) {
			if(HELP_LIST.size() > i+index*COMMANDS_PER_PAGE) cmds = cmds+"§7» §r"+HELP_LIST.get(i+index*COMMANDS_PER_PAGE)+"\n";
		}
		return cmds;
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if(args.length == 0) {
			sendHelp(s, 1);
			return true;
		}
		try{
			int number = Integer.parseInt(args[0]);
			sendHelp(s, number);
		}catch(NumberFormatException e) {
			s.sendMessage("§7» §cError: Invalid page number.");
		}catch(ArrayIndexOutOfBoundsException e) {
			s.sendMessage("§7» §cError: Invalid page number.");
		}
		
		return true;
	}
	
	void sendHelp(CommandSender s, int index) {
		s.sendMessage("§7» §6Showing server commands, page "+index+" of "+TOTAL_PAGES+":\n"+getHelpAtIndex(index));
	}
}
