package com.liferay.launchpad.api;

public interface LaunchpadClientConfigurator {

	<T extends LaunchpadBaseClient> void configure(T client);

}