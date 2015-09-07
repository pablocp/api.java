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

import com.liferay.launchpad.sdk.ContentType;

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

	public static LaunchpadParser get(ContentType contentType) {
		return get(contentType.contentType());
	}

	public static LaunchpadParser get(String contentType) {
		return LaunchpadSerializerEngine.instance().parser(contentType);
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

	/**
	 * {@link #parse(String) Parses} string without throwing an exception and
	 * returning <code>null</code> on failures.
	 */
	public default <T> T parseSilently(String string) {
		try {
			return parse(string);
		}
		catch (Exception ignore) {
			return null;
		}
	}

	public default <T> T parseSilently(String string, Class<T> type) {
		try {
			return parse(string, type);
		}
		catch (Exception ignore) {
			return null;
		}
	}

}