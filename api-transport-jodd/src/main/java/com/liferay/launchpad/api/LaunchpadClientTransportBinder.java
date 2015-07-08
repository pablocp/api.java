package com.liferay.launchpad.api;

public class LaunchpadClientTransportBinder implements TransportBinder {

	public Transport initTransport() {
		return new JoddHttpTransport();
	}

}