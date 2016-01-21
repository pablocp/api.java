/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */

package com.liferay.launchpad.serializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Main class that performs bindings and holds instances of serializer and parser.
 */
public class LaunchpadSerializerEngine {

	/**
	 * Returns singleton instance of the engine.
	 */
	public static LaunchpadSerializerEngine instance() {
		return instance;
	}

	/**
	 * Returns te {@link LaunchpadParser} instance.
	 */
	public LaunchpadParser parser(String contentType) {
		Engines engines = lookupEngines(contentType);

		return engines.getParser();
	}

	public LaunchpadParser parser() {
		return defaultEngines().getParser();
	}

	/**
	 * Manual registration of engines.
	 */
	public void registerEngines(
			String serializerType, Engines engines, boolean isDefault) {
		enginesMap.put(serializerType, engines);

		if (isDefault == true) {
			defaultEngines = engines;
		}
	}

	/**
	 * Returns the {@link LaunchpadSerializer} instance.
	 */
	public LaunchpadSerializer serializer(String contentType) {
		Engines engines = lookupEngines(contentType);

		return engines.getSerializer();
	}

	public LaunchpadSerializer serializer() {
		return defaultEngines().getSerializer();
	}

	protected LaunchpadSerializerEngine() {
	}

	protected Engines defaultEngines() {
		if (defaultEngines == null) {
			throw new LaunchpadSerializerException("Default content type is not set.");
		}
		return defaultEngines;
	}

	protected Engines lookupEngines(String contentType) {
		Engines engines = enginesMap.get(contentType);

		if (engines == null) {
			throw new LaunchpadSerializerException(
				"Engine for content type not found: " + contentType);
		}

		return engines;
	}

	private static final LaunchpadSerializerEngine instance =
		new LaunchpadSerializerEngine();

	private Engines defaultEngines;
	private Map<String, Engines> enginesMap = new HashMap<>();

}
