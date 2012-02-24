package com.wolfy9247.AntiPub;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class AntiPubListener implements Listener {
	public static Main plugin;
	protected FileConfiguration config;
	
	public String terminateMessage(String string) {
		String advertAlert = "Advertising and/or posting IPv4 addresses is not allowed on  this server!";
		string = config.getString(string);
		if(!string.equalsIgnoreCase(advertAlert) && !string.isEmpty()) {
			return string;
		}
		else {
			return advertAlert;
		}
	}
	
	public void processBlockMessages(ConfigurationSection subSection, String message, Player player) {
		ConfigurationSection mainSection = config.getConfigurationSection("AntiPub.Block Messages");
		String userMessage = mainSection.getString(subSection.getName() + ".User Notification");
		String adminMessage = mainSection.getString(subSection.getName() + ".Admin Notification");
		if(subSection.getBoolean("Alert User")) {
			player.sendMessage(ProcessPrint.processPrint(userMessage, player, message));
		}
		if(subSection.getBoolean("Alert Admins")) {
			Bukkit.broadcast(ProcessPrint.processPrint(adminMessage, player, message), "AntiPub.notify");
		}
	}
	
	public boolean isExempt(String message, String section) {
		Object[] exemptList = config.getList("AntiPub."+section+".Exempt "+section).toArray();
		for(int i = 0; i < exemptList.length; i++) {
			if(message.contains(exemptList[i].toString())) {
				return true;
			}
		}
		return false;
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
		plugin = Main.instance;
		config = plugin.getConfig();
		
		Player player = e.getPlayer();
		String message = e.getMessage();
		ConfigurationSection section = config.getConfigurationSection("AntiPub");
		
		if(player instanceof Player && player != null) {
			if(player.hasPermission("antipub.*") || player.hasPermission("antipub.bypass")) {
				return;
			}
			else if(ProcessFilters.isValidIPv4(message)) {
				if(isExempt(message, "Domains")) {
					return;
				}
				else {
					section = config.getConfigurationSection("AntiPub.IPv4");
					if(config.getBoolean(section + ".Block IPv4")) {
						processBlockMessages(section, message, player);
						e.setCancelled(true);
					}
				}
			}
			else if(ProcessFilters.isURL(message)) {
				if(isExempt(message, "IPv4")) {
					return;
				}
				else {
					section = config.getConfigurationSection("AntiPub.Domains");
					if(config.getBoolean(section.getName() + ".Block Domains")) {
						processBlockMessages(section, message, player);
						e.setCancelled(true);
					}
					else if(section.getBoolean("Block Domains")) {
						processBlockMessages(section, message, player);
						e.setCancelled(true);
					}
				}
			}
			else {
				return;
			}
		}
		else {
			return;
		}
	}
}
