package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.RequestImpl;
import com.liferay.launchpad.sdk.Response;

import java.util.concurrent.CompletableFuture;

/**
 * Transport implementations defines how the data will be actually transferred.
 * By the design, the transport is asynchronous and non-blocking.
 */
public interface Transport {

	/**
	 * Sends client request and returns promise/future of a response.
	 */
	public CompletableFuture<Response> send(RequestImpl request);

}