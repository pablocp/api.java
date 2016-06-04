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

package com.wedeploy.api.serializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Main class that performs bindings and holds instances of serializer and parser.
 */
public class SerializerEngine {

	/**
	 * Returns singleton instance of the engine.
	 */
	public static SerializerEngine instance() {
		return instance;
	}

	public Parser parser() {
		return defaultEngines().getParser();
	}

	/**
	 * Returns te {@link Parser} instance.
	 */
	public Parser parser(String contentType) {
		Engines engines = lookupEngines(contentType);

		return engines.getParser();
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

	public Serializer serializer() {
		return defaultEngines().getSerializer();
	}

	/**
	 * Returns the {@link Serializer} instance.
	 */
	public Serializer serializer(String contentType) {
		Engines engines = lookupEngines(contentType);

		return engines.getSerializer();
	}

	protected SerializerEngine() {
	}

	protected void clearEngines() {
		defaultEngines = null;
		enginesMap.clear();
	}

	protected Engines defaultEngines() {
		if (defaultEngines == null) {
			throw new SerializerException("Default content type is not set.");
		}

		return defaultEngines;
	}

	protected Engines lookupEngines(String contentType) {
		Engines engines = enginesMap.get(contentType);

		if (engines == null) {
			throw new SerializerException(
				"Engine for content type not found: " + contentType);
		}

		return engines;
	}

	private static final SerializerEngine instance = new SerializerEngine();

	private Engines defaultEngines;
	private Map<String, Engines> enginesMap = new HashMap<>();

}