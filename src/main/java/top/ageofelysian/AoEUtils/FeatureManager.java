package top.ageofelysian.AoEUtils;

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;

public class FeatureManager {
	
	private HashMap<String, Feature> featureList = new HashMap<String, Feature>();
	
	public void registerFeatures(FileConfiguration config) {
		featureList.put("announcement", new Announcement(config, "announcement"));
		featureList.put("randomteleport", new RandomTeleport(config, "randomteleport"));
	}
	
	public void loadFeaturesConfig() {
		for(Feature feature : featureList.values()) {
			if (feature.isEnabled) {
				feature.loadConfig();
			}
		}
	}
	
	//As not all features have a run method therefore we will call the method manually
	public void startFeatures() {
		for(Feature feature : featureList.values()) {
			if (feature.isEnabled) {
				feature.load();
			}
		}
	}
	
	public Feature getFeature(String featureName) {
		return featureList.get(featureName);
	}
}
