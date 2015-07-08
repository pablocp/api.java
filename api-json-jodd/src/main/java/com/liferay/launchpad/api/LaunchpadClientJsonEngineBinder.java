package com.liferay.launchpad.api;

public class LaunchpadClientJsonEngineBinder implements JsonEngineBinder {
	@Override
	public JsonEngine initJsonEngine() {
		return new JoddJsonEngine();
	}
}