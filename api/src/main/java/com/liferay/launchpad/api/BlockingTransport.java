package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.Request;
import com.liferay.launchpad.sdk.Response;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

/**
 */
@MultiJava(version = 8)
public abstract class BlockingTransport
		extends BaseBlockingTransport<CompletableFuture<Response>> {

	@Override
	public final CompletableFuture<Response> send(Request request) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				Response clientResponse = sendBlockingRequest(request);
				validateResponse(clientResponse);
				return clientResponse;
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
	protected abstract Response sendBlockingRequest(Request request);

}