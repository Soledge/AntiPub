package wolfy9247.antipub;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Type;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main plugin;
	protected FileConfiguration config;
	
	private PlayerListener playerListener = new playerListener(this);
	
	public final Logger log = Logger.getLogger("Minecraft");
	
	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.log.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " enabled!");
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Type.PLAYER_CHAT, playerListener, Event.Priority.Monitor, this);
	}
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.log.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " disabled!");
		
	}
	
}
