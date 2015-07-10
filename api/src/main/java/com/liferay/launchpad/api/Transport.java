package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.RequestImpl;
import com.liferay.launchpad.sdk.ResponseImpl;

/**
 * Transport implementations defines how the data will be actually transferred.
 */
public interface Transport<F> {

	/**
	 * Sends client request and returns promise/future of a response.
	 */
	public F send(RequestImpl request, ResponseConsumer responseConsumer);

	/**
	 * Response consumer.
	 */
	public interface ResponseConsumer {

		/**
		 * Accepts response.
		 */
		public void acceptResponse(ResponseImpl response);

	}

}