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
import static org.junit.Assert.assertTrue;

import com.liferay.launchpad.ApiClient;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 */
public class RequestTest {

	@BeforeClass
	public static void beforeClass() {
		ApiClient.init();
	}

	@Test
	public void testBody() {
		Request request = new RequestImpl("http://127.0.0.1");
		request.body("foo");
		assertEquals("foo", request.body());
	}

	@Test
	public void testBodyValue_withJson() {
		RequestImpl request = new RequestImpl("http://localhost:8080");
		request.contentType(ContentType.JSON);
		request.body("1");
		int parsed = request.bodyValue();
		Assert.assertEquals(1, parsed);
	}

	@Test
	public void testBodyValue_withJson_ignoreParams() {
		RequestImpl request = new RequestImpl("http://localhost:8080");
		request.contentType(ContentType.JSON);
		request.param("key", "value");
		request.body("{\"a\":1}");
		Map parsed = request.bodyValue();
		Assert.assertEquals(1, parsed.get("a"));
		Assert.assertFalse(parsed.containsKey("key"));
	}

	@Test(expected = RuntimeException.class)
	public void testBodyValue_withJson_invalidBody() {
		RequestImpl request = new RequestImpl("http://localhost:8080");
		request.contentType(ContentType.JSON);
		request.body("invalid");
		request.bodyValue();
	}

	@Test
	public void testBodyValue_withoutContentType() {
		RequestImpl request = new RequestImpl("http://localhost:8080");
		request.body("1");
		String parsed = request.bodyValue();
		Assert.assertEquals("1", parsed);
	}

	@Test
	public void testBodyValue_withoutContentType_convertType() {
		RequestImpl request = new RequestImpl("http://localhost:8080");
		request.body("1");
		int parsed = request.bodyValue(Integer.class);
		Assert.assertEquals(1, parsed);
	}

	@Test
	public void testContentType() {
		Request request = new RequestImpl("http://127.0.0.1");

		request.contentType(ContentType.JSON);

		assertTrue(request.isContentType(ContentType.JSON));
	}

	@Test
	public void testCustomUrlScheme() {
		Request request = new RequestImpl(
			"blah://localhost:8080/path/sub?query=1&other=2");
		assertEquals("blah://localhost:8080", request.baseUrl());
		assertEquals("query=1&other=2", request.query());
	}

	@Test
	public void testFileUploads() {
		RequestImpl request = new RequestImpl("http://127.0.0.1");
		FileUpload[] fileUploads = new FileUpload[] {};
		request.fileUploads(fileUploads);
		Assert.assertArrayEquals(fileUploads, request.fileUploads());
	}

	@Test
	public void testHeaders() {
		Request request = new RequestImpl("http://127.0.0.1");
		request.header("header", "1");
		assertEquals("1", request.headers().get("header"));
	}

	@Test
	public void testMethod() {
		Request request = new RequestImpl("http://127.0.0.1");
		request.method("post");
		assertEquals("post", request.method());
	}

	@Test
	public void testParams() {
		Request request = new RequestImpl(
			"http://localhost:8080/path/sub?param1=1");
		request.param("param2", "2");
		assertEquals(null, request.params().get("param1"));
		assertEquals("2", request.params().get("param2"));
	}

	@Test
	public void testResponse() {
		RequestImpl request = new RequestImpl("http://127.0.0.1");
		Response response = new ResponseImpl(request);
		assertEquals(response, request.response());
	}

	@Test
	public void testUrl() {
		Request request = new RequestImpl(
			"http://localhost:8080/path/sub?query=1");
		assertEquals("http://localhost:8080/path/sub?query=1", request.url());
	}

	@Test
	public void testUrlBaseUrl() {
		Request request = new RequestImpl(
			"http://localhost:8080/path/sub?query=1");
		assertEquals("http://localhost:8080", request.baseUrl());
	}

	@Test
	public void testUrlPath() {
		Request request = new RequestImpl(
			"http://localhost:8080/path/sub?query=1");
		assertEquals("/path/sub", request.path());
	}

	@Test
	public void testUrlQuery() {
		Request request = new RequestImpl(
			"http://localhost:8080/path/sub?query=1&other=2");
		assertEquals("query=1&other=2", request.query());
	}

}