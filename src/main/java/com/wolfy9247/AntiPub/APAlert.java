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

import com.avaje.ebean.ExpressionList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.*;

public class APAlert {

    private ConfigurationSection section;
	private Player player;
	private Format format;
    private APMessage message;
    private AntiPub plugin;
    private String logTag;
	
	public APAlert(AsyncPlayerChatEvent e, AntiPub plugin) {
        player = e.getPlayer();
        message = new APMessage(e.getMessage());
        format = new Format(e);
        this.plugin = plugin;
        section = plugin.getConfig().getConfigurationSection(message.getType());
        logTag = plugin.getLogTag();
	}
	
	public void triggerAlerts() {
		triggerAdminAlert();
		triggerUserAlert();
        if(requiresAction()) {
            takeAction();
        }
	}

	
	private void triggerUserAlert() {
		player.sendMessage(logTag + ChatColor.RED + format.formatMessage(section.getString("user-notification")));
	}
	
	private void triggerAdminAlert() {
		if(format != null) {
			Bukkit.broadcast(logTag + ChatColor.RED + format.formatMessage(section.getString("admin-notification")), "antipub.notify");
		}
	}

    public void logAlert() {
        if(plugin.getConfig().getBoolean("Global.log-alerts") && section.getBoolean("log-alert")) {
            final APStats stats = new APStats();
            final Calendar currentDate = Calendar.getInstance();
            final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            stats.setProtocol(message.getType());
            stats.setPlayerName(player.getName());
            stats.setMessage(message.getMessage());
            stats.setServerTime(formatter.format(currentDate.getTime()));

            /* This operations run in a separate thread as a workaround for an Ebean exception
             * that is caused by modifying the DB as an extension of an async event.
             */
             class LogThread extends Thread {
                volatile boolean finished = false;

                public void stopThread() {
                    finished = true;
                }

                @Override
                public void run() {
                    while(!finished) {
                        plugin.getDatabase().save(stats);
                        if(plugin.getConfig().getBoolean("Global.log-display")) {
                            Bukkit.broadcast(logTag + ChatColor.DARK_GREEN + "Alert ID #: " + stats.getId() + " has been logged.", "antipub.notify");
                            stopThread();
                        } else {
                            stopThread();
                        }
                    }
                }
            };
            LogThread t = new LogThread();
            t.start(); // Saves the database.
        }
    }

    private boolean requiresAction() {
        final int maxAttempts = section.getInt("user-attempts");

        /* A new class must be created in order to retrieve the value from the
         * new thread, which is required due to the Ebean exception.
        */
        class DBThread extends Thread {
            volatile boolean finished = false;
            private int playerAttempts;

            public void stopThread() {
                finished = true;
            }

            @Override
            public void run() {
                while(!finished) {
                    playerAttempts = plugin.getDatabase().find(APStats.class).where().ieq("playerName", player.getName()).findRowCount() + 1;
                }
            }

            public int getPlayerAttempts() {
                return playerAttempts;
            }
        };

        DBThread t = new DBThread();
        t.start();

        int playerAttempts = t.getPlayerAttempts();
        while(t.isAlive()) {
            playerAttempts = t.getPlayerAttempts();
            if(playerAttempts >= 1) {
                t.stopThread();
            }
        }
        if(playerAttempts % maxAttempts == 0) {
            return true;
        } else {
            return false;
        }
    }

    private void takeAction() {
        final String command = format.formatMessage(section.getString("action").replace("/", ""));
        Bukkit.broadcast(logTag + ChatColor.RED + "User surpassed amount of attempts!", "antipub.notify");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
