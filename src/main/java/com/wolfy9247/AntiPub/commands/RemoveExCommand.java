package main.java.com.wolfy9247.AntiPub.commands;

import main.java.com.wolfy9247.AntiPub.AntiPub;

import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

public class RemoveExCommand implements APCommand {

	@Override
	public String getPermissionNode() {
		return "ap.remx";
	}

	@Override
	public String[] getNames() {
		return new String[]{"remx"};
	}

	@Override
	public String getSyntax() {
		return "remx <exception>";
	}

	@Override
	public void execute(AntiPub plugin, CommandSender sender, String[] args) {
		sender.sendMessage(ChatColor.RED + "[AntiPub] This feature has not yet been implemented in this version of AntiPub.");
	}

}
