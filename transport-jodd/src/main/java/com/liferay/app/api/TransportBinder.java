package com.liferay.app.api;

public class TransportBinder {

	public static Transport newTransport() {
		return new JoddHttpTransport();
	}

}