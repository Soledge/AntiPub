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
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ToggleCommand implements APCommand {

	@Override
	public String getPermissionNode() {
		return "antipub.toggle";
	}

	@Override
	public String[] getNames() {
		return new String[]{"toggle"};
	}

	@Override
	public String getSyntax() {
		return "<IPv4|URL> <on|off>";
	}

	/* @TODO: Hook in with permissions to change antipub.notify to false for individual users.
	 * This function only applies to those who have or inherit antipub.notify.
	 */
	@Override
	public void execute(AntiPub plugin, CommandSender sender, String[] args) {
		FileConfiguration config = plugin.getConfig();
		String logTag = plugin.getLogTag();

		if (args.length == 0) {
			sender.sendMessage(logTag + ChatColor.DARK_RED + "Specify either [ipv4] or [url]. See /ap help for more information.");
		} else if (args[0].equalsIgnoreCase("ipv4")) {
			ConfigurationSection section = config.getConfigurationSection("IPv4");

			if (args.length < 2) {
				sender.sendMessage(logTag + "IPv4 filtering is currently: " + boolToString(section.getBoolean("block-protocol")));
				return;
			} else if (sender.hasPermission("ap.toggle.ipv4")) {
				if (args[1].equalsIgnoreCase("on")) {
					section.set("block-protocol", true);
					sender.sendMessage(logTag + ChatColor.DARK_GREEN + "IPv4 blocking has been turned on.");
				} else if (args[1].equalsIgnoreCase("off")) {
					section.set("block-protocol", false);
					sender.sendMessage(logTag + ChatColor.DARK_RED + "IPv4 blocking has been turned off.");
				} else {
					sender.sendMessage(logTag + ChatColor.DARK_RED + "You must specify either [on] or [off].");
				}
			}
		} else if (args[0].equalsIgnoreCase("URL")) {
			ConfigurationSection section = config.getConfigurationSection("URL");

			if (args.length < 2) {
				sender.sendMessage(logTag + "URL filtering is currently: " + boolToString(section.getBoolean("block-protocol")));
				return;
			} else if (sender.hasPermission("ap.toggle.url")) {
				if (args[1].equalsIgnoreCase("on")) {
					section.set("block-protocol", true);
					sender.sendMessage(logTag + ChatColor.DARK_GREEN + "URL filters have been turned on.");
				} else if (args[1].equalsIgnoreCase("off")) {
					section.set("block-protocol", false);
					sender.sendMessage(logTag + ChatColor.DARK_RED + "URL filters have been turned off.");
				} else {
					sender.sendMessage(logTag + ChatColor.DARK_RED + "You must specify either [on] or [off].");
				}
			}
		} else {
			sender.sendMessage(logTag + ChatColor.DARK_RED + "Sorry, either the command does not exist or you do not have permission for this operation.");
		}
		plugin.saveConfig();
		plugin.reloadConfig();
	}

	private String boolToString(boolean bool) {
		if (bool) {
			return ChatColor.DARK_GREEN + "[ON]";
		} else {
			return ChatColor.DARK_RED + "[OFF]";
		}
	}
}
