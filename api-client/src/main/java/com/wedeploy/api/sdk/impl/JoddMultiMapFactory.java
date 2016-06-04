package com.wedeploy.api.sdk.impl;

import com.wedeploy.api.sdk.MultiMap;
import com.wedeploy.api.sdk.MultiMapFactory;
public class JoddMultiMapFactory implements MultiMapFactory {

	@Override
	public <V> MultiMap<V> createMultiMap(boolean caseSensitive) {
		return new MultiMapImpl<>(caseSensitive);
	}

}