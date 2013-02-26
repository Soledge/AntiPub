package main.java.com.wolfy9247.AntiPub.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import main.java.com.wolfy9247.AntiPub.AntiPub;

public class ReloadCommand implements APCommand {
	
	@Override
	public String getPermissionNode() {
		return "ap.reload";
	}
	
	@Override
	public String[] getNames() {
		return new String[]{"reload"};
	}
	
	@Override
	public String getSyntax() {
		return "reload";
	}
	
	@Override
	public void execute(AntiPub plugin, CommandSender sender, String[] args) {
		plugin.loadConfiguration();
		sender.sendMessage(ChatColor.GREEN + "AntiPub configuration has been reloaded.");
	}

}
