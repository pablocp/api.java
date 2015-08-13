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

package com.liferay.launchpad.sdk;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Multi-map.
 */
public interface PodMultiMap extends Iterable<Map.Entry<String, Object>> {

	/**
	 * Creates new case-sensitive implementation of the multi-map.
	 */
	public static PodMultiMap newCaseSensitiveMultiMap() {
		return new PodMultiMapImpl(true);
	}

	/**
	 * Creates new default implementation of the multi-map.
	 */
	public static PodMultiMap newMultiMap() {
		return new PodMultiMapImpl(false);
	}

	/**
	 * Adds a new value with the specified name and value.
	 *
	 * @param name The name
	 * @param value The value being added
	 * @return a reference to this, so the API can be used fluently
	 */
	public PodMultiMap add(String name, Object value);

	/**
	 * Adds all values from the map.
	 *
	 * @param map source map
	 * @return a reference to this, so the API can be used fluently
	 */
	public PodMultiMap addAll(Map<String, Object> map);

	/**
	 * Adds new values under the specified name.
	 *
	 * @param name The name being set
	 * @param values The values
	 * @return a reference to this, so the API can be used fluently
	 */
	public PodMultiMap addAll(String name, Iterable<Object> values);

	/**
	 * Removes all
	 *
	 * @return a reference to this, so the API can be used fluently
	 */
	public PodMultiMap clear();

	/**
	 * Checks to see if there is a value with the specified name.
	 *
	 * @param name The name to search for
	 * @return true if at least one entry is found
	 */
	public boolean contains(String name);

	/**
	 * Returns all entries in the multi-map.
	 *
	 * @return A immutable {@link java.util.List} of the name-value entries, which will be
	 * empty if no pairs are found
	 */
	public List<Map.Entry<String, Object>> entries();

	/**
	 * Get the String value associated with a key.
	 */
	public default String get(String name) {
		Object value = getObject(name);

		return ValuesUtil.toString(value);
	}

	/**
	 * Returns the values with the specified name.
	 *
	 * @param name The name to search
	 * @return A immutable {@link java.util.List} of values which will be empty
	 * if no values are found
	 */
	public List<Object> getAll(String name);

	/**
	 * Get the boolean value associated with a key.
	 */
	public default Boolean getBoolean(String name) {
		Object value = getObject(name);

		if (value == null) {
			return null;
		}

		if (value.getClass() == Boolean.class) {
			return (boolean)value;
		}

		return Boolean.valueOf(value.toString().trim().toLowerCase());
	}

	/**
	 * Get the double value associated with a key.
	 */
	public default Double getDouble(String name) {
		Object value = getObject(name);

		if (value == null) {
			return null;
		}

		return value instanceof Number ?
				((Number)value).doubleValue() :
				Double.parseDouble((String)value);
	}

	/**
	 * Get the int value associated with a key.
	 */
	public default Integer getInt(String name) {
		Object value = getObject(name);

		if (value == null) {
			return null;
		}

		return value instanceof Number ?
			((Number)value).intValue() : Integer.parseInt((String) value);
	}

	/**
	 * Get the long value associated with a key.
	 */
	public default Long getLong(String name) {
		Object value = getObject(name);

		if (value == null) {
			return null;
		}

		return value instanceof Number ?
			((Number)value).longValue() : Long.parseLong((String) value);
	}

	/**
	 * Returns the value of with the specified name.  If there are
	 * more than one values for the specified name, the first value is returned.
	 * Returns {@code null} if value does not exist.
	 */
	public Object getObject(String name);

	/**
	 * Returns <code>true</code> if this map is case sensitive.
	 */
	public boolean isCaseSensitive();

	/**
	 * Returns <code>true</code> if empty.
	 */
	public boolean isEmpty();

	/**
	 * Gets a immutable {@link java.util.Set} of all names
	 *
	 * @return A {@link java.util.Set} of all names
	 */
	public Set<String> names();

	/**
	 * Removes the value with the given name
	 *
	 * @param name The name  of the value to remove
	 * @return a reference to this, so the API can be used fluently
	 */
	public PodMultiMap remove(String name);

	/**
	 * Sets a value under the specified name.
	 * <p>
	 * If there is an existing key with the same name, it is removed.
	 *
	 * @param name The name
	 * @param value The value
	 * @return a reference to this, so the API can be used fluently
	 */
	public PodMultiMap set(String name, Object value);

	/**
	 * Sets values from given map.
	 *
	 * @param map The source map
	 * @return a reference to this, so the API can be used fluently
	 */
	public PodMultiMap setAll(Map<String, Object> map);

	/**
	 * Sets values for the specified name.
	 *
	 * @param name The name being set
	 * @param values The values being set
	 * @return a reference to this, so the API can be used fluently
	 */
	public PodMultiMap setAll(String name, Iterable<Object> values);

	/**
	 * Return the number of keys.
	 */
	public int size();

	/**
	 * Converts map to a java map.
	 */
	public Map<String, Object> toMap();

}