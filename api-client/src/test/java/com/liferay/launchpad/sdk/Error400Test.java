/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */

package com.liferay.launchpad.sdk;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import jodd.json.JsonParser;

import org.junit.Test;
public class Error400Test {

	@Test
	public void testBadRequest() {
		Response response = createResponse();

		ResponseError.badRequest().end(response);

		Map<String, Object> body = deserializeBody(response);

		assertEquals(2, body.size());
		assertEquals(Integer.valueOf(400), body.get("code"));
		assertEquals("Bad Request", body.get("message"));
	}

	@Test
	public void testBadRequest_badContent() {
		Response response = createResponse();

		ResponseError.badRequest().badContent().end(response);

		Map<String, Object> body = deserializeBody(response);

		assertEquals(3, body.size());
		assertEquals(Integer.valueOf(400), body.get("code"));
		assertEquals("Bad Request", body.get("message"));

		List<Map> errors = (List)body.get("errors");
		assertEquals(1, errors.size());
		assertEquals("badContent", errors.get(0).get("reason"));
	}

	@Test
	public void testBadRequest_badContent_keyExpired() {
		Response response = createResponse();

		ResponseError.badRequest().badContent().keyExpired().end(response);

		Map<String, Object> body = deserializeBody(response);

		assertEquals(3, body.size());
		assertEquals(Integer.valueOf(400), body.get("code"));
		assertEquals("Bad Request", body.get("message"));

		List<Map> errors = (List)body.get("errors");
		assertEquals(2, errors.size());
		assertEquals("badContent", errors.get(0).get("reason"));
		assertEquals("keyExpired", errors.get(1).get("reason"));
	}

	private static Response createResponse() {
		RequestImpl request = new RequestImpl("http://localhost:8080/");

		Response response = new ResponseImpl(request);

		return response;
	}

	private static Map<String, Object> deserializeBody(Response response) {
		String body = response.body();

		return new JsonParser().parse(body);
	}

}