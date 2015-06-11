package com.liferay.app.api;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
public abstract class GoogleTest {

	@Test
	public void testGoogleHomePage() throws Exception {
		ClientResponse response = LaunchpadClient
			.url("http://google.com")
			.use(transport())
			.get()
			.get();

		System.out.println(response.body());

		assertNotNull(response.body());
	}

	protected abstract Transport transport();

}