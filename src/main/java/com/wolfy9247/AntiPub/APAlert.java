package com.wolfy9247.AntiPub;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class APAlert {
	
	private String[] alertMsg;
	private Player player;
	private Format format;
	
	public APAlert(String[] alertStr, AsyncPlayerChatEvent e) {
		player = e.getPlayer();
		alertMsg = alertStr;
		format = new Format(e);
	}
	
	public void triggerAlerts() {
		triggerAdminAlert();
		triggerUserAlert();
	}
	
	public void triggerUserAlert() {
		player.sendMessage(ChatColor.RED + "[AntiPub] " + format.formatMessage(alertMsg[0]));
	}
	
	public void triggerAdminAlert() {
		if(format != null) {
			Bukkit.broadcast(ChatColor.RED + "[AntiPub] " + format.formatMessage(alertMsg[1]), "antipub.notify");
		}
	}
}
