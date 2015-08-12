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

	public LaunchpadParser parser() {
		return parser(defaultSerializerType);
	}

	/**
	 * Returns te {@link LaunchpadParser} instance.
	 */
	public LaunchpadParser parser(SerializerType serializerType) {
		Engines engines = lookupEngines(serializerType);

		return engines.getParser();
	}

	/**
	 * Manual registration of engines.
	 */
	public void registerEngines(
		SerializerType serializerType, Engines engines) {

		enginesMap.put(serializerType, engines);
	}

	public LaunchpadSerializer serializer() {
		return serializer(defaultSerializerType);
	}

	/**
	 * Returns the {@link LaunchpadSerializer} instance.
	 */
	public LaunchpadSerializer serializer(SerializerType serializerType) {
		Engines engines = lookupEngines(serializerType);

		return engines.getSerializer();
	}

	protected LaunchpadSerializerEngine() {
	}

	protected Engines lookupEngines(SerializerType serializerType) {
		Engines engines = enginesMap.get(serializerType);

		if (engines == null) {
			switch (serializerType) {
				case JSON:
					engines = DefaultJsonEngines.getDefaultEngines();
					break;
				default:
					engines = null;
			}

			if (engines == null) {
				throw new LaunchpadSerializerException(
					"Default Serializer not specified for " + serializerType);
			}

			enginesMap.put(serializerType, engines);
		}

		return engines;
	}

	protected SerializerType defaultSerializerType = SerializerType.JSON;

	private static final LaunchpadSerializerEngine instance =
		new LaunchpadSerializerEngine();

	private Map<SerializerType, Engines> enginesMap = new HashMap<>();

}