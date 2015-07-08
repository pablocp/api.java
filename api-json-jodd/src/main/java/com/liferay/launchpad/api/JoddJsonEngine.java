package com.liferay.launchpad.api;

import jodd.json.JsonSerializer;

public class JoddJsonEngine implements JsonEngine {

	public JoddJsonEngine() {
		jsonSerializer = new JsonSerializer();
		jsonSerializer.deep(true);
	}

	@Override
	public String serializeToJson(Object object) {
		return jsonSerializer.serialize(object);
	}


	private final JsonSerializer jsonSerializer;
}