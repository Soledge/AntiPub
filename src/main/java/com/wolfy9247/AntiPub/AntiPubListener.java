package main.java.com.wolfy9247.AntiPub;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AntiPubListener implements Listener {
	public static AntiPub plugin;
	protected FileConfiguration config;
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		plugin = AntiPub.getInstance();
		config = plugin.getConfig();
		
		Player player = e.getPlayer();
		APMessage message = new APMessage(e);
		
		ConfigurationSection section = message.getSection();
		
		if(player instanceof Player && player != null) {
			if(message.isAllowed()) {
				return;
			}
			else {
				e.setCancelled(true);
				APAlert alert = new APAlert(section, e);
				alert.triggerAlerts();
			}
		}
	}
}
