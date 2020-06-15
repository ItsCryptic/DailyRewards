package com.tek.rewards.main;

import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import com.tek.rcoreui.InterfaceManager;
import com.tek.rewards.Configuration;
import com.tek.rewards.RewardsCommand;
import com.tek.rewards.data.Connector;

public class Main extends JavaPlugin {
	
	private static Main instance;
	private InterfaceManager interfaceManager;
	private Configuration configuration;
	private Connector connector;
	
	@Override
	public void onEnable() {
		instance = this;
		
		getConfig();
		saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		
		interfaceManager = new InterfaceManager(this);
		interfaceManager.register(10);
		
		configuration = new Configuration();
		configuration.load();
		
		connector = new Connector();
		try {
			connector.connect();
			getLogger().log(Level.INFO, "Connection established to the database.");
		} catch (ClassNotFoundException | SQLException e) {
			getLogger().log(Level.SEVERE, "Exception caught while connecting to database.");
			e.printStackTrace();
		}
		
		getCommand("rewards").setExecutor(new RewardsCommand());
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public InterfaceManager getInterfaceManager() {
		return interfaceManager;
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}
	
	public Connector getConnector() {
		return connector;
	}
	
}
