package main.java.com.wolfy9247.AntiPub;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class APAlert extends AntiPub {
	
	private ConfigurationSection section;
	private Player player;
	private Format format;
	
	public APAlert(ConfigurationSection s, AsyncPlayerChatEvent e) {
		player = e.getPlayer();
		section = s;
		format = new Format(e);
	}
	
	public void triggerAlerts() {
		triggerUserAlert();
		triggerAdminAlert();
	}
	
	public void triggerUserAlert() {
		player.sendMessage(format.formatMessage(section.getString("user-notification")));
	}
	
	public void triggerAdminAlert() {} {
		Bukkit.broadcast(format.formatMessage(section.getString("admin-notification")), "antipub.notify");
	}
	
}
