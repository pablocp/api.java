package com.liferay.launchpad.serializer;

import com.liferay.launchpad.serializer.impl.JoddJsonParser;
import com.liferay.launchpad.serializer.impl.JoddJsonSerializer;

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

	private static Engines engines = new Engines(
		new JoddJsonSerializer(), new JoddJsonParser());

}