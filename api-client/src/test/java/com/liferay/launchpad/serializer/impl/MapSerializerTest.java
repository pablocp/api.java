package com.liferay.launchpad.serializer.impl;

import com.liferay.launchpad.ApiClient;
import com.liferay.launchpad.serializer.LaunchpadParser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MapSerializerTest {

	@BeforeClass
	public static void beforeClass() {
		ApiClient.init();
	}

	@Test
	public void testSerializeToMap() {
		String values = "{\"one\" : 123, \"two\" : 456}";

		Map<String, Long> numbers =
			LaunchpadParser.get().parseAsMap(values, String.class, Long.class);

		assertEquals(2, numbers.size());

		assertEquals(Long.valueOf(123L), numbers.get("one"));
		assertEquals(Long.valueOf(456L), numbers.get("two"));
	}

}
