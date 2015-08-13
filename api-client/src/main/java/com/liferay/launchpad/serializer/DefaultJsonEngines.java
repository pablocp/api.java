package com.liferay.launchpad.serializer;

/**
 * Default serializer engine implementation.
 */
public class DefaultJsonEngines {

	public static Engines getDefaultEngines() {
		return engines;
	}

	public static void setDefaultEngines(Engines engines) {
		DefaultJsonEngines.engines = engines;
	}

	private static Engines engines;

}