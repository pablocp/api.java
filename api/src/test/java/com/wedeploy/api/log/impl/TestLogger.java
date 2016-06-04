package com.wedeploy.api.log.impl;

import com.wedeploy.api.log.Logger;

import java.util.List;

/**
 * @author Lais Andrade
 */
public class TestLogger implements Logger {

	public TestLogger(String name, List<LogEntry> logEntries) {
		this.name = name;
		this.logEntries = logEntries;
	}

	@Override
	public void debug(String message, Object... values) {
		logEntries.add(new LogEntry(Level.DEBUG, message, null, values));
	}

	@Override
	public void error(String message, Object... values) {
		logEntries.add(new LogEntry(Level.ERROR, message, null, values));
	}

	@Override
	public void error(String message, Throwable throwable, Object... values) {
		logEntries.add(new LogEntry(Level.ERROR, message, throwable, values));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void info(String message, Object... values) {
		logEntries.add(new LogEntry(Level.INFO, message, null, values));
	}

	@Override
	public boolean isDebugEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(Level level) {
		return true;
	}

	@Override
	public boolean isErrorEnabled() {
		return true;
	}

	@Override
	public boolean isInfoEnabled() {
		return true;
	}

	@Override
	public boolean isTraceEnabled() {
		return true;
	}

	@Override
	public boolean isWarnEnabled() {
		return true;
	}

	@Override
	public void log(Level level, String message, Object... values) {
		logEntries.add(new LogEntry(level, message, null, values));
	}

	@Override
	public void trace(String message, Object... values) {
		logEntries.add(new LogEntry(Level.TRACE, message, null, values));
	}

	@Override
	public void warn(String message, Object... values) {
		logEntries.add(new LogEntry(Level.WARN, message, null, values));
	}

	@Override
	public void warn(String message, Throwable throwable, Object... values) {
		logEntries.add(new LogEntry(Level.WARN, message, throwable, values));
	}

	public static class LogEntry {

		public LogEntry(
			Level level, String message, Throwable throwable, Object[] values) {

			this.level = level;
			this.message = message;
			this.throwable = throwable;
			this.values = values;
		}

		public final Level level;
		public final String message;
		public final Throwable throwable;
		public final Object[] values;

	}

	private final List<LogEntry> logEntries;
	private final String name;

}