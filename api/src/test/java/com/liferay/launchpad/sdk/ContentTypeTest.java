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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
public class ContentTypeTest {

	@Test
	public void testCharset() {
		assertNull(new ContentType("text/html").charset());
		assertNull(new ContentType("text/html", null).charset());
		assertEquals(
			"UTF-8", new ContentType("text/html;charset=UTF-8").charset());
	}

	@Test
	public void testConstructor_parseContentTypeString() {
		assertNull(new ContentType("text/html; charset=").charset());
		assertEquals(
			"UTF-8", new ContentType("text/html; charset=UTF-8").charset());
		assertEquals(
			"UTF-8", new ContentType("text/html; charset= UTF-8").charset());
		assertEquals(
			"UTF-8", new ContentType("text/html; charset=\"UTF-8").charset());
		assertEquals(
			"UTF-8", new ContentType("text/html; charset=\"UTF-8\"").charset());
	}

	@Test
	public void testEquals() {
		assertEquals(ContentType.HTML, ContentType.HTML);
		assertEquals(ContentType.HTML, new ContentType("text/html", "UTF-8"));
		assertNotEquals(ContentType.HTML, ContentType.JSON);
		assertNotEquals(ContentType.HTML, new ContentType("text/html"));
		assertNotEquals(ContentType.HTML, new ContentType("text/html", null));
		assertNotEquals(ContentType.HTML, "text/html");
	}

	@Test
	public void testHashCode() {
		assertEquals(ContentType.HTML.hashCode(), ContentType.HTML.hashCode());
		assertEquals(
			ContentType.HTML.hashCode(),
			new ContentType("text/html", "UTF-8").hashCode());
		assertNotEquals(
			ContentType.HTML.hashCode(), ContentType.JSON.hashCode());
		assertNotEquals(
			ContentType.HTML.hashCode(),
			new ContentType("text/html").hashCode());
		assertNotEquals(
			ContentType.HTML.hashCode(),
			new ContentType("text/html", null).hashCode());
	}

	@Test
	public void testSame() {
		assertTrue(ContentType.HTML.isSame("text/html;charset=UTF-8"));
		assertTrue(ContentType.HTML.isSame("text/html"));
		assertFalse(ContentType.HTML.isSame("application/json"));
		assertFalse(ContentType.HTML.isSame(null));
	}

}