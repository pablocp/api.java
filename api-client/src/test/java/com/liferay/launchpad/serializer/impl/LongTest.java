package com.liferay.launchpad.serializer.impl;

import com.liferay.launchpad.ApiClient;
import com.liferay.launchpad.serializer.LaunchpadParser;
import com.liferay.launchpad.serializer.LaunchpadSerializer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LongTest {

	@BeforeClass
	public static void beforeClass() {
		ApiClient.init();
	}

	@Test
	public void testLongSerialization() {
		Numbers numbers = new Numbers();

		String json = LaunchpadSerializer.get().serialize(numbers);

		assertTrue(json.contains("\"" + numbers.getId1() + "\""));
		assertTrue(json.contains("\"" + numbers.getId2() + "\""));
	}

	@Test
	public void testLongParsing() {
		String values = "{\"id1\" : 12345678901, \"id2\" : \"1234567890\"}";

		Numbers numbers = LaunchpadParser.get().parse(values, Numbers.class);

		assertEquals(12345678901L, numbers.getId1().longValue());
		assertEquals(1234567890L, numbers.getId2());
	}
}
