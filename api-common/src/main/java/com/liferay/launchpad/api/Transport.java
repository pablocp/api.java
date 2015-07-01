package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.Request;

/**
 * Transport implementations defines how the data will be actually transferred.
 */
public interface Transport<F> {

	/**
	 * Sends client request and returns promise/future of a response.
	 */
	public F send(Request request);

}