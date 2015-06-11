package com.liferay.launchpad.api;

import java.lang.reflect.Method;

/**
 * Static binder for {@link Transport} implementation.
 */
@SuppressWarnings("unchecked")
public class Transports {

	public static final String BINDER_METHOD_NAME = "newTransport";

	public static final String TRANSPORT_BINDER_CLASSNAME = ".TransportBinder";

	public static Transport getTransport() {
		if (transport == null) {
			ClassLoader classLoader = Transports.class.getClassLoader();

			String packageName = Transports.class.getPackage().getName();

			String binderClassName = packageName + TRANSPORT_BINDER_CLASSNAME;

			Class transportBinder;

			try {
				transportBinder = classLoader.loadClass(binderClassName);
			}
			catch (ClassNotFoundException e) {
				throw new LaunchpadClientException(
					"Static binder not found: " + binderClassName, e);
			}

			Method binderMethod;

			try {
				binderMethod = transportBinder.getMethod(BINDER_METHOD_NAME);
			}
			catch (NoSuchMethodException e) {
				throw new LaunchpadClientException(
					"Binder method not found: " + BINDER_METHOD_NAME, e);
			}

			try {
				transport = (Transport)binderMethod.invoke(null);
			}
			catch (Exception e) {
				throw new LaunchpadClientException(
					"Binder method invocation failed", e);
			}
		}

		return transport;
	}

	private static Transport transport;

}