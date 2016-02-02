package com.liferay.launchpad.serializer.impl;

import com.liferay.launchpad.serializer.LaunchpadParser;

import java.util.List;
import java.util.Map;

import jodd.json.JsonParser;
public class JsonLaunchpadParser implements LaunchpadParser {

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