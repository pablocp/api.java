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

package com.liferay.launchpad.security;

import java.util.HashMap;
import java.util.Map;

/**
 * Main class that performs bindings and holds instances of hash generators.
 */
public class LaunchpadHashEngine {

	/**
	 * Returns singleton instance of the engine.
	 */
	public static LaunchpadHashEngine instance() {
		return instance;
	}

	public static void setFactory(LaunchpadHashGeneratorFactory factory) {
		LaunchpadHashEngine.factory = factory;
	}

	/**
	 * Returns te {@link com.liferay.launchpad.serializer.LaunchpadParser} instance.
	 */
	public LaunchpadHashGenerator lookup(String algorithm) {
		LaunchpadHashGenerator generator = generators.get(algorithm);

		if (generator != null) {
			return generator;
		}

		if (factory == null) {
			throw new LaunchpadHashException("No hash generator factory set.");
		}

		generator = factory.create(algorithm);
		generators.put(algorithm, generator);

		return generator;
	}

	/**
	 * Manual registration of engines.
	 */
	public LaunchpadHashEngine register(
		String algorithm, LaunchpadHashGenerator generator) {

		generators.put(algorithm, generator);
		return this;
	}

	protected LaunchpadHashEngine() {
	}

	protected void clearEngine() {
		factory = null;
		generators.clear();
	}

	private static LaunchpadHashGeneratorFactory factory;
	private static final LaunchpadHashEngine instance =
		new LaunchpadHashEngine();

	private Map<String, LaunchpadHashGenerator> generators = new HashMap<>();

}