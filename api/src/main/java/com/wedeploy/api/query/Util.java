package com.wedeploy.api.query;

import java.lang.reflect.Array;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Utility class for Query components parsing.
 */
public class Util {

	public static String toString(Iterable value) {
		StringBuilder builder = new StringBuilder();

		builder.append('[');

		Iterator iterator = value.iterator();

		while (iterator.hasNext()) {
			builder.append(toString(iterator.next()));

			if (iterator.hasNext()) {
				builder.append(",");
			}
		}

		builder.append(']');

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

	public static String toString(Object value) {
		if (value instanceof Embodied) {
			return toString(((Embodied)value).body());
		}

		if (value instanceof Iterable) {
			return toString((Iterable)value);
		}

		if (value instanceof Map) {
			return toString((Map)value);
		}

		if (value instanceof String) {
			return toString((String)value);
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

	/**
	 * Wraps a <code>(key, value)</code> pair into a {@link Map}.
	 *
	 * @param key the map key
	 * @param value the value associated to the key
	 * @return a map with a entry <code>(key, value)</code>
	 */
	public static Map wrap(String key, Object value) {
		Map map = new HashMap();

		map.put(key, value);
		return map;
	}

	protected Util() {
	}

	private static String toString(String value) {
		StringBuilder sb = new StringBuilder();

		sb.append('"');

		int len = value.length();

		for (int i = 0; i < len; i++) {
			char c = value.charAt(i);

			switch (c) {
				case '"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				case '/':
					sb.append("\\/");
					break;
				case '\b':
					sb.append("\\b");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\t':
					sb.append("\\t");
					break;
				default:
					sb.append(c);
			}
		}

		sb.append('"');

		return sb.toString();
	}

}