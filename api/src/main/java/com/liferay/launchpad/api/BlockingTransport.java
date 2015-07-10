package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.RequestImpl;
import com.liferay.launchpad.sdk.Response;
import com.liferay.launchpad.sdk.ResponseImpl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * Transport base implementation for all 3rd party libraries
 * that blocks while sending.
 */
public abstract class BlockingTransport implements Transport {

	@Override
	public final CompletableFuture<Response> send(RequestImpl request) {
		return CompletableFuture.supplyAsync(
			() -> sendBlockingRequest(request), executor);
	}

	protected BlockingTransport() {
		this(false);
	}

	/**
	 * Creates new transport with dynamic thread pool.
	 */
	protected BlockingTransport(boolean useDedicatedExecutor) {
		if (useDedicatedExecutor) {
			this.executor = Executors.newCachedThreadPool();
		}
		else {
			this.executor = ForkJoinPool.commonPool();
		}
	}

	/**
	 * Creates new transport with fixed number of threads.
	 */
	protected BlockingTransport(int numberOfThreads) {
		executor = Executors.newFixedThreadPool(numberOfThreads);
	}

	/**
	 * Sends blocking request and returns the response.
	 */
	protected abstract ResponseImpl sendBlockingRequest(RequestImpl request);

	protected final ExecutorService executor;

}