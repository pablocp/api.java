package com.liferay.app.api;

import java.util.concurrent.Callable;

/**
 * Asynchronous runner executes {@link Callable} and returns a String result.
 */
public interface AsyncRunner<F> {

	/**
	 * Executor types.
	 */
	enum ExecutorType {
		/**
		 * Fixed executor, requires number of threads to be specified.
		 */
		FIXED, /**
		 * Dynamic executor, will spawn new threads if needed.
		 */
		CACHED
	}

	/**
	 * Runs callable asynchronously, using the exe.
	 */
	F runAsync(Callable<ClientResponse> callable);

}