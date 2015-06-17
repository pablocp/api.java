package com.liferay.launchpad.api;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
@MultiJava(version = 6)
public abstract class BlockingTransport
		extends BaseBlockingTransport<Future<ClientResponse>> {

	@Override
	public final Future<ClientResponse> send(
		final ClientRequest clientRequest) {

		return executor.submit(new Callable<ClientResponse>() {
			@Override
			public ClientResponse call() throws Exception {
				return sendBlockingRequest(clientRequest);
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
	protected abstract ClientResponse sendBlockingRequest(
		ClientRequest clientRequest);

}