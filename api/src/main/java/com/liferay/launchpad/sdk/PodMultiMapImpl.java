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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

/**
 * Case-insensitive multi-map. It's optimized Linked-HashMap, designed for
 * small number of items and <code>String</code> non-null keys.
 */
class PodMultiMapImpl implements PodMultiMap {

	PodMultiMapImpl(boolean caseSensitive) {
		head.before = head.after = head;
		this.caseSensitive = caseSensitive;
	}

	@Override
	public PodMultiMap add(final String name, final String value) {
		int h = hash(name);
		int i = index(h);
		_add(h, i, name, value);
		return this;
	}

	@Override
	public PodMultiMap addAll(Map<String, String> map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			add(entry.getKey(), entry.getValue());
		}

		return this;
	}

	public PodMultiMap addAll(PodMultiMap map) {
		for (Map.Entry<String, String> entry : map.entries()) {
			add(entry.getKey(), entry.getValue());
		}

		return this;
	}

	@Override
	public PodMultiMap addAll(String name, Iterable<String> values) {
		int h = hash(name);
		int i = index(h);

		for (String value : values) {
			_add(h, i, name, value);
		}

		return this;
	}

	/**
	 * Clears the map.
	 */
	@Override
	public PodMultiMap clear() {
		for (int i = 0; i < entries.length; i++) {
			entries[i] = null;
		}

		head.before = head.after = head;
		return this;
	}

	/**
	 * Returns <code>true</code> if name exist.
	 */
	@Override
	public boolean contains(String name) {
		return getEntry(name) != null;
	}

	@Override
	public List<Map.Entry<String, String>> entries() {
		List<Map.Entry<String, String>> all = new LinkedList<>();

		MapEntry e = head.after;
		while (e != head) {
			all.add(e);
			e = e.after;
		}

		return all;
	}

	/**
	 * Returns the first value from the map associated with the name.
	 * Returns <code>null</code> if name does not exist or
	 * if associated value is <code>null</code>.
	 */
	@Override
	public String get(final String name) {
		Map.Entry<String, String> entry = getEntry(name);

		if (entry == null) {
			return null;
		}

		return entry.getValue();
	}

	/**
	 * Returns all values associated with the name.
	 */
	@Override
	public List<String> getAll(final String name) {
		LinkedList<String> values = new LinkedList<>();

		int h = hash(name);
		int i = index(h);
		MapEntry e = entries[i];
		while (e != null) {
			if ((e.hash == h) && eq(name, e.key)) {
				values.addFirst(e.getValue());
			}

			e = e.next;
		}

		return values;
	}

	/**
	 * Returns first entry for given name. Returns <code>null</code> if entry
	 * does not exist.
	 */
	public Map.Entry<String, String> getEntry(final String name) {
		int h = hash(name);
		int i = index(h);
		MapEntry e = entries[i];
		while (e != null) {
			if ((e.hash == h) && eq(name, e.key)) {
				return e;
			}

			e = e.next;
		}

		return null;
	}

	@Override
	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	/**
	 * Returns <code>true</code> if map is empty.
	 */
	@Override
	public boolean isEmpty() {
		return head == head.after;
	}

	/**
	 * Returns iterator of all entries.
	 */
	@Override
	public Iterator<Map.Entry<String, String>> iterator() {
		final MapEntry[] e = {head.after};

		return new Iterator<Map.Entry<String, String>>() {
			@Override
			public boolean hasNext() {
				return e[0] != head;
			}

			@Override
			public Map.Entry<String, String> next() {
				if (hasNext() == false) {
					throw new NoSuchElementException(
						"No next() entry in the iteration");
				}

				MapEntry next = e[0];
				e[0] = e[0].after;
				return next;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public Set<String> names() {
		Set<String> names = new TreeSet<>(
			caseSensitive ? null : String.CASE_INSENSITIVE_ORDER);

		MapEntry e = head.after;
		while (e != head) {
			names.add(e.getKey());
			e = e.after;
		}

		return names;
	}

	@Override
	public PodMultiMap remove(final String name) {
		int h = hash(name);
		int i = index(h);
		_remove(h, i, name);
		return this;
	}

	@Override
	public PodMultiMap set(final String name, final String value) {
		int h = hash(name);
		int i = index(h);
		_remove(h, i, name);
		_add(h, i, name, value);
		return this;
	}

	@Override
	public PodMultiMap setAll(Map<String, String> map) {
		return _set(map.entrySet());
	}

	public PodMultiMap setAll(PodMultiMap multiMap) {
		return _set(multiMap);
	}

	@Override
	public PodMultiMap setAll(
			final String name, final Iterable<String> values) {

		int h = hash(name);
		int i = index(h);

		_remove(h, i, name);

		for (String v : values) {
			_add(h, i, name, v);
		}

		return this;
	}

	/**
	 * Returns the number of keys. This is not the number of all elements.
	 * Not optimized.
	 */
	@Override
	public int size() {
		return names().size();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, String> entry : this) {
			sb.append(entry).append('\n');
		}

		return sb.toString();
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();

		entries().forEach(entry -> {
			String key = entry.getKey();

			Object existing = map.get(key);

			if (existing != null) {
				if (existing instanceof List) {
					((List)existing).add(entry.getValue());
				}
				else {
					List list = new LinkedList();

					list.add(existing);
					list.add(entry.getValue());

					map.put(key, list);
				}
			}
			else {
				map.put(key, entry.getValue());
			}
		});

		return map;
	}

	/**
	 * Returns <code>true</code> if two names are the same. Names letter case
	 * is ignored.
	 */
	private boolean eq(String name1, String name2) {
		int nameLen = name1.length();

		if (nameLen != name2.length()) {
			return false;
		}

		for (int i = nameLen - 1; i >= 0; i--) {
			char c1 = name1.charAt(i);
			char c2 = name2.charAt(i);

			if (c1 != c2) {
				if (this.caseSensitive) {
					return false;
				}

				if ((c1 >= 'A') && (c1 <= 'Z')) {
					c1 += 32;
				}

				if ((c2 >= 'A') && (c2 <= 'Z')) {
					c2 += 32;
				}

				if (c1 != c2) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Calculates hash value of the input string.
	 */
	private int hash(String name) {
		int h = 0;

		for (int i = name.length() - 1; i >= 0; i--) {
			char c = name.charAt(i);

			if (!caseSensitive) {
				if ((c >= 'A') && (c <= 'Z')) {
					c += 32;
				}
			}

			h = 31 * h + c;
		}

		if (h > 0) {
			return h;
		}

		if (h == Integer.MIN_VALUE) {
			return Integer.MAX_VALUE;
		}

		return -h;
	}

	/**
	 * Calculates bucket index from the hash.
	 */
	private static int index(int hash) {
		return hash & BUCKET_SIZE;
	}

	private void _add(
		final int hash, final int index, final String name,
		final String value) {

		// update the hash table

		MapEntry e = entries[index];
		MapEntry newEntry = null;
		entries[index] = newEntry = new MapEntry(hash, name, value);
		newEntry.next = e;

		// update the linked list

		newEntry.addBefore(head);
	}

	private void _remove(final int hash, final int index, String name) {
		MapEntry e = entries[index];

		if (e == null) {
			return;
		}

		for (;; ) {
			if ((e.hash == hash) && eq(name, e.key)) {
				e.remove();
				MapEntry next = e.next;

				if (next != null) {
					entries[index] = next;
					e = next;
				}
				else {
					entries[index] = null;
					return;
				}
			}
			else {
				break;
			}
		}

		for (;; ) {
			MapEntry next = e.next;

			if (next == null) {
				break;
			}

			if ((next.hash == hash) && eq(name, next.key)) {
				e.next = next.next;
				next.remove();
			}
			else {
				e = next;
			}
		}
	}

	private PodMultiMap _set(Iterable<Map.Entry<String, String>> map) {
		clear();

		for (Map.Entry<String, String> entry : map) {
			add(entry.getKey(), entry.getValue());
		}

		return this;
	}

	private static final int BUCKET_SIZE = 31;

	private final boolean caseSensitive;
	private final MapEntry[] entries = new MapEntry[BUCKET_SIZE + 1];
	private final MapEntry head = new MapEntry(-1, null, null);

	private static final class MapEntry implements Map.Entry<String, String> {

		final int hash;
		final String key;
		String value = null;
		MapEntry next = null;
		MapEntry before, after;

		private MapEntry(int hash, String key, String value) {
			this.hash = hash;
			this.key = key;
			this.value = value;
		}

		void remove() {
			before.after = after;
			after.before = before;
		}

		void addBefore(MapEntry e) {
			after = e;
			before = e.before;
			before.after = this;
			after.before = this;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		public String setValue(String value) {
			String oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public String toString() {
			return getKey() + ": " + getValue();
		}
	}

}