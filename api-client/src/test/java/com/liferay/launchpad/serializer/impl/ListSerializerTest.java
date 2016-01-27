package com.liferay.launchpad.serializer.impl;

import static org.junit.Assert.assertEquals;

import com.liferay.launchpad.ApiClient;
import com.liferay.launchpad.serializer.LaunchpadParser;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
public class ListSerializerTest {

	@BeforeClass
	public static void beforeClass() {
		ApiClient.init();
	}

	@Test
	public void testSerializeToList() {
		String values = "[123, 456]";

		List<Long> numbers =
			LaunchpadParser.get().parseAsList(values, Long.class);

		assertEquals(2, numbers.size());

		assertEquals(123L, numbers.get(0).longValue());
		assertEquals(456L, numbers.get(1).longValue());
	}

}