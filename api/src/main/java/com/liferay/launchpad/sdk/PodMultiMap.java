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
public interface PodMultiMap<V> extends Iterable<Map.Entry<String, V>> {

	/**
	 * Creates new case-sensitive implementation of the multi-map.
	 */
	public static <T> PodMultiMap<T> newCaseSensitiveMultiMap() {
		return PodMultiMapFactory.Default.factory.createMultiMap(true);
	}

	/**
	 * Creates new default implementation of the multi-map.
	 */
	public static <T> PodMultiMap<T> newMultiMap() {
		return PodMultiMapFactory.Default.factory.createMultiMap(false);
	}

	/**
	 * Adds a new value with the specified name and value.
	 *
	 * @param name The name
	 * @param value The value being added
	 * @return a reference to this, so the API can be used fluently
	 */
	public PodMultiMap<V> add(String name, V value);

	/**
	 * Adds all values from the map.
	 *
	 * @param map source map
	 * @return a reference to this, so the API can be used fluently
	 */
	public PodMultiMap<V> addAll(Map<String, V> map);

	/**
	 * Adds new values under the specified name.
	 *
	 * @param name The name being set
	 * @param values The values
	 * @return a reference to this, so the API can be used fluently
	 */
	public PodMultiMap<V> addAll(String name, Iterable<V> values);

	/**
	 * Removes all values.
	 *
	 * @return a reference to this, so the API can be used fluently
	 */
	public PodMultiMap<V> clear();

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
	public List<Map.Entry<String, V>> entries();

	/**
	 * Get the String value associated with a key.
	 */
	public V get(String name);

	/**
	 * Returns the values with the specified name.
	 *
	 * @param name The name to search
	 * @return A immutable {@link java.util.List} of values which will be empty
	 * if no values are found
	 */
	public List<V> getAll(String name);

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
	public PodMultiMap<V> remove(String name);

	/**
	 * Sets a value under the specified name.
	 * <p>
	 * If there is an existing key with the same name, it is removed.
	 *
	 * @param name The name
	 * @param value The value
	 * @return a reference to this, so the API can be used fluently
	 */
	public PodMultiMap<V> set(String name, V value);

	/**
	 * Sets values from given map.
	 *
	 * @param map The source map
	 * @return a reference to this, so the API can be used fluently
	 */
	public PodMultiMap<V> setAll(Map<String, V> map);

	/**
	 * Sets values for the specified name.
	 *
	 * @param name The name being set
	 * @param values The values being set
	 * @return a reference to this, so the API can be used fluently
	 */
	public PodMultiMap<V> setAll(String name, Iterable<V> values);

	/**
	 * Return the number of keys.
	 */
	public int size();

}