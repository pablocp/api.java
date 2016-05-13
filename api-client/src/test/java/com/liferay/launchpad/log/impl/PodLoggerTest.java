package com.liferay.launchpad.log.impl;

import com.liferay.launchpad.ApiClient;
import com.liferay.launchpad.log.PodLogger;
import com.liferay.launchpad.log.PodLoggerFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class PodLoggerTest {

	@BeforeClass
	public static void beforeClass() {
		ApiClient.init();
	}

	@Test
	public void testLogger() {
		PrintStream original = System.out;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		System.setOut(ps);

		PodLogger log = PodLoggerFactory.getLogger("Foo");

		log.warn("123");

		System.out.flush();
		System.setOut(original);

		assertEquals(
			"WARN Foo " + this.getClass().getName() + " testLogger 31 - 123", baos.toString().trim());
	}


}
