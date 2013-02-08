package com.wolfy9247.AntiPub;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static Main instance;
	protected static FileConfiguration config;
	
	public final static Logger log = Logger.getLogger("Minecraft");
    public static final String logTag = "[AntiPub] ";
    public static final String pLogTag = ChatColor.GOLD + "[AntiPub] ";
    
	public void loadConfiguration() {
		config = this.getConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(logTag + pdfFile.getName() + " v" + pdfFile.getVersion() + " disabled!");
	}
	
	@Override
	public void onEnable() {
		instance = this;
		PluginDescriptionFile pdfFile = this.getDescription();
		getConfig().options().copyDefaults(true);
		saveConfig();
		getServer().getPluginManager().registerEvents(new AntiPubListener(), this);
		log.info(logTag + pdfFile.getName() + " v" + pdfFile.getVersion() + " enabled!");
	}
	
	public boolean isConsole(CommandSender sender) {
		if(sender.getName() == "CONSOLE")
			return true;
		else return false;
	}
	
	public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("ap") || label.equalsIgnoreCase("antipub")) {
			if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
				if(args.length >= 2) {
					/* @TODO: Make help for each individual command, such as the
					 * syntax, how to use them, etc, etc. 
					 */
					if(isConsole(sender))
						log.info(logTag + "Sorry, this function isn't supported in v0.3 (yet!)");
					else sender.sendMessage(pLogTag + ChatColor.RED + "Sorry, this function isn't supported in v0.3 (yet!)");
					return true;
				}
				sender.sendMessage(pLogTag + ChatColor.BLUE + "==== " + ChatColor.RED + "AntiPub" + ChatColor.BLUE + "Help ====");
				if(sender.hasPermission("antipub.reload"))
					sender.sendMessage(pLogTag + "/ap reload - Reloads the configuration.");
				sender.sendMessage(pLogTag + "/ap help <?command> - Prints the help menu.");
				if(sender.hasPermission("antipub.mod")) {
					sender.sendMessage(pLogTag + "/ap set <args> - Toggles an item in the config.");
				}
				if(sender.hasPermission("antipub.admin")) {
					sender.sendMessage(pLogTag + "/ap exempt <IP | Domain> <args> - Exempt from filters.");
				}
				return true;
			}
			else if(args.length >= 1) {
				if(args[0].equalsIgnoreCase("reload")) {
					if(sender.hasPermission("antipub.reload")) {
						try {
							reloadConfig();
							loadConfiguration();
						}
						catch(NullPointerException npe) {
							log.warning(logTag + "An error has occured!");
							if(sender instanceof Player) {
								if(isConsole(sender)) {
									sender.sendMessage(pLogTag + pLogTag + ChatColor.RED + "A config error has occured. Check the console for more info.");
								}
								npe.printStackTrace();
							}
							return true;
						}
						if(isConsole(sender))
							log.info(logTag + "Configuration sucessfully reloaded!");
						else {
							sender.sendMessage(pLogTag + "Configuration sucessfully reloaded!");
							log.info(logTag + "Configuration sucessfully reloaded by "+sender.getName()+".");
						}
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("set")) {
					if(sender.hasPermission("antipub.mod")) {
						if(args.length >= 2) {
							config = getConfig();
							/* @TODO: Fix the IPv4 and Domains having to be exact, as well
							 * as having to be CaSe Sensitive. 
							 */
							if(config.getConfigurationSection("AntiPub."+args[1]) == null) {
								if(isConsole(sender)) {
									log.info(logTag + "Invalid command arguements: '"+args[1]+"'.");
								}
								else sender.sendMessage(pLogTag + ChatColor.RED + "Invalid command arguements: '"+args[1]+"'.");
								return false;
							}
							ConfigurationSection confSection = config.getConfigurationSection("AntiPub."+args[1]);
							List<String> exemptionList = confSection.getStringList("Exempt "+confSection.getName());
							if(args.length >= 3) {
								if(args[2].equalsIgnoreCase("disabled")) {
									if(!(confSection.getBoolean("Block "+args[1]))) {
										if(isConsole(sender))
											log.info(logTag + confSection.getName() + " filter is already disabled!");
										else sender.sendMessage(pLogTag + ChatColor.RED + confSection.getName() + " filter is already disabled!");
										return true;
									}
									else {
										confSection.set("Block "+args[1], false);
										sender.sendMessage(pLogTag + ChatColor.RED + confSection.getName() + " filter disabled!");
										log.info(logTag + confSection.getName() + " filter disabled by "+sender.getName()+".");
										saveConfig();
										return true;
									}
								}
								else if(args[2].equalsIgnoreCase("enabled")) {
									if(confSection.getBoolean("Block "+args[1])) {
										if(isConsole(sender))
											log.info(logTag + confSection.getName() + " filter is already enabled!");
										else sender.sendMessage(pLogTag + ChatColor.GREEN + confSection.getName() + " filter is already enabled!");
										return true;
									}
									else {
										confSection.set("Block "+args[1], true);
										sender.sendMessage(pLogTag + ChatColor.GREEN + confSection.getName() + " filter enabled!");
										log.info(logTag + confSection.getName() + " filter enabled by "+sender.getName()+".");
										saveConfig();
										return true;
									}
								}
							else if(args.length >= 4) {
								if(args[2].equalsIgnoreCase("alerts")) {
									if(args.length >= 5) {
										if(args[3].equalsIgnoreCase("user")) {
											if(args[4].equalsIgnoreCase("enabled")) {
												if(confSection.getBoolean("Alert User")) {
													if(isConsole(sender))
														log.info(logTag + confSection.getName() +  " user alerts are already enabled!");
													else sender.sendMessage(pLogTag + ChatColor.GREEN + confSection.getName() + " user alerts are already enabled!");
													return true;
												}
												else {
													confSection.set("Alert User", true);
													sender.sendMessage(pLogTag + ChatColor.GREEN + confSection.getName() + " user alerts enabled!");
													log.info(logTag + confSection.getName() + " user alerts enabled by "+sender.getName()+".");
													saveConfig();
													return true;
												}
											}
											else if(args[4].equalsIgnoreCase("disabled")) {
												if(!(confSection.getBoolean("Alert User"))) {
													if(isConsole(sender))
														log.info(logTag + confSection.getName() + " user alerts are already disabled!");
													else sender.sendMessage(pLogTag + ChatColor.RED + confSection.getName() + " user alerts are already disabled!");
													return true;
												}
												else {
													confSection.set("Alert User", false);
													sender.sendMessage(pLogTag + ChatColor.RED + confSection.getName() + " user alerts disabled!");
													log.info(logTag + confSection.getName() + " user alerts disabled by "+sender.getName()+".");
													saveConfig();
													return true;
												}
											}
										}
										else if(args[3].equalsIgnoreCase("admins")) {
											if(args[4].equalsIgnoreCase("enabled")) {
												if(confSection.getBoolean("Alert Admins")) {
													if(isConsole(sender)) 
														log.info(logTag + confSection.getName() + " admin alerts are already enabled!");
													else sender.sendMessage(pLogTag + ChatColor.GREEN + confSection.getName() + " admin Alerts are already enabled!");
												}
												else {
													confSection.set("Alert Admins", true);
													sender.sendMessage(pLogTag + ChatColor.GREEN + confSection.getName() + " admin alerts enabled!");
													log.info(logTag + confSection.getName() + " admin alerts enabled by "+sender.getName()+".");
													return true;
												}
											}
											else if(args[4].equalsIgnoreCase("disabled")) {
												if(!(confSection.getBoolean("Alert Admins"))) {
													if(isConsole(sender)) 
														log.info(logTag + confSection.getName() + " admin alerts are already disabled!");
													else sender.sendMessage(pLogTag + ChatColor.RED + confSection.getName() + " admin alerts are already disabled!");
													return true;
												}
												else {
													confSection.set("Alert Admins", false);
													sender.sendMessage(pLogTag + ChatColor.RED + confSection.getName() + " alerts disabled!");
													log.info(logTag + confSection.getName() + " admin alerts disabled by "+sender.getName()+".");
													return true;
												}
											}
										}
									}
									else if(args.length < 5) {
										if(isConsole(sender))
											log.info(logTag + "Insufficient arguements provided!");
										else sender.sendMessage(pLogTag + ChatColor.RED + "Insufficient arguements provided!");
										return false;
									}
								}
								else if(args[2].equalsIgnoreCase("exempt")) {
									if(confSection.getName() == "IPv4") {
										if(ProcessFilters.isValidIPv4(args[3])) {
											exemptionList.add(args[3]);
											if(isConsole(sender)) 
												log.info(logTag + confSection.getName() + " exemption added!");
											else {
												sender.sendMessage(pLogTag + ChatColor.GREEN + confSection.getName() + " exemption added!");
												log.info(logTag + confSection.getName() + " exemption added by "+sender.getName() + "!");
											}
											return true;
										}
										else {
											if(isConsole(sender)) 
												log.info(logTag + "'" + args[3] + "' is not a valid IPv4 address!");
											else sender.sendMessage(pLogTag + ChatColor.RED + "'" + args[3] + "' is not a valid IPv4 address!");
											return false;
										}
									}
									else if(confSection.getName() == "Domains") {
										if(ProcessFilters.isURL(args[3])) {
											exemptionList.add(args[3]);
											if(isConsole(sender))
												log.info(logTag + confSection.getName() + " exemtpion added!");
											else sender.sendMessage(pLogTag + ChatColor.GREEN + confSection.getName() + " exemption added!");
											log.info(logTag + confSection.getName() + " exemption added by " +sender.getName() + "!");
											return true;
										}
										else {
											if(isConsole(sender))
												log.info(logTag + "'" + args[3] + "'" + " is not a valid domain!");
											else sender.sendMessage(pLogTag + ChatColor.RED + "'" + args[3] + "' is not a valid domain!");
											return false;
										}
									}
								}
							}
								else if(args.length != 4) {
									if(isConsole(sender))
										log.info(logTag + "Insufficient arguements provided!");
									else sender.sendMessage(pLogTag + ChatColor.RED + "Insufficient arguements provided!");
									return false;
								}
							}
							else if(args.length != 3) {
								if(isConsole(sender))
									log.info(logTag + "Insufficient arguements provided!");
								else sender.sendMessage(pLogTag + ChatColor.RED + "Insufficient arguements provided!");
								return false;
							}
						}
						else if(args.length != 2) {
							if(isConsole(sender))
								log.info(logTag + "Insufficient arguements provided!");
							else sender.sendMessage(pLogTag + ChatColor.RED + "Insufficient arguements provided!");
							return false;
						}
					}
				} // End of "set" section.
				else {
					if(isConsole(sender))
						log.info(logTag + "Insufficient arguements provided!");
					else sender.sendMessage(pLogTag + ChatColor.RED + "Insufficient arguements provided!");
					return false;
				}
			} // End of args.length == 1.
		} // End of 'ap' || 'antipub' command.
		return true;
	}
}
		