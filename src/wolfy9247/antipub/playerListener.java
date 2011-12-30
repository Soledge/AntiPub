package wolfy9247.antipub;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

public class playerListener extends PlayerListener {
	public static Main plugin;
	protected FileConfiguration config;
	
	public playerListener(Main instance) { 
		plugin = instance;
	}
	
	public String stringToIP (String message) {
		String tmp = message.replaceAll("[^\\d\\.\\:]", "");
		if(tmp.contains(":")) {
			int index = tmp.indexOf(":");
			tmp = tmp.substring(0, index);
			return tmp;
		}
		else return tmp;
	}
	
	@Override
	public void onPlayerChat(PlayerChatEvent event) {
		Player player = event.getPlayer();
		
		String message = stringToIP(event.getMessage());
		
		if(player instanceof Player) {
			if(player.hasPermission("antipub")) {
				event.setCancelled(false);
			}
			else {
					if(message.matches("\\b0*(2(5[0-5]|[0-4]\\d)|1?\\d{1,2})(\\.0*(2(5[0-5]|[0-4]\\d)|1?\\d{1,2})){3}\\b")) {
						/* if(Main.plugin.getConfig().getString("Blocked Message") != null) {
							player.sendMessage(Main.plugin.getConfig().getString("Blocked Message"));
						}
					else {} */
					player.sendMessage("Advertising and/or posting IPv4 addresses is not allowed on  this server!");
					event.setCancelled(true);
				}
				else {
					event.setCancelled(false);
				}
			}
		}
	}
}
