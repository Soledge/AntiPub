package com.wolfy9247.AntiPub.commands;


import com.wolfy9247.AntiPub.AntiPub;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


public class HelpCommand implements APCommand {
	
	@Override
	public String getPermissionNode() {
		return "ap.help";
	}

	@Override
	public String[] getNames() {
		return new String[]{"help"};
	}

	@Override
	public String getSyntax() {
		return "";
	}

	@Override
	public void execute(AntiPub plugin, CommandSender sender, String[] args) {
		plugin.getDispatcher().showAvailableCommands(sender);
	}

}
