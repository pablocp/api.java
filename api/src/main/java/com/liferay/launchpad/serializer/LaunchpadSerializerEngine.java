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
		return parser(defaultContentType);
	}

	/**
	 * Returns te {@link LaunchpadParser} instance.
	 */
	public LaunchpadParser parser(String contentType) {
		Engines engines = lookupEngines(contentType);

		return engines.getParser();
	}

	/**
	 * Manual registration of engines.
	 */
	public void registerEngines(String serializerType, Engines engines) {
		enginesMap.put(serializerType, engines);
	}

	public LaunchpadSerializer serializer() {
		return serializer(defaultContentType);
	}

	/**
	 * Returns the {@link LaunchpadSerializer} instance.
	 */
	public LaunchpadSerializer serializer(String contentType) {
		Engines engines = lookupEngines(contentType);

		return engines.getSerializer();
	}

	public void setDefaultContentType(String defaultContentType) {
		this.defaultContentType = defaultContentType;
	}

	protected LaunchpadSerializerEngine() {
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

	private String defaultContentType;

	private Map<String, Engines> enginesMap = new HashMap<>();

}