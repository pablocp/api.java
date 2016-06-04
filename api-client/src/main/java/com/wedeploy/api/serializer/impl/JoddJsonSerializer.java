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

package com.wedeploy.api.serializer.impl;

import com.wedeploy.api.sdk.MultiMap;
import com.wedeploy.api.serializer.Serializer;

import jodd.json.JsonSerializer;
public class JoddJsonSerializer implements Serializer {

	@Override
	public String serialize(Object object, boolean deep) {
		return JsonSerializer
			.create()
			.deep(deep)
			.use(MultiMap.class, POD_MULTI_MAP_JSON_SERIALIZER)
			.use(long.class, LONG_TO_STRING_JSON_SERIALIZER)
			.use(Long.class, LONG_TO_STRING_JSON_SERIALIZER)
			.serialize(object);
	}

	private static final LongToStringJsonSerializer
		LONG_TO_STRING_JSON_SERIALIZER = new LongToStringJsonSerializer();

	private static final PodMultiMapJsonSerializer POD_MULTI_MAP_JSON_SERIALIZER
		= new PodMultiMapJsonSerializer();

}