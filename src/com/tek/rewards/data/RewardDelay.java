package com.tek.rewards.data;

import java.util.concurrent.TimeUnit;

public enum RewardDelay {
	
	ONCE(Long.MAX_VALUE),
	HOURLY(TimeUnit.HOURS.toMillis(1)),
	DAILY(TimeUnit.DAYS.toMillis(1)),
	WEEKLY(TimeUnit.DAYS.toMillis(1) * 7),
	MONTHLY(TimeUnit.DAYS.toMillis(1) * 30),
	YEARLY(TimeUnit.DAYS.toMillis(1) * 365);
	
	private long millis;
	
	private RewardDelay(long millis) {
		this.millis = millis;
	}
	
	public long getDelayMillis() {
		return millis;
	}
	
}
