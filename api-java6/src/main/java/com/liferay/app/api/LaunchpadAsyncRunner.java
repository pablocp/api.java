package com.liferay.app.api;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Configurable executor that runs all the asynchronous calls.
 */
@MultiJava(version = 6)
public class LaunchpadAsyncRunner extends BaseAsyncRunner<Future> {

	public LaunchpadAsyncRunner(
		ExecutorType executorType, int numberOfThreads) {

		super(executorType, numberOfThreads);
	}

	@Override
	public Future runAsync(Callable callable) {
		return executor.submit(callable);
	}

}