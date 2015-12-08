package com.liferay.launchpad.log.impl;

import com.liferay.launchpad.log.PodLogger;

import jodd.util.StringUtil;

import org.slf4j.LoggerFactory;

/**
 * Default implementation of <code>PodLogger</code>.
 */
public class Slf4jLogger implements PodLogger {

	public Slf4jLogger(String name) {
		this.logger = LoggerFactory.getLogger(name);
	}

	@Override
	public void debug(String message, Object... values) {
		logger.debug(message, values);
	}

	@Override
	public void error(String message, Object... values) {
		logger.error(message, values);
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

		logger.error(message, throwable);
	}

	@Override
	public String getName() {
		return logger.getName();
	}

	@Override
	public void info(String message, Object... values) {
		logger.info(message, values);
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
				logger.trace(message, values);
				break;
			case DEBUG:
				logger.debug(message, values);
				break;
			case INFO:
				logger.info(message, values);
				break;
			case WARN:
				logger.warn(message, values);
				break;
			case ERROR:
				logger.error(message, values);
				break;
		}
	}

	@Override
	public void trace(String message, Object... values) {
		logger.trace(message, values);
	}

	@Override
	public void warn(String message, Object... values) {
		logger.warn(message, values);
	}

	@Override
	public void warn(String message, Throwable throwable, Object... values) {
		logger.warn(message, throwable, values);
	}

	private final org.slf4j.Logger logger;

}