package com.liferay.launchpad.api;

import jodd.json.JsonParser;
import jodd.json.JsonSerializer;

import java.util.List;

public class JoddJsonEngine implements JsonEngine {

	public JoddJsonEngine() {
		jsonSerializer = new JsonSerializer();
		jsonSerializer.deep(false);

		jsonDeepSerializer = new JsonSerializer();
		jsonDeepSerializer.deep(true);

		jsonParser = new JsonParser();
	}

	@Override
	public <T> List<T> parseJsonToList(String json, Class<T> type) {
		return new JsonParser().map("values", type).parse(json);
	}

	@Override
	public <T> T parseJsonToModel(String json) {
		return jsonParser.parse(json);
	}

	@Override
	public <T> T parseJsonToModel(String json, Class<T> model) {
		return jsonParser.parse(json, model);
	}

	@Override
	public String serializeToJson(Object object) {
		return jsonSerializer.serialize(object);
	}

	@Override
	public String serializeToJson(Object object, boolean deep) {
		if (deep) {
			return jsonDeepSerializer.serialize(object);
		}

		return jsonSerializer.serialize(object);
	}

	private final JsonSerializer jsonDeepSerializer;
	private final JsonParser jsonParser;
	private final JsonSerializer jsonSerializer;

}