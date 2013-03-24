/*
 * AntiPub
 * Copyright (C) 2013 Wolfy9247 <https://github.com/Wolfy9247>
 *
 * Unless explicitly acquired and licensed from Licensor under another
 * license, the contents of this file are subject to the Reciprocal Public
 * License ("RPL") Version 1.5, or subsequent versions as allowed by the RPL,
 * and You may not copy or use this file in either source code or executable
 * form, except in compliance with the terms and conditions of the RPL.
 *
 * All software distributed under the RPL is provided strictly on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, AND
 * LICENSOR HEREBY DISCLAIMS ALL SUCH WARRANTIES, INCLUDING WITHOUT
 * LIMITATION, ANY WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, QUIET ENJOYMENT, OR NON-INFRINGEMENT. See the RPL for specific
 * language governing rights and limitations under the RPL.
 */

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
