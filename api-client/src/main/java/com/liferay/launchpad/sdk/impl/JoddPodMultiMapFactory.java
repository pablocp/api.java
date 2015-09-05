package com.liferay.launchpad.sdk.impl;

import com.liferay.launchpad.sdk.PodMultiMap;
import com.liferay.launchpad.sdk.PodMultiMapFactory;
public class JoddPodMultiMapFactory implements PodMultiMapFactory {

	@Override
	public <V> PodMultiMap<V> createMultiMap(boolean caseSensitive) {
		return new PodMultiMapImpl<>(caseSensitive);
	}

}