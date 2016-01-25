/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */

package com.liferay.launchpad.log;

import com.liferay.launchpad.sdk.PodException;
public class PodLoggerFactory {

	protected PodLoggerFactory() {}

	/**
	 * Returns logger for given class.
	 */
	public static PodLogger getLogger(Class clazz) {
		return getLogger(clazz.getName());
	}

	/**
	 * Returns logger for given name.
	 */
	public static PodLogger getLogger(String name) {
		initLoggerFactory();

		return loggerFactory.getLogger(name);
	}

	/**
	 * Sets the logger factory manually.
	 */
	public static void setLoggerFactory(
		PodLoggerFactoryInterface podLoggerFactory) {

		loggerFactory = podLoggerFactory;
	}

	private static void initLoggerFactory() {
		if (loggerFactory != null) {
			return;
		}

		Class clazz = PodLoggerFactory.class;

		ClassLoader classLoader = clazz.getClassLoader();

		Class podLoggerClass = null;

		try {
			podLoggerClass = classLoader.loadClass(
				clazz.getPackage().getName() + "." + defaultImplPackage + "." +
					defaultImplClass);
		}
		catch (ClassNotFoundException e) {
			throw new PodException("PodLogger implementation not found", e);
		}

		try {
			loggerFactory =
				(PodLoggerFactoryInterface)podLoggerClass.newInstance();
		}
		catch (Exception e) {
			throw new PodException("Invalid PodLogger implementation", e);
		}
	}

	public static void setDefaultImplPackage(String name) {
		defaultImplPackage = name;
	}

	public static void setDefaultImplClass(String name) {
		defaultImplClass = name;
	}

	protected static String defaultImplPackage = "impl";
	protected static String defaultImplClass = "PodLoggerImpl";

	private static PodLoggerFactoryInterface loggerFactory;

}