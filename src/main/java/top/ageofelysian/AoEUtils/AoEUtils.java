package top.ageofelysian.AoEUtils;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class AoEUtils extends JavaPlugin{

	public Logger logger = Logger.getLogger("Minecraft");
	private static AoEUtils instance;
	
	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Version "  + pdfFile.getVersion() + " by " + pdfFile.getAuthors() + " Has Been Enabled!");
		instance = this;
	}
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has Been Disabled!");
	}
	
	public AoEUtils getInstance() {
		return instance;
	}
}
