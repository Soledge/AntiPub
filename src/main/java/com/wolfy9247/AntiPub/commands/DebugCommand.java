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

package com.wolfy9247.AntiPub.commands;


import com.wolfy9247.AntiPub.AntiPub;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


public class DebugCommand implements APCommand {

	@Override
	public String getPermissionNode() {
		return "antipub.debug";
	}

	@Override
	public String[] getNames() {
		return new String[]{"debug"};
	}

	@Override
	public String getSyntax() {
		return "debug";
	}

	@Override
	public void execute(AntiPub plugin, CommandSender sender, String[] args) {
		sender.sendMessage(ChatColor.RED + "[AntiPub] This feature has not yet been implemented in this version of AntiPub.");
	}

}
