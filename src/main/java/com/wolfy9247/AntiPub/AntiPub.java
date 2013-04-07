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

import com.wolfy9247.AntiPub.commands.APCommandDispatcher;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;
import org.mcstats.Metrics.Graph;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AntiPub extends JavaPlugin {

    private Metrics metrics;
	private APCommandDispatcher dispatcher;
	private PluginDescriptionFile pdfFile;
    private static AntiPub instance;
    protected static FileConfiguration config;
	
	public final static Logger log = Logger.getLogger("Minecraft");
    public static final String logTag = ChatColor.GOLD  + "[AntiPub] ";
    public static final String pLogTag = logTag.substring(logTag.indexOf('['));

	@Override
	public void onEnable() {
        instance = this;
		dispatcher = new APCommandDispatcher(this);
		pdfFile = this.getDescription();
		loadConfiguration();
		getServer().getPluginManager().registerEvents(new AntiPubListener(this), this);
        setupDatabase();
        loadStats();
		log.info(pLogTag + pdfFile.getName() + " v" + pdfFile.getVersion() + " enabled!");
	}

    public void loadStats() {
        try {
            metrics = new Metrics(this);

            if(!(metrics.isOptOut())) {
                Graph graph = metrics.createGraph("Protocol Percentage");

                graph.addPlotter(new Metrics.Plotter("IPv4") {

                    @Override
                    public int getValue() {
                        return getDatabase().find(APStats.class).where().ieq("protocol", "IPv4").findRowCount();
                    }

                });

                graph.addPlotter(new Metrics.Plotter("URL") {

                    @Override
                    public int getValue() {
                        return getDatabase().find(APStats.class).where().ieq("protocol", "URL").findRowCount();
                    }

                });
                metrics.start();
                log.info(pLogTag + "Initializing Metrics service... done.");
                log.info(pLogTag + "To view statistics, visit: http://mcstats.org/plugin/AntiPub");
            } else {
                log.info(pLogTag + "Metrics is disabled. No data sent.");
                metrics.disable();
            }
        } catch (IOException e) {
            log.warning(pLogTag + "Metrics failed to submit data.");
        }
    }

    private void setupDatabase() {
        try {
            getDatabase().find(APStats.class).findRowCount();
        } catch (PersistenceException e) {
            log.info(pLogTag + "######### AntiPub Database Notice #########");
            log.info(pLogTag + "Metrics SQLite Database is being installed... done.");
            log.info(pLogTag + "Change opt-out to 'true' in plugins/PluginMetrics/");
            log.info(pLogTag + "config.yml to not send anonymous statistics.");
            log.info(pLogTag + "######### AntiPub Database Notice End #########");
            log.info(pLogTag + "######### AntiPub Database Creation #########");
            installDDL();
            log.info(pLogTag + "######### Database Creation End #########");
        }
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(APStats.class);
        return list;
    }

	public void loadConfiguration() {
		config = this.getConfig();
		config.options().copyDefaults(true);
		saveConfig();
	}
	
	@Override
	public void onDisable() {
		log.info(pLogTag + pdfFile.getName() + " v" + pdfFile.getVersion() + " disabled!");
        saveConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ap") || cmd.getName().equalsIgnoreCase("antipub")) {
			if (!dispatcher.dispatch(sender, args)) {
				dispatcher.showAvailableCommands(sender);
			}
		}
		return true;
	}
	
	public static AntiPub getInstance() {
		return instance;
	}

	public APCommandDispatcher getDispatcher() {
		return dispatcher;
	}

    public String getLogTag() {
        return logTag;
    }
}
		