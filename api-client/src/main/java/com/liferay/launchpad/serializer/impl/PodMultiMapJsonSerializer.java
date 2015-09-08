package com.liferay.launchpad.serializer.impl;

import com.liferay.launchpad.sdk.PodMultiMap;

import java.util.List;

import jodd.json.JsonContext;
import jodd.json.Path;
import jodd.json.impl.KeyValueJsonSerializer;
public class PodMultiMapJsonSerializer
	extends KeyValueJsonSerializer<PodMultiMap<?>> {

	@Override
	public void serializeValue(JsonContext jsonContext, PodMultiMap<?> map) {
		jsonContext.writeOpenObject();

		int count = 0;

		Path currentPath = jsonContext.getPath();

		for (String key : map.names()) {
			final List<?> list = map.getAll(key);

			Object value = list.size() == 1 ? list.get(0) : list;

			count = serializeKeyValue(
				jsonContext, currentPath, key, value, count);
		}

		jsonContext.writeCloseObject();
	}

}