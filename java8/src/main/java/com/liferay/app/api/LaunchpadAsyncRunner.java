package com.liferay.app.api;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Configurable api executor that runs all the asynchronous calls.
 */
@MultiJava(version = 8)
public class LaunchpadAsyncRunner implements AsyncRunner<CompletableFuture> {

	public LaunchpadAsyncRunner(int numberOfThreads) {
		executor = Executors.newFixedThreadPool(numberOfThreads);
	}

	@Override
	public CompletableFuture runAsync(Callable<String> callable) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return callable.call();
			}
			catch (Exception e) {
				throw new LaunchpadClientExecutionException(
					"Execution failed", e);
			}
		});
	}

	protected final ExecutorService executor;

}