package com.liferay.launchpad.api;

import java.util.concurrent.Callable;

/**
 * Asynchronous runner executes {@link Callable} and returns a result. Returned
 * result is a promise of the result.
 */
public interface AsyncRunner<F> {

	/**
	 * Runs callable asynchronously.
	 */
	F runAsync(Callable<ClientResponse> callable);

}