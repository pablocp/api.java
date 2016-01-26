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

package com.liferay.launchpad.sdk;

import com.liferay.launchpad.sdk.io.SocketIO;

import java.nio.file.Path;

import java.util.List;
import java.util.Map;

/**
 * Execution context.
 */
public interface Context {

	/**
	 * Returns application configuration.
	 */
	public AppConfig appConfig();

	/**
	 * Returns application shared data map.
	 */
	public SharedData<Object, Object> appData();

	/**
	 * Returns assets file from <code>assets</code> folder.
	 */
	public Path assetsFile(String fileName);

	/**
	 * Returns assets files from <code>assets</code> folder that matches
	 * provided glob patterns.
	 */
	public List<Path> assetsFiles(String... glob);

	/**
	 * Returns base path of this pod.
	 */
	public String basePath();

	/**
	 * Returns user configuration or <code>null</code> if no configuration is
	 * specified.
	 */
	public Map<String, Object> config();

	/**
	 * Parses the configuration into a bean of the given type.
	 * This method loads and parses the configuration on each call.
	 */
	public <T> T config(Class<T> clazz);

	/**
	 * Returns the socket io server of this pod.
	 */
	public SocketIO io();

	/**
	 * Returns context name.
	 */
	public String name();

	/**
	 * Returns single file from <code>web</code> folder. Returns
	 * <code>null</code> if file does not exist.
	 */
	public Path webFile(String fileName);

	/**
	 * Returns static files from <code>web</code> folder that matches provided
	 * glob patterns.
	 */
	public List<Path> webFiles(String... glob);

}