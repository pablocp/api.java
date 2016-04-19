package com.liferay.launchpad.sdk.impl;

import com.liferay.launchpad.sdk.PodMultiMap;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
public class TestPodMultiMap<V> implements PodMultiMap<V> {

	public TestPodMultiMap(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	@Override
	public PodMultiMap<V> add(CharSequence name, V value) {
		if (!map.containsKey(name)) {
			map.put(name, new LinkedList<>());
		}

		map.get(name).addLast(value);
		return this;
	}

	@Override
	public PodMultiMap<V> addAll(Map<CharSequence, V> m) {
		m.forEach((name, value) -> {
			if (!map.containsKey(name)) {
				map.put(name, new LinkedList<>());
			}

			map.get(name).addLast(value);
		});
		return this;
	}

	@Override
	public PodMultiMap<V> addAll(CharSequence name, Iterable<V> values) {
		if (!map.containsKey(name)) {
			map.put(name, new LinkedList<>());
		}

		values.forEach(map.get(name)::add);
		return this;
	}

	@Override
	public PodMultiMap<V> clear() {
		map.clear();
		return this;
	}

	@Override
	public boolean contains(CharSequence name) {
		return map.containsKey(name);
	}

	@Override
	public List<Map.Entry<CharSequence, V>> entries() {
		return map.entrySet().stream()
			.flatMap(
				entry -> entry.getValue().stream().map(
					val -> new AbstractMap.SimpleEntry<>(entry.getKey(), val)))
			.collect(Collectors.toList());
	}

	@Override
	public V get(CharSequence name) {
		return map.containsKey(name) ? map.get(name).getLast() : null;
	}

	@Override
	public List<V> getAll(CharSequence name) {
		return map.get(name);
	}

	@Override
	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Iterator<Map.Entry<CharSequence, V>> iterator() {
		return entries().iterator();
	}

	@Override
	public Set<CharSequence> names() {
		return map.keySet();
	}

	@Override
	public PodMultiMap<V> remove(CharSequence name) {
		map.remove(name);
		return this;
	}

	@Override
	public PodMultiMap<V> set(CharSequence name, V value) {
		map.put(name, new LinkedList<>());
		map.get(name).addLast(value);
		return this;
	}

	@Override
	public PodMultiMap<V> setAll(Map<CharSequence, V> m) {
		clear();
		addAll(m);
		return this;
	}

	@Override
	public PodMultiMap<V> setAll(CharSequence name, Iterable<V> values) {
		map.remove(name);
		addAll(name, values);
		return this;
	}

	@Override
	public int size() {
		return map.size();
	}

	private final boolean caseSensitive;
	private final Map<CharSequence, LinkedList<V>> map = new HashMap<>();

}
