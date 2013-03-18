package com.wolfy9247.AntiPub;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AntiPubListener implements Listener {
	public static AntiPub plugin;
	protected FileConfiguration config;
	private APAlert alert;
	
	public AntiPubListener(AntiPub ap) {
		plugin = ap;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		config = plugin.getConfig();
		
		Player player = e.getPlayer();
		APMessage message = new APMessage(plugin, e);
		
		ConfigurationSection section = message.getSection();
		
		if(player instanceof Player && player != null) {
			if(message.isAllowed()) {
				return;
			}
			else {
			      String[] alertMessages = { section.getString("user-notification"), section.getString("admin-notification") };
			      alert = new APAlert(alertMessages, e);
			      alert.triggerAlerts();
			      e.setCancelled(true);
			}
		}
	}
}
