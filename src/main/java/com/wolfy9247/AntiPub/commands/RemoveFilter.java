package main.java.com.wolfy9247.AntiPub.commands;

import main.java.com.wolfy9247.AntiPub.AntiPub;

import org.bukkit.command.CommandSender;

public class RemoveFilter implements APCommand {

	@Override
	public String getPermissionNode() {
		return "ap.";
	}

	@Override
	public String[] getNames() {
		return new String[]{""};
	}

	@Override
	public String getSyntax() {
		return "";
	}

	@Override
	public void execute(AntiPub plugin, CommandSender sender, String[] args) {

	}

}
