package com.wedeploy.api.sdk;
public interface MultiMapFactory {

	public <V> MultiMap<V> createMultiMap(boolean caseSensitive);

	public static class Default {

		public static MultiMapFactory factory;

		protected Default() {
		}

	}

}