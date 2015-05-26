package com.liferay.app.api;

import java.util.concurrent.CompletableFuture;

/**
 * Java 8 client.
 */
@MultiJava(version = 8)
public class LaunchpadClient
		extends LaunchpadBaseClient<CompletableFuture<ClientResponse>, LaunchpadClient> {

	/**
	 * Sets new executor.
	 */
	public static void setExecutor(
		AsyncRunner.ExecutorType executorType, int numberOfThreads) {

		asyncRunner = new LaunchpadAsyncRunner(executorType, numberOfThreads);
	}

	/**
	 * Static factory for creating launchpad client.
	 */
	public static LaunchpadClient url(String url) {
		return new LaunchpadClient(url);
	}

	public LaunchpadClient(String url) {
		super(url);
	}

	/**
	 * Creates new {@link LaunchpadBaseClient}.
	 */
	public LaunchpadClient path(String path) {
		return new LaunchpadClient(url, path);
	}

	private LaunchpadClient(String baseUrl, String url) {
		super(baseUrl, url);
	}

	static {
		setExecutor(AsyncRunner.ExecutorType.FIXED, 10);
	}

}