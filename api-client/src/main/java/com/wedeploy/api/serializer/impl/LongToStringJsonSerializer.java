package com.wedeploy.api.serializer.impl;

import jodd.json.JsonContext;
import jodd.json.TypeJsonSerializer;
public class LongToStringJsonSerializer implements TypeJsonSerializer<Long> {

	@Override
	public boolean serialize(JsonContext jsonContext, Long value) {
		jsonContext.writeString(value.toString());
		return true;
	}

}