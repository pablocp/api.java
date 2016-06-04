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

import com.wedeploy.api.sdk.ContentType;

/**
 * WeDeploy Serializer performs serialization of an object into a string.
 * By default, serialization is <i>deep</i>, so all inner collections
 * and maps get serialized as well. Optionally, if serialization is
 * <i>shallow</i>, only top level properties will be serialized.
 */
public interface Serializer {

	public static Serializer get() {
		return SerializerEngine.instance().serializer();
	}

	public static Serializer get(ContentType contentType) {
		return get(contentType.contentType());
	}

	public static Serializer get(String contentType) {
		return SerializerEngine.instance().serializer(contentType);
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