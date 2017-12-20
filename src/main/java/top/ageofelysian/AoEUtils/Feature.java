package top.ageofelysian.AoEUtils;

import org.bukkit.configuration.file.FileConfiguration;

public class Feature {
	
	FileConfiguration config;
	String featureName;
	boolean isEnabled;
	
	public Feature(FileConfiguration config, String featureName) {
		this.config = config;
		this.featureName = featureName;
		this.isEnabled = config.getBoolean(featureName + ".enable", true);
		loadConfig();
	}
	
	public void loadConfig() {}
	
	public void load() {}

}
