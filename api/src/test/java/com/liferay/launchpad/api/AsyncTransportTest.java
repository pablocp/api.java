package com.liferay.launchpad.api;

import com.liferay.launchpad.api.impl.TestTransport;
import com.liferay.launchpad.sdk.PodMultiMapFactory;
import com.liferay.launchpad.sdk.Request;
import com.liferay.launchpad.sdk.Response;
import com.liferay.launchpad.sdk.impl.TestPodMultiMap;

import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
public class AsyncTransportTest {

	@BeforeClass
	public static void setup() {
		PodMultiMapFactory.Default.factory = TestPodMultiMap::new;
	}

	@Test
	public void testSend_withException() {
		try {
			Launchpad
				.url("http://google.com")
				.use(new AsyncTransport() {
					@Override
					public CompletableFuture<Response> sendAsync(
						Request request) {

						return CompletableFuture.supplyAsync(() -> {
							throw new Error("Error");
						});
					}

				})
				.get();

			Assert.fail("No exception thrown.");
		}
		catch (LaunchpadClientException e) {
			Assert.assertEquals("Transport failed", e.getMessage());
			Assert.assertTrue(e.getCause() instanceof Error);
		}
	}

	@Test
	public void testSend_withRuntimeException() {
		try {
			Launchpad
				.url("http://google.com")
				.use(new AsyncTransport() {
					@Override
					public CompletableFuture<Response> sendAsync(
						Request request) {

						return CompletableFuture.supplyAsync(() -> {
							throw new RuntimeException("Error");
						});
					}

				})
				.get();

			Assert.fail("No exception thrown.");
		}
		catch (RuntimeException e) {
			Assert.assertEquals("Error", e.getMessage());
		}
	}

	@Test
	public void testSend_withSuccessfulRequest() {
		Response clientResponse = Launchpad
			.url("http://google.com")
			.use(new AsyncTransport() {
				@Override
				public CompletableFuture<Response> sendAsync(Request request) {
					return CompletableFuture.completedFuture(
						new TestTransport().send(request));
				}

			})
			.get();

		Assert.assertNotNull(clientResponse);
		Assert.assertTrue(clientResponse.succeeded());
	}

}