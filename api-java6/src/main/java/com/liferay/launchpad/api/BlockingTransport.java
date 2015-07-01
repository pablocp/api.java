package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.Request;
import com.liferay.launchpad.sdk.Response;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
@MultiJava(version = 6)
public abstract class BlockingTransport
		extends BaseBlockingTransport<Future<Response>> {

	@Override
	public final Future<Response> send(final Request request) {
		return executor.submit(new Callable<Response>() {
			@Override
			public Response call() throws Exception {
				return sendBlockingRequest(request);
			}
		});
	}

	protected BlockingTransport() {
		super(null);
	}

	protected BlockingTransport(int numberOfThreads) {
		super(numberOfThreads);
	}

	/**
	 * Sends blocking request and returns the response.
	 */
	protected abstract Response sendBlockingRequest(Request request);

}