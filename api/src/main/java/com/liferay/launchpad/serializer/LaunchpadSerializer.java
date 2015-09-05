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

/**
 * Launchpad Serializer performs serialization of an object into a string.
 * By default, serialization is <i>deep</i>, so all inner collections
 * and maps get serialized as well. Optionally, if serialization is
 * <i>shallow</i>, only top level properties will be serialized.
 */
public interface LaunchpadSerializer {

	public static LaunchpadSerializer get() {
		return LaunchpadSerializerEngine.instance().serializer();
	}

	public static LaunchpadSerializer get(String contentType) {
		return LaunchpadSerializerEngine.instance().serializer(contentType);
	}

	/**
	 * Serializes an object, deep.
	 */
	default public String serialize(Object object) {
		return serialize(object, true);
	}

	/**
	 * Serializes an object.
	 */
	public String serialize(Object object, boolean deep);

}