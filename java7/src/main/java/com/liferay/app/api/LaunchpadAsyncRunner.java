package com.liferay.app.api;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Configurable api executor that runs all the asynchronous calls.
 */
@MultiJava(version = 7)
public class LaunchpadAsyncRunner implements AsyncRunner<Future> {

	public LaunchpadAsyncRunner(int numberOfThreads) {
		executor = Executors.newFixedThreadPool(numberOfThreads);
	}

	@Override
	public Future runAsync(Callable callable) {
		return executor.submit(callable);
	}

	protected final ExecutorService executor;

}