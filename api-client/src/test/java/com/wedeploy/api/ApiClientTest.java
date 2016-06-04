package com.wedeploy.api;

import com.wedeploy.api.realtime.RealTimeFactory;
import com.wedeploy.api.realtime.impl.SocketIORealTimeFactory;
import com.wedeploy.api.sdk.MultiMapFactory;
import com.wedeploy.api.sdk.impl.JoddMultiMapFactory;
import com.wedeploy.api.serializer.Parser;
import com.wedeploy.api.serializer.Serialize;
import com.wedeploy.api.serializer.Serializer;
import com.wedeploy.api.serializer.SerializerEngine;
import com.wedeploy.api.serializer.impl.JoddJsonParser;
import com.wedeploy.api.serializer.impl.JoddJsonSerializer;
import com.wedeploy.api.transport.impl.DefaultTransport;
import com.wedeploy.api.transport.impl.JoddHttpTransport;

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
			MultiMapFactory.Default.factory instanceof JoddMultiMapFactory);
		Assert.assertTrue(
			DefaultTransport.defaultTransport() instanceof JoddHttpTransport);
		Assert.assertEquals(JoddJson.jsonAnnotation, Serialize.class);
		Assert.assertTrue(
			RealTimeFactory.Default.factory instanceof SocketIORealTimeFactory);

		Assert.assertTrue(
			SerializerEngine.instance().parser() instanceof JoddJsonParser);
		Assert.assertTrue(
			SerializerEngine.instance().serializer() instanceof
				JoddJsonSerializer);
		Assert.assertTrue(Parser.get() instanceof JoddJsonParser);
		Assert.assertTrue(Serializer.get() instanceof JoddJsonSerializer);
	}

}