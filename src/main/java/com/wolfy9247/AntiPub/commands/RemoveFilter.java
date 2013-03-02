package main.java.com.wolfy9247.AntiPub.commands;


import main.java.com.wolfy9247.AntiPub.AntiPub;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;


public class RemoveFilter implements APCommand {

	@Override
	public String getPermissionNode() {
		return "ap.remf";
	}

	@Override
	public String[] getNames() {
		return new String[]{"remove-filter"};
	}

	@Override
	public String getSyntax() {
		return "remf";
	}

	@Override
	public void execute(AntiPub plugin, CommandSender sender, String[] args) {
		FileConfiguration config = plugin.getConfig();
		
		// IPv4 or Domains
			// IPv4
				// for()... to list the current filters.
					// Take input for the number (i.e. [1],[2], etc.) and remove from list.
	}

}
