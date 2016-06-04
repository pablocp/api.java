package com.wedeploy.api;

import com.wedeploy.api.sdk.Response;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
public class WeDeployTest {

	@BeforeClass
	public static void beforeClass() {
		ApiClient.init();
	}

	@Test
	public void testGoogleHomePage_invalidAsync() {
		WeDeploy
			.url("http://google.com/404")
			.getAsync()
			.thenAccept(response -> Assert.assertFalse(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			})
			.join();
	}

	@Test
	public void testGoogleHomePage_invalidBlocking() {
		Response clientResponse = WeDeploy.url("http://google.com/404").get();

		Assert.assertNotNull(clientResponse);
		Assert.assertFalse(clientResponse.succeeded());
	}

	@Test
	public void testGoogleHomePage_withAsync() {
		WeDeploy
			.url("http://google.com")
			.getAsync()
			.thenAccept(response -> Assert.assertTrue(response.succeeded()))
			.exceptionally(exception -> {
				Assert.fail(exception.getMessage());
				return null;
			})
			.join();
	}

	@Test
	public void testGoogleHomePage_withBlocking() {
		Response clientResponse = WeDeploy.url("http://google.com").get();

		Assert.assertNotNull(clientResponse);
		Assert.assertTrue(clientResponse.succeeded());
	}

}