package main.java.com.wolfy9247.AntiPub;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessFilters extends AntiPub {
	public static ProcessFilters instance;
	public static AntiPub plugin;
	
	public String stringToIP (String message) {
		String tmp = message.replaceAll("[^\\d\\.\\:]", "");
		if(tmp.contains(":")) {
			int index = tmp.indexOf(":");
			tmp = tmp.substring(0, index);
			return tmp;
		}
		else return tmp;
	}
	
	private static ArrayList<String> pullLinks(String text) {
		ArrayList<String> links = new ArrayList<String>();
		 
		// String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
		String regex = "^(((ht|f)tp(s?))\\://)?([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&amp;%\\$\\-]+)*@)*((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|localhost|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(\\:[0-9]+)*(/($|[a-zA-Z0-9\\.\\,\\?\\'\\\\\\+&amp;%\\$#\\=~_\\-]+))*$";
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
	public static boolean isURL(String message) {
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
	
	public static boolean isValidIPv4(String message) {
		plugin = AntiPub.instance;
		config = plugin.getConfig();
		String regex = "\\b0*(2(5[0-5]|[0-4]\\d)|1?\\d{1,2})(\\.0*(2(5[0-5]|[0-4]\\d)|1?\\d{1,2})){3}\\b";
		boolean configOverride = config.getBoolean("AntiPub.IPv4.Custom Filter Override");
		if(!(config.getList("AntiPub.IPv4.Custom Filters").isEmpty())) {
            List<?> filterList = config.getList("AntiPub.IPv4.Custom Filters");
			Iterator<?> iter = filterList.iterator();
			while(iter.hasNext()) {
				regex = iter.next().toString();
				if(message.matches(regex)) {
					return true;
				}
				else {
					if(!configOverride) {
						break;
					}
					else {
						return false;
					}
				}
			}
		}
		if(message.matches(regex) & !configOverride) {
			return true;
		}
		else {
			return false;
		}
	}
}
