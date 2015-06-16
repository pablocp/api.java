package com.liferay.launchpad.api;

import java.util.concurrent.CompletableFuture;

/**
 * Java 8 client.
 */
@MultiJava(version = 8)
public class LaunchpadClient
		extends LaunchpadBaseClient<CompletableFuture<ClientResponse>, LaunchpadClient> {

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
		return new LaunchpadClient(customTransport, url, path);
	}

	private LaunchpadClient(Transport transport, String baseUrl, String url) {
		super(transport, baseUrl, url);
	}

	static {
		mainAsyncRunner = new LaunchpadAsyncRunner();
	}

}