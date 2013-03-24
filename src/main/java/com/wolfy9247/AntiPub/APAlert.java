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
