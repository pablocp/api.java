package com.liferay.app.api;

import org.junit.Test;
public class GoogleTest {

	@Test
	public void testGoogleHomePage() throws Exception {
		LaunchpadClient
			.url("http://google.com")
			.get()
			.thenAccept(response -> System.out.println(response))
			.get();
	}

}