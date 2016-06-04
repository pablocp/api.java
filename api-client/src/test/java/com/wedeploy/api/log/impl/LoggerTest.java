package com.wedeploy.api.log.impl;

import static org.junit.Assert.assertEquals;

import com.wedeploy.api.ApiClient;
import com.wedeploy.api.log.Logger;
import com.wedeploy.api.log.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.BeforeClass;
import org.junit.Test;
public class LoggerTest {

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

		Logger log = LoggerFactory.getLogger("Foo");

		log.warn("123");

		System.out.flush();
		System.setOut(original);

		assertEquals(
			"WARN Foo " + this.getClass().getName() + " testLogger 31 - 123",
			baos.toString().trim());
	}

}