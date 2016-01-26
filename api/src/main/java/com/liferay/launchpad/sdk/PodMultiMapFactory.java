package com.liferay.launchpad.sdk;
public interface PodMultiMapFactory {

	public <V> PodMultiMap<V> createMultiMap(boolean caseSensitive);

	public static class Default {

		protected Default() {}

		public static PodMultiMapFactory factory;

	}

}