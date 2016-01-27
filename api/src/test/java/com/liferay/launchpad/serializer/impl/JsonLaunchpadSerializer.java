package com.liferay.launchpad.serializer.impl;

import com.liferay.launchpad.serializer.LaunchpadSerializer;
import jodd.json.JsonSerializer;

public class JsonLaunchpadSerializer implements LaunchpadSerializer {

	@Override
	public String serialize(Object object, boolean deep) {
		return JsonSerializer
			.create()
			.deep(deep)
			.serialize(object);
	}

}
