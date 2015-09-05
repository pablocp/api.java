package com.liferay.launchpad.serializer.impl;

import com.liferay.launchpad.sdk.PodMultiMap;
import jodd.json.JsonContext;
import jodd.json.Path;
import jodd.json.TypeJsonSerializer;

import java.util.List;

public class PodMultiMapJsonSerializer implements TypeJsonSerializer<PodMultiMap<?>> {

	@Override
	public void serialize(JsonContext jsonContext, PodMultiMap<?> map) {
		jsonContext.writeOpenObject();

		int count = 0;

		Path currentPath = jsonContext.getPath();

		for (String key : map.names()) {
			final List<?> list = map.getAll(key);

			Object value = list.size() == 1 ? list.get(0) : list;

			currentPath.push(key);

			// check if we should include the field

			boolean include = true;

			if (value != null) {

				// + all collections are not serialized by default

				include = jsonContext.matchIgnoredPropertyTypes(value.getClass(), false, include);

				// + path queries: excludes/includes

				include = jsonContext.matchPathToQueries(include);
			}

			// done

			if (!include) {
				currentPath.pop();
				continue;
			}

			jsonContext.pushName(key, count > 0);

			jsonContext.serialize(value);

			if (jsonContext.isNamePopped()) {
				count++;
			}

			currentPath.pop();
		}

		jsonContext.writeCloseObject();

	}
}