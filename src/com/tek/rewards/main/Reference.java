package com.tek.rewards.main;

import org.bukkit.ChatColor;

public class Reference {
	
	public static final String INVALID_SYNTAX = color("&cInvalid syntax.");
	public static final String NO_PERMISSIONS = color("&cYou do not have the permission to use this.");
	public static final String PERMISSION_RELOAD  = "dailyrewards.reload";
	public static final String PERMISSION_REWARDS = "dailyrewards.rewards";
	
	public static String color(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
	
}
