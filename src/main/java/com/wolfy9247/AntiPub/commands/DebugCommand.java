package main.java.com.wolfy9247.AntiPub.commands;


import main.java.com.wolfy9247.AntiPub.AntiPub;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


public class DebugCommand implements APCommand {

	@Override
	public String getPermissionNode() {
		return "ap.debug";
	}

	@Override
	public String[] getNames() {
		return new String[]{"debug"};
	}

	@Override
	public String getSyntax() {
		return "debug";
	}

	@Override
	public void execute(AntiPub plugin, CommandSender sender, String[] args) {
		sender.sendMessage(ChatColor.RED + "[AntiPub] This feature has not yet been implemented in this version of AntiPub.");
	}

}
