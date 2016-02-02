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

import com.liferay.launchpad.serializer.Engines;
import com.liferay.launchpad.serializer.LaunchpadParser;
import com.liferay.launchpad.serializer.LaunchpadSerializerEngine;
import com.liferay.launchpad.serializer.impl.JsonLaunchpadParser;
import com.liferay.launchpad.serializer.impl.JsonLaunchpadSerializer;

import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
public class ErrorTest {

	@BeforeClass
	public static void setup() {
		PodMultiMapFactory.Default.factory = TestPodMultiMap::new;
		LaunchpadSerializerEngine.instance().registerEngines(
			ContentType.JSON.contentType(),
			new Engines(
				new JsonLaunchpadSerializer(), new JsonLaunchpadParser()),
			true);
	}

	@Test
	public void testBadRequest() {
		Response response = createResponse();

		ResponseError.badRequest().into(response).end();

		checkResponseBody(response, 400, "Bad Request");
	}

	@Test
	public void testBadRequest_badContent() {
		Response response = createResponse();

		ResponseError.badRequest().badContent().into(response).end();
		checkResponseBody(response, 400, "Bad Request", "badContent");

		response = createResponse();
		ResponseError.badRequest().badContent("msg").into(response).end();
		checkResponseReasonAndMessage(response, "badContent", "msg");
	}

	@Test
	public void testBadRequest_badRequest() {
		Response response = createResponse();

		ResponseError.badRequest().badRequest().into(response).end();
		checkResponseBody(response, 400, "Bad Request", "badRequest");

		response = createResponse();
		ResponseError.badRequest().badRequest("msg").into(response).end();
		checkResponseReasonAndMessage(response, "badRequest", "msg");
	}

	@Test
	public void testBadRequest_exists() {
		Response response = createResponse();

		ResponseError.badRequest().exists().into(response).end();
		checkResponseBody(response, 400, "Bad Request", "exists");

		response = createResponse();
		ResponseError.badRequest().exists("msg").into(response).end();
		checkResponseReasonAndMessage(response, "exists", "msg");
	}

	@Test
	public void testBadRequest_invalidDocumentValue() {
		Response response = createResponse();

		ResponseError.badRequest().invalidDocumentValue().into(response).end();
		checkResponseBody(response, 400, "Bad Request", "invalidDocumentValue");

		response = createResponse();
		ResponseError.badRequest().invalidDocumentValue("msg").into(response).end();
		checkResponseReasonAndMessage(response, "invalidDocumentValue", "msg");
	}

	@Test
	public void testBadRequest_invalidQuery() {
		Response response = createResponse();

		ResponseError.badRequest().invalidQuery().into(response).end();
		checkResponseBody(response, 400, "Bad Request", "invalidQuery");

		response = createResponse();
		ResponseError.badRequest().invalidQuery("msg").into(response).end();
		checkResponseReasonAndMessage(response, "invalidQuery", "msg");
	}

	@Test
	public void testBadRequest_keyExpired() {
		Response response = createResponse();

		ResponseError.badRequest().keyExpired().into(response).end();
		checkResponseBody(response, 400, "Bad Request", "keyExpired");

		response = createResponse();
		ResponseError.badRequest().keyExpired("msg").into(response).end();
		checkResponseReasonAndMessage(response, "keyExpired", "msg");
	}

	@Test
	public void testBadRequest_keyInvalid() {
		Response response = createResponse();

		ResponseError.badRequest().keyInvalid().into(response).end();
		checkResponseBody(response, 400, "Bad Request", "keyInvalid");

		response = createResponse();
		ResponseError.badRequest().keyInvalid("msg").into(response).end();
		checkResponseReasonAndMessage(response, "keyInvalid", "msg");
	}

	@Test
	public void testBadRequest_parseError() {
		Response response = createResponse();

		ResponseError.badRequest().parseError().into(response).end();
		checkResponseBody(response, 400, "Bad Request", "parseError");

		response = createResponse();
		ResponseError.badRequest().parseError("msg").into(response).end();
		checkResponseReasonAndMessage(response, "parseError", "msg");
	}

	@Test
	public void testBadRequest_required() {
		Response response = createResponse();

		ResponseError.badRequest().required().into(response).end();
		checkResponseBody(response, 400, "Bad Request", "required");

		response = createResponse();
		ResponseError.badRequest().required("msg").into(response).end();
		checkResponseReasonAndMessage(response, "required", "msg");
	}

	@Test
	public void testBadRequest_validationError() {
		Response response = createResponse();

		ResponseError.badRequest().validationError().into(response).end();
		checkResponseBody(response, 400, "Bad Request", "validationError");

		response = createResponse();
		ResponseError.badRequest().validationError("msg").into(response).end();
		checkResponseReasonAndMessage(response, "validationError", "msg");
	}

	@Test
	public void testError_checkErrorData() {
		ErrorData<Response> data = ResponseError.forbidden("msg").data();
		assertEquals(403, data.statusCode());
		assertEquals("msg", data.statusMessage());
		assertEquals(0, data.getSubErrors().size());
	}

	@Test
	public void testError_encodeStatusMessage() {
		Response response = createResponse();

		ResponseError.notFound().notFound("\"\\/\b\f\n\r\tabcde").into(response).end();

		checkResponseReasonAndMessage(
			response, "notFound", "\"\\/\b\f\n\r\tabcde");
	}

	@Test
	public void testError_multipleReasons() {
		Response response = createResponse();

		ResponseError.badRequest().badContent().keyExpired().into(response).end();

		checkResponseBody(
			response, 400, "Bad Request", "badContent", "keyExpired");
	}

	@Test
	public void testError_withCustomReason() {
		Response response = createResponse();

		ResponseError.internalError().error("reason", "msg").into(response).end();

		checkResponseReasonAndMessage(response, "reason", "msg");
	}

	@Test
	public void testForbidden() {
		Response response = createResponse();

		ResponseError.forbidden().into(response).end();

		checkResponseBody(response, 403, "Forbidden");
	}

	@Test
	public void testForbidden_corsRequestOrigin() {
		Response response = createResponse();

		ResponseError.forbidden().corsRequestOrigin().into(response).end();
		checkResponseBody(response, 403, "Forbidden", "corsRequestOrigin");

		response = createResponse();
		ResponseError.forbidden().corsRequestOrigin("msg").into(response).end();
		checkResponseReasonAndMessage(response, "corsRequestOrigin", "msg");
	}

	@Test
	public void testForbidden_forbidden() {
		Response response = createResponse();

		ResponseError.forbidden().forbidden().into(response).end();
		checkResponseBody(response, 403, "Forbidden", "forbidden");

		response = createResponse();
		ResponseError.forbidden().forbidden("msg").into(response).end();
		checkResponseReasonAndMessage(response, "forbidden", "msg");
	}

	@Test
	public void testForbidden_limitExceeded() {
		Response response = createResponse();

		ResponseError.forbidden().limitExceeded().into(response).end();
		checkResponseBody(response, 403, "Forbidden", "limitExceeded");

		response = createResponse();
		ResponseError.forbidden().limitExceeded("msg").into(response).end();
		checkResponseReasonAndMessage(response, "limitExceeded", "msg");
	}

	@Test
	public void testForbidden_quotaExceeded() {
		Response response = createResponse();

		ResponseError.forbidden().quotaExceeded().into(response).end();
		checkResponseBody(response, 403, "Forbidden", "quotaExceeded");

		response = createResponse();
		ResponseError.forbidden().quotaExceeded("msg").into(response).end();
		checkResponseReasonAndMessage(response, "quotaExceeded", "msg");
	}

	@Test
	public void testForbidden_rateLimitExceeded() {
		Response response = createResponse();

		ResponseError.forbidden().rateLimitExceeded().into(response).end();
		checkResponseBody(response, 403, "Forbidden", "rateLimitExceeded");

		response = createResponse();
		ResponseError.forbidden().rateLimitExceeded("msg").into(response).end();
		checkResponseReasonAndMessage(response, "rateLimitExceeded", "msg");
	}

	@Test
	public void testForbidden_responseTooLarge() {
		Response response = createResponse();

		ResponseError.forbidden().responseTooLarge().into(response).end();
		checkResponseBody(response, 403, "Forbidden", "responseTooLarge");

		response = createResponse();
		ResponseError.forbidden().responseTooLarge("msg").into(response).end();
		checkResponseReasonAndMessage(response, "responseTooLarge", "msg");
	}

	@Test
	public void testForbidden_unknownAuth() {
		Response response = createResponse();

		ResponseError.forbidden().unknownAuth().into(response).end();
		checkResponseBody(response, 403, "Forbidden", "unknownAuth");

		response = createResponse();
		ResponseError.forbidden().unknownAuth("msg").into(response).end();
		checkResponseReasonAndMessage(response, "unknownAuth", "msg");
	}

	@Test
	public void testInternalError() {
		Response response = createResponse();

		ResponseError.internalError().into(response).end();

		checkResponseBody(response, 500, "Internal Server Error");
	}

	@Test
	public void testInternalError_internalError() {
		Response response = createResponse();

		ResponseError.internalError().internalError().into(response).end();
		checkResponseBody(
			response, 500, "Internal Server Error", "internalError");

		response = createResponse();
		ResponseError.internalError().internalError("msg").into(response).end();
		checkResponseReasonAndMessage(response, "internalError", "msg");
	}

	@Test
	public void testMethodNotAllowed() {
		Response response = createResponse();

		ResponseError.methodNotAllowed().into(response).end();

		checkResponseBody(response, 405, "Method Not Allowed");
	}

	@Test
	public void testMethodNotAllowed_httpMethodNotAllowed() {
		Response response = createResponse();

		ResponseError.methodNotAllowed().httpMethodNotAllowed().into(response).end();
		checkResponseBody(
			response, 405, "Method Not Allowed", "httpMethodNotAllowed");

		response = createResponse();
		ResponseError.methodNotAllowed().httpMethodNotAllowed("").into(response).end();
		checkResponseReasonAndMessage(response, "httpMethodNotAllowed", "");
	}

	@Test
	public void testMethodNotAllowed_withAllowHeader() {
		Response response = createResponse();

		ResponseError
			.methodNotAllowed()
			.httpMethodNotAllowed()
			.allowHeader("get", "post")
			.into(response).end();

		checkResponseBody(
			response, 405, "Method Not Allowed", "httpMethodNotAllowed");

		assertEquals("[get, post]", response.header("Allow"));
	}

	@Test
	public void testMethodNotAllowed_withAllowHeader_withNoValue() {
		Response response = createResponse();

		ResponseError
			.methodNotAllowed()
			.httpMethodNotAllowed()
			.allowHeader()
			.into(response).end();

		checkResponseBody(
			response, 405, "Method Not Allowed", "httpMethodNotAllowed");

		assertEquals("[]", response.header("Allow"));
	}

	@Test
	public void testNotFound() {
		Response response = createResponse();

		ResponseError.notFound().into(response).end();

		checkResponseBody(response, 404, "Not Found");
	}

	@Test
	public void testNotFound_notFound() {
		Response response = createResponse();

		ResponseError.notFound().notFound().into(response).end();
		checkResponseBody(response, 404, "Not Found", "notFound");

		response = createResponse();
		ResponseError.notFound().notFound("msg").into(response).end();
		checkResponseReasonAndMessage(response, "notFound", "msg");
	}

	@Test
	public void testNotFound_unsupportedProtocol() {
		Response response = createResponse();

		ResponseError.notFound().unsupportedProtocol().into(response).end();
		checkResponseBody(response, 404, "Not Found", "unsupportedProtocol");

		response = createResponse();
		ResponseError.notFound().unsupportedProtocol("msg").into(response).end();
		checkResponseReasonAndMessage(response, "unsupportedProtocol", "msg");
	}

	@Test
	public void testRequestTimeout() {
		Response response = createResponse();

		ResponseError.requestTimeout().into(response).end();

		checkResponseBody(response, 408, "Request Timeout");
	}

	@Test
	public void testRequestTimeout_requestTimeout() {
		Response response = createResponse();

		ResponseError.requestTimeout().requestTimeout().into(response).end();
		checkResponseBody(response, 408, "Request Timeout", "requestTimeout");

		response = createResponse();
		ResponseError.requestTimeout().requestTimeout("msg").into(response).end();
		checkResponseReasonAndMessage(response, "requestTimeout", "msg");
	}

	@Test
	public void testResponseError_constructorDummyCoverage() {
		new ResponseError();
	}

	@Test
	public void testUnauthorized() {
		Response response = createResponse();

		ResponseError.unauthorized().into(response).end();

		checkResponseBody(response, 401, "Unauthorized");
	}

	@Test
	public void testUnauthorized_unauthorized() {
		Response response = createResponse();

		ResponseError.unauthorized().unauthorized().into(response).end();
		checkResponseBody(response, 401, "Unauthorized", "unauthorized");

		response = createResponse();
		ResponseError.unauthorized().unauthorized("msg").into(response).end();
		checkResponseReasonAndMessage(response, "unauthorized", "msg");
	}

	private static Response createResponse() {
		RequestImpl request = new RequestImpl("http://localhost:8080/");

		Response response = new ResponseImpl(request);

		return response;
	}

	private static Map<String, Object> deserializeBody(Response response) {
		return LaunchpadParser.get(ContentType.JSON).parse(response.body());
	}

	private void checkResponseBody(
		Response response, int code, String message, String... reason) {

		Map<String, Object> body = deserializeBody(response);

		assertEquals(code, body.get("code"));
		assertEquals(message, body.get("message"));

		if ((reason == null) || (reason.length == 0)) {
			assertEquals(2, body.size());
			return;
		}

		List<Map> errors = (List)body.get("errors");

		assertEquals(3, body.size());
		assertEquals(reason.length, errors.size());

		for (int i = 0; i < reason.length; i++) {
			assertEquals(reason[i], errors.get(i).get("reason"));
		}
	}

	private void checkResponseReasonAndMessage(
		Response response, String reason, String message) {

		Map<String, Object> body = deserializeBody(response);
		List<Map> errors = (List)body.get("errors");

		assertEquals(1, errors.size());
		assertEquals(reason, errors.get(0).get("reason"));
		assertEquals(message, errors.get(0).get("message"));
	}

}