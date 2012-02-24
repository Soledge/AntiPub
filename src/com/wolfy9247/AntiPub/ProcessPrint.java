package com.wolfy9247.AntiPub;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ProcessPrint extends Main {
	public static ProcessPrint instance;
	
	protected FileConfiguration config;
	
	public static String processPrint(String printout, Player user, String message) {
		
		String temp = printout;
		
		int index = temp.indexOf("*user");
		
		if(index != -1) {
			if(user == null) {
				log.warning(logTag + "Invalid '*user' in configuration!");
			}
			else {
				temp = temp.replace("*user", user.getName());
			}
		}
		
		index = temp.indexOf("*message");
		
		if(index != -1) {
			if(message != null) {
				temp = temp.replace("*message", message);
			}
		}
		
		index = temp.indexOf("*ip");
		
		if(index != -1) {
			temp = temp.replace("*ip", "" + user.getAddress().getAddress());
		}
		
		index = temp.indexOf("*hostname");
		
		if(index != -1) {
			temp = temp.replace("*hostname", "" + user.getAddress().getAddress().getHostName());
		}
		
		index  = temp.indexOf("*port");
		
		if(index != -1) {
			temp = temp.replace("*port", "" + user.getAddress().getPort());
		}
		
		index = temp.indexOf("[*tag]");
		
		if(index != -1) {
			temp = temp.replace("[*tag]", "[AntiPub]");
		}
		
		index = temp.indexOf("&");
		
		if(index != -1) {
			temp = convertToColor(temp);
		}
		return temp;
	}
	
	public static String colorsChat(String message) {
		char[] charMessage = message.toCharArray();
		String finalMessage = "";
		int color = 1;
		for(int i = 0; i < charMessage.length; i++) {
			finalMessage += "§" + Integer.toHexString(color);
			finalMessage += charMessage[i];
			color++;
			if(color >= 16) {
				color = 1;
			}
		}
		return finalMessage;
	}
	
	public static String convertToColor(String withoutColor) {
		int count = withoutColor.length();
		char[] colorless = withoutColor.toCharArray();
		String withColor = "";
		for(int i = 0; i < count; i++) {
			if(isColorChar(colorless[i]) && (i+1) < count) {
				if(isColorNumber(colorless[i+1])) {
					withColor += "§";
				}
				else if(Character.toLowerCase(colorless[i+1]) == 'r') {
					boolean found = false;
					int indexOfColorChar = i+2;
					String rainbowString = new String(colorless);
					
					while(indexOfColorChar < count && !found) {
						found = isColorChar(colorless[indexOfColorChar]) && (isColorNumber(colorless[indexOfColorChar+1]) || Character.toLowerCase(colorless[indexOfColorChar+1]) == 'r');
						indexOfColorChar++;
					}
					if(found) {
						indexOfColorChar--;
					}
					rainbowString = colorsChat(rainbowString.substring(i+2, indexOfColorChar));
					withColor += rainbowString;
               
					i = indexOfColorChar - 1;
               
				} 
				else {
					withColor += colorless[i];
				}
			} 
			else {
				withColor += colorless[i];
			}
		}
		return withColor;
	}
	
	public static boolean isColorChar(char c) {
		String[] chars = {"&","^"};
		boolean charUsed = false;
			for(int i = 0; (i < chars.length && !charUsed); i++) {
				if(c == chars[i].toCharArray()[0]) {
					charUsed = true;
				}
			}
		return charUsed;
	}
	
	public static boolean isColorNumber(char c) {
		c = Character.toLowerCase(c);
		return ((c == '0') || (c == '1') || (c == '2') || (c == '3') || (c == '4') || (c == '5') || (c == '6') || (c == '7') || (c == '8') || (c == '9') || (c == 'a') || (c == 'b') || (c == 'c') || (c == 'd') || (c == 'e') || (c == 'f'));
	}
}
