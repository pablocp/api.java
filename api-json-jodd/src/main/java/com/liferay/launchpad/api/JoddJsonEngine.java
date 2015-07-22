package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.json.JsonParser;
import com.liferay.launchpad.sdk.json.JsonSerializer;

import java.util.List;

/**
 */
public class JoddJsonEngine implements JsonEngine {

	public JoddJsonEngine() {
		clientJsonSerializer = new ClientJsonSerializer();
		clientJsonParser = new ClientJsonParser();
	}

	@Override
	public com.liferay.launchpad.sdk.json.JsonParser getJsonParser() {
		return clientJsonParser;
	}

	@Override
	public com.liferay.launchpad.sdk.json.JsonSerializer getJsonSerializer() {
		return clientJsonSerializer;
	}

	@Override
	public <T> List<T> parseJsonToList(String json, Class<T> type) {
		return clientJsonParser.parseAsList(json, type);
	}

	@Override
	public <T> T parseJsonToModel(String json) {
		return clientJsonParser.parse(json);
	}

	@Override
	public <T> T parseJsonToModel(String json, Class<T> model) {
		return clientJsonParser.parse(json, model);
	}

	@Override
	public String serializeToJson(Object object) {
		return serializeToJson(object, false);
	}

	@Override
	public String serializeToJson(Object object, boolean deep) {
		return clientJsonSerializer.serialize(object, deep);
	}

	public static class ClientJsonParser implements JsonParser {
		@Override
		public <T> T parse(String json) {
			return jsonParser.parse(json);
		}

		@Override
		public <T> T parse(String json, Class<T> type) {
			return new jodd.json.JsonParser().parse(json, type);
		}

		@Override
		public <T> List<T> parseAsList(String json, Class<T> type) {
			return new jodd.json.JsonParser().map("values", type).parse(json);
		}

		jodd.json.JsonParser jsonParser = new jodd.json.JsonParser();
	}

	public static class ClientJsonSerializer implements JsonSerializer {
		@Override
		public String serialize(Object object) {
			return serialize(object, false);
		}

		@Override
		public String serialize(Object object, boolean deep) {

			// TODO: Improve loose checking for query classes to skip serialization.

			switch (object.getClass().getPackage().getName()) {
				case "com.liferay.launchpad.query":
					return object.toString();
			}

			if (deep) {
				return joddDeepJsonSerializer.serialize(object);
			}
			else {
				return joddJsonSerializer.serialize(object);
			}
		}

		jodd.json.JsonSerializer joddDeepJsonSerializer =
			new jodd.json.JsonSerializer().deep(true);

		jodd.json.JsonSerializer joddJsonSerializer =
			new jodd.json.JsonSerializer();
	}

	private final ClientJsonParser clientJsonParser;
	private final ClientJsonSerializer clientJsonSerializer;

}