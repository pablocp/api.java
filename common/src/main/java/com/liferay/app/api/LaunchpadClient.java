package com.liferay.app.api;

import java.util.concurrent.Future;

/**
 * Java 7 client.
 */
@MultiJava(version = 6)
public class LaunchpadClient
		extends LaunchpadBaseClient<Future<String>, LaunchpadClient> {

	static {
		setExecutor(AsyncRunner.ExecutorType.FIXED, 10);
	}

	public LaunchpadClient(String url) {
		super(url);
	}

	LaunchpadClient(String baseUrl, String url) {
		super(baseUrl, url);
	}

	/**
	 * Static factory for creating launchpad client.
	 */
	public static LaunchpadClient url(String url) {
		return new LaunchpadClient(url);
	}

	/**
	 * Creates new {@link LaunchpadBaseClient}.
	 */
	public LaunchpadClient path(String path) {
		return new LaunchpadClient(url, path);
	}

	/**
	 * Sets new executor.
	 */
	public static void setExecutor(
			AsyncRunner.ExecutorType executorType, int numberOfThreads) {

		asyncRunner = new LaunchpadAsyncRunner(executorType, numberOfThreads);
	}

}