package com.liferay.launchpad.api;

import java.util.List;

import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
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
		return serializeToJson(object, false);
	}

	@Override
	public String serializeToJson(Object object, boolean deep) {

		// TODO: Improve loose checking for query classes to skip serialization.

		switch (object.getClass().getPackage().getName()) {
			case "com.liferay.launchpad.query":
				object = object.toString();
		}

		if (deep) {
			return jsonDeepSerializer.serialize(object);
		}

		return jsonSerializer.serialize(object);
	}

	private final JsonSerializer jsonDeepSerializer;
	private final JsonParser jsonParser;
	private final JsonSerializer jsonSerializer;

}