package com.wedeploy.api;

import com.wedeploy.api.sdk.MultiMapFactory;
import com.wedeploy.api.sdk.Request;
import com.wedeploy.api.sdk.Response;
import com.wedeploy.api.sdk.impl.TestMultiMap;
import com.wedeploy.api.transport.impl.AsyncTransport;
import com.wedeploy.api.transport.impl.TestTransport;

import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
public class AsyncTransportTest {

	@BeforeClass
	public static void setup() {
		MultiMapFactory.Default.factory = TestMultiMap::new;
	}

	@Test
	public void testSend_withException() {
		try {
			WeDeploy
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
		catch (WeDeployException wde) {
			Assert.assertEquals("Transport failed", wde.getMessage());
			Assert.assertTrue(wde.getCause() instanceof Error);
		}
	}

	@Test
	public void testSend_withRuntimeException() {
		try {
			WeDeploy
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
		catch (RuntimeException re) {
			Assert.assertEquals("Error", re.getMessage());
		}
	}

	@Test
	public void testSend_withSuccessfulRequest() {
		Response clientResponse = WeDeploy
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