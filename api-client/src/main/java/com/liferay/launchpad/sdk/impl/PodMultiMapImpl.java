package com.liferay.launchpad.sdk.impl;

import com.liferay.launchpad.sdk.PodMultiMap;

import java.util.Map;

import jodd.http.HttpMultiMap;
public class PodMultiMapImpl<V>
	extends HttpMultiMap<V> implements PodMultiMap<V> {

	public PodMultiMapImpl(boolean caseSensitive) {
		super(caseSensitive);
		this.caseSensitive = caseSensitive;
	}

	@Override
	public PodMultiMapImpl<V> add(String name, V value) {
		super.add(name, value);
		return this;
	}

	@Override
	public PodMultiMapImpl<V> addAll(HttpMultiMap<V> map) {
		super.addAll(map);
		return this;
	}

	@Override
	public PodMultiMapImpl<V> addAll(Map<String, V> map) {
		super.addAll(map);
		return this;
	}

	@Override
	public PodMultiMapImpl<V> addAll(String name, Iterable<V> values) {
		super.addAll(name, values);
		return this;
	}

	@Override
	public PodMultiMapImpl<V> clear() {
		super.clear();
		return this;
	}

	@Override
	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	@Override
	public PodMultiMapImpl<V> remove(String name) {
		super.remove(name);
		return this;
	}

	@Override
	public PodMultiMapImpl<V> set(String name, V value) {
		super.set(name, value);
		return this;
	}

	@Override
	public PodMultiMapImpl<V> setAll(HttpMultiMap<V> multiMap) {
		super.setAll(multiMap);
		return this;
	}

	@Override
	public PodMultiMapImpl<V> setAll(Map<String, V> map) {
		super.setAll(map);
		return this;
	}

	@Override
	public PodMultiMapImpl<V> setAll(String name, Iterable<V> values) {
		super.setAll(name, values);
		return this;
	}

	private final boolean caseSensitive;

}