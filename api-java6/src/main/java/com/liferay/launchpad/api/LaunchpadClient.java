package com.liferay.launchpad.api;

import java.util.concurrent.Future;

/**
 * Java 6 client.
 */
@MultiJava(version = 6)
public class LaunchpadClient
		extends LaunchpadBaseClient<Future<ClientResponse>, LaunchpadClient> {

	/**
	 * Static factory for creating launchpad client.
	 */
	public static LaunchpadClient url(String url) {
		return new LaunchpadClient(url);
	}

	public LaunchpadClient(String url) {
		super(url);
	}

	public LaunchpadClient(String url, String path) {
		super(url, path);
	}

	/**
	 * Creates new {@link LaunchpadBaseClient}.
	 */
	public LaunchpadClient path(String path) {
		return new LaunchpadClient(url, path)
			.use(customTransport)
			.use(customRunner);
	}

	static {
		mainAsyncRunner = new LaunchpadAsyncRunner();
	}

}