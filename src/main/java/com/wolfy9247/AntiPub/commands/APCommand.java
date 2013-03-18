package com.wolfy9247.AntiPub.commands;

import com.wolfy9247.AntiPub.AntiPub;

import org.bukkit.command.CommandSender;



/**
 * AntiPub sub-command.
 * This command-handling format is credited to evilmidget38 on GitHub/Bukkit forums.
 *
 * @author Wolfy9247
 */

public interface APCommand {
	
	/**
	* @return the permission node required to execute this command, or null if
	* none
	*/
	String getPermissionNode();

	/**
	* @return an array containing all possible aliases of this command
	*/
	String[] getNames();
	    
	/**
	* @return the proper syntax for the arguments of this command.
	*/
	String getSyntax();

	/**
	* Execute this command.
	*
	* @param plugin the plugin instance
	* @param sender the sender of the command
	* @param args the arguments of the subcommand
	*/
	void execute(AntiPub plugin, CommandSender sender, String[] args);
}
