package com.wolfy9247.AntiPub.commands;

import com.wolfy9247.AntiPub.AntiPub;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;



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
        plugin.reloadConfig();
		sender.sendMessage(ChatColor.GREEN + "AntiPub configuration has been reloaded.");
	}

}
