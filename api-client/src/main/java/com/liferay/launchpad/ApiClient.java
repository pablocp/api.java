package com.liferay.launchpad;

import com.liferay.launchpad.api.DefaultTransport;
import com.liferay.launchpad.api.impl.JoddHttpTransport;
import com.liferay.launchpad.serializer.DefaultJsonEngines;
import com.liferay.launchpad.serializer.Engines;
import com.liferay.launchpad.serializer.impl.JoddJsonParser;
import com.liferay.launchpad.serializer.impl.JoddJsonSerializer;

public class ApiClient {

	public static void init() {
		DefaultTransport.setDefaultTransport(new JoddHttpTransport());

		DefaultJsonEngines.setDefaultEngines(
			new Engines(new JoddJsonSerializer(), new JoddJsonParser()));
	}
}