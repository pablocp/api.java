package com.liferay.launchpad.api;

import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
public class JoddJsonEngine implements JsonEngine {

	public JoddJsonEngine() {
		jsonSerializer = new JsonSerializer();
		jsonSerializer.deep(true);

		jsonParser = new JsonParser();
	}

	@Override
	public <T> T parseJsonToModel(String json, Class<T> model) {
		return jsonParser.parse(json, model);
	}

	@Override
	public String serializeToJson(Object object) {
		return jsonSerializer.serialize(object);
	}

	private final JsonParser jsonParser;
	private final JsonSerializer jsonSerializer;

}