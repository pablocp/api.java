package com.liferay.app.api;

import java.util.concurrent.Callable;

/**
 * Asynchronous runner executes {@link Callable} and returns a String result.
 */
public interface AsyncRunner<F> {

	F runAsync(Callable<String> callable);

}