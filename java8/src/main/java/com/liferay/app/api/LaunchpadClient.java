package com.liferay.app.api;

import java.util.concurrent.CompletableFuture;

/**
 * Java 8 client.
 */
@MultiJava(version = 8)
public class LaunchpadClient
		extends LaunchpadBaseClient<CompletableFuture, LaunchpadClient> {

	/**
	 * Sets new executor.
	 */
	// TODO(igor): add types!

	public static void setExecutor(int numberOfThreads) {
		asyncRunner = new LaunchpadAsyncRunner(numberOfThreads);
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
		setExecutor(10);
	}

}