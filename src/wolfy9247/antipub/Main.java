package wolfy9247.antipub;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static Main instance;
	protected FileConfiguration config;
	
	public final Logger log = Logger.getLogger("Minecraft");
    public static String logTag = "[CommandAlerter] ";
	
	public String getConfigString(String string) {
		string = getConfig().getString(string);
		return string;
	}
	
	public boolean getConfigBoolean(String path) {
		boolean bool = getConfig().getBoolean(path);
		return bool;
	}
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(logTag + pdfFile.getName() + " v" + pdfFile.getVersion() + " disabled!");
	}
	
	@Override
	public void onEnable() {
		Main.instance = this;
		PluginDescriptionFile pdfFile = this.getDescription();
		getConfig().options().copyDefaults(true);
		saveConfig();
		getServer().getPluginManager().registerEvents(new AntiPubListener(), this);
		log.info(logTag + pdfFile.getName() + " v" + pdfFile.getVersion() + " enabled!");
		log.info(logTag + "DEBUG | Boolean Test: "+getConfig().getBoolean("AntiPub.Block IPv4"));
	}
}
