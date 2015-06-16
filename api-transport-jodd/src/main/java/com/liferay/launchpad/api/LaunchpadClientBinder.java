package com.liferay.launchpad.api;

public class LaunchpadClientBinder implements TransportBinder {

	public Transport initTransport() {
		return new JoddHttpTransport();
	}

}