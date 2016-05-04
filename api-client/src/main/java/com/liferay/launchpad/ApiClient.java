package com.liferay.launchpad;

import com.liferay.launchpad.api.DefaultTransport;
import com.liferay.launchpad.api.RealTimeFactory;
import com.liferay.launchpad.api.impl.JoddHttpTransport;
import com.liferay.launchpad.api.impl.SocketIORealTimeFactory;
import com.liferay.launchpad.sdk.ContentType;
import com.liferay.launchpad.sdk.PodMultiMapFactory;
import com.liferay.launchpad.sdk.impl.JoddPodMultiMapFactory;
import com.liferay.launchpad.serializer.Engines;
import com.liferay.launchpad.serializer.LaunchpadSerializerEngine;
import com.liferay.launchpad.serializer.Serialize;
import com.liferay.launchpad.serializer.impl.JoddJsonParser;
import com.liferay.launchpad.serializer.impl.JoddJsonSerializer;
import com.liferay.launchpad.serializer.impl.JoddTextParser;
import com.liferay.launchpad.serializer.impl.JoddTextSerializer;

import jodd.json.JoddJson;
public class ApiClient {

	public static void init() {
		PodMultiMapFactory.Default.factory = new JoddPodMultiMapFactory();

		DefaultTransport.defaultTransport(new JoddHttpTransport());

		JoddJson.jsonAnnotation = Serialize.class;

		RealTimeFactory.Default.factory = new SocketIORealTimeFactory();

		LaunchpadSerializerEngine.instance().registerEngines(
			ContentType.JSON.contentType(),
			new Engines(new JoddJsonSerializer(), new JoddJsonParser()), true);

		LaunchpadSerializerEngine.instance().registerEngines(
			ContentType.TEXT.contentType(),
			new Engines(new JoddTextSerializer(), new JoddTextParser()), false);
	}

	protected ApiClient() {
	}

}