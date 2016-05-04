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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.liferay.launchpad.sdk.impl.TestPodMultiMap;
import com.liferay.launchpad.serializer.Engines;
import com.liferay.launchpad.serializer.LaunchpadSerializerEngine;
import com.liferay.launchpad.serializer.impl.JsonLaunchpadParser;
import com.liferay.launchpad.serializer.impl.JsonLaunchpadSerializer;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 */
public class ResponseTest {

	@BeforeClass
	public static void setupClass() {
		PodMultiMapFactory.Default.factory = TestPodMultiMap::new;
		LaunchpadSerializerEngine.instance().registerEngines(
			ContentType.JSON.contentType(),
			new Engines(
				new JsonLaunchpadSerializer(), new JsonLaunchpadParser()),
			true);
	}

	@Before
	public void setup() {
		request = new RequestImpl("localhost");
	}

	@Test
	public void testBody() {
		Response response = new ResponseImpl(request);

		response.body("foo");
		assertEquals("foo", response.body());
	}

	@Test
	public void testBodyMap() {
		Response response = new ResponseImpl(request);

		response.contentType(ContentType.JSON);
		response.body("{\"key\":1}");
		Map<String, Integer> map = response.bodyMap(Integer.class);
		assertEquals(1, map.size());
		assertEquals(Integer.valueOf(1), map.get("key"));
	}

	@Test
	public void testEnd_withBodyAndContentType() {
		Response response = new ResponseImpl(request);

		response.end("foo", ContentType.HTML);
		assertEquals("foo", response.body());
		assertEquals(ContentType.HTML.toString(), response.contentType());
	}

	@Test
	public void testEnd_withObjectBody() {
		Response response = new ResponseImpl(request);

		response.end(1);
		assertEquals("1", response.body());
	}

	@Test
	public void testEnd_withStringBody() {
		Response response = new ResponseImpl(request);

		response.end("foo");
		assertEquals("foo", response.body());
	}

	@Test
	public void testHeaders() {
		Response response = new ResponseImpl(request);

		response.header("header", "1");
		assertEquals("1", response.headers().get("header"));
	}

	@Test
	public void testIsCommitted() {
		Response response = new ResponseImpl(request);

		assertFalse(response.isCommitted());
		response.body("foo");
		assertTrue(response.isCommitted());
	}

	@Test
	public void testPipe() {
		Response response = new ResponseImpl(request);
		Response toResponse = new ResponseImpl(request);
		response.header("header", "value");
		response.pipe(toResponse);
		assertEquals(response.statusMessage(), toResponse.statusMessage());
		assertEquals(response.statusCode(), toResponse.statusCode());
		assertEquals(response.body(), toResponse.body());
		assertEquals(response.headers().size(), toResponse.headers().size());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testPodContext_throwsUnsupportedOperation() {
		new ResponseImpl(request).context();
	}

	@Test
	public void testRedirect() {
		Response response = new ResponseImpl(request);

		response.redirect("url");
		assertEquals(302, response.statusCode());
		assertEquals("url", response.headers().get("Location"));
	}

	@Test
	public void testRequest() {
		Response response = new ResponseImpl(request);

		assertEquals(request, response.request());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testSession_throwsUnsupportedOperation() {
		new ResponseImpl(request).session();
	}

	@Test
	public void testStatus() {
		Response response = new ResponseImpl(request);

		response.status(200);
		assertEquals(200, response.statusCode());
		assertEquals("OK", response.statusMessage());

		response.status(200, "message");
		assertEquals(200, response.statusCode());
		assertEquals("message", response.statusMessage());

		response.status(Response.Status.OK);
		assertEquals(200, response.statusCode());
		assertEquals("OK", response.statusMessage());

		response.status(1);
		assertEquals(1, response.statusCode());
		assertEquals("(1)", response.statusMessage());
	}

	@Test
	public void testStatus_dummyCoverageTest() {
		new Response.Status();
		Response response = new ResponseImpl(request);

		for (int i = 200; i < 600; i++) {
			Assert.assertNotNull(response.status(i).statusMessage());
		}
	}

	@Test
	public void testStatusCode() {
		Response response = new ResponseImpl(request);

		response.status(200);
		assertEquals(200, response.statusCode());
		assertEquals("OK", response.statusMessage());
	}

	@Test
	public void testStatusMessage() {
		Response response = new ResponseImpl(request);

		response.status(200, "OK!");
		assertEquals(200, response.statusCode());
		assertEquals("OK!", response.statusMessage());
	}

	@Test
	public void testSucceeded() {
		Response response = new ResponseImpl(request);

		response.status(0);
		assertFalse(response.succeeded());
		response.status(200);
		assertTrue(response.succeeded());
		response.status(399);
		assertTrue(response.succeeded());
		response.status(400);
		assertFalse(response.succeeded());
	}

	private Request request;

}