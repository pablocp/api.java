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

	public Transport get(String implementationName) {
		Class<? extends Transport> transportClass = transports.get(
			implementationName);

		if (transportClass == null) {
			throw new LaunchpadClientException(
				"Invalid transport name: " + implementationName);
		}

		try {
			return transportClass.newInstance();
		}
		catch (Exception e) {
			throw new LaunchpadClientException("Can't create transport", e);
		}
	}

	/**
	 * Returns default transport.
	 */
	public Transport getDefault() {
		return get(DEFAULT_TRANSPORT_NAME);
	}

	private TransportFactory() {
		transports = new HashMap<String, Class<? extends Transport>>();

		transports.put("default", JoddHttpTransport.class);
		transports.put("jodd", JoddHttpTransport.class);
	}

	private static final TransportFactory instance = new TransportFactory();

	private Map<String, Class<? extends Transport>> transports;

}