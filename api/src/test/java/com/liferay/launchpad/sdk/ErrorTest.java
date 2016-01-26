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
public class ErrorTest {

	@Test
	public void testBadRequest() {
		Response response = createResponse();

		ResponseError.badRequest().end(response);

		checkResponseBody(response, 400, "Bad Request");
	}

	@Test
	public void testBadRequest_badContent() {
		Response response = createResponse();

		ResponseError.badRequest().badContent().end(response);
		checkResponseBody(response, 400, "Bad Request", "badContent");

		response = createResponse();
		ResponseError.badRequest().badContent("message").end(response);
		checkResponseReasonAndMessage(response, "badContent", "message");
	}

	@Test
	public void testBadRequest_badRequest() {
		Response response = createResponse();

		ResponseError.badRequest().badRequest().end(response);
		checkResponseBody(response, 400, "Bad Request", "badRequest");

		response = createResponse();
		ResponseError.badRequest().badRequest("message").end(response);
		checkResponseReasonAndMessage(response, "badRequest", "message");
	}

	@Test
	public void testBadRequest_exists() {
		Response response = createResponse();

		ResponseError.badRequest().exists().end(response);
		checkResponseBody(response, 400, "Bad Request", "exists");

		response = createResponse();
		ResponseError.badRequest().exists("message").end(response);
		checkResponseReasonAndMessage(response, "exists", "message");
	}

	@Test
	public void testBadRequest_invalidDocumentValue() {
		Response response = createResponse();

		ResponseError.badRequest().invalidDocumentValue().end(response);
		checkResponseBody(response, 400, "Bad Request", "invalidDocumentValue");

		response = createResponse();
		ResponseError.badRequest().invalidDocumentValue("msg").end(response);
		checkResponseReasonAndMessage(response, "invalidDocumentValue", "msg");
	}

	@Test
	public void testBadRequest_invalidQuery() {
		Response response = createResponse();

		ResponseError.badRequest().invalidQuery().end(response);
		checkResponseBody(response, 400, "Bad Request", "invalidQuery");

		response = createResponse();
		ResponseError.badRequest().invalidQuery("message").end(response);
		checkResponseReasonAndMessage(response, "invalidQuery", "message");
	}

	@Test
	public void testBadRequest_keyExpired() {
		Response response = createResponse();

		ResponseError.badRequest().keyExpired().end(response);
		checkResponseBody(response, 400, "Bad Request", "keyExpired");

		response = createResponse();
		ResponseError.badRequest().keyExpired("message").end(response);
		checkResponseReasonAndMessage(response, "keyExpired", "message");
	}

	@Test
	public void testBadRequest_keyInvalid() {
		Response response = createResponse();

		ResponseError.badRequest().keyInvalid().end(response);
		checkResponseBody(response, 400, "Bad Request", "keyInvalid");

		response = createResponse();
		ResponseError.badRequest().keyInvalid("message").end(response);
		checkResponseReasonAndMessage(response, "keyInvalid", "message");
	}

	@Test
	public void testBadRequest_parseError() {
		Response response = createResponse();

		ResponseError.badRequest().parseError().end(response);
		checkResponseBody(response, 400, "Bad Request", "parseError");

		response = createResponse();
		ResponseError.badRequest().parseError("message").end(response);
		checkResponseReasonAndMessage(response, "parseError", "message");
	}

	@Test
	public void testBadRequest_required() {
		Response response = createResponse();

		ResponseError.badRequest().required().end(response);
		checkResponseBody(response, 400, "Bad Request", "required");

		response = createResponse();
		ResponseError.badRequest().required("message").end(response);
		checkResponseReasonAndMessage(response, "required", "message");
	}

	@Test
	public void testBadRequest_validationError() {
		Response response = createResponse();

		ResponseError.badRequest().validationError().end(response);
		checkResponseBody(response, 400, "Bad Request", "validationError");

		response = createResponse();
		ResponseError.badRequest().validationError("message").end(response);
		checkResponseReasonAndMessage(response, "validationError", "message");
	}

	@Test
	public void testError_checkErrorData() {
		ErrorData<Response> data = ResponseError.forbidden("message").data();
		assertEquals(403, data.statusCode());
		assertEquals("message", data.statusMessage());
		assertEquals(0, data.getSubErrors().size());
	}

	@Test
	public void testError_encodeStatusMessage() {
		Response response = createResponse();

		ResponseError.notFound().notFound("\"\\/\b\f\n\r\tabcde").end(response);

		checkResponseReasonAndMessage(
			response, "notFound", "\"\\/\b\f\n\r\tabcde");
	}

	@Test
	public void testError_multipleReasons() {
		Response response = createResponse();

		ResponseError.badRequest().badContent().keyExpired().end(response);

		checkResponseBody(
			response, 400, "Bad Request", "badContent", "keyExpired");
	}

	@Test
	public void testError_withCustomReason() {
		Response response = createResponse();

		ResponseError.internalError().error("reason", "message").end(response);

		checkResponseReasonAndMessage(response, "reason", "message");
	}

	@Test
	public void testForbidden() {
		Response response = createResponse();

		ResponseError.forbidden().end(response);

		checkResponseBody(response, 403, "Forbidden");
	}

	@Test
	public void testForbidden_corsRequestOrigin() {
		Response response = createResponse();

		ResponseError.forbidden().corsRequestOrigin().end(response);
		checkResponseBody(response, 403, "Forbidden", "corsRequestOrigin");

		response = createResponse();
		ResponseError.forbidden().corsRequestOrigin("message").end(response);
		checkResponseReasonAndMessage(response, "corsRequestOrigin", "message");
	}

	@Test
	public void testForbidden_forbidden() {
		Response response = createResponse();

		ResponseError.forbidden().forbidden().end(response);
		checkResponseBody(response, 403, "Forbidden", "forbidden");

		response = createResponse();
		ResponseError.forbidden().forbidden("message").end(response);
		checkResponseReasonAndMessage(response, "forbidden", "message");
	}

	@Test
	public void testForbidden_limitExceeded() {
		Response response = createResponse();

		ResponseError.forbidden().limitExceeded().end(response);
		checkResponseBody(response, 403, "Forbidden", "limitExceeded");

		response = createResponse();
		ResponseError.forbidden().limitExceeded("message").end(response);
		checkResponseReasonAndMessage(response, "limitExceeded", "message");
	}

	@Test
	public void testForbidden_quotaExceeded() {
		Response response = createResponse();

		ResponseError.forbidden().quotaExceeded().end(response);
		checkResponseBody(response, 403, "Forbidden", "quotaExceeded");

		response = createResponse();
		ResponseError.forbidden().quotaExceeded("message").end(response);
		checkResponseReasonAndMessage(response, "quotaExceeded", "message");
	}

	@Test
	public void testForbidden_rateLimitExceeded() {
		Response response = createResponse();

		ResponseError.forbidden().rateLimitExceeded().end(response);
		checkResponseBody(response, 403, "Forbidden", "rateLimitExceeded");

		response = createResponse();
		ResponseError.forbidden().rateLimitExceeded("message").end(response);
		checkResponseReasonAndMessage(response, "rateLimitExceeded", "message");
	}

	@Test
	public void testForbidden_responseTooLarge() {
		Response response = createResponse();

		ResponseError.forbidden().responseTooLarge().end(response);
		checkResponseBody(response, 403, "Forbidden", "responseTooLarge");

		response = createResponse();
		ResponseError.forbidden().responseTooLarge("message").end(response);
		checkResponseReasonAndMessage(response, "responseTooLarge", "message");
	}

	@Test
	public void testForbidden_unknownAuth() {
		Response response = createResponse();

		ResponseError.forbidden().unknownAuth().end(response);
		checkResponseBody(response, 403, "Forbidden", "unknownAuth");

		response = createResponse();
		ResponseError.forbidden().unknownAuth("message").end(response);
		checkResponseReasonAndMessage(response, "unknownAuth", "message");
	}

	@Test
	public void testInternalError() {
		Response response = createResponse();

		ResponseError.internalError().end(response);

		checkResponseBody(response, 500, "Internal Server Error");
	}

	@Test
	public void testInternalError_internalError() {
		Response response = createResponse();

		ResponseError.internalError().internalError().end(response);
		checkResponseBody(
			response, 500, "Internal Server Error", "internalError");

		response = createResponse();
		ResponseError.internalError().internalError("message").end(response);
		checkResponseReasonAndMessage(response, "internalError", "message");
	}

	@Test
	public void testMethodNotAllowed() {
		Response response = createResponse();

		ResponseError.methodNotAllowed().end(response);

		checkResponseBody(response, 405, "Method Not Allowed");
	}

	@Test
	public void testMethodNotAllowed_httpMethodNotAllowed() {
		Response response = createResponse();

		ResponseError.methodNotAllowed().httpMethodNotAllowed().end(response);
		checkResponseBody(
			response, 405, "Method Not Allowed", "httpMethodNotAllowed");

		response = createResponse();
		ResponseError.methodNotAllowed().httpMethodNotAllowed("").end(response);
		checkResponseReasonAndMessage(response, "httpMethodNotAllowed", "");
	}

	@Test
	public void testMethodNotAllowed_withAllowHeader() {
		Response response = createResponse();

		ResponseError
			.methodNotAllowed()
			.httpMethodNotAllowed()
			.allowHeader("get", "post")
			.end(response);

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
			.end(response);

		checkResponseBody(
			response, 405, "Method Not Allowed", "httpMethodNotAllowed");

		assertEquals("[]", response.header("Allow"));
	}

	@Test
	public void testNotFound() {
		Response response = createResponse();

		ResponseError.notFound().end(response);

		checkResponseBody(response, 404, "Not Found");
	}

	@Test
	public void testNotFound_notFound() {
		Response response = createResponse();

		ResponseError.notFound().notFound().end(response);
		checkResponseBody(response, 404, "Not Found", "notFound");

		response = createResponse();
		ResponseError.notFound().notFound("message").end(response);
		checkResponseReasonAndMessage(response, "notFound", "message");
	}

	@Test
	public void testNotFound_unsupportedProtocol() {
		Response response = createResponse();

		ResponseError.notFound().unsupportedProtocol().end(response);
		checkResponseBody(response, 404, "Not Found", "unsupportedProtocol");

		response = createResponse();
		ResponseError.notFound().unsupportedProtocol("msg").end(response);
		checkResponseReasonAndMessage(response, "unsupportedProtocol", "msg");
	}

	@Test
	public void testRequestTimeout() {
		Response response = createResponse();

		ResponseError.requestTimeout().end(response);

		checkResponseBody(response, 408, "Request Timeout");
	}

	@Test
	public void testRequestTimeout_requestTimeout() {
		Response response = createResponse();

		ResponseError.requestTimeout().requestTimeout().end(response);
		checkResponseBody(response, 408, "Request Timeout", "requestTimeout");

		response = createResponse();
		ResponseError.requestTimeout().requestTimeout("message").end(response);
		checkResponseReasonAndMessage(response, "requestTimeout", "message");
	}

	@Test
	public void testResponseError_constructorDummyCoverage() {
		new ResponseError();
	}

	@Test
	public void testUnauthorized() {
		Response response = createResponse();

		ResponseError.unauthorized().end(response);

		checkResponseBody(response, 401, "Unauthorized");
	}

	@Test
	public void testUnauthorized_unauthorized() {
		Response response = createResponse();

		ResponseError.unauthorized().unauthorized().end(response);
		checkResponseBody(response, 401, "Unauthorized", "unauthorized");

		response = createResponse();
		ResponseError.unauthorized().unauthorized("message").end(response);
		checkResponseReasonAndMessage(response, "unauthorized", "message");
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