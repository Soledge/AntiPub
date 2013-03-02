package main.java.com.wolfy9247.AntiPub;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class APMessage extends AntiPub {
	
	private AntiPub plugin;
	private FileConfiguration config;
	private String message;
	private Player player;
    
	private final String IPv4Regex = "\\b0*(2(5[0-5]|[0-4]\\d)|1?\\d{1,2})(\\.0*(2(5[0-5]|[0-4]\\d)|1?\\d{1,2})){3}\\b";
    private final String URLRegex = "^(((ht|f)tp(s?))\\://)?([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&amp;%\\$\\-]+)*@)*((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|localhost|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(\\:[0-9]+)*(/($|[a-zA-Z0-9\\.\\,\\?\\'\\\\\\+&amp;%\\$#\\=~_\\-]+))*$";
	    
	public APMessage(AsyncPlayerChatEvent e) {
		plugin = AntiPub.getInstance();
	    config = plugin.getConfiguration();
	    message = e.getMessage();
	    player = e.getPlayer();
	}
	
	public boolean isAllowed() {
		if(player.hasPermission("antipub.bypass")) {
			return true;
		}
		else {
			String type = getType();

			if(type != null) {
				if(isExempt(message))
					return true;
				else 
					return false;
			}
		}
		return true;
	}
	
	public String getType() {
		if(isValidIPv4())
			return "IPv4";
		else if(isURL())
			return "URL";
		else 
			return "";
	}
	
	public ConfigurationSection getSection() {
		return config.getConfigurationSection(getType());
	}
	
	public boolean isExempt(String message) {
		List<?> exemptList = config.getList(getType() + ".exemptions");
		for(Object obj : exemptList) {
			if(message.contains(obj.toString()))
				return true;
		}
		return false;
	}
	    
	public String toIPv4 () {
		String tmp = message.replaceAll("[^\\d\\.\\:]", "");
		if(tmp.contains(":")) {
			int index = tmp.indexOf(":");
			tmp = tmp.substring(0, index);
			return tmp;
		}
		else return tmp;
	}
		
	// @TODO: Detect .com, .url, .org, etc. within the string as a URL. 
	public boolean isURL() {
		if(pullLinks(message).isEmpty()) {
			/* The function returning an empty ArrayList would mean
			* that no links were able to be found in the string
			* so nothing was added to the list.
			*/
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean isValidIPv4() {
		if(message.matches(IPv4Regex))
			return true;
		else
			return false;
	}
	
	private boolean checkFilters() {
		List<?> filterList = config.getList("Global.custom-filters");
		// Iterator<?> iter = filterList.iterator();
		
		for(Object regex : filterList) {
			if(message.matches(regex.toString()))
				return false;
		}
		
		return true;
	}
	
	private ArrayList<String> pullLinks(String text) {
		ArrayList<String> links = new ArrayList<String>();
		String regex = URLRegex;
		
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
		
	public String terminateMessage(String string) {
		String advertAlert = "Advertising and/or posting IPv4 addresses is not allowed on  this server!";
		string = plugin.getConfig().getString(string);
		if(!string.equalsIgnoreCase(advertAlert) && !string.isEmpty()) {
			return string;
		}
		else {
			return advertAlert;
		}
	}
}
