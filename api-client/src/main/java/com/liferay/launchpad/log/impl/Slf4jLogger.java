package com.liferay.launchpad.log.impl;

import com.liferay.launchpad.log.PodLogger;

import jodd.util.StringUtil;

import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 * Default implementation of <code>PodLogger</code>.
 */
public class Slf4jLogger implements PodLogger {

	private static final String FQCN = PodLogger.class.getName();

	public Slf4jLogger(String name) {
		this.logger = LoggerFactory.getLogger(name);

		if (logger instanceof LocationAwareLogger) {
			locationAwareLogger = (LocationAwareLogger) logger;
		}
		else {
			locationAwareLogger = null;
		}
	}

	@Override
	public void debug(String message, Object... values) {
		if (locationAwareLogger != null) {
			locationAwareLogger.log(
				null, FQCN, LocationAwareLogger.DEBUG_INT, message, values, null);
		}
		else {
			logger.debug(message, values);
		}
	}

	@Override
	public void error(String message, Object... values) {
		if (locationAwareLogger != null) {
			locationAwareLogger.log(
				null, FQCN, LocationAwareLogger.ERROR_INT, message, values, null);
		}
		else {
			logger.warn(message, values);
		}
	}

	@Override
	public void error(String message, Throwable throwable, Object... values) {

		// manually create the message in order to print stack trace.
		// it's a bug with logback as it does not print the stacktrace
		// when error(string, throwable, string...) is used.

		for (Object value : values) {
			String str = StringUtil.toSafeString(value);

			message = StringUtil.replaceFirst(message, "{}", str);
		}

		if (locationAwareLogger != null) {
			locationAwareLogger.log(
				null, FQCN, LocationAwareLogger.ERROR_INT, message, values, throwable);
		}
		else {
			logger.error(message, throwable);
		}
	}

	@Override
	public String getName() {
		return logger.getName();
	}

	@Override
	public void info(String message, Object... values) {
		if (locationAwareLogger != null) {
			locationAwareLogger.log(
				null, FQCN, LocationAwareLogger.INFO_INT, message, values, null);
		}
		else {
			logger.info(message, values);
		}
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public boolean isEnabled(Level level) {
		switch (level) {
			case TRACE:
				return logger.isTraceEnabled();
			case DEBUG:
				return logger.isDebugEnabled();
			case INFO:
				return logger.isInfoEnabled();
			case WARN:
				return logger.isWarnEnabled();
			case ERROR:
				return logger.isErrorEnabled();
			default:
				throw new IllegalArgumentException();
		}
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	@Override
	public void log(Level level, String message, Object... values) {
		switch (level) {
			case TRACE:
				trace(message, values);
				break;
			case DEBUG:
				debug(message, values);
				break;
			case INFO:
				info(message, values);
				break;
			case WARN:
				warn(message, values);
				break;
			case ERROR:
				error(message, values);
				break;
		}
	}

	@Override
	public void trace(String message, Object... values) {
		if (locationAwareLogger != null) {
			locationAwareLogger.log(
				null, FQCN, LocationAwareLogger.TRACE_INT, message, values, null);
		}
		else {
			logger.trace(message, values);
		}
	}

	@Override
	public void warn(String message, Object... values) {
		if (locationAwareLogger != null) {
			locationAwareLogger.log(
				null, FQCN, LocationAwareLogger.WARN_INT, message, values, null);
		}
		else {
			logger.warn(message, values);
		}
	}

	@Override
	public void warn(String message, Throwable throwable, Object... values) {
		if (locationAwareLogger != null) {
			locationAwareLogger.log(
				null, FQCN, LocationAwareLogger.WARN_INT, message, values, throwable);
		}
		else {
			logger.warn(message, throwable, values);
		}
	}

	private final org.slf4j.Logger logger;
	private final LocationAwareLogger locationAwareLogger;
}
