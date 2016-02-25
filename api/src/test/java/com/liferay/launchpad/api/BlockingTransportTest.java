package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.PodMultiMapFactory;
import com.liferay.launchpad.sdk.Request;
import com.liferay.launchpad.sdk.Response;
import com.liferay.launchpad.sdk.ResponseImpl;
import com.liferay.launchpad.sdk.impl.TestPodMultiMap;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
public class BlockingTransportTest {

	@BeforeClass
	public static void setup() {
		PodMultiMapFactory.Default.factory = TestPodMultiMap::new;
	}

	@Test
	public void testSendAsync_withDedicatedExecutor() {
		Launchpad
			.url("http://google.com")
			.use(new BlockingTransport(true) {
				@Override
				public Response send(Request request) {
					return new ResponseImpl(request).status(200);
				}

			})
			.getAsync()
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			})
			.join();
	}

	@Test
	public void testSendAsync_withDefaultConstructor() {
		Launchpad
			.url("http://google.com")
			.use(new BlockingTransport() {
				@Override
				public Response send(Request request) {
					return new ResponseImpl(request).status(200);
				}

			})
			.getAsync()
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			})
			.join();
	}

	@Test
	public void testSendAsync_withFixedNumberOfThreads() {
		Launchpad
			.url("http://google.com")
			.use(new BlockingTransport(10) {
				@Override
				public Response send(Request request) {
					return new ResponseImpl(request).status(200);
				}

			})
			.getAsync()
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			})
			.join();
	}

	@Test
	public void testSendAsync_withRuntimeException() {
		Launchpad
			.url("http://google.com")
			.use(new BlockingTransport() {
				@Override
				public Response send(Request request) {
					throw new RuntimeException("Error");
				}

			})
			.getAsync()
			.thenAccept(response -> Assert.fail("Response received"))
			.exceptionally(exception -> {
				Assert.assertEquals("Error", exception.getCause().getMessage());
				return null;
			})
			.join();
	}

}