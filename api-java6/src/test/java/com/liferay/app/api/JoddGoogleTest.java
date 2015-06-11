package com.liferay.app.api;

public class JoddGoogleTest extends GoogleTest {

	@Override
	protected Transport transport() {
		return transport;
	}

	private final Transport transport = new JoddHttpTransport();

}