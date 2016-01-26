package com.liferay.launchpad.log;

import com.liferay.launchpad.log.impl.TestPodLogger;
import com.liferay.launchpad.sdk.PodException;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Lais Andrade
 */
public class PodLoggerTest {

	@Before
	public void setup() {
		logEntries = new ArrayList<>();
		PodLoggerFactory.setLoggerFactory(
			name -> new TestPodLogger(name, logEntries));
	}

	@Test
	public void testLevel_isEnabledFor() {
		PodLogger.Level[] values = PodLogger.Level.values();

		for (PodLogger.Level value : values) {
			for (PodLogger.Level previous : values) {
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
		PodLogger logger = PodLoggerFactory.getLogger("name");
		logger.log(PodLogger.Level.DEBUG, "msg");
		logger.log(PodLogger.Level.DEBUG, "msg", 1);

		Assert.assertEquals(2, logEntries.size());
		Assert.assertArrayEquals(new Object[] {1}, logEntries.get(1).values);

		for (TestPodLogger.LogEntry entry : logEntries) {
			Assert.assertEquals(PodLogger.Level.DEBUG, entry.level);
			Assert.assertEquals("msg", entry.message);
		}
	}

	@Test
	public void testLogger_withMessage() {
		PodLogger logger = PodLoggerFactory.getLogger("name");
		logger.debug("msg");
		logger.error("msg");
		logger.info("msg");
		logger.trace("msg");
		logger.warn("msg");

		Assert.assertEquals(PodLogger.Level.values().length, logEntries.size());
		Assert.assertEquals(PodLogger.Level.DEBUG, logEntries.get(0).level);
		Assert.assertEquals(PodLogger.Level.ERROR, logEntries.get(1).level);
		Assert.assertEquals(PodLogger.Level.INFO, logEntries.get(2).level);
		Assert.assertEquals(PodLogger.Level.TRACE, logEntries.get(3).level);
		Assert.assertEquals(PodLogger.Level.WARN, logEntries.get(4).level);

		for (TestPodLogger.LogEntry entry : logEntries) {
			Assert.assertEquals("msg", entry.message);
		}
	}

	@Test
	public void testLogger_withMessageAndValues() {
		PodLogger logger = PodLoggerFactory.getLogger("name");
		logger.debug("msg", 1);
		logger.error("msg", 1);
		logger.info("msg", 1);
		logger.trace("msg", 1);
		logger.warn("msg", 1);

		Assert.assertEquals(PodLogger.Level.values().length, logEntries.size());

		for (TestPodLogger.LogEntry entry : logEntries) {
			Assert.assertEquals("msg", entry.message);
			Assert.assertArrayEquals(new Object[] {1}, entry.values);
		}
	}

	@Test
	public void testLogger_withThrowable() {
		PodLogger logger = PodLoggerFactory.getLogger(this.getClass());
		Exception e = new Exception();
		logger.error("msg", e);
		logger.error("msg", e, 1);
		logger.warn("msg", e);
		logger.warn("msg", e, 1);

		Assert.assertEquals(4, logEntries.size());
		Assert.assertEquals(PodLogger.Level.ERROR, logEntries.get(0).level);
		Assert.assertEquals(PodLogger.Level.ERROR, logEntries.get(1).level);
		Assert.assertArrayEquals(new Object[] {1}, logEntries.get(1).values);
		Assert.assertEquals(PodLogger.Level.WARN, logEntries.get(2).level);
		Assert.assertEquals(PodLogger.Level.WARN, logEntries.get(3).level);
		Assert.assertArrayEquals(new Object[] {1}, logEntries.get(3).values);

		for (TestPodLogger.LogEntry entry : logEntries) {
			Assert.assertEquals("msg", entry.message);
			Assert.assertEquals(e, entry.throwable);
		}
	}

	@Test
	public void testLoggerFactory_initLoggerFactory() {
		PodLoggerFactory.setLoggerFactory(null);
		PodLoggerFactory.getLogger("name");
	}

	@Test
	public void testLoggerFactory_initLoggerFactory_implNotFound() {
		String oldClassName = PodLoggerFactory.defaultClassName;
		PodLoggerFactory.setDefaultImplClass("invalid");
		PodLoggerFactory.setLoggerFactory(null);

		try {
			PodLoggerFactory.getLogger("name");
			Assert.fail("Exception not thrown.");
		}
		catch (PodException e) {
			Assert.assertEquals(
				"PodLogger implementation not found: invalid", e.getMessage());
		}
		finally {
			PodLoggerFactory.setDefaultImplClass(oldClassName);
		}
	}

	@Test
	public void testLoggerFactory_initLoggerFactory_invalidImpl() {
		String oldClass = PodLoggerFactory.defaultClassName;
		PodLoggerFactory.setDefaultImplClass("TestPodLogger");
		PodLoggerFactory.setLoggerFactory(null);

		try {
			PodLoggerFactory.getLogger("name");
			Assert.fail("Exception not thrown.");
		}
		catch (PodException e) {
			Assert.assertTrue(
				e.getMessage().startsWith(
					"PodLogger implementation not found: TestPodLogger"));
		}
		finally {
			PodLoggerFactory.setDefaultImplClass(oldClass);
		}
	}

	@Test
	public void testPodLoggerFactory_constructorDummyCoverage() {
		new PodLoggerFactory();
	}

	private List<TestPodLogger.LogEntry> logEntries;

}
