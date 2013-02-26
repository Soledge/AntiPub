package main.java.com.wolfy9247.AntiPub.commands;

import main.java.com.wolfy9247.AntiPub.AntiPub;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ToggleCommand implements APCommand {

	@Override
	public String getPermissionNode() {
		return "ap.toggle";
	}

	@Override
	public String[] getNames() {
		return new String[]{"toggle"};
	}

	@Override
	public String getSyntax() {
		return "toggle <[IPv4|domain]> <[on|off|alerts]>";
	}

	/* Todo: Hook in with permissions to change antipub.notify to false  
	 * for invidual users to toggle their notifications."
	 */
	@Override
	public void execute(AntiPub plugin, CommandSender sender, String[] args) {
		FileConfiguration config = plugin.getConfig();
		
		if(args[1].isEmpty()) {
			sender.sendMessage(ChatColor.RED + "[AntiPub] Toggle failed: You must specify either 'ipv4' or 'domain'");
		}
		
		else if(args[0].equalsIgnoreCase("ipv4") && sender.hasPermission("ap.toggle.ipv4")) {
			ConfigurationSection section = config.getConfigurationSection("AntiPub.IPv4");

			switch(args[1]) {
				case "on":  	section.set("Block IPv4", true);
								sender.sendMessage(ChatColor.GREEN + "[AntiPub] IPv4 blocking has been turned on.");
								break;
				case "off": 	section.set("Block IPv4", false);
								sender.sendMessage(ChatColor.RED + "[AntiPub] IPv4 blocking has been turned off.");
								break;
				/* Notice: This will only toggle user alerts. Admin alerts can only be disabled
				 * from the config.yml file.
				 */
				case "alerts":  switch(args[2]) {
									case "on": 	section.set("Alert User", true);
												sender.sendMessage(ChatColor.GREEN + 
														"[AntiPub] User alerts have been turned on.");
												break;
									case "off": section.set("Alert User", false);
												sender.sendMessage(ChatColor.RED + 
														"[AntiPub] User alerts have been turned off.");
												break;
									default:	sender.sendMessage(ChatColor.RED + 
														"[AntiPub] You must specify either 'on' or 'off'");
												break;	 
								}
				default:	   sender.sendMessage(ChatColor.RED + "[AntiPub] You must specify either 'on' or 'off'");
							   break;	   
			}
		}
		else if(args[0].equalsIgnoreCase("domain") && sender.hasPermission("ap.toggle.domain")) {
			ConfigurationSection section = config.getConfigurationSection("AntiPub.Domains");

			switch(args[1]) {
				case "on":  	section.set("Block Domains", true);
								sender.sendMessage(ChatColor.GREEN + "[AntiPub] Domain blocking has been turned on.");
								break;
				case "off": 	section.set("Block Domains", false);
								sender.sendMessage(ChatColor.RED + "[AntiPub] Domain blocking has been turned off.");
								break;
								
				/* Notice: This will only toggle user alerts. Admin alerts can only be disabled
				 * from the config.yml file.
				 */
				case "alerts":  switch(args[2]) {
									case "on": 	section.set("Alert User", true);
												sender.sendMessage(ChatColor.GREEN + 
														"[AntiPub] User alerts have been turned on.");
												break;
									case "off": section.set("Alert User", false);
												sender.sendMessage(ChatColor.RED + 
														"[AntiPub] User alerts have been turned off.");
												break;
									default:	sender.sendMessage(ChatColor.RED + 
														"[AntiPub] You must specify either 'on' or 'off'");
												break; 
								}
				default:	  sender.sendMessage(ChatColor.RED + "[AntiPub] You must specify either 'on' or 'off'");
							  break;	   
			}
		}
		else {
			sender.sendMessage(ChatColor.RED + "[AntiPub] Sorry, either the command does not exist or you do not have permission for this operation.");
		}

	}
}