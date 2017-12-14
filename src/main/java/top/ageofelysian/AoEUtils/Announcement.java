package top.ageofelysian.AoEUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Announcement extends Feature{
	
	private HashMap<String, List<String>> announcements;
	private int interval;
	private Random random;
	
	public Announcement(FileConfiguration config, String featureName) {
		super(config, featureName);
		random = new Random();
	}
	
	@Override
	public void loadConfig() {
		
		if(!isEnabled) {
			return;
		}
		
		interval = config.getInt(featureName + ".interval");
		
		announcements = new HashMap<String, List<String>>();
		
		for(String group : config.getConfigurationSection(featureName + ".groups").getValues(false).keySet()) {
			List<String> groupAnnouncements = new ArrayList<String>();
			for(String announcement : config.getStringList(featureName + ".groups." + group)) {
				groupAnnouncements.add(ChatColor.translateAlternateColorCodes('&', announcement));
			}
			
			if (!groupAnnouncements.isEmpty()) {
				announcements.put(group, groupAnnouncements);
			}
			
		}
	}
	
	public void run() {
		Bukkit.getScheduler().runTaskTimer(AoEUtils.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				for(Entry<String, List<String>> entry : announcements.entrySet()) {
					List<String> groupAnnouncements = entry.getValue();
					Bukkit.broadcast(groupAnnouncements.get(random.nextInt(groupAnnouncements.size())), "aoeutils." + featureName + "." + entry.getKey());
				}
			}
		}, 20 * interval, 20 * interval);
	}
}
