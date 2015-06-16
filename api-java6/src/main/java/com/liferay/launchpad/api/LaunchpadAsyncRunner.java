package com.liferay.launchpad.api;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Configurable api executor that runs all the asynchronous calls.
 */
@MultiJava(version = 6)
public class LaunchpadAsyncRunner extends BaseAsyncRunner<Future> {

	public LaunchpadAsyncRunner() {
	}

	public LaunchpadAsyncRunner(int numberOfThreads) {
		super(numberOfThreads);
	}

	@Override
	public Future runAsync(Callable callable) {
		return executor.submit(callable);
	}

}