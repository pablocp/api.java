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

package com.wedeploy.api.sdk;

/**
 * Simple map.
 */
public interface SharedData<K, V> {

	/**
	 * Get a value from the map.
	 */
	V get(K key);

	/**
	 * Put a value in the map.
	 */
	void put(K key, V value);

	/**
	 * Put the entry only if there is no entry with the key already present.
	 */
	void putIfAbsent(K key, V value);

	/**
	 * Remove a value from the map.
	 */
	V remove(K key);

	/**
	 * Remove a value from the map, only if entry already exists with same value.
	 */
	boolean removeIfPresent(K key, V value);

	/**
	 * Replace the entry only if it is currently mapped to some value.
	 */
	V replace(K key, V value);

	/**
	 * Replace the entry only if it is currently mapped to a specific value.
	 */
	boolean replaceIfPresent(K key, V oldValue, V newValue);

	/**
	 * Clear all entries in the map
	 */
	void clear();

	/**
	 * Provide the number of entries in the map
	 */
	int size();

}