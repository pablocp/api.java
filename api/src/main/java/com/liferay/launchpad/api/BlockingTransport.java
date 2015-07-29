package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.Request;
import com.liferay.launchpad.sdk.Response;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * Blocking transport is base implementation for blocking 3rd party
 * implementations. It uses executors pool to perform the async call.
 * Implementations of this class only have to implement the blocking
 * {@link #send(Request) send call}.
 *
 * @see AsyncTransport
 */
public abstract class BlockingTransport implements Transport {

	@Override
	public final CompletableFuture<Response> sendAsync(Request request) {

		return CompletableFuture.supplyAsync(() -> send(request), executor);
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

	protected final ExecutorService executor;

}