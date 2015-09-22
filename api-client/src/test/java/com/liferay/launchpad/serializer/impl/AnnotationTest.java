package com.liferay.launchpad.serializer.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.liferay.launchpad.ApiClient;
import com.liferay.launchpad.sdk.ContentType;
import com.liferay.launchpad.serializer.LaunchpadParser;
import com.liferay.launchpad.serializer.LaunchpadSerializer;

import org.junit.BeforeClass;
import org.junit.Test;
public class AnnotationTest {

	@BeforeClass
	public static void beforeClass() {
		ApiClient.init();
	}

	@Test
	public void testParse_withAnnotation() {
		String json = "{\"id\" : \"QWE\",\"name\" : \"Jenny\" }";

		User user = LaunchpadParser.get(
			ContentType.JSON).parse(json, User.class);

		assertEquals("QWE", user.getUserId());
		assertEquals("Jenny", user.getName());
	}

	@Test
	public void testSerialize_withAnnotation() {
		User user = new User();

		String json = LaunchpadSerializer.get(ContentType.JSON).serialize(user);

		assertTrue(json.contains("\"id\""));
		assertTrue(json.contains("\"name\""));
		assertFalse(json.contains("\"userId\""));
	}

}