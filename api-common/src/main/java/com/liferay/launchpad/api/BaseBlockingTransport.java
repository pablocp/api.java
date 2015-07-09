package com.liferay.launchpad.api;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Base blocking transport is used by 3rd party libraries that operates
 * with http synchronously.
 */
public abstract class BaseBlockingTransport<F> implements Transport<F> {

	/**
	 * Creates new transport with dynamic thread pool.
	 */
	protected BaseBlockingTransport(ExecutorService executorService) {
		if (executorService != null) {
			this.executor = executorService;
		}
		else {
			this.executor = Executors.newCachedThreadPool();
		}
	}

	/**
	 * Creates new transport with fixed number of threads.
	 */
	protected BaseBlockingTransport(int numberOfThreads) {
		executor = Executors.newFixedThreadPool(numberOfThreads);
	}

	protected final ExecutorService executor;

}