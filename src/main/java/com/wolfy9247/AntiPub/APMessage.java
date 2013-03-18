package com.wolfy9247.AntiPub;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class APMessage {
	
	private AntiPub plugin;
	private FileConfiguration config;
	private String message;
	private Player player;
    
	private final String IPv4Regex = "\\b0*(2(5[0-5]|[0-4]\\d)|1?\\d{1,2})(\\.0*(2(5[0-5]|[0-4]\\d)|1?\\d{1,2})){3}\\b";
    private final String URLRegex = "^(((ht|f)tp(s?))\\://)?([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&amp;%\\$\\-]+)*@)*((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|localhost|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(\\:[0-9]+)*(/($|[a-zA-Z0-9\\.\\,\\?\\'\\\\\\+&amp;%\\$#\\=~_\\-]+))*$";

    public APMessage(String str) {
    	plugin = AntiPub.getInstance();
    	config = plugin.getConfig();
    	message = str;
    }
    
	public APMessage(AntiPub ap, AsyncPlayerChatEvent e) {
		plugin = ap;
	    config = plugin.getConfig();
	    message = e.getMessage();
	    player = e.getPlayer();
	}
	
	public boolean isAllowed() {
		if(player.hasPermission("antipub.bypass")) {
			return true;
		}
		else {
			String type = getType();

			if(type != "") {
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
		//List<?> exemptList = config.getList(getType() + ".exemptions");
		
		for(Object obj : getSection().getList("exemptions")) {
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
		if(pullLinks(message).isEmpty())
			return false;
		else
			return true;
	}
	
	public boolean isValidIPv4() {
		if(pullIPs(message).isEmpty()) {
			/* The function returning an empty ArrayList would mean
			* that no IP's were able to be found in the string
			* so nothing was added to the list.
			*/
			return false;
		}
		else {
			return true;
		}
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
	
	private ArrayList<String> pullLinks(String str) {
		ArrayList<String> links = new ArrayList<String>();
		String regex = URLRegex;
		
		String[] strArray = str.split("\\s");
		for(String strPart : strArray) {
			if(strPart.matches(regex))
				links.add(strPart);
		}
		return links;
		
	}
	
	private ArrayList<String> pullIPs(String str) {
		ArrayList<String> strings = new ArrayList<String>();
		
		Pattern p = Pattern.compile(IPv4Regex);
		Matcher m = p.matcher(str);
		
		while(m.find()) {
			for(int i = 0; i <= m.groupCount(); i++) {
				String result = m.group(i);
				strings.add(result);
			}
		}
		
		Bukkit.broadcastMessage("strings: " + strings);
		return strings;
	}
}
