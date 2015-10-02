package com.liferay.launchpad.api;

import java.net.URISyntaxException;

import java.util.Map;

/**
 * Real-time.
 */
public abstract class RealTime {

	/**
	 * Creates an implementation of the real-time.
	 */
	public static RealTime io(String url, Map<String, Object> options)
		throws URISyntaxException {

		return RealTimeFactory.Default.factory.createRealTime(url, options);
	}

	/**
	 * Closes real-time connection.
	 */
	public abstract void close();

	/**
	 * Activates all callbacks of type `event` with the given args.
	 */
	public abstract void emit(String event, Object... args);

	/**
	 * Registers a callback of type `event` with `fn`.
	 */
	public abstract void on(String event, Listener fn);

	@FunctionalInterface
	public interface Listener {
		public void call(Object... args);

	}

}