package com.wedeploy.api.sdk.impl;

import com.wedeploy.api.sdk.MultiMap;

import java.util.Map;

import jodd.http.HttpMultiMap;
public class MultiMapImpl<V> extends HttpMultiMap<V> implements MultiMap<V> {

	public MultiMapImpl(boolean caseSensitive) {
		super(caseSensitive);
		this.caseSensitive = caseSensitive;
	}

	@Override
	public MultiMapImpl<V> add(String name, V value) {
		super.add(name, value);
		return this;
	}

	@Override
	public MultiMapImpl<V> addAll(Map<String, V> map) {
		super.addAll(map);
		return this;
	}

	@Override
	public MultiMapImpl<V> addAll(String name, Iterable<V> values) {
		super.addAll(name, values);
		return this;
	}

	@Override
	public MultiMapImpl<V> clear() {
		super.clear();
		return this;
	}

	@Override
	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	@Override
	public MultiMapImpl<V> remove(String name) {
		super.remove(name);
		return this;
	}

	@Override
	public MultiMapImpl<V> set(String name, V value) {
		super.set(name, value);
		return this;
	}

	@Override
	public MultiMapImpl<V> setAll(Map<String, V> map) {
		super.setAll(map);
		return this;
	}

	@Override
	public MultiMapImpl<V> setAll(String name, Iterable<V> values) {
		super.setAll(name, values);
		return this;
	}

	private final boolean caseSensitive;

}