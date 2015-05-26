package com.liferay.app.api;

import com.liferay.app.api.impl.JoddHttpTransport;

import java.util.HashMap;
import java.util.Map;
public class TransportFactory {

	public static final String DEFAULT_TRANSPORT_NAME = "default";

	/**
	 * Returns {@code TransportFactory} instance.
	 */
	public static TransportFactory instance() {
		return instance;
	}

	/**
	 * Returns transport registered for given name.
	 */
	public Transport get(String implementationName) {
		Transport transport = transports.get(implementationName);

		if (transport == null) {
			throw new LaunchpadClientException(
				"Invalid transport: " + implementationName);
		}

		return transport;
	}

	/**
	 * Returns default transport.
	 */
	public Transport getDefault() {
		return get(DEFAULT_TRANSPORT_NAME);
	}

	/**
	 * Registers new default transport.
	 */
	public void registerDefaultTransport(Transport transport) {
		transports.put(DEFAULT_TRANSPORT_NAME, transport);
	}

	/**
	 * Registers new transport.
	 */
	public void registerTransport(String name, Transport transport) {
		transports.put(name, transport);
	}

	private TransportFactory() {
		transports = new HashMap<String, Transport>();

		Transport defaultTransport = new JoddHttpTransport();

		transports.put("default", defaultTransport);
		transports.put("jodd", defaultTransport);
	}

	private static final TransportFactory instance = new TransportFactory();

	private Map<String, Transport> transports;

}