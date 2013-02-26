package main.java.com.wolfy9247.AntiPub.commands;

import java.util.ArrayList;
import java.util.List;

import main.java.com.wolfy9247.AntiPub.AntiPub;

import org.bukkit.command.CommandSender;

public class HelpCommand implements APCommand {
	
	@Override
	public String getPermissionNode() {
		return "ap.help";
	}

	@Override
	public String[] getNames() {
		// TODO Auto-generated method stub
		return new String[]{"help"};
	}

	@Override
	public String getSyntax() {
		return "help";
	}

	@Override
	public void execute(AntiPub plugin, CommandSender sender, String[] args) {
		
	}

}
