package com.liferay.launchpad.api;

import org.junit.Assert;
import org.junit.Test;
public class GoogleTest {

	@Test
	public void testGoogleHomePage() {
		LaunchpadClient
			.url("http://google.com")
			.get()
			.thenAccept(clientResponse -> Assert.assertEquals(200, clientResponse.statusCode()))
			.exceptionally(e -> {
				Assert.fail("Must complete 200");
				return null;
			})
			.join();
	}

	@Test
	public void testGoogleHomePage_invalid() {
		LaunchpadClient
			.url("http://google.com/404")
			.get()
			.whenComplete((clientResponse, e) -> {
				Assert.assertEquals(404, clientResponse.statusCode());
				Assert.assertTrue(
					e.getCause() instanceof LaunchpadClientException);
			})
			.thenAccept((clientResponse) -> Assert.fail("Must complete exceptionally 404"))
			.exceptionally(e -> null)
			.join();
	}

}