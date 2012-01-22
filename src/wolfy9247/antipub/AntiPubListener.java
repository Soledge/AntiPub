package wolfy9247.antipub;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class AntiPubListener implements Listener {
	public static Main plugin;
	
	public String stringToIP (String message) {
		String tmp = message.replaceAll("[^\\d\\.\\:]", "");
		if(tmp.contains(":")) {
			int index = tmp.indexOf(":");
			tmp = tmp.substring(0, index);
			return tmp;
		}
		else return tmp;
	}
	
	private ArrayList<String> pullLinks(String text) {
		ArrayList<String> links = new ArrayList<String>();
		 
		String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		while(m.find()) {
		String urlStr = m.group();
		if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
			urlStr = urlStr.substring(1, urlStr.length() - 1);
		}
		links.add(urlStr);
	}
		return links;
}
	// @TODO: Detect .com, .url, .org, etc. within the string as a URL. 
	public boolean isURL(String message) {
		if(pullLinks(message).isEmpty()) {
			return false;
		}
		/* else if(message.contains("[dot]")) {
			return true;
		}*/
		else {
			return true;
		}
	}
	
	public String terminateMessage(String string) {
		String advertAlert = "Advertising and/or posting IPv4 addresses is not allowed on  this server!";
		string = plugin.getConfigString(string);
		if(!string.equalsIgnoreCase(advertAlert) && !string.isEmpty()) {
			return string;
		}
		else {
			return advertAlert;
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChat(PlayerChatEvent event) {
		Player player = event.getPlayer();
		
		String message = stringToIP(event.getMessage());
		String regex = "\\b0*(2(5[0-5]|[0-4]\\d)|1?\\d{1,2})(\\.0*(2(5[0-5]|[0-4]\\d)|1?\\d{1,2})){3}\\b";
		
		if(player instanceof Player) {
			if(player.hasPermission("antipub.*") || player.hasPermission("antipub.bypass")) {
				event.setCancelled(false);
			}
			else if(message.matches(regex) && plugin.getConfigBoolean("AntiPub.Block IPv4")) {
					player.sendMessage(terminateMessage("AntiPub.Blocked Message"));
					event.setCancelled(true);
			}
			else if(isURL(event.getMessage()) && plugin.getConfigBoolean("AntiPub.Block Domains")) {
				player.sendMessage(terminateMessage("AntiPub.Blocked Message"));
				event.setCancelled(true);
			}
			else {
				event.setCancelled(false);
			}
		}
	}
}
