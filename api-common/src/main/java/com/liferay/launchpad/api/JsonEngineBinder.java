package com.liferay.launchpad.api;

/**
 * JsonEngine binder.
 */
public interface JsonEngineBinder {

	/**
	 * Initializes and returns {@link JsonEngine} instance. Called <b>once</b>
	 * by client on the first sent request. Created instance of the engine is
	 * stored and reused by all other requests.
	 */
	public JsonEngine initJsonEngine();

}