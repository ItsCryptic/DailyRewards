package com.tek.rewards;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.tek.rcoreui.InterfaceComponent;
import com.tek.rcoreui.InterfaceState;
import com.tek.rcoreui.components.SwitchComponent;
import com.tek.rewards.data.Reward;
import com.tek.rewards.data.RewardDelay;
import com.tek.rewards.main.Main;

public class RewardsInterface extends InterfaceState {

	public RewardsInterface() {
		super(Main.getInstance().getConfiguration().getGuiTitle(), 
			  Main.getInstance().getConfiguration().getGuiHeight());
	}

	@Override
	public void initialize(List<InterfaceComponent> components) {
		for(Reward reward : Main.getInstance().getConfiguration().getRewards()) {
			ItemStack unavailable = reward.formatUnavailableIcon();
			ItemStack available = reward.formatAvailableIcon();
			boolean rewardState;
			if(reward.getDelay().equals(RewardDelay.ONCE)) {
				rewardState = !Main.getInstance().getConnector().containsUser(getUUID(), reward.getId());
			} else {
				long lastUsage = Main.getInstance().getConnector().containsUser(getUUID(), reward.getId()) ?
						Main.getInstance().getConnector().getLastUsage(getUUID(), reward.getId())
						: 0;
				rewardState = System.currentTimeMillis() - lastUsage >= reward.getDelay().getDelayMillis();
			}
			SwitchComponent rewardSwitch = new SwitchComponent(reward.getX(), reward.getY(), unavailable, available, rewardState);
			rewardSwitch.getState().addWatcher(state -> {
				Player p = getOwner();
				if(state) {
					XSound.BLOCK_ANVIL_PLACE.playSound(p);
					rewardSwitch.getState().setValueSilent(false);
				} else {
					if(Main.getInstance().getConnector().isOpened()) {
						XSound.ENTITY_PLAYER_LEVELUP.playSound(p);
						for(ItemStack item : reward.getItems()) {
							p.getInventory().addItem(item);
						}
						for(String command : reward.getCommands()) {
							command = command.replace("%player%", p.getName());
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
						}
						Main.getInstance().getConnector().setLastUsage(getUUID(), reward.getId());
					}
				}
			});
			components.add(rewardSwitch);
		}
	}

}
