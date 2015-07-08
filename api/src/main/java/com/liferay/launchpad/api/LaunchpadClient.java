package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.Response;

import java.util.concurrent.CompletableFuture;

/**
 * Java 8 client.
 */
@MultiJava(version = 8)
public class LaunchpadClient
		extends LaunchpadBaseClient<CompletableFuture<Response>, LaunchpadClient> {

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
		return new LaunchpadClient(url, path)
			.use(currentTransport)
			.use(currentJsonEngine);
	}

	private LaunchpadClient(String url, String path) {
		super(url, path);
	}

}