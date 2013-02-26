package main.java.com.wolfy9247.AntiPub;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import main.java.com.wolfy9247.AntiPub.commands.APCommand;
import main.java.com.wolfy9247.AntiPub.commands.APCommandDispatcher;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiPub extends JavaPlugin {
	
	public static AntiPub instance;
	public APCommandDispatcher dispatcher;
	protected static FileConfiguration config;
	
	public final static Logger log = Logger.getLogger("Minecraft");
    public static final String logTag = "[AntiPub] ";
    public static final String pLogTag = ChatColor.GOLD + "[AntiPub] ";
    
	public void loadConfiguration() {
		config = this.getConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(logTag + pdfFile.getName() + " v" + pdfFile.getVersion() + " disabled!");
	}
	
	@Override
	public void onEnable() {
		instance = this;
		dispatcher = new APCommandDispatcher(this);
		PluginDescriptionFile pdfFile = this.getDescription();
		getConfig().options().copyDefaults(true);
		saveConfig();
		getServer().getPluginManager().registerEvents(new AntiPubListener(), this);
		log.info(logTag + pdfFile.getName() + " v" + pdfFile.getVersion() + " enabled!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ap") || cmd.getName().equalsIgnoreCase("antipub")) {
			if (!dispatcher.dispatch(sender, args)) {
				showAvailableCommands(sender, cmd);
			}
		}
		return true;
	}
	
	private void showAvailableCommands(CommandSender sender, Command cmd) {
		final List<String> availableCommands = new ArrayList<String>();
		
		for (final APCommand command : dispatcher.getCommands()) {
			if (command.getPermissionNode() == null || sender.hasPermission(command.getPermissionNode())) {
				availableCommands.add(APCommandDispatcher.getFullSyntax(command));
			}
		}
		
		if (availableCommands.isEmpty()) {
			sender.sendMessage(ChatColor.RED + "You don't have permission.");
		} else {
			sender.sendMessage("Try one of the following commands: ");
			sender.sendMessage(availableCommands.toArray(new String[availableCommands.size()]));
		}
	}
}
		