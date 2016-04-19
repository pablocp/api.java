package com.liferay.launchpad.sdk.impl;

import com.liferay.launchpad.sdk.PodMultiMap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jodd.http.HttpMultiMap;
public class PodMultiMapImpl<V> implements PodMultiMap<V> {

	private final HttpMultiMap<V> mm;

	public PodMultiMapImpl(boolean caseSensitive) {
		mm = caseSensitive ?
			HttpMultiMap.newCaseSensitveMap() :
			HttpMultiMap.newCaseInsensitveMap();
		this.caseSensitive = caseSensitive;
	}

	@Override
	public PodMultiMapImpl<V> add(CharSequence name, V value) {
		mm.add(name.toString(), value);
		return this;
	}

	@Override
	public PodMultiMapImpl<V> addAll(Map<CharSequence, V> map) {
		map.forEach((k,v) -> mm.add(k.toString(), v));
		return this;
	}

	@Override
	public PodMultiMapImpl<V> addAll(CharSequence name, Iterable<V> values) {
		mm.addAll(name.toString(), values);
		return this;
	}

	@Override
	public PodMultiMapImpl<V> clear() {
		mm.clear();
		return this;
	}

	@Override
	public boolean contains(CharSequence name) {
		return mm.contains(name.toString());
	}

	@Override
	public List<Map.Entry<CharSequence, V>> entries() {
		List list = mm.entries();

		return (List<Map.Entry<CharSequence, V>>) list;
	}

	@Override
	public V get(CharSequence name) {
		return mm.get(name.toString());
	}

	@Override
	public List<V> getAll(CharSequence name) {
		return mm.getAll(name.toString());
	}

	@Override
	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	@Override
	public boolean isEmpty() {
		return mm.isEmpty();
	}

	@Override
	public Set<CharSequence> names() {
		Set names = mm.names();
		return names;
	}

	@Override
	public PodMultiMapImpl<V> remove(CharSequence name) {
		mm.remove(name.toString());
		return this;
	}

	@Override
	public PodMultiMapImpl<V> set(CharSequence name, V value) {
		mm.set(name.toString(), value);
		return this;
	}

	@Override
	public PodMultiMapImpl<V> setAll(Map<CharSequence, V> map) {
		mm.clear();
		map.forEach((k,v) -> mm.set(k.toString(),v));
		return this;
	}

	@Override
	public PodMultiMapImpl<V> setAll(CharSequence name, Iterable<V> values) {
		mm.setAll(name.toString(), values);
		return this;
	}

	@Override
	public int size() {
		return mm.size();
	}

	private final boolean caseSensitive;

	@Override
	public Iterator<Map.Entry<CharSequence, V>> iterator() {
		Iterator iterator = mm.iterator();

		return iterator;
	}
}
