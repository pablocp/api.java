package com.liferay.launchpad.sdk;
public interface AppConfig {

	/**
	 * The public domain for this application.
	 */
	public String domain();

	/**
	 * The master token than can be used to bypass authorization rules.
	 * This gives the request root access to the application.
	 */
	public String masterToken();

	/**
	 * The name of the application.
	 */
	public String name();

}