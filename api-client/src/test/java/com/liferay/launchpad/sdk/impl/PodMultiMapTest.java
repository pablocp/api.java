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

package com.liferay.launchpad.sdk.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.liferay.launchpad.ApiClient;
import com.liferay.launchpad.sdk.PodMultiMap;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
public class PodMultiMapTest {

	@BeforeClass
	public static void beforeClass() {
		ApiClient.init();
	}

	@Test
	public void testGetAll() {
		PodMultiMap mm = createMultiMap();
		addDefaultSet(mm);

		List<String> list = mm.getAll("One");
		assertEquals(1, list.size());
		assertEquals("1", list.get(0));

		list = mm.getAll("one");

		if (mm.isCaseSensitive()) {
			assertEquals(0, list.size());
		}
		else {
			assertEquals(1, list.size());
			assertEquals("1", list.get(0));
		}
	}

	@Test
	public void testGetObject() {
		PodMultiMap mm = createMultiMap();
		addDefaultSet(mm);

		if (mm.isCaseSensitive()) {
			assertEquals("1", mm.get("One"));
			assertNull(mm.get("one"));
		}
		else {
			assertEquals("1", mm.get("One"));
			assertEquals("1", mm.get("one"));
		}
	}

	@Test
	public void testNames() {
		PodMultiMap mm = createMultiMap();
		addDefaultSet(mm);

		if (mm.isCaseSensitive()) {
			assertEquals(4, mm.names().size());
		}
		else {
			assertEquals(3, mm.names().size());
		}
	}

	protected void addDefaultSet(PodMultiMap mm) {
		mm.add("One", "1");
		mm.add("two", "two");
		mm.add("Two", "TWO");
		mm.add("three", "3");
		mm.add("three", "4");
	}

	protected PodMultiMap createMultiMap() {
		return PodMultiMap.newMultiMap();
	}

}