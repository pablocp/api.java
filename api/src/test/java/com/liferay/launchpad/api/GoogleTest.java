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
	public void testDefaultTransport_constructorDummyCoverage() {
		new DefaultTransport();
	}

	@Test
	public void testGoogleHomePage_invalidAsync() {
		Launchpad
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
		Response clientResponse = Launchpad.url("http://google.com/404").get();

		Assert.assertNotNull(clientResponse);
		Assert.assertFalse(clientResponse.succeeded());
	}

	@Test
	public void testGoogleHomePage_withAsync() {
		Launchpad
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
		Response clientResponse = Launchpad.url("http://google.com").get();

		Assert.assertNotNull(clientResponse);
		Assert.assertTrue(clientResponse.succeeded());
	}

	@Test
	public void testRealTimeFactoryDefault_constructorDummyCoverage() {
		new RealTimeFactory.Default();
	}

}