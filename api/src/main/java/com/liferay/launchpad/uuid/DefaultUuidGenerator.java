package com.liferay.launchpad.uuid;

/**
 * Static reference to default uuid generator.
 */
public class DefaultUuidGenerator {

	/**
	 * Returns default uuid generator or <code>null</code> if default
	 * generator is not set.
	 */
	public static LaunchpadUuidGenerator getDefaultGenerator() {
		return generator;
	}

	/**
	 * Sets default transport.
	 */
	public static void setDefaultGenerator(
		LaunchpadUuidGenerator defaultGenerator) {

		generator = defaultGenerator;
	}

	private static LaunchpadUuidGenerator generator;

}