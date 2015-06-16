package com.liferay.launchpad.api;

/**
 * Dynamic binder to <code>LaunchpadClientBinder</code> implementations.
 */
public class Binder {

	public static final String LAUNCHPAD_CLIENT_BINDER_CLASSNAME =
		Binder.class.getPackage().getName() + ".LaunchpadClientBinder";

	/**
	 * Returns transport binder or <code>null</code> if client binder did not
	 * provide any.
	 */
	public static TransportBinder getTransportBinder() {
		init();
		return transportBinder;
	}

	/**
	 * Initialize binding by dynamical loading of the binding class.
	 */
	private static void init() {
		if (launchpadClientBinderClass != null) {
			return;
		}

		ClassLoader classLoader = Binder.class.getClassLoader();

		try {
			launchpadClientBinderClass =
				classLoader.loadClass(LAUNCHPAD_CLIENT_BINDER_CLASSNAME);
		}
		catch (ClassNotFoundException e) {
			throw new LaunchpadClientException(
				"Static binder not found: " + LAUNCHPAD_CLIENT_BINDER_CLASSNAME,
				e);
		}

		Object binder;

		try {
			binder = launchpadClientBinderClass.newInstance();
		}
		catch (Exception e) {
			throw new LaunchpadClientException(
				"Unable to create binder instance.", e);
		}

		// store

		if (binder instanceof TransportBinder) {
			transportBinder = (TransportBinder) binder;
		}
	}

	private static Class launchpadClientBinderClass;
	private static TransportBinder transportBinder;
}