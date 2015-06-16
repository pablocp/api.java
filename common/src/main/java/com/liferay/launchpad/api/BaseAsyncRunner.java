package com.liferay.launchpad.api;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Base class containing common code.
 */
public abstract class BaseAsyncRunner<F> implements AsyncRunner<F> {

	/**
	 * Creates new launchpad runner with given type and optional number of
	 * threads.
	 */
	protected BaseAsyncRunner(ExecutorType executorType, int numberOfThreads) {
		this.executorType = executorType;

		switch (executorType) {
			case FIXED:
				executor = Executors.newFixedThreadPool(numberOfThreads);
				break;
			case CACHED:
				executor = Executors.newCachedThreadPool();
				break;
			default:
				executor = null;
		}
	}

	protected final ExecutorService executor;
	protected final ExecutorType executorType;

}