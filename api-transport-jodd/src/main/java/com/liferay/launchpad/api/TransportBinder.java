package com.liferay.launchpad.api;

public class TransportBinder {

	public static Transport newTransport() {
		return new JoddHttpTransport();
	}

}