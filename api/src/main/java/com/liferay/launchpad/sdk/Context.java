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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
	 * Returns assets file from <code>assets</code> folder.
	 */
	public File assetsFile(String fileName);

	/**
	 * Returns assets files from <code>assets</code> folder that matches
	 * provided <i>wildcard</i> glob patterns.
	 */
	public List<File> assetsFiles(String... glob);

	/**
	 * Returns base path of this pod.
	 */
	public String basePath();

	/**
	 * Returns user configuration or <code>null</code> if no configuration is
	 * specified.
	 */
	public Map<String, Object> configuration();

	/**
	 * Returns the socket io server of this pod.
	 */
	public SocketIO io();

	/**
	 * Returns context name.
	 */
	public String name();

	/**
	 * Loads Java resource from context classloader.
	 */
	public InputStream openJavaResource(String name) throws IOException;

	/**
	 * Returns single file from <code>web</code> folder. Returns
	 * <code>null</code> if file does not exist.
	 */
	public File webFile(String fileName);

	/**
	 * Returns static files from <code>web</code> folder that matches provided
	 * <i>wildcard</i> glob patterns.
	 */
	public List<File> webFiles(String... glob);

}