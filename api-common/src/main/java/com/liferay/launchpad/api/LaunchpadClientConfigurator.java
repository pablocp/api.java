package com.liferay.launchpad.api;

public interface LaunchpadClientConfigurator {

	public <T extends LaunchpadBaseClient> void configure(T client);

}