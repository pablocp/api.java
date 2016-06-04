package com.wedeploy.api;

import com.wedeploy.api.realtime.RealTimeFactory;
import com.wedeploy.api.realtime.impl.SocketIORealTimeFactory;
import com.wedeploy.api.sdk.ContentType;
import com.wedeploy.api.sdk.MultiMapFactory;
import com.wedeploy.api.sdk.impl.JoddMultiMapFactory;
import com.wedeploy.api.serializer.Engines;
import com.wedeploy.api.serializer.Serialize;
import com.wedeploy.api.serializer.SerializerEngine;
import com.wedeploy.api.serializer.impl.JoddJsonParser;
import com.wedeploy.api.serializer.impl.JoddJsonSerializer;
import com.wedeploy.api.serializer.impl.JoddTextParser;
import com.wedeploy.api.serializer.impl.JoddTextSerializer;
import com.wedeploy.api.transport.impl.DefaultTransport;
import com.wedeploy.api.transport.impl.JoddHttpTransport;

import jodd.json.JoddJson;
public class ApiClient {

	public static void init() {
		MultiMapFactory.Default.factory = new JoddMultiMapFactory();

		DefaultTransport.defaultTransport(new JoddHttpTransport());

		JoddJson.jsonAnnotation = Serialize.class;

		RealTimeFactory.Default.factory = new SocketIORealTimeFactory();

		SerializerEngine.instance().registerEngines(
			ContentType.JSON.contentType(),
			new Engines(new JoddJsonSerializer(), new JoddJsonParser()), true);

		SerializerEngine.instance().registerEngines(
			ContentType.TEXT.contentType(),
			new Engines(new JoddTextSerializer(), new JoddTextParser()), false);
	}

	protected ApiClient() {
	}

}