package com.wedeploy.api.serializer.impl;

import com.wedeploy.api.serializer.Parser;

import java.util.List;
import java.util.Map;
public class JsonParser implements Parser {

	@Override
	public <T> T parse(String string) {
		return new jodd.json.JsonParser().parse(string);
	}

	@Override
	public <T> T parse(String string, Class<T> type) {
		return new jodd.json.JsonParser().parse(string, type);
	}

	@Override
	public <T> List<T> parseAsList(String string, Class<T> componentType) {
		return new jodd.json.JsonParser()
			.map(jodd.json.JsonParser.VALUES, componentType)
			.parse(string);
	}

	@Override
	public <K, V> Map<K, V> parseAsMap(
		String string, Class<K> keyType, Class<V> valueType) {

		return new jodd.json.JsonParser()
			.map("keys", keyType)
			.map("values", valueType)
			.parse(string);
	}

}