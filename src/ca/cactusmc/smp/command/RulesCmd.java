package ca.cactusmc.smp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.ImmutableList;

public class RulesCmd implements CommandExecutor {

	final ImmutableList<String> RULES_LIST = ImmutableList.of(
			"§31. §fNo clientside modifications or resourcepacks that give your client an advantage over other players are permitted.",
			"§32. §fBlatently edgy humour is unfunny and is also punishable in excess. No racism, sexism, homophobia, etc.",
			"§33. §fDo not harass other server members.",
			"§34. §fGriefing and stealing are discouraged, major instances of griefing will be punished."
			);
	
	final int COMMANDS_PER_PAGE = 5;
	final int TOTAL_PAGES = (int) Math.ceil(RULES_LIST.size()/COMMANDS_PER_PAGE)+1;
	
	String getRulesAtIndex(int index) {
		index--;
		String cmds = "";
		for(int i = 0; i<COMMANDS_PER_PAGE; i++) {
			if(RULES_LIST.size() > i+index*COMMANDS_PER_PAGE) cmds = cmds+"§7» §r"+RULES_LIST.get(i+index*COMMANDS_PER_PAGE)+"\n";
		}
		return cmds;
	}
	
	void sendRules(CommandSender s, int index) {
		s.sendMessage("§7» §6Showing server rules, page "+index+" of "+TOTAL_PAGES+":\n"+getRulesAtIndex(index));
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if(args.length == 0) {
			sendRules(s, 1);
			return true;
		}
		try{
			int number = Integer.parseInt(args[0]);
			sendRules(s, number);
		}catch(NumberFormatException e) {
			s.sendMessage("§7» §cError: Invalid page number.");
		}catch(ArrayIndexOutOfBoundsException e) {
			s.sendMessage("§7» §cError: Invalid page number.");
		}
		return true;
	}

}
