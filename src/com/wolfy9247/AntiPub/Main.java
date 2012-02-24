package com.wolfy9247.AntiPub;

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
    public static String logTag = "[AntiPub] ";
    
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
	
	public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("ap") || label.equalsIgnoreCase("antipub")) {
			if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
				sender.sendMessage("==== AntiPub Help ====");
				if(sender.hasPermission("antipub.reload"))
					sender.sendMessage("/ap reload - Reloads the configuration.");
				sender.sendMessage("/ap help <?command> - Prints the help menu.");
				if(sender.hasPermission("antipub.mod")) {
					sender.sendMessage("/ap set <args> - Toggles an item in the config.");
				}
				if(sender.hasPermission("antipub.admin")) {
					sender.sendMessage("/ap exempt <IP | Domain> <args> - Exempt from filtering.");
				}
				if(args[1].length() > 0) {
					/* @TODO: Make help for each individual command, such as the
					 * syntax, how to use them, etc, etc. 
					 */
					sender.sendMessage(ChatColor.RED + "Sorry, this function isn't supported in v0.3 (yet!)");
					return true;
				}
				return true;
			}
			else if(args[0].length() > 0) {
				if(args[0].equalsIgnoreCase("reload")) {
					if(sender.hasPermission("antipub.reload")) {
						try {
							reloadConfig();
							loadConfiguration();
						}
						catch(NullPointerException npe) {
							log.warning(logTag + "An error has occured!");
							if(sender instanceof Player) {
								if(sender != null) {
									sender.sendMessage(ChatColor.RED + "A config error has occured. Check the console for more info.");
								}
								npe.printStackTrace();
							}
							return true;
						}
						log.info(logTag + "Configuration sucessfully reloaded!");
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("set")) {
					if(sender.hasPermission("antipub.mod")) {
						if(args[1].length() > 0) {
							ConfigurationSection confSection = config.getConfigurationSection("AntiPub."+args[1]);
							if(args[2].length() > 0) {
								if(args[2].equalsIgnoreCase("disabled")) {
									if(confSection.getBoolean("Block "+args[1], false)) {
										sender.sendMessage(ChatColor.RED + "This section is already disabled!");
									}
									else {
										confSection.set("Block "+args[1], false);
									}
								}
								else if(args[2].equalsIgnoreCase("enabled")) {
									if(confSection.getBoolean("Block "+args[1], true)) {
										sender.sendMessage(ChatColor.RED + "This section is already enabled!");
									}
									else {
										confSection.set("Block "+args[1], true);
									}
								}
								else if(args[2].equalsIgnoreCase("alerts")) {
									if(args[3].length() > 0) {
										if(args[4].length() > 0) {
											if(args[3].equalsIgnoreCase("user")) {
												if(args[4].equalsIgnoreCase("enabled")) {
													confSection.set("Alert User", true);
												}
												else if(args[4].equalsIgnoreCase("disabled")) {
													confSection.set("Alert User", false);
												}
											}
											else if(args[3].equalsIgnoreCase("admins")) {
												if(args[4].equalsIgnoreCase("enabled")) {
													confSection.set("Alert Admins", true);
												}
												else if(args[4].equalsIgnoreCase("disabled")) {
													confSection.set("Alert Admins", false);
												}
											}
										}
									}
								}
								else if(args[2].equalsIgnoreCase("exempt")) {
									if(confSection.getName() == "IPv4") {
										if(ProcessFilters.isValidIPv4(args[3])) {
											// Add to the list of exemptions.
										}
										else {
											sender.sendMessage(ChatColor.RED + "This is not a valid IPv4 address!");
										}
									}
									else if(confSection.getName() == "Domains") {
										if(ProcessFilters.isURL(args[3])) {
											// Add to the list of exemptions.
										}
										else {
											sender.sendMessage(ChatColor.RED + "This is not a valid domain!");
										}
									}
								}
								else if(args[3].isEmpty()) {
									sender.sendMessage(ChatColor.RED + "Insufficient arguements provided!");
								}
							}
							else if(args[2].isEmpty()) {
								sender.sendMessage(ChatColor.RED + "Insufficient arguements provided!");
							}
						}
						else if(args[1].isEmpty()) {
							sender.sendMessage(ChatColor.RED + "Insufficient arguements provided!");
						}
					}
				}
			}
		}
		return true;
	}
}
		