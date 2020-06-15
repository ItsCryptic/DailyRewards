package com.tek.rewards.data;

import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.tek.rewards.Configuration;
import com.tek.rewards.XMaterial;
import com.tek.rewards.main.Main;

public class Reward {
	
	private String id;
	private String name;
	private List<ItemStack> items;
	private List<String> commands;
	private RewardDelay delay;
	private int x;
	private int y;
	
	public Reward(String id, String name, List<ItemStack> items, List<String> commands, RewardDelay delay, int x, int y) {
		this.id = id;
		this.name = name;
		this.items = items;
		this.commands = commands;
		this.delay = delay;
		this.x = x;
		this.y = y;
	}

	public ItemStack formatAvailableIcon() {
		ItemStack item = XMaterial.CHEST_MINECART.parseItem();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack formatUnavailableIcon() {
		Configuration config = Main.getInstance().getConfiguration();
		
		ItemStack item = XMaterial.MINECART.parseItem();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		
		if(delay.equals(RewardDelay.ONCE)) {
			meta.setLore(Arrays.asList(config.getAlreadyUsed(), config.getLoreOnce()));
		} else if(delay.equals(RewardDelay.HOURLY)) {
			meta.setLore(Arrays.asList(config.getAlreadyUsed(), config.getLoreHourly()));
		} else if(delay.equals(RewardDelay.DAILY)) {
			meta.setLore(Arrays.asList(config.getAlreadyUsed(), config.getLoreDaily()));
		} else if(delay.equals(RewardDelay.WEEKLY)) {
			meta.setLore(Arrays.asList(config.getAlreadyUsed(), config.getLoreWeekly()));
		} else if(delay.equals(RewardDelay.MONTHLY)) {
			meta.setLore(Arrays.asList(config.getAlreadyUsed(), config.getLoreMonthly()));
		} else if(delay.equals(RewardDelay.YEARLY)) {
			meta.setLore(Arrays.asList(config.getAlreadyUsed(), config.getLoreYearly()));
		}
		
		item.setItemMeta(meta);
		return item;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<ItemStack> getItems() {
		return items;
	}
	
	public List<String> getCommands() {
		return commands;
	}

	public RewardDelay getDelay() {
		return delay;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
