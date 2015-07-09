package com.liferay.launchpad.api;

/**
 * Dynamic binder to <code>LaunchpadClientBinder</code> implementations.
 */
public class Binder {

	public static final String LAUNCHPAD_CLIENT_JSONENGINE_BINDER_CLASSNAME =
		Binder.class.getPackage().getName() +
			".LaunchpadClientJsonEngineBinder";

	public static final String LAUNCHPAD_CLIENT_TRANSPORT_BINDER_CLASSNAME =
		Binder.class.getPackage().getName() + ".LaunchpadClientTransportBinder";

	/**
	 * Returns JSON binder or <code>null</code> if client binder did not
	 * provide any.
	 */
	public static JsonEngineBinder getJsonEngineBinder() {
		if (jsonEngineBinder == null) {
			Object binder = createBinder(
				LAUNCHPAD_CLIENT_JSONENGINE_BINDER_CLASSNAME);

			if (binder instanceof JsonEngineBinder) {
				jsonEngineBinder = (JsonEngineBinder)binder;
			}
		}

		return jsonEngineBinder;
	}

	/**
	 * Returns transport binder or <code>null</code> if client binder did not
	 * provide any.
	 */
	public static <F> TransportBinder<F> getTransportBinder() {
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
	 * Creates new binder instance.
	 */
	private static Object createBinder(String binderClassName) {
		ClassLoader classLoader = Binder.class.getClassLoader();

		Class launchpadClientBinderClass = null;

		try {
			launchpadClientBinderClass = classLoader.loadClass(binderClassName);
		}
		catch (ClassNotFoundException e) {
			throw new LaunchpadClientException(
				"Static binder not found: " + binderClassName, e);
		}

		try {
			binder = launchpadClientBinderClass.newInstance();
		}
		catch (Exception e) {
			throw new LaunchpadClientException(
				"Unable to create binder instance.", e);
		}

		return binder;
	}

	private static Object binder;
	private static JsonEngineBinder jsonEngineBinder;
	private static TransportBinder transportBinder;

}