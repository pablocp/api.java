package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.RequestImpl;
import com.liferay.launchpad.sdk.Response;
import com.liferay.launchpad.sdk.ResponseImpl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

/**
 */
@MultiJava(version = 8)
public abstract class BlockingTransport
		extends BaseBlockingTransport<CompletableFuture<Response>> {

	@Override
	public final CompletableFuture<Response> send(
		RequestImpl request, ResponseConsumer responseConsumer) {

		return CompletableFuture.supplyAsync(() -> {
			try {
				ResponseImpl clientResponse = sendBlockingRequest(request);

				Util.validateResponse(clientResponse);

				if (responseConsumer != null) {
					responseConsumer.acceptResponse(clientResponse);
				}

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
	protected abstract ResponseImpl sendBlockingRequest(RequestImpl request);

}