package com.wedeploy.api.serializer.impl;

import com.wedeploy.api.serializer.Serializer;
public class JsonSerializer implements Serializer {

	@Override
	public String serialize(Object object, boolean deep) {
		return jodd.json.JsonSerializer
			.create()
			.deep(deep)
			.serialize(object);
	}

}