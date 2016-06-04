package com.wedeploy.api.log;

import com.wedeploy.api.WeDeployException;
import com.wedeploy.api.log.impl.TestLogger;
import com.wedeploy.api.log.impl.TestLoggerFactory;
import com.wedeploy.api.transport.impl.TestTransport;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Lais Andrade
 */
public class LoggerTest {

	@Before
	public void setup() {
		logEntries = new ArrayList<>();
		LoggerFactory.setLoggerFactory(
			name -> new TestLogger(name, logEntries));
	}

	@Test
	public void testLevel_isEnabledFor() {
		Logger.Level[] values = Logger.Level.values();

		for (Logger.Level value : values) {
			for (Logger.Level previous : values) {
				if (previous == value) {
					break;
				}

				Assert.assertTrue(value.isEnabledFor(previous));
				Assert.assertFalse(previous.isEnabledFor(value));
			}
		}
	}

	@Test
	public void testLogger_withLevel() {
		Logger logger = LoggerFactory.getLogger("name");

		logger.log(Logger.Level.DEBUG, "msg");
		logger.log(Logger.Level.DEBUG, "msg", 1);

		Assert.assertEquals(2, logEntries.size());
		Assert.assertArrayEquals(new Object[] {1}, logEntries.get(1).values);

		for (TestLogger.LogEntry entry : logEntries) {
			Assert.assertEquals(Logger.Level.DEBUG, entry.level);
			Assert.assertEquals("msg", entry.message);
		}
	}

	@Test
	public void testLogger_withMessage() {
		Logger logger = LoggerFactory.getLogger("name");

		logger.debug("msg");
		logger.error("msg");
		logger.info("msg");
		logger.trace("msg");
		logger.warn("msg");

		Assert.assertEquals(Logger.Level.values().length, logEntries.size());
		Assert.assertEquals(Logger.Level.DEBUG, logEntries.get(0).level);
		Assert.assertEquals(Logger.Level.ERROR, logEntries.get(1).level);
		Assert.assertEquals(Logger.Level.INFO, logEntries.get(2).level);
		Assert.assertEquals(Logger.Level.TRACE, logEntries.get(3).level);
		Assert.assertEquals(Logger.Level.WARN, logEntries.get(4).level);

		for (TestLogger.LogEntry entry : logEntries) {
			Assert.assertEquals("msg", entry.message);
		}
	}

	@Test
	public void testLogger_withMessageAndValues() {
		Logger logger = LoggerFactory.getLogger("name");

		logger.debug("msg", 1);
		logger.error("msg", 1);
		logger.info("msg", 1);
		logger.trace("msg", 1);
		logger.warn("msg", 1);

		Assert.assertEquals(Logger.Level.values().length, logEntries.size());

		for (TestLogger.LogEntry entry : logEntries) {
			Assert.assertEquals("msg", entry.message);
			Assert.assertArrayEquals(new Object[] {1}, entry.values);
		}
	}

	@Test
	public void testLogger_withThrowable() {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		Exception e = new Exception();
		logger.error("msg", e);
		logger.error("msg", e, 1);
		logger.warn("msg", e);
		logger.warn("msg", e, 1);

		Assert.assertEquals(4, logEntries.size());
		Assert.assertEquals(Logger.Level.ERROR, logEntries.get(0).level);
		Assert.assertEquals(Logger.Level.ERROR, logEntries.get(1).level);
		Assert.assertArrayEquals(new Object[] {1}, logEntries.get(1).values);
		Assert.assertEquals(Logger.Level.WARN, logEntries.get(2).level);
		Assert.assertEquals(Logger.Level.WARN, logEntries.get(3).level);
		Assert.assertArrayEquals(new Object[] {1}, logEntries.get(3).values);

		for (TestLogger.LogEntry entry : logEntries) {
			Assert.assertEquals("msg", entry.message);
			Assert.assertEquals(e, entry.throwable);
		}
	}

	@Test
	public void testLoggerFactory_initLoggerFactory() {
		String oldClassName = LoggerFactory.defaultClassName;

		LoggerFactory.setLoggerFactory(null);
		LoggerFactory.setDefaultImplClass(
			TestLoggerFactory.class.getCanonicalName());

		try {
			Assert.assertNotNull(LoggerFactory.getLogger("name"));
		}
		finally {
			LoggerFactory.setDefaultImplClass(oldClassName);
		}
	}

	@Test(expected = WeDeployException.class)
	public void testLoggerFactory_initLoggerFactory_implNotFound() {
		LoggerFactory.setLoggerFactory(null);
		LoggerFactory.getLogger("name");
	}

	@Test(expected = WeDeployException.class)
	public void testLoggerFactory_initLoggerFactory_invalidImpl() {
		String oldClass = LoggerFactory.defaultClassName;
		LoggerFactory.setDefaultImplClass(
			TestTransport.class.getCanonicalName());
		LoggerFactory.setLoggerFactory(null);

		try {
			LoggerFactory.getLogger("name");
			Assert.fail("Exception not thrown.");
		}
		finally {
			LoggerFactory.setDefaultImplClass(oldClass);
		}
	}

	@Test
	public void testPodLoggerFactory_constructorDummyCoverage() {
		new LoggerFactory();
	}

	private List<TestLogger.LogEntry> logEntries;

}