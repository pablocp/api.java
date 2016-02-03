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

import static junit.framework.TestCase.assertNull;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.liferay.launchpad.serializer.Engines;
import com.liferay.launchpad.serializer.LaunchpadSerializerEngine;
import com.liferay.launchpad.serializer.LaunchpadSerializerException;
import com.liferay.launchpad.serializer.impl.JsonLaunchpadParser;
import com.liferay.launchpad.serializer.impl.JsonLaunchpadSerializer;
import com.liferay.launchpad.serializer.impl.TextLaunchpadParser;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 */
public class RequestTest {

	@BeforeClass
	public static void setup() {
		PodMultiMapFactory.Default.factory = TestPodMultiMap::new;
		LaunchpadSerializerEngine.instance().registerEngines(
			ContentType.JSON.contentType(),
			new Engines(
				new JsonLaunchpadSerializer(), new JsonLaunchpadParser()),
			true);
		LaunchpadSerializerEngine.instance().registerEngines(
			ContentType.TEXT.contentType(),
			new Engines(null, new TextLaunchpadParser()), true);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAuth() {
		new RequestImpl("localhost").auth();
	}

	@Test
	public void testBody() {
		Request request = new RequestImpl("http://127.0.0.1");
		request.body("foo");
		assertEquals("foo", request.body());
	}

	@Test
	public void testBody_withNullObject() {
		Request request = new RequestImpl("http://127.0.0.1");
		request.body((Object)null);
		assertNull(request.bodyBytes());
	}

	@Test
	public void testBody_withStringAsObject() {
		Request request = new RequestImpl("http://127.0.0.1");
		request.body((Object) "foo");
		assertEquals("foo", request.body());
	}

	@Test
	public void testBody_withUnsupportedEncoding() {
		Request request = new RequestImpl("http://127.0.0.1");
		String oldEncoding = RequestImpl.BODY_ENCODING;

		try {
			RequestImpl.BODY_ENCODING = "invalid";
			request.body("foo");
			Assert.assertNull(request.bodyBytes());
		}
		finally {
			RequestImpl.BODY_ENCODING = oldEncoding;
		}

		request.body("foo");
		Assert.assertEquals("foo", request.body());

		try {
			RequestImpl.BODY_ENCODING = "invalid";
			Assert.assertNull(request.body());
		}
		finally {
			RequestImpl.BODY_ENCODING = oldEncoding;
		}
	}

	@Test
	public void testBodyBytes() throws Exception {
		RequestImpl request = new RequestImpl("http://localhost:8080");
		request.body("value");
		Assert.assertArrayEquals(
			"value".getBytes("UTF-8"), request.bodyBytes());
	}

	@Test
	public void testBodyList_withJson() throws Exception {
		RequestImpl request = new RequestImpl("http://localhost:8080");
		request.contentType(ContentType.JSON);
		request.body("[1,2]");
		Assert.assertArrayEquals(
			new Integer[] {1, 2}, request.bodyList(Integer.class).toArray());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testBodyList_withNoContentType() throws Exception {
		RequestImpl request = new RequestImpl("http://localhost:8080");
		request.body("1");
		request.bodyList(Integer.class);
	}

	@Test
	public void testBodyList_withNullBody() throws Exception {
		RequestImpl request = new RequestImpl("http://localhost:8080");
		Assert.assertNull(request.bodyList(Integer.class));
	}

	@Test
	public void testBodyMap_withJson() {
		RequestImpl request = new RequestImpl("http://localhost:8080");
		request.contentType(ContentType.JSON);
		request.body("{\"key\":\"value\"}");
		Map parsed = request.bodyMap(String.class, String.class);
		Assert.assertEquals(1, parsed.size());
		Assert.assertEquals("value", parsed.get("key"));
		parsed = request.bodyMap(String.class);
		Assert.assertEquals(1, parsed.size());
		Assert.assertEquals("value", parsed.get("key"));
	}

	@Test(expected = RuntimeException.class)
	public void testBodyMap_withJson_invalidBody() {
		RequestImpl request = new RequestImpl("http://localhost:8080");
		request.contentType(ContentType.JSON);
		request.body("invalid");
		request.bodyMap(String.class);
	}

	@Test(expected = RuntimeException.class)
	public void testBodyMap_withJson_invalidKey() {
		RequestImpl request = new RequestImpl("http://localhost:8080");
		request.contentType(ContentType.JSON);
		request.body("{1:\"value\"}");
		request.bodyMap(String.class);
	}

	@Test
	public void testBodyMap_withNullBody() {
		RequestImpl request = new RequestImpl("http://localhost:8080");
		Assert.assertNull(request.bodyMap(String.class));
		Assert.assertNull(request.bodyMap(String.class, String.class));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testBodyMap_withoutContentType() {
		RequestImpl request = new RequestImpl("http://localhost:8080");
		request.body("{\"key\":\"value\"}");
		request.bodyMap(String.class);
	}

	@Test
	public void testBodyValue_withJson() {
		RequestImpl request = new RequestImpl("http://localhost:8080");
		request.contentType(ContentType.JSON);
		request.body("1");
		int parsed = request.bodyValue(Integer.class);
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
	public void testBodyValue_withNullBody() {
		RequestImpl request = new RequestImpl("http://localhost:8080");
		Assert.assertNull(request.bodyValue());
		Assert.assertNull(request.bodyValue(this.getClass()));
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

	@Test(expected = LaunchpadSerializerException.class)
	public void testBodyValue_withoutContentType_invalidBody() {
		RequestImpl request = new RequestImpl("http://localhost:8080");
		request.body("invalid");
		request.bodyValue(Integer.class);
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
	public void testCustomUrlScheme_withBasePathOnly() {
		Request request = new RequestImpl("http://127.0.0.1:8080");
		Assert.assertEquals("http://127.0.0.1:8080", request.baseUrl());
		Assert.assertEquals("", request.path());
		Assert.assertNull(request.query());
	}

	@Test(expected = PodException.class)
	public void testCustomUrlScheme_withInvalidUrl() {
		new RequestImpl("url?a={}");
	}

	@Test
	public void testCustomUrlScheme_withNullUrl() {
		Request request = new RequestImpl(null);
		Assert.assertNull(request.baseUrl());
		Assert.assertNull(request.path());
		Assert.assertNull(request.query());
	}

	@Test
	public void testCustomUrlScheme_withoutProtocol() {
		Request request = new RequestImpl("localhost");
		Assert.assertEquals("http://localhost", request.baseUrl());
	}

	@Test
	public void testCustomUrlScheme_withRelativeUrl() {
		Request request = new RequestImpl("/path?a=b");
		Assert.assertNull(request.baseUrl());
		Assert.assertEquals("/path", request.path());
		Assert.assertEquals("a=b", request.query());
	}

	@Test
	public void testFileUploads() {
		RequestImpl request = new RequestImpl("http://127.0.0.1");
		FileUpload[] fileUploads = new FileUpload[0];
		request.fileUploads(fileUploads);
		Assert.assertArrayEquals(fileUploads, request.fileUploads());
	}

	@Test
	public void testForm() {
		Request request = new RequestImpl("http://localhost");
		request.form("key", "value");
		request.form("key", 1);
		request.forms().add("key", 2);
		request.form("key2", false);

		assertEquals(2, request.forms().size());
		assertArrayEquals(
			new Object[] {1, 2}, request.forms().getAll("key").toArray());
		assertArrayEquals(
			new Object[] {false}, request.forms().getAll("key2").toArray());
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

	@Test(expected = UnsupportedOperationException.class)
	public void testNext() {
		new RequestImpl("localhost").next();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testNext_withHandler() {
		new RequestImpl("localhost").next(() -> {
		});
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

	@Test(expected = UnsupportedOperationException.class)
	public void testSession() {
		new RequestImpl("localhost").session();
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
	public void testUrlLocal() {
		Request request = new RequestImpl("/foo");

		assertNull(request.baseUrl());
		assertEquals("/foo", request.path());
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

	@Test(expected = UnsupportedOperationException.class)
	public void testValues() {
		new RequestImpl("localhost").values();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testValues_withModelClass() {
		new RequestImpl("localhost").values(this.getClass());
	}

}