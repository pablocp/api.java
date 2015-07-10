package com.liferay.launchpad.query;

import java.lang.reflect.Array;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class Util {

	private Util() {}

	public static Map wrap(String name, Object value) {
		Map map = new HashMap();
		map.put(name, value);
		return map;
	}

	public static String toString(Object value) {
		if (value instanceof Embodied) {
			return toString(((Embodied)value).body());
		}

		if (value instanceof Collection) {
			return toString((Collection)value);
		}

		if (value instanceof Map) {
			return toString((Map)value);
		}

		if (value instanceof String) {
			return "\"" + value + "\"";
		}

		if (value.getClass().isArray()) {
			int length = Array.getLength(value);

			if (length == 0) {
				return "[]";
			}

			StringBuilder builder = new StringBuilder();
			builder.append('[');

			for (int i = 0; i < length; i++) {
				builder.append(toString(Array.get(value, i))).append(',');
			}

			builder.setCharAt(builder.length()-1, ']');

			return builder.toString();
		}

		return value.toString();
	}

	public static String toString(Collection value) {
		if (value.size() == 0) {
			return "[]";
		}

		StringBuilder builder = new StringBuilder();
		builder.append('[');

		for (Object item : value) {
			builder.append(toString(item)).append(',');
		}

		builder.setCharAt(builder.length() - 1, ']');

		return builder.toString();
	}

	public static String toString(Map<String, Object> value) {
		if (value.size() == 0) {
			return "{}";
		}

		StringBuilder builder = new StringBuilder();
		builder.append('{');

		for (Map.Entry<String, Object> entry : value.entrySet()) {
			builder.append(toString(entry.getKey())).append(':');
			builder.append(toString(entry.getValue())).append(",");
		}

		builder.setCharAt(builder.length() - 1, '}');

		return builder.toString();
	}

}