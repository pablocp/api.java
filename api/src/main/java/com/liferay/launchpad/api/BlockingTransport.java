package com.liferay.launchpad.api;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
@MultiJava(version = 8)
public abstract class BlockingTransport
		extends BaseBlockingTransport<CompletableFuture<ClientResponse>> {

	@Override
	public final CompletableFuture<ClientResponse> send(
		ClientRequest clientRequest) {

		return CompletableFuture.supplyAsync(() -> {
			try {
				return sendBlockingRequest(clientRequest);
			}
			catch (Exception e) {
				throw new LaunchpadClientExecutionException(
					"Execution failed", e);
			}
		}, executor);
	}

	protected BlockingTransport() {
		this(false);
	}

	protected BlockingTransport(boolean useExecutor) {
		super(useExecutor ? null : ForkJoinPool.commonPool());
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