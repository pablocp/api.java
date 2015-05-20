package com.liferay.app.api;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class GoogleTest {

	@Test
	public void testGoogleHomePage() throws Exception {
		String body = LaunchpadClient
			.url("http://google.com")
			.get()
			.get();

		System.out.println(body);

		assertNotNull(body);
	}

}