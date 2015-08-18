package com.liferay.launchpad.api;

import com.liferay.launchpad.ApiClient;
import com.liferay.launchpad.sdk.Response;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
public class GoogleTest {

	@BeforeClass
	public static void beforeClass() {
		ApiClient.init();
	}

	@Test
	public void testGoogleHomePage() {
		final Response[] response = new Response[1];
		final Throwable[] throwable = new Throwable[1];

		Launchpad
			.url("http://google.com")
			.getAsync()
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

		Launchpad
			.url("http://google.com/404")
			.getAsync()
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