package com.liferay.launchpad.api;

/**
 * Transport binder.
 */
public interface TransportBinder<F> {

	/**
	 * Initializes and returns {@link Transport} instance. Called <b>once</b>
	 * by client on the first sent request. Created instance of transport is
	 * stored and reused by all other requests.
	 */
	public Transport initTransport();

}