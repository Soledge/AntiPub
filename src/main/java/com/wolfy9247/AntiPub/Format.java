package main.java.com.wolfy9247.AntiPub;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Format {
	
	private final String[] tokens = {"<user>", "<message>", "<ip>", "<hostname>", "<port>"};
	private String message;
	private Player player;
	private String playerMessage;
	
	public Format(AsyncPlayerChatEvent e) {
		player = e.getPlayer();
		playerMessage = e.getMessage();
	}
	
	public String formatMessage(String str) {
		message = str;
		for(int i = 0; i < tokens.length; i++) {
			switch(i) {
				case 0: message = message.replace(tokens[i], player.getName());
						break;
				case 1: message = message.replace(tokens[i], "\"" + playerMessage + "\"");
						break;
				case 2: message = message.replace(tokens[i], player.getAddress().getAddress().getHostAddress());
						break;
				case 3: message = message.replace(tokens[i], player.getAddress().getAddress().getCanonicalHostName());
						break;
				case 4: message = message.replace(tokens[i], new Integer(player.getAddress().getPort()).toString());
						break;
			}
		}
		return message;
	}

}
