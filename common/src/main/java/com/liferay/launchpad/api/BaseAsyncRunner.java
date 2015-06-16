package com.liferay.launchpad.api;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Base class containing common code and the executor.
 */
public abstract class BaseAsyncRunner<F> implements AsyncRunner<F> {

	/**
	 * Creates new launchpad runner with dynamic thread pool.
	 */
	protected BaseAsyncRunner(ExecutorService executorService) {
		if (executorService != null) {
			this.executor = executorService;
		}
		else {
			this.executor = Executors.newCachedThreadPool();
		}
	}

	/**
	 * Creates new launchpad runner with fixed number of threads.
	 */
	protected BaseAsyncRunner(int numberOfThreads) {
		executor = Executors.newFixedThreadPool(numberOfThreads);
	}

	protected final ExecutorService executor;

}