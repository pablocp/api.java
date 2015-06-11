package com.liferay.launchpad.api;

public class JoddGoogleTest extends GoogleTest {

	@Override
	protected Transport transport() {
		return transport;
	}

	private final Transport transport = new JoddHttpTransport();

}