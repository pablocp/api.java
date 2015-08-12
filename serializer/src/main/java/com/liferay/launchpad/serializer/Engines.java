package com.liferay.launchpad.serializer;

public class Engines {

	public Engines(LaunchpadSerializer serializer, LaunchpadParser parser) {
		this.parser = parser;
		this.serializer = serializer;
	}

	public LaunchpadParser getParser() {
		return parser;
	}

	public LaunchpadSerializer getSerializer() {
		return serializer;
	}

	private final LaunchpadParser parser;
	private final LaunchpadSerializer serializer;

}