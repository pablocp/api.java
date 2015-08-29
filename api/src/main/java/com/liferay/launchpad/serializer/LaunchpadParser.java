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

import java.util.List;

/**
 * Launchpad Parser deserializes the input string into an object. There are two
 * types of parsing:
 * <ul>
 *     <li>to common JDK types, like <code>List</code>, <code>Map</code>,
 *			<code>String</code>...</li>
 *     <li>to specific types.</li>
 * </ul>
 */
public interface LaunchpadParser {

	public static LaunchpadParser get() {
		return LaunchpadSerializerEngine.instance().parser();
	}

	/**
	 * Parses string to Java common type.
	 */
	public <T> T parse(String string);

	/**
	 * Parses string to given type.
	 */
	public <T> T parse(String string, Class<T> type);

	/**
	 * Parses string to a list of given type.
	 */
	public <T> List<T> parseAsList(String string, Class<T> componentType);

}