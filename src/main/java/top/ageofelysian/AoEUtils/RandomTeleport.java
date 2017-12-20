package top.ageofelysian.AoEUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RandomTeleport extends Feature implements CommandExecutor{
	
	HashMap<String, int[]> worldConfig;
	HashMap<UUID, Double[]> playerData; //First double is the player's last use, second double is player's remaining uses
	int cooldown, maxUses, timeToReplenishUses;
	boolean replenishUses;
	long lastReplenish;

	public RandomTeleport(FileConfiguration config, String featureName) {
		super(config, featureName);
		playerData = new HashMap<UUID, Double[]>();
	}
	
	@Override
	public void loadConfig() {
		
		cooldown = config.getInt(featureName + ".cooldown");
		replenishUses = config.getBoolean(featureName + ".replenishUses");
		timeToReplenishUses = config.getInt(featureName + ".timeToReplenishUses");
		maxUses = config.getInt(featureName + ".maxUses");
		
		worldConfig = new HashMap<String, int[]>();
		
		for(String world : config.getConfigurationSection(featureName + ".worlds").getValues(false).keySet()) {
			int[] options = new int[3];
			options[0] = config.getInt(featureName + ".worlds." + world + ".centerX");
			options[1] = config.getInt(featureName + ".worlds." + world + ".centerZ");
			options[2] = config.getInt(featureName + ".worlds." + world + ".maxRange");
			worldConfig.put(world, options);
		}
	}
	
	@Override
	public void load() {
		AoEUtils.getInstance().getCommand("randomteleport").setExecutor(this);
		AoEUtils.getInstance().getCommand("randomteleport").setAliases(Arrays.asList("rtp"));
		AoEUtils.getInstance().getCommand("randomteleport").setDescription("Teleports a player to a random location in the current world.");
		AoEUtils.getInstance().getCommand("randomteleport").setUsage("To teleport (/randomteleport) or to find out your remaining uses (/randomteleport info).");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			checkPlayer(player);
			updateUses();
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("info")) {
					if(player.hasPermission("aoeutils." + featureName + "." + "info")) {
						player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "RandomTeleportation > " + ChatColor.YELLOW + "Number of uses remaining: " + playerData.get(player.getUniqueId())[1].longValue());
						
						if (replenishUses) {
							
							// Generate time to next replenish
							long nextReplenish = (lastReplenish + timeToReplenishUses * 1000) - System.currentTimeMillis();
							long nextReplenishHours = TimeUnit.MILLISECONDS.toHours(nextReplenish) % 24;
							long nextReplenishMinutes = TimeUnit.MILLISECONDS.toMinutes(nextReplenish) % 60;
							long nextReplenishSeconds = TimeUnit.MILLISECONDS.toSeconds(nextReplenish) % 60;
							
							StringBuilder nextReplenishBuilder = new StringBuilder();
							nextReplenishBuilder
									.append(ChatColor.GREEN + "" + ChatColor.BOLD + "RandomTeleportation > " + ChatColor.YELLOW + "Next use in: ")
									.append(((nextReplenishHours == 0) ? "" : nextReplenishHours + ((nextReplenishHours == 1) ? " hour " : " hours ")))
									.append(((nextReplenishMinutes == 0) ? "" : nextReplenishMinutes + ((nextReplenishMinutes == 1) ? " minute " : " minutes ")))
									.append(((nextReplenishSeconds == 0) ? "now" : nextReplenishSeconds + ((nextReplenishSeconds == 1) ? " second " : " seconds ")));
							player.sendMessage(nextReplenishBuilder.toString());
						}
						else {
							player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "RandomTeleportation > " + ChatColor.YELLOW + "Next use in: never");
						}
						
						//Generate time till cooldown ends
						if ((System.currentTimeMillis() - playerData.get(player.getUniqueId())[0].longValue()) / 1000 <= cooldown) {
							
							long cooldownLeft = cooldown * 1000 - (System.currentTimeMillis() - playerData.get(player.getUniqueId())[0].longValue());
							long cooldownLeftHours = TimeUnit.MILLISECONDS.toHours(cooldownLeft) % 24;
							long cooldownLeftMinutes = TimeUnit.MILLISECONDS.toMinutes(cooldownLeft) % 60;
							long cooldownLeftSeconds = TimeUnit.MILLISECONDS.toSeconds(cooldownLeft) % 60;
							
							StringBuilder cooldownLeftBuilder = new StringBuilder();
							cooldownLeftBuilder.append(ChatColor.GREEN + "" + ChatColor.BOLD + "RandomTeleportation > " + ChatColor.YELLOW + "Your cooldown ends in: ")
											   .append(((cooldownLeftHours == 0) ? "" : cooldownLeftHours + ((cooldownLeftHours == 1) ? " hour " : " hours ")))
											   .append(((cooldownLeftMinutes == 0) ? "" : cooldownLeftMinutes + ((cooldownLeftMinutes == 1) ? " minute " : " minutes ")))
											   .append(((cooldownLeftSeconds == 0) ? "now" : cooldownLeftSeconds + ((cooldownLeftSeconds == 1) ? " second " : " seconds ")));
							player.sendMessage(cooldownLeftBuilder.toString());
						}
						else if(System.currentTimeMillis() - playerData.get(player.getUniqueId())[0].longValue() > cooldown) {
							player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "RandomTeleportation > " + ChatColor.YELLOW + "You currently have no cooldown.");
						}
					} 
					else {
						player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "RandomTeleportation > " + ChatColor.RED + "You do not have permission to use this command." );
					}
					return true;
				}
			}
			if (player.hasPermission("aoeutils." + featureName + "." + player.getWorld().getName())) {
				
				if ((System.currentTimeMillis() - playerData.get(player.getUniqueId())[0]) / 1000 > cooldown) {
					if (playerData.get(player.getUniqueId())[1] > 0) {
						if (worldConfig.containsKey(player.getWorld().getName())) {

							int[] config = worldConfig.get(player.getWorld().getName());
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spreadplayers " + config[0] + " " + config[1] + " " + "0.0 " + config[2] + " " + "false " + player.getName());

							playerData.get(player.getUniqueId())[0] = Double.valueOf(System.currentTimeMillis());
							playerData.get(player.getUniqueId())[1] = playerData.get(player.getUniqueId())[1] - 1;
						} 
						else {
							player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "RandomTeleportation > " + ChatColor.RED + "RandomTeleport is not enabled in this world.");
						}
					}
					else {
						player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "RandomTeleportation > " + ChatColor.RED + "You have no uses left. Use /randomteleport info to find out when will you get a use." );
					}
				}
				else {
					player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "RandomTeleportation > " + ChatColor.RED + "Please wait for your cooldown to end to use this command. Use /randomteleport info to find out when your cooldown will end." );
				}
			}
			else {
				player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "RandomTeleportation > " + ChatColor.RED + "You do not have permission to use this command.");
			}
		}
		else {
			sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "RandomTeleportation > " + ChatColor.RED + "Only players can run this command.");
		}
		return true;
	}
	
	public void updateUses() {
		if (replenishUses) {
			if ((System.currentTimeMillis() - lastReplenish) / 1000 > timeToReplenishUses) {

				Long usesToAdd = (System.currentTimeMillis() - lastReplenish) / 1000 / timeToReplenishUses;

				for (Entry<UUID, Double[]> entry : playerData.entrySet()) {
					if (entry.getValue()[1] == maxUses) {
						continue;
					}
					entry.getValue()[1] = (entry.getValue()[1] + usesToAdd < maxUses) ? entry.getValue()[1] + usesToAdd : maxUses;
				}

				lastReplenish = System.currentTimeMillis();
			}
		}
	}
	
	public void checkPlayer(Player player) {
		if(!playerData.containsKey(player.getUniqueId())) {
			playerData.put(player.getUniqueId(), new Double[] {Double.valueOf(0), Double.valueOf(maxUses)});
		}
	}
	
}
