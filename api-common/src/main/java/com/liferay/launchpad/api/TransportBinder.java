package com.liferay.launchpad.api;

/**
 *  TransportProvider
 */
public interface TransportBinder<F> {

	/**
	 * Initializes and returns {@link Transport} instance. Called once by client
	 * on the first sent request. Created instance of transport is stored and
	 * reused by all other requests.
	 */
	public Transport<F> initTransport();

}