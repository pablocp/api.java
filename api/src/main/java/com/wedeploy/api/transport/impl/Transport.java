package com.wedeploy.api.transport.impl;

import com.wedeploy.api.sdk.Request;
import com.wedeploy.api.sdk.Response;

import java.util.concurrent.CompletableFuture;

/**
 * Transport implementations defines how the data will be actually transferred.
 * By the design, the transport is asynchronous and non-blocking.
 */
public interface Transport {

	/**
	 * Sends client request and returns response in sync manner.
	 * This call is <i>blocking</i> the thread.
	 */
	public Response send(Request request);

	/**
	 * Sends client request and returns promise/future of a response.
	 * This call is <i>asynchronous</i> and does not block the thread.
	 */
	public CompletableFuture<Response> sendAsync(Request request);

}