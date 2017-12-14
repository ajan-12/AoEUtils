package top.ageofelysian.AoEUtils;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class AoEUtils extends JavaPlugin{

	public Logger logger = Logger.getLogger("Minecraft");
	private static AoEUtils instance;
	private static FeatureManager featureManager;
	
	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Version "  + pdfFile.getVersion() + " by " + pdfFile.getAuthors() + " Has Been Enabled!");
		instance = this;
		saveDefaultConfig();
		featureManager = new FeatureManager();
		featureManager.registerFeatures(getConfig());
		featureManager.loadFeaturesConfig();
		featureManager.startFeatures();
	}
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has Been Disabled!");
	}
	
	public static AoEUtils getInstance() {
		return instance;
	}
	
	public static FeatureManager getFeatureManager() {
		return featureManager;
	}
	
}
