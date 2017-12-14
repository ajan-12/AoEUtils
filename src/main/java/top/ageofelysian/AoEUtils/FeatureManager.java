package top.ageofelysian.AoEUtils;

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;

public class FeatureManager {
	
	private HashMap<String, Feature> featureList = new HashMap<String, Feature>();
	
	public void registerFeatures(FileConfiguration config) {
		featureList.put("announcement", new Announcement(config, "announcement"));
	}
	
	public void loadFeaturesConfig() {
		for(Feature feature : featureList.values()) {
			feature.loadConfig();
		}
	}
	
	//As not all features have a run method therefore we will call the method manually
	public void startFeatures() {
		if(((Announcement) getFeature("announcement")).isEnabled) {((Announcement) getFeature("announcement")).run();}
	}
	
	public Feature getFeature(String featureName) {
		return featureList.get(featureName);
	}
}
