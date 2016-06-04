package com.wedeploy.api.transport.impl;

/**
 * Static reference to default transport.
 */
public class DefaultTransport {

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

	public DefaultTransport() {
	}

	private static Transport transport;

}