package com.wolfy9247.AntiPub;

import java.util.logging.Logger;

import com.wolfy9247.AntiPub.commands.APCommandDispatcher;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiPub extends JavaPlugin {
	
	private static AntiPub instance;
	private static APCommandDispatcher dispatcher;
	protected static FileConfiguration config;
	private PluginDescriptionFile pdfFile;
	
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
		log.info(pLogTag + pdfFile.getName() + " v" + pdfFile.getVersion() + " enabled!");
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

	public static APCommandDispatcher getDispatcher() {
		return dispatcher;
	}

    public String getLogTag() {
        return logTag;
    }
}
		