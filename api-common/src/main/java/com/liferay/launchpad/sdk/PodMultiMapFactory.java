package com.liferay.launchpad.sdk;

/**
 * Factory for <code>PodMultiMap</code>, needed because of Java6.
 */
public class PodMultiMapFactory {

	public static PodMultiMap newMultiMap() {
		return new PodMultiMapImpl();
	}

}