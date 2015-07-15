package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.Response;

import org.junit.Assert;
import org.junit.Test;
public class GoogleTest {

	@Test
	public void testGoogleHomePage() {
		final Response[] response = new Response[1];
		final Throwable[] throwable = new Throwable[1];

		LaunchpadClient
			.url("http://google.com")
			.get()
			.whenComplete((clientResponse, e) -> {
				response[0] = clientResponse;
				throwable[0] = e;
			})
			.thenAccept((clientResponse) -> Assert.assertTrue(clientResponse.succeeded()))
			.join();

		Assert.assertNotNull(response[0]);
		Assert.assertNull(throwable[0]);
	}

	@Test
	public void testGoogleHomePage_invalid() {
		final Response[] response = new Response[1];
		final Throwable[] throwable = new Throwable[1];

		LaunchpadClient
			.url("http://google.com/404")
			.get()
			.whenComplete((clientResponse, e) -> {
				response[0] = clientResponse;
				throwable[0] = e;
			})
			.thenAccept((clientResponse) -> Assert.assertFalse(clientResponse.succeeded()))
			.join();

		Assert.assertNotNull(response[0]);
		Assert.assertNull(throwable[0]);
	}

}