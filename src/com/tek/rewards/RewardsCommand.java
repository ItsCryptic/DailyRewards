package com.tek.rewards;

import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tek.rewards.main.Main;
import com.tek.rewards.main.Reference;

public class RewardsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Configuration config = Main.getInstance().getConfiguration();
		String PREFIX = config.getPrefix();
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(PREFIX + Reference.color("&cYou must be a player to use this."));
			return false;
		}
		
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("reload")) {
				if(sender.hasPermission(Reference.PERMISSION_RELOAD)) {
					Main.getInstance().getConfiguration().load();
					try {
						Main.getInstance().getConnector().reconnect();
					} catch (ClassNotFoundException | SQLException e) {
						Main.getInstance().getLogger().log(Level.SEVERE, "Exception caught while connecting to database.");
						e.printStackTrace();
					}
					sender.sendMessage(PREFIX + Reference.color("&aThe configuration has been reloaded!"));
				} else {
					sender.sendMessage(PREFIX + Reference.NO_PERMISSIONS);
				}
			} else if(args[0].equalsIgnoreCase("debug")) {
				if(sender.hasPermission(Reference.PERMISSION_RELOAD)) {
					sender.sendMessage(Reference.color("&8&lDebug Information"));
					sender.sendMessage(Reference.color("&6 Database Connection: &a" + (Main.getInstance().getConnector().isOpened() ? "OPENED" : "CLOSED")));
					sender.sendMessage(Reference.color("&6 Reward Count: &a" + Main.getInstance().getConfiguration().getRewards().size()));
				} else {
					sender.sendMessage(PREFIX + Reference.NO_PERMISSIONS);
				}
			} else {
				sender.sendMessage(PREFIX + Reference.INVALID_SYNTAX);
			}
		} else if(args.length == 0) {
			if(sender.hasPermission(Reference.PERMISSION_REWARDS)) {
				Main.getInstance().getInterfaceManager().openInterface((Player) sender, new RewardsInterface());
			} else {
				sender.sendMessage(PREFIX + Reference.NO_PERMISSIONS);
			}
		} else {
			sender.sendMessage(PREFIX + Reference.INVALID_SYNTAX);
		}
		
		return false;
	}

}
