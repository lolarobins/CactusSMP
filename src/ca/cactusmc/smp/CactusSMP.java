package ca.cactusmc.smp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import ca.cactusmc.smp.command.BuyCmd;
import ca.cactusmc.smp.command.ColourCmd;
import ca.cactusmc.smp.command.HelpCmd;
import ca.cactusmc.smp.command.InviteCmd;
import ca.cactusmc.smp.command.LinksCmd;
import ca.cactusmc.smp.command.ListCmd;
import ca.cactusmc.smp.command.MeCmd;
import ca.cactusmc.smp.command.MessageCmd;
import ca.cactusmc.smp.command.PluginsCmd;
import ca.cactusmc.smp.command.RgcontributionCmd;
import ca.cactusmc.smp.command.RgdonationCmd;
import ca.cactusmc.smp.command.RulesCmd;
import ca.cactusmc.smp.command.StatsCmd;
import ca.cactusmc.smp.command.SuicideCmd;
import ca.cactusmc.smp.command.TPSCmd;
import ca.cactusmc.smp.command.TeamChatCmd;
import ca.cactusmc.smp.command.TeamCmd;
import ca.cactusmc.smp.command.TeamMsgCmd;
import ca.cactusmc.smp.command.VoteCmd;
import ca.cactusmc.smp.command.WhitelistCmd;
import ca.cactusmc.smp.listener.ChatEvent;
import ca.cactusmc.smp.listener.CommandPreProcessEvent;
import ca.cactusmc.smp.listener.DeathEvent;
import ca.cactusmc.smp.listener.JoinEvent;
import ca.cactusmc.smp.listener.LeaveEvent;
import ca.cactusmc.smp.listener.PreLoginEvent;
import ca.cactusmc.smp.listener.ServerListEvent;
import ca.cactusmc.smp.listener.VoteEvent;
import ca.cactusmc.smp.tabcomplete.InviteTC;
import ca.cactusmc.smp.tabcomplete.TeamTC;

public class CactusSMP extends JavaPlugin {

	public static CactusSMP singleton;
	
	@Override
	public void onEnable() {
		singleton = this;
		createTeamFile();
		createPlayerFile();
		createInviteFile();
		createWhitelistFile();
		
		Update.runUpdates();
	
		getCommand("team").setExecutor(new TeamCmd());
		getCommand("team").setTabCompleter(new TeamTC());
		getCommand("tps").setExecutor(new TPSCmd());
		getCommand("msg").setExecutor(new MessageCmd());
		getCommand("colour").setExecutor(new ColourCmd());
		getCommand("teammsg").setExecutor(new TeamMsgCmd());
		getCommand("teamchat").setExecutor(new TeamChatCmd());
		getCommand("links").setExecutor(new LinksCmd());
		getCommand("plugins").setExecutor(new PluginsCmd());
		getCommand("me").setExecutor(new MeCmd());
		getCommand("help").setExecutor(new HelpCmd());
		getCommand("wl").setExecutor(new WhitelistCmd());
		getCommand("list").setExecutor(new ListCmd());
		getCommand("invite").setExecutor(new InviteCmd());
		getCommand("invite").setTabCompleter(new InviteTC());
		getCommand("stats").setExecutor(new StatsCmd());
		getCommand("rules").setExecutor(new RulesCmd());
		getCommand("suicide").setExecutor(new SuicideCmd());
		getCommand("vote").setExecutor(new VoteCmd());
		getCommand("buy").setExecutor(new BuyCmd());
		getCommand("rgcontribution").setExecutor(new RgcontributionCmd());
		getCommand("rgdonation").setExecutor(new RgdonationCmd());
		
		getServer().getPluginManager().registerEvents(new ChatEvent(), this);
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new LeaveEvent(), this);
		getServer().getPluginManager().registerEvents(new DeathEvent(), this);
		getServer().getPluginManager().registerEvents(new CommandPreProcessEvent(), this);
		getServer().getPluginManager().registerEvents(new PreLoginEvent(), this);
		getServer().getPluginManager().registerEvents(new ServerListEvent(), this);
		getServer().getPluginManager().registerEvents(new VoteEvent(), this);
	}
	
	@Override
	public void onDisable() {
		singleton = null;
	}
	
	private File teamFile;
    private FileConfiguration teamFileConfig;
	public FileConfiguration getTeamFile() {return teamFileConfig;}
	
	private void createTeamFile() {
        teamFile = new File(getDataFolder(), "data/team.yml");
        if (!teamFile.exists()) {
            teamFile.getParentFile().mkdirs();
            saveResource("data/team.yml", false);
         }
        teamFileConfig= new YamlConfiguration();
        try {
            teamFileConfig.load(teamFile);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
    public void saveTeamFile() {
        if (teamFileConfig == null || teamFile == null) {
            return;
        }
        try {
            getTeamFile().save(teamFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private File playerData;
	private FileConfiguration playerDataConfig;
	public FileConfiguration getPlayerFile() {return playerDataConfig;}
	
    private void createPlayerFile() {
        playerData = new File(getDataFolder(), "data/player.yml");
        if (!playerData.exists()) {
            playerData.getParentFile().mkdirs();
            saveResource("data/player.yml", false);
         }
        playerDataConfig= new YamlConfiguration();
        try {
            playerDataConfig.load(playerData);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void savePlayerFile() {
        if (playerDataConfig == null || playerData == null) {
            return;
        }
        try {
            playerDataConfig.save(playerData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private File inviteData;
   	private FileConfiguration inviteDataConfig;
   	public FileConfiguration getInviteFile() {return inviteDataConfig;}
   	
       private void createInviteFile() {
           inviteData = new File(getDataFolder(), "data/invite.yml");
           if (!inviteData.exists()) {
               inviteData.getParentFile().mkdirs();
               saveResource("data/invite.yml", false);
            }
           inviteDataConfig= new YamlConfiguration();
           try {
               inviteDataConfig.load(inviteData);
           } catch (InvalidConfigurationException e) {
               e.printStackTrace();
           } catch (FileNotFoundException e) {
   			e.printStackTrace();
   		} catch (IOException e) {
   			e.printStackTrace();
   		}
       }
       
       public void saveInviteFile() {
           if (inviteDataConfig == null || inviteData == null) {
               return;
           }
           try {
               inviteDataConfig.save(inviteData);
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       
       private File whitelistData;
      	private FileConfiguration whitelistDataConfig;
      	public FileConfiguration getWhitelistFile() {return whitelistDataConfig;}
      	
          private void createWhitelistFile() {
              whitelistData = new File(getDataFolder(), "data/whitelist.yml");
              if (!whitelistData.exists()) {
                  whitelistData.getParentFile().mkdirs();
                  saveResource("data/whitelist.yml", false);
               }
              whitelistDataConfig= new YamlConfiguration();
              try {
                  whitelistDataConfig.load(whitelistData);
              } catch (InvalidConfigurationException e) {
                  e.printStackTrace();
              } catch (FileNotFoundException e) {
      			e.printStackTrace();
      		} catch (IOException e) {
      			e.printStackTrace();
      		}
          }
          
          public void saveWhitelistFile() {
              if (whitelistDataConfig == null || whitelistData == null) {
                  return;
              }
              try {
                  whitelistDataConfig.save(whitelistData);
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
}

