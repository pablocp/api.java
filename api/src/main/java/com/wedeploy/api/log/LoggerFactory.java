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

package com.wedeploy.api.log;

import com.wedeploy.api.WeDeployException;
public class LoggerFactory {

	/**
	 * Returns logger for given class.
	 */
	public static Logger getLogger(Class clazz) {
		return getLogger(clazz.getName());
	}

	/**
	 * Returns logger for given name.
	 */
	public static Logger getLogger(String name) {
		initLoggerFactory();

		return loggerFactory.getLogger(name);
	}

	/**
	 * Sets custom default implementation class name.
	 * @see #setLoggerFactory(LoggerFactoryInterface)
	 */
	public static void setDefaultImplClass(String className) {
		defaultClassName = className;
	}

	/**
	 * Sets default logger factory instance. If set, default name will not be
	 * used.
	 * @see #setDefaultImplClass(String)
	 */
	public static void setLoggerFactory(LoggerFactoryInterface loggerFactory) {
		LoggerFactory.loggerFactory = loggerFactory;
	}

	protected LoggerFactory() {
	}

	protected static String defaultClassName =
		LoggerFactory.class.getPackage().getName() + ".impl.LoggerImpl";

	private static void initLoggerFactory() {
		if (loggerFactory != null) {
			return;
		}

		Class clazz = LoggerFactory.class;

		ClassLoader classLoader = clazz.getClassLoader();

		Class loggerClass = null;

		try {
			loggerClass = classLoader.loadClass(defaultClassName);
		}
		catch (ClassNotFoundException cnfe) {
			throw new WeDeployException(
				"Logger implementation not found: " + defaultClassName, cnfe);
		}

		try {
			loggerFactory = (LoggerFactoryInterface)loggerClass.newInstance();
		}
		catch (Exception e) {
			throw new WeDeployException(
				"Invalid Logger implementation: " + defaultClassName, e);
		}
	}

	private static LoggerFactoryInterface loggerFactory;

}