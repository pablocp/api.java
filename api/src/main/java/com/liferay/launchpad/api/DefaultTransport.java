package com.liferay.launchpad.api;

import com.liferay.launchpad.api.impl.JoddHttpTransport;

/**
 * Static reference to default transport.
 */
public class DefaultTransport {

	/**
	 * Returns default transport or <code>null</code> if default transport is not set.
	 */
	public static Transport getDefaultTransport() {
		return transport;
	}

	/**
	 * Sets default transport.
	 */
	public static void setDefaultTransport(Transport defaultTransport) {
		transport = defaultTransport;
	}

	private static Transport transport = new JoddHttpTransport();

}