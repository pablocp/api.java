package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.Response;
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
		final Response[] response = new Response[1];
		final Throwable[] throwable = new Throwable[1];

		LaunchpadClient
			.url("http://google.com/404")
			.get()
			.whenComplete((clientResponse, e) -> {
				response[0] = clientResponse;
				throwable[0] = e.getCause();
			})
			.thenAccept((clientResponse) -> Assert.fail("Must complete exceptionally 404"))
			.exceptionally(e -> null)
			.join();

		Assert.assertNull(response[0]);
		Assert.assertTrue(throwable[0] instanceof ResponseErrorException);
	}

	@Test
	public void testGoogleHomePage_invalid_noException() {
		final Response[] response = new Response[1];
		final Throwable[] throwable = new Throwable[1];

		LaunchpadClient
			.url("http://google.com/404")
			.throwExceptionOnResponseError(false)
			.get()
			.whenComplete((clientResponse, e) -> {
				response[0] = clientResponse;
				throwable[0] = e.getCause();
			})
			.thenAccept((clientResponse) -> Assert.fail("Must complete exceptionally 404"))
			.exceptionally(e -> null)
			.join();

		Assert.assertNotNull(response[0]);
		Assert.assertNull(throwable[0]);
		Assert.assertEquals(404, response[0].statusCode());
	}

}