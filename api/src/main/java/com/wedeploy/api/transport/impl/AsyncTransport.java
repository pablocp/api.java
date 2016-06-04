package com.wedeploy.api.transport.impl;

import com.wedeploy.api.WeDeployException;
import com.wedeploy.api.sdk.Request;
import com.wedeploy.api.sdk.Response;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Asynchronous implementation of the transport for 3rd party implementations
 * that are natively asynchronous. Just implements the sync variant of the
 * sending method.
 *
 * @see BlockingTransport
 */
public abstract class AsyncTransport implements Transport {

	@Override
	public Response send(Request request) {
		CompletableFuture<Response> completableFutureOfResponse = sendAsync(
			request);

		try {
			return completableFutureOfResponse.join();
		}
		catch (CompletionException ce) {
			Throwable cause = ce.getCause();

			if (cause instanceof RuntimeException) {
				throw (RuntimeException)cause;
			}

			throw new WeDeployException("Transport failed", cause);
		}
	}

}