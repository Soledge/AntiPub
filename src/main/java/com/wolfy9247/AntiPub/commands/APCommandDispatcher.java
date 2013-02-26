package main.java.com.wolfy9247.AntiPub.commands;

import java.util.LinkedHashMap;
import java.util.Map;

import main.java.com.wolfy9247.AntiPub.AntiPub;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class APCommandDispatcher {
	private static final APCommand[] commandArr = {new ReloadCommand(), new HelpCommand()};
    private final Map<String, APCommand> commands = new LinkedHashMap<String, APCommand>();
    private final AntiPub plugin;

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
            return ChatColor.AQUA + "/ap " + c.getNames()[0];
        } else {
            return ChatColor.AQUA + "/ap " + c.getNames()[0] + " " + ChatColor.BLUE + syntax;
        }
    }
}
