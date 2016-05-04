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

/**
 * Pod logger.
 */
public interface PodLogger {

	/**
	 * Logs a message at DEBUG level.
	 */
	public default void debug(String message) {
		debug(message, NO_ARGS);
	}

	/**
	 * Logs a message at DEBUG level.
	 */
	public void debug(String message, Object... values);

	/**
	 * Logs a message at ERROR level.
	 */
	public default void error(String message) {
		error(message, NO_ARGS);
	}

	/**
	 * Logs a message at ERROR level.
	 */
	public void error(String message, Object... values);

	/**
	 * Logs a message at ERROR level.
	 */
	public default void error(String message, Throwable throwable) {
		error(message, throwable, NO_ARGS);
	}

	/**
	 * Logs a message at ERROR level.
	 */
	public void error(String message, Throwable throwable, Object... values);

	/**
	 * Returns Logger name.
	 */
	public String getName();

	/**
	 * Logs a message at INFO level.
	 */
	public default void info(String message) {
		info(message, NO_ARGS);
	}

	/**
	 * Logs a message at INFO level.
	 */
	public void info(String message, Object... values);

	/**
	 * Returns <code>true</code> if DEBUG level is enabled.
	 */
	public boolean isDebugEnabled();

	/**
	 * Returns <code>true</code> if certain logging
	 * level is enabled.
	 */
	public boolean isEnabled(Level level);

	/**
	 * Returns <code>true</code> if ERROR level is enabled.
	 */
	public boolean isErrorEnabled();

	/**
	 * Returns <code>true</code> if INFO level is enabled.
	 */
	public boolean isInfoEnabled();

	/**
	 * Returns <code>true</code> if TRACE level is enabled.
	 */
	public boolean isTraceEnabled();

	/**
	 * Returns <code>true</code> if WARN level is enabled.
	 */
	public boolean isWarnEnabled();

	/**
	 * Logs a message at provided logging level.
	 */
	public default void log(Level level, String message) {
		log(level, message, NO_ARGS);
	}

	/**
	 * Logs a message at provided logging level.
	 */
	public void log(Level level, String message, Object... values);

	/**
	 * Logs a message at TRACE level.
	 */
	public default void trace(String message) {
		trace(message, NO_ARGS);
	}

	/**
	 * Logs a message at TRACE level.
	 */
	public void trace(String message, Object... values);

	/**
	 * Logs a message at WARN level.
	 */
	public default void warn(String message) {
		warn(message, NO_ARGS);
	}

	/**
	 * Logs a message at WARN level.
	 */
	public void warn(String message, Object... values);

	/**
	 * Logs a message at WARN level.
	 */
	public default void warn(String message, Throwable throwable) {
		warn(message, throwable, NO_ARGS);
	}

	/**
	 * Logs a message at WARN level.
	 */
	public void warn(String message, Throwable throwable, Object... values);

	public Object[] NO_ARGS = new Object[0];

	/**
	 * Logger level.
	 */
	public enum Level {

		TRACE(1), DEBUG(2), INFO(3), WARN(4), ERROR(5);

		/**
		 * Returns <code>true</code> if this level
		 * is enabled for given required level.
		 */
		public boolean isEnabledFor(Level level) {
			if (this.value >= level.value) {
				return true;
			}

			return false;
		}

		private Level(int value) {
			this.value = value;
		}

		private final int value;

	}

}