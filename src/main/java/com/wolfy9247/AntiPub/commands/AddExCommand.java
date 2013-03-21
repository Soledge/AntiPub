package com.wolfy9247.AntiPub.commands;

import com.wolfy9247.AntiPub.AntiPub;

import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;


public class AddExCommand implements APCommand {

	@Override
	public String getPermissionNode() {
		return "antipub.addx";
	}

	@Override
	public String[] getNames() {
		return new String[]{"addx"};
	}

	@Override
	public String getSyntax() {
		return "addx <exception>";
	}

	@Override
	public void execute(AntiPub plugin, CommandSender sender, String[] args) {
		sender.sendMessage(ChatColor.RED + "[AntiPub] This feature has not yet been implemented in this version of AntiPub.");
	}

}
