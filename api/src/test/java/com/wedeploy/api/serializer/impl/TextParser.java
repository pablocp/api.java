package com.wedeploy.api.serializer.impl;

import com.wedeploy.api.serializer.Parser;
import com.wedeploy.api.serializer.SerializerException;

import java.util.List;
import java.util.Map;
public class TextParser implements Parser {

	@Override
	public <T> T parse(String string) {return(T) string; }

	@Override
	public <T> T parse(String string, Class<T> type) {
		try {
			return type.getConstructor(String.class).newInstance(string);
		}
		catch (Exception e) {
			throw new SerializerException(string, e);
		}
	}

	@Override
	public <T> List<T> parseAsList(String string, Class<T> componentType) {
		throw new UnsupportedOperationException(string);
	}

	@Override
	public <K, V> Map<K, V> parseAsMap(
		String string, Class<K> keyType, Class<V> valueType) {

		throw new UnsupportedOperationException(string);
	}

}