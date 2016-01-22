package com.liferay.launchpad.api;

/**
 * Static reference to default transport.
 */
public class DefaultTransport {

	protected DefaultTransport() {}

	/**
	 * Returns default transport or <code>null</code> if default transport is not set.
	 */
	public static Transport defaultTransport() {
		return transport;
	}

	/**
	 * Sets default transport.
	 */
	public static void defaultTransport(Transport defaultTransport) {
		transport = defaultTransport;
	}

	private static Transport transport;

}