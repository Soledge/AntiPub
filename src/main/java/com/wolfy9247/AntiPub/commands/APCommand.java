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

import org.bukkit.command.CommandSender;



/**
 * AntiPub sub-command.
 * This command-handling format is credited to evilmidget38 on GitHub/Bukkit forums.
 *
 * @author Wolfy9247
 */

public interface APCommand {
	
	/**
	* @return the permission node required to execute this command, or null if
	* none
	*/
	String getPermissionNode();

	/**
	* @return an array containing all possible aliases of this command
	*/
	String[] getNames();
	    
	/**
	* @return the proper syntax for the arguments of this command.
	*/
	String getSyntax();

	/**
	* Execute this command.
	*
	* @param plugin the plugin instance
	* @param sender the sender of the command
	* @param args the arguments of the subcommand
	*/
	void execute(AntiPub plugin, CommandSender sender, String[] args);
}
