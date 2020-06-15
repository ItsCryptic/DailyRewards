package com.tek.rewards.data;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.stream.Collectors;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.tek.rewards.Configuration;
import com.tek.rewards.main.Main;

public class Connector {
	
	private Connection connection;
	private Statement statement;
	
	public void connect() throws SQLException, ClassNotFoundException {
		if(connection != null && !connection.isClosed()) {
			return;
		}
		
		Configuration config = Main.getInstance().getConfiguration();
		
		Class.forName("com.mysql.jdbc.Driver");
		connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + config.getHost() + ":" + config.getPort() + "/" + config.getDatabase() + "?user=" + config.getUsername() + "&password=" + config.getPassword());
	
		statement = (Statement) connection.createStatement();
		for(String rewardId : config.getRewards().stream().map(Reward::getId).collect(Collectors.toList())) {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + rewardId + "(uuid CHAR(32) NOT NULL, last_usage BIGINT NOT NULL)");
		}
		statement.close();
	}
	
	public void reconnect() throws SQLException, ClassNotFoundException {
		if(connection != null && !connection.isClosed()) {
			connection.close();
		}
		
		connect();
	}
	
	public long getLastUsage(UUID uuid, String table) {
		if(isOpened()) {
			try {
				statement = (Statement) connection.createStatement();
				ResultSet set = statement.executeQuery("SELECT * FROM " + table + " WHERE uuid = \"" + uuid.toString().replace("-", "") + "\"");
				set.next();
				long lastUsage = set.getLong(2);
				statement.close();
				return lastUsage;
			} catch (SQLException e) {
				e.printStackTrace();
				return System.currentTimeMillis();
			}
		} else {
			return System.currentTimeMillis();
		}
	}
	
	public void setLastUsage(UUID uuid, String table) {
		if(isOpened()) {
			if(containsUser(uuid, table)) {
				try {
					statement = (Statement) connection.createStatement();
					statement.executeUpdate("UPDATE " + table + " SET last_usage = " + System.currentTimeMillis() + " WHERE uuid = \"" + uuid.toString().replace("-", "") + "\"");
					statement.close();
				} catch (SQLException e) { }
			} else {
				try {
					statement = (Statement) connection.createStatement();
					statement.executeUpdate("INSERT INTO " + table + " VALUES ('" + uuid.toString().replace("-", "") + "', " + System.currentTimeMillis() + ")");
					statement.close();
				} catch (SQLException e) { }
			}
		}
	}
	
	public boolean containsUser(UUID uuid, String table) {
		if(isOpened()) {
			try {
				statement = (Statement) connection.createStatement();
				ResultSet set = statement.executeQuery("SELECT * FROM " + table + " WHERE uuid = \"" + uuid.toString().replace("-", "") + "\"");
				boolean contains = set.next();
				statement.close();
				return contains;
			} catch (SQLException e) {
				return true;
			}
		} else {
			return true;
		}
	}
	
	public boolean isOpened() {
		try {
			return connection != null && !connection.isClosed();
		} catch (SQLException e) {
			return false;
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
}
