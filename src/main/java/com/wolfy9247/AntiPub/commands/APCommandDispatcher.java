package com.wolfy9247.AntiPub.commands;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import com.wolfy9247.AntiPub.AntiPub;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class APCommandDispatcher {
	private static final APCommand[] commandArr = {new ReloadCommand(), new ToggleCommand(), new HelpCommand(), new AddExCommand(), new RemoveExCommand()};
    private final Map<String, APCommand> commands = new LinkedHashMap<String, APCommand>();
    private AntiPub plugin;

    public APCommandDispatcher(final AntiPub plugin) {
        this.plugin = plugin;

        for (final APCommand command : commandArr) {
            for (final String name : command.getNames()) {
                this.commands.put(name.toLowerCase(), command);
            }
        }
    }

    public APCommand getCommand(final String str) {
        return commands.get(str.toLowerCase());
    }

    public boolean dispatch(final CommandSender sender, final String[] args) {
        if (args.length == 0) {
            return false;
        }
        final APCommand command = getCommand(args[0].toLowerCase());
        if (command == null) {
            return false;
        }
        final String permissionNode = command.getPermissionNode();
        if (permissionNode != null && !sender.hasPermission(permissionNode)) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use that command.");
            return true;
        }
        final int numArgs = args.length - 1;
        final String[] newArgs = new String[numArgs];
        System.arraycopy(args, 1, newArgs, 0, numArgs);

        command.execute(plugin, sender, newArgs);
        return true;
    }

    public APCommand[] getCommands() {
        return commandArr.clone();
    }

    public static String getFullSyntax(APCommand c) {
        final String syntax = c.getSyntax();
        if (syntax == null) {
            return ChatColor.DARK_GREEN + "/ap " + ChatColor.GRAY + c.getNames()[0] + " (" + c.getPermissionNode() + ")";
        } else {
            return ChatColor.DARK_GREEN + "/ap " + c.getNames()[0] + " " + ChatColor.DARK_PURPLE + syntax + ChatColor.GRAY + " (" + c.getPermissionNode() + ")";
        }
    }
    
	public void showAvailableCommands(CommandSender sender) {
		final List<String> availableCommands = new ArrayList<String>();
		
		for (final APCommand command : plugin.getDispatcher().getCommands()) {
			if (command.getPermissionNode() == null || sender.hasPermission(command.getPermissionNode())) {
				availableCommands.add(APCommandDispatcher.getFullSyntax(command));
			}
		}
		
		if (availableCommands.isEmpty()) {
			sender.sendMessage(plugin.getLogTag() + ChatColor.RED + "You don't have permission to use that command.");
		} else {
            sender.sendMessage(plugin.getLogTag() + "Select a command from the list below: ");
            sender.sendMessage(ChatColor.DARK_RED + "##############################################");
			sender.sendMessage(ChatColor.DARK_RED + "Processing list of commands... done.");
			sender.sendMessage(availableCommands.toArray(new String[availableCommands.size()]));
            sender.sendMessage(ChatColor.DARK_RED + "##############################################");
		}
	}
}
