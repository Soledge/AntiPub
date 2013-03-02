package main.java.com.wolfy9247.AntiPub;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Format extends AntiPub {
	
	private final String[] tokens = {"<user>", "<message>", "<ip>", "<hostname>", "<port>"};
	private String message;
	private Player player;
	
	public Format(AsyncPlayerChatEvent e) {
		player = e.getPlayer();
	}
	
	public String formatMessage(String m) {
		message = m;
		for(int i = 0; i < tokens.length; i++) {
			switch(i) {
				case 1: message.replace(tokens[i], player.getName());
						break;
				case 2: message.replace(tokens[i], message);
						break;
				case 3: message.replace(tokens[i], player.getAddress().toString());
						break;
				case 4: message.replace(tokens[i], player.getAddress().getHostName());
						break;
				case 5: message.replace(tokens[i], new Integer(player.getAddress().getPort()).toString());
						break;
			}
		}
		return message;
	}

}
