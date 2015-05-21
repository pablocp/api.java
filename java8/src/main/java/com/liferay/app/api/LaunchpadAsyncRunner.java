package com.liferay.app.api;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * Configurable api executor that runs all the asynchronous calls.
 */
@MultiJava(version = 8)
public class LaunchpadAsyncRunner extends BaseAsyncRunner<CompletableFuture> {

	public LaunchpadAsyncRunner(
		ExecutorType executorType, int numberOfThreads) {

		super(executorType, numberOfThreads);
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

}