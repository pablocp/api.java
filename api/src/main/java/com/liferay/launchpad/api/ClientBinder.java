package com.liferay.launchpad.api;

/**
 * Dynamic binder to <code>LaunchpadClientBinder</code> implementations.
 */
public class ClientBinder {

	public static final String LAUNCHPAD_CLIENT_TRANSPORT_BINDER_CLASSNAME =
		ClientBinder.class.getPackage().getName() + ".LaunchpadClientTransportBinder";

	/**
	 * Returns transport binder or <code>null</code> if client binder did not
	 * provide any.
	 */
	public static TransportBinder getTransportBinder() {
		if (transportBinder == null) {
			Object binder = createBinder(
				LAUNCHPAD_CLIENT_TRANSPORT_BINDER_CLASSNAME);

			// instances

			if (binder instanceof TransportBinder) {
				transportBinder = (TransportBinder)binder;
			}
		}

		return transportBinder;
	}

	/**
	 * Sets transport binder manually.
	 */
	public static void setTransportBinder(TransportBinder newTransportBinder) {
		transportBinder = newTransportBinder;
	}

	/**
	 * Creates new binder instance.
	 */
	private static Object createBinder(String binderClassName) {
		ClassLoader classLoader = ClientBinder.class.getClassLoader();

		Class launchpadClientBinderClass = null;

		try {
			launchpadClientBinderClass = classLoader.loadClass(binderClassName);
		}
		catch (ClassNotFoundException e) {
			throw new LaunchpadClientException(
				"Static binder not found: " + binderClassName, e);
		}

		Object binder = null;

		try {
			binder = launchpadClientBinderClass.newInstance();
		}
		catch (Exception e) {
			throw new LaunchpadClientException(
				"Unable to create binder instance.", e);
		}

		return binder;
	}

	private static TransportBinder transportBinder;

}