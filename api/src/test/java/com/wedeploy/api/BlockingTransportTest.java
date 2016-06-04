package com.wedeploy.api;

import com.wedeploy.api.sdk.MultiMapFactory;
import com.wedeploy.api.sdk.Request;
import com.wedeploy.api.sdk.Response;
import com.wedeploy.api.sdk.ResponseImpl;
import com.wedeploy.api.sdk.impl.TestMultiMap;
import com.wedeploy.api.transport.impl.BlockingTransport;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
public class BlockingTransportTest {

	@BeforeClass
	public static void setup() {
		MultiMapFactory.Default.factory = TestMultiMap::new;
	}

	@Test
	public void testSendAsync_withDedicatedExecutor() {
		WeDeploy
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
		WeDeploy
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
		WeDeploy
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
		WeDeploy
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