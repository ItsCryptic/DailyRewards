package com.tek.rewards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import com.tek.rewards.data.Reward;
import com.tek.rewards.data.RewardDelay;
import com.tek.rewards.main.Main;
import com.tek.rewards.main.Reference;

public class Configuration {
	
	private String host;
	private int port;
	private String database;
	private String username;
	private String password;
	private String prefix;
	private String alreadyUsed;
	private String loreOnce, loreHourly, loreDaily, loreWeekly, loreMonthly, loreYearly;
	private int guiHeight;
	private String guiTitle;
	private List<Reward> rewards;
	
	public void load() {
		Main.getInstance().reloadConfig();
		FileConfiguration config = Main.getInstance().getConfig();
		
		host = config.getString("host");
		port = config.getInt("port");
		database = config.getString("database");
		username = config.getString("username");
		password = config.getString("password");
		guiHeight = config.getInt("gui_height");
		guiTitle = Reference.color(config.getString("gui_title"));
		
		prefix = Reference.color(config.getString("prefix"));
		
		alreadyUsed = Reference.color(config.getString("already_used"));
		loreOnce = Reference.color(config.getString("lore_once"));
		loreHourly = Reference.color(config.getString("lore_hourly"));
		loreDaily = Reference.color(config.getString("lore_daily"));
		loreWeekly = Reference.color(config.getString("lore_weekly"));
		loreMonthly = Reference.color(config.getString("lore_monthly"));
		loreYearly = Reference.color(config.getString("lore_yearly"));
		
		rewards = new ArrayList<Reward>();
		if(config.isConfigurationSection("rewards")) {
			ConfigurationSection rewardsSection = config.getConfigurationSection("rewards");
			for(String id : rewardsSection.getKeys(false)) {
				ConfigurationSection rewardSection = rewardsSection.getConfigurationSection(id);
				String name = Reference.color(rewardSection.getString("name"));
				try {
					RewardDelay reward = RewardDelay.valueOf(rewardSection.getString("delay").toUpperCase());
					int x = rewardSection.getInt("x");
					int y = rewardSection.getInt("y");
					
					List<ItemStack> items = new ArrayList<ItemStack>();
					if(rewardSection.isConfigurationSection("items")) {
						ConfigurationSection itemsSection = rewardSection.getConfigurationSection("items");
						for(String materialString : itemsSection.getKeys(false)) {
							String[] materialTokens = materialString.split(":");
							if(materialTokens.length == 2) {
								int count = itemsSection.getInt(materialString);
								String key = materialTokens[0];
								byte data = Byte.parseByte(materialTokens[1]);
								XMaterial mat = XMaterial.matchXMaterial(key, data);
								
								if(mat != null) {
									ItemStack item = mat.parseItem();
									item.setAmount(count);
									items.add(item);
								} else {
									Main.getInstance().getLogger().log(Level.WARNING, "Failed to load reward item \"" + materialString + "\": Invalid item.");
								}
							} else {
								Main.getInstance().getLogger().log(Level.WARNING, "Failed to load reward item \"" + materialString + "\": Needs NAME:DATA");
							}
						}
					}
					
					List<String> commands;
					if(rewardSection.isList("commands")) {
						commands = rewardSection.getStringList("commands");
					} else {
						commands = Arrays.asList();
					}
					
					rewards.add(new Reward(id, name, items, commands, reward, x, y));
					Main.getInstance().getLogger().log(Level.INFO, "Loaded reward \"" + id + "\" with " + items.size() + " items.");
				} catch(IllegalArgumentException e) {
					Main.getInstance().getLogger().log(Level.SEVERE, "Failed to load reward \"" + id + "\": Invalid delay value.");
				}
			}
		}
	}
	
	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getDatabase() {
		return database;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public String getPrefix() {
		return prefix;
	}

	public String getAlreadyUsed() {
		return alreadyUsed;
	}

	public String getLoreOnce() {
		return loreOnce;
	}

	public String getLoreHourly() {
		return loreHourly;
	}

	public String getLoreDaily() {
		return loreDaily;
	}

	public String getLoreWeekly() {
		return loreWeekly;
	}

	public String getLoreMonthly() {
		return loreMonthly;
	}

	public String getLoreYearly() {
		return loreYearly;
	}

	public int getGuiHeight() {
		return guiHeight;
	}
	
	public String getGuiTitle() {
		return guiTitle;
	}
	
	public List<Reward> getRewards() {
		return rewards;
	}
	
}
