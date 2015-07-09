package com.liferay.launchpad.api;

import java.util.Map;

/**
 * Simple JSON engine.
 */
public interface JsonEngine {

	/**
	 * Parses JSON string to given model.
	 */
	public <T> T parseJsonToModel(String json, Class<T> model);

	/**
	 * Parses JSON string to Map.
	 */
	public Map<String, Object> parseJsonToModel(String json);

	/**
	 * Serializes object to JSON string.
	 */
	public String serializeToJson(Object object);

}