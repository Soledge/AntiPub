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
import com.wolfy9247.AntiPub.APStats;
import com.wolfy9247.AntiPub.AntiPub;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;


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
		return "<testfilter> <msg>";
	}

	@Override
	public void execute(AntiPub plugin, CommandSender sender, String[] args) {
        String logTag = plugin.getLogTag();
        if(args.length == 0) {
            sender.sendMessage(logTag + "AntiPub Debug Interface: ");
            sender.sendMessage(ChatColor.DARK_GRAY + "##############################################");
            sender.sendMessage("[#1] Test Filters - /ap debug testfilter <msg>");
            sender.sendMessage("[#2] Test Database - /ap debug testdb");
            sender.sendMessage(ChatColor.DARK_GRAY + "##############################################");
            sender.sendMessage(logTag + "Use the above syntax to perform an action.");
        } else {
            if(args[0].equalsIgnoreCase("testfilter")) {
                if(args.length < 2) {
                    sender.sendMessage(logTag + ChatColor.DARK_RED + "You must specify a message to test!");
                } else {
                    APMessage message = new APMessage(args[1]);
                    sender.sendMessage(logTag + "IPv4 Test: " + boolToString(message.isValidIPv4()));
                    sender.sendMessage(logTag + "URL Test: " + boolToString(message.isURL()));
                }
            } else if(args[0].equalsIgnoreCase("testdb")) {
                String[] protocols = {"IPv4", "URL"};
                for(String protocol : protocols) {
                    List<APStats> stats = plugin.getDatabase().find(APStats.class).where().ieq("protocol", protocol).findList();
                    if(stats.size() == 0) {
                        sender.sendMessage(logTag + ChatColor.DARK_RED + "No " + protocol + " entries found.");
                    } else {
                        APStats lastEntry = stats.get(stats.size() - 1);
                        sender.sendMessage(logTag + "########################################");
                        sender.sendMessage(logTag + "Last " + lastEntry.getProtocol() + " message: " + lastEntry.getMessage());
                        sender.sendMessage(logTag + "ID #: " + lastEntry.getId());
                        sender.sendMessage(logTag + "Total " + lastEntry.getProtocol() + " entries logged: " +
                                            plugin.getDatabase().find(APStats.class).where().ieq("protocol", protocol).findRowCount());
                        sender.sendMessage(logTag + "########################################");
                    }
                }
            } else {
                sender.sendMessage(logTag + ChatColor.RED + "You must enter a valid command.");
            }
        }
    }

    private String boolToString(boolean bool) {
        if (!bool) {
            return ChatColor.DARK_GREEN + "[PASSED - EXEMPT]";
        } else {
            return ChatColor.DARK_RED + "[FAILED - NOT EXEMPT]";
        }
	}
}
