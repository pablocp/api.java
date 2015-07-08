package com.liferay.launchpad.api;

/**
 * Simple JSON engine.
 */
public interface JsonEngine {

	/**
	 * Serializes object to JSON string.
	 */
	public String serializeToJson(Object object);
}