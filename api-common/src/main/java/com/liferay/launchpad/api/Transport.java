package com.liferay.launchpad.api;

/**
 * Transport implementations defines how the data will be actually transferred.
 */
public interface Transport<F> {

	/**
	 * Sends client request and returns promise/future of a response.
	 */
	public F send(ClientRequest clientRequest);

}