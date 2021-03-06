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

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Format {
	
	private final String[] tokens = {"<user>", "<message>", "<ip>", "<hostname>", "<port>"};
	private String message;
	private Player player;
	private String playerMessage;
	
	public Format(AsyncPlayerChatEvent e) {
		player = e.getPlayer();
		playerMessage = e.getMessage();
	}
	
	public String formatMessage(String str) {
		message = str;
		for(int i = 0; i < tokens.length; i++) {
			switch(i) {
				case 0: message = message.replace(tokens[i], player.getName());
						break;
				case 1: message = message.replace(tokens[i], "\"" + playerMessage + "\"");
						break;
				case 2: message = message.replace(tokens[i], player.getAddress().getAddress().getHostAddress());
						break;
				case 3: message = message.replace(tokens[i], player.getAddress().getAddress().getCanonicalHostName());
						break;
				case 4: message = message.replace(tokens[i], new Integer(player.getAddress().getPort()).toString());
						break;
			}
		}
		return message;
	}

}
