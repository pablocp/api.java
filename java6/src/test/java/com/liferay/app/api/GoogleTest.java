package com.liferay.app.api;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
public class GoogleTest {

	@Test
	public void testGoogleHomePage() throws Exception {
		ClientResponse response = LaunchpadClient
			.url("http://google.com")
			.get()
			.get();

		System.out.println(response.body());

		assertNotNull(response.body());
	}

}