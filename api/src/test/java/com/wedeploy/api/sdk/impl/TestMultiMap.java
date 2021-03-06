package com.wedeploy.api.sdk.impl;

import com.wedeploy.api.sdk.MultiMap;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
public class TestMultiMap<V> implements MultiMap<V> {

	public TestMultiMap(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	@Override
	public MultiMap<V> add(String name, V value) {
		if (!map.containsKey(name)) {
			map.put(name, new LinkedList<>());
		}

		map.get(name).addLast(value);
		return this;
	}

	@Override
	public MultiMap<V> addAll(Map<String, V> m) {
		m.forEach((name, value) -> {
			if (!map.containsKey(name)) {
				map.put(name, new LinkedList<>());
			}

			map.get(name).addLast(value);
		});
		return this;
	}

	@Override
	public MultiMap<V> addAll(String name, Iterable<V> values) {
		if (!map.containsKey(name)) {
			map.put(name, new LinkedList<>());
		}

		values.forEach(map.get(name)::add);
		return this;
	}

	@Override
	public MultiMap<V> clear() {
		map.clear();
		return this;
	}

	@Override
	public boolean contains(String name) {
		return map.containsKey(name);
	}

	@Override
	public List<Map.Entry<String, V>> entries() {
		return map.entrySet().stream()
			.flatMap(
				entry -> entry.getValue().stream().map(
					val -> new AbstractMap.SimpleEntry<>(entry.getKey(), val)))
			.collect(Collectors.toList());
	}

	@Override
	public V get(String name) {
		return map.containsKey(name) ? map.get(name).getLast() : null;
	}

	@Override
	public List<V> getAll(String name) {
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
	public Iterator<Map.Entry<String, V>> iterator() {
		return entries().iterator();
	}

	@Override
	public Set<String> names() {
		return map.keySet();
	}

	@Override
	public MultiMap<V> remove(String name) {
		map.remove(name);
		return this;
	}

	@Override
	public MultiMap<V> set(String name, V value) {
		map.put(name, new LinkedList<>());
		map.get(name).addLast(value);
		return this;
	}

	@Override
	public MultiMap<V> setAll(Map<String, V> m) {
		clear();
		addAll(m);
		return this;
	}

	@Override
	public MultiMap<V> setAll(String name, Iterable<V> values) {
		map.remove(name);
		addAll(name, values);
		return this;
	}

	@Override
	public int size() {
		return map.size();
	}

	private final boolean caseSensitive;
	private final Map<String, LinkedList<V>> map = new HashMap<>();

}