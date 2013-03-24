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

import com.wolfy9247.AntiPub.APMessage;
import com.wolfy9247.AntiPub.AntiPub;

import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;


public class AddExCommand implements APCommand {

	@Override
	public String getPermissionNode() {
		return "antipub.addx";
	}

	@Override
	public String[] getNames() {
		return new String[]{"addx"};
	}

	@Override
	public String getSyntax() {
		return "<IPv4|URL> <exception>";
	}

	@Override
	public void execute(AntiPub plugin, CommandSender sender, String[] args) {
        FileConfiguration config = plugin.getConfig();
        String logTag = plugin.getLogTag();

        if (args.length == 0) {
            sender.sendMessage(logTag + ChatColor.DARK_RED + "Specify either [ipv4] or [url]. See /ap help for more information.");
        } else if (args[0].equalsIgnoreCase("ipv4")) {
            if(args.length < 2) {
                sender.sendMessage(logTag + ChatColor.DARK_RED + "You must specify an exception!");
            } else {
                ConfigurationSection section = config.getConfigurationSection("IPv4");
                List exList = section.getList("exemptions");
                APMessage exception = new APMessage(args[1]);

                if (sender.hasPermission("antipub.addx.ipv4")) {
                    if(exception.isValidIPv4()) {
                        exList.add(args[1]);
                        sender.sendMessage(logTag + ChatColor.DARK_GREEN + "\"" + args[1] + "\" has been added to position #" + exList.size() + ".");
                    } else {
                        sender.sendMessage(logTag + ChatColor.DARK_RED + "The exception is not a valid IPv4 address.");
                    }
                }
            }
        } else if (args[0].equalsIgnoreCase("URL")) {
            if(args.length < 2) {
                sender.sendMessage(logTag + ChatColor.DARK_RED + "You must specify an exception!");
            } else {
                ConfigurationSection section = config.getConfigurationSection("URL");
                List exList = section.getList("exemptions");
                APMessage exception = new APMessage(args[1]);

                if (sender.hasPermission("antipub.addx.url")) {
                    if(exception.isURL()) {
                        exList.add(args[1]);
                        sender.sendMessage(logTag + ChatColor.DARK_GREEN + "\"" + args[1] + "\" has been added to position #" + exList.size() + ".");
                    } else {
                        sender.sendMessage(logTag + ChatColor.DARK_RED + "The exception is not a valid URL address.");
                    }
                }
            }
        } else {
            sender.sendMessage(logTag + ChatColor.DARK_RED + "Sorry, either the command does not exist or you do not have permission for this operation.");
        }
        plugin.saveConfig();
        plugin.reloadConfig();
	}

}
