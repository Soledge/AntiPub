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
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class RemoveExCommand implements APCommand {

	@Override
	public String getPermissionNode() {
		return "antipub.remx";
	}

	@Override
	public String[] getNames() {
		return new String[]{"remx"};
	}

	@Override
	public String getSyntax() {
		return "<IPv4|URL> <index>";
	}

	@Override
	public void execute(AntiPub plugin, CommandSender sender, String[] args) {
        FileConfiguration config = plugin.getConfig();
        String logTag = plugin.getLogTag();

        if (args.length == 0) {
            sender.sendMessage(logTag + ChatColor.DARK_RED + "Specify either [ipv4] or [url]. See /ap help for more information.");
        } else if (args[0].equalsIgnoreCase("ipv4")) {
            ConfigurationSection section = config.getConfigurationSection("IPv4");
            List exList = section.getList("exemptions");

            if (sender.hasPermission("antipub.remx.ipv4")) {
                if(args.length == 1) {
                    sender.sendMessage(plugin.getLogTag() + "Select an exception from the list below: ");
                    sender.sendMessage(ChatColor.DARK_RED + "##############################################");
                    for(int i = 0; i < exList.size(); i++) {
                        sender.sendMessage("[#" + (i + 1) + "] " + exList.get(i));
                    }
                    sender.sendMessage(ChatColor.DARK_RED + "##############################################");
                    sender.sendMessage(logTag + "To remove an exception, type /ap remx <IPv4|URL> <#>");
                } else {
                    try {
                        int index = Integer.parseInt(args[1]);
                        if(index > exList.size() || index < exList.size()) {
                            sender.sendMessage(logTag + ChatColor.DARK_RED + "The specified index does not exist. To see the list, execute /ap remx <IPv4|URL>.");
                        } else {
                            index = index - 1;
                            if(index == 0) {
                                exList.set(index, "default");
                            } else {
                                exList.remove(index);
                            }
                            sender.sendMessage(logTag + ChatColor.DARK_GREEN + "The exception has been removed.");
                        }
                    } catch(NumberFormatException e) {
                        sender.sendMessage(logTag + ChatColor.DARK_RED + "The specified index is not valid.");
                    }
                }
            }
        } else if (args[0].equalsIgnoreCase("URL")) {
            ConfigurationSection section = config.getConfigurationSection("URL");
            List exList = section.getList("exemptions");

            if (sender.hasPermission("antipub.remx.url")) {
                if(args.length == 1) {
                    sender.sendMessage(plugin.getLogTag() + "Select an exception from the list below: ");
                    sender.sendMessage(ChatColor.DARK_RED + "##############################################");
                    for(int i = 0; i < exList.size(); i++) {
                        sender.sendMessage("[#" + (i + 1) + "] " + exList.get(i));
                    }
                    sender.sendMessage(ChatColor.DARK_RED + "##############################################");
                    sender.sendMessage(logTag + "To remove an exception, type /ap remx <IPv4|URL> <#>");
                } else {
                    try {
                        int index = Integer.parseInt(args[1]);
                        if(index > exList.size() || index < exList.size()) {
                            sender.sendMessage(logTag + ChatColor.DARK_RED + "The specified index does not exist. To see the list, execute /ap remx <IPv4|URL>.");
                        } else {
                            index = index - 1;
                            if(index == 0) {
                                exList.set(index, "default");
                            } else {
                                exList.remove(index);
                            }
                            sender.sendMessage(logTag + ChatColor.DARK_GREEN + "The exception has been removed.");
                        }
                    } catch(NumberFormatException e) {
                        sender.sendMessage(logTag + ChatColor.DARK_RED + "The specified index is not valid. See /ap help for more information.");
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
