package main.java.com.wolfy9247.AntiPub.commands;


import main.java.com.wolfy9247.AntiPub.AntiPub;

import org.bukkit.command.CommandSender;


public class AddFilter implements APCommand {

	@Override
	public String getPermissionNode() {
		return "ap.addf";
	}

	@Override
	public String[] getNames() {
		return new String[]{"add-filter"};
	}

	@Override
	public String getSyntax() {
		return "addf";
	}

	@Override
	public void execute(AntiPub plugin, CommandSender sender, String[] args) {
		// IPv4 or Domains
			// IPv4
				// for()... to list the current filters.
					// Take input for the number (i.e. [1],[2], etc.) and remove from list.
	}

}