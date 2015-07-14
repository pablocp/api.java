package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.json.JsonParser;
import com.liferay.launchpad.sdk.json.JsonSerializer;

import java.util.List;

/**
 * Simple JSON engine.
 */
public interface JsonEngine {

	/**
	 * Gets Json Serializer.
	 */
	public JsonParser getJsonParser();

	/**
	 * Gets Json Serializer.
	 */
	public JsonSerializer getJsonSerializer();

	/**
	 * Parses JSON string to list of objects.
	 */
	<T> List<T> parseJsonToList(String json, Class<T> type);

	/**
	 * Parses JSON string to an object.
	 */
	public <T> T parseJsonToModel(String json);

	/**
	 * Parses JSON string to given model.
	 */
	public <T> T parseJsonToModel(String json, Class<T> model);

	/**
	 * Serializes object to JSON string.
	 */
	public String serializeToJson(Object object);

	/**
	 * Serializes object to JSON string.
	 */
	public String serializeToJson(Object object, boolean deep);

}