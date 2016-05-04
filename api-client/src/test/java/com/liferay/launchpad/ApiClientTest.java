package com.liferay.launchpad;

import com.liferay.launchpad.api.DefaultTransport;
import com.liferay.launchpad.api.RealTimeFactory;
import com.liferay.launchpad.api.impl.JoddHttpTransport;
import com.liferay.launchpad.api.impl.SocketIORealTimeFactory;
import com.liferay.launchpad.sdk.PodMultiMapFactory;
import com.liferay.launchpad.sdk.impl.JoddPodMultiMapFactory;
import com.liferay.launchpad.serializer.LaunchpadParser;
import com.liferay.launchpad.serializer.LaunchpadSerializer;
import com.liferay.launchpad.serializer.LaunchpadSerializerEngine;
import com.liferay.launchpad.serializer.Serialize;
import com.liferay.launchpad.serializer.impl.JoddJsonParser;
import com.liferay.launchpad.serializer.impl.JoddJsonSerializer;

import jodd.json.JoddJson;

import org.junit.Assert;
import org.junit.Test;
public class ApiClientTest {

	@Test
	public void testConstructor_dummyCoverage() {
		new ApiClient();
	}

	@Test
	public void testInit() {
		ApiClient.init();

		Assert.assertTrue(
			PodMultiMapFactory.Default.factory instanceof
				JoddPodMultiMapFactory);
		Assert.assertTrue(
			DefaultTransport.defaultTransport() instanceof JoddHttpTransport);
		Assert.assertEquals(JoddJson.jsonAnnotation, Serialize.class);
		Assert.assertTrue(
			RealTimeFactory.Default.factory instanceof SocketIORealTimeFactory);

		Assert.assertTrue(
			LaunchpadSerializerEngine.instance().parser() instanceof
				JoddJsonParser);
		Assert.assertTrue(
			LaunchpadSerializerEngine.instance().serializer() instanceof
				JoddJsonSerializer);
		Assert.assertTrue(LaunchpadParser.get() instanceof JoddJsonParser);
		Assert.assertTrue(
			LaunchpadSerializer.get() instanceof JoddJsonSerializer);
	}

}