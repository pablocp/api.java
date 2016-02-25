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

import java.util.Map;

/**
 * Server session.
 */
public interface Session {

	/**
	 * Returns the session data as {@link java.util.Map}.
	 */
	public Map<String, Object> data();

	/**
	 * Mark the session to be destroyed.
	 */
	public void destroy();

	/**
	 * Get some data from the session.
	 */
	public <T> T get(String key);

	/**
	 * Returns the unique ID of the session.
	 */
	public String id();

	/**
	 * Return true is the session was marked to be destroyed.
	 */
	public boolean isDestroyed();

	/**
	 * Returns the time the session was last accessed.
	 */
	public long lastAccessed();

	/**
	 * Put some data in a session.
	 */
	public Session put(String key, Object value);

	/**
	 * Removes some data from the session.
	 */
	public <T> T remove(String key);

}