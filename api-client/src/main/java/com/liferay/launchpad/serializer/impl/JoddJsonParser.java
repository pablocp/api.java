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

package com.liferay.launchpad.serializer.impl;

import com.liferay.launchpad.serializer.LaunchpadParser;

import java.util.List;
import java.util.Map;

import jodd.json.JsonParser;
public class JoddJsonParser implements LaunchpadParser {

	@Override
	public <T> T parse(String string) {
		return new JsonParser().parse(string);
	}

	@Override
	public <T> T parse(String string, Class<T> type) {
		return new JsonParser().parse(string, type);
	}

	@Override
	public <T> List<T> parseAsList(String string, Class<T> componentType) {
		return new JsonParser()
			.map(JsonParser.VALUES, componentType)
			.parse(string);
	}

	@Override
	public <K, V> Map<K, V> parseAsMap(
		String string, Class<K> keyType, Class<V> valueType) {

		return new JsonParser()
			.map("keys", keyType)
			.map("values", valueType)
			.parse(string);
	}

}