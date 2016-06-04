package com.wedeploy.api.uuid;

/**
 * Static reference to default uuid generator.
 */
public class DefaultUuidGenerator {

	/**
	 * Returns default uuid generator or <code>null</code> if default
	 * generator is not set.
	 */
	public static UuidGenerator getDefaultGenerator() {
		return generator;
	}

	/**
	 * Sets default transport.
	 */
	public static void setDefaultGenerator(UuidGenerator defaultGenerator) {
		generator = defaultGenerator;
	}

	protected DefaultUuidGenerator() {
	}

	private static UuidGenerator generator;

}