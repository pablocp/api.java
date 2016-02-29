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

import com.liferay.launchpad.ApiClient;
import com.liferay.launchpad.sdk.PodMultiMap;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
public abstract class PodMultiMapTest {

	@BeforeClass
	public static void beforeClass() {
		ApiClient.init();
	}

	@Test
	public void testAddAll_withMap() {
		Map<String, Integer> data = new HashMap<>();
		data.put("one", 1);
		data.put("two", 2);

		PodMultiMap mm = createMultiMap();
		mm.addAll(data);

		assertEquals(1, mm.get("one"));
		assertEquals(2, mm.get("two"));
	}

	@Test
	public void testAddAll_withIterable() {
		PodMultiMap mm = createMultiMap();
		addDefaultSet(mm);

		Map<String, String> data = new HashMap<>();
		data.put("One", "0");

		mm.addAll(data);

		if (mm.isCaseSensitive()) {
			assertArrayEquals(
				new Object[]{ "1", "0" }, mm.getAll("One").toArray());
			assertArrayEquals(
				new Object[]{ "TWO" }, mm.getAll("Two").toArray());
		}
		else {
			assertArrayEquals(
				new Object[]{ "1", "0" }, mm.getAll("one").toArray());
			assertArrayEquals(
				new Object[]{ "two", "TWO" }, mm.getAll("two").toArray());
		}
	}

	@Test
	public void testRemove() {
		PodMultiMap mm = createMultiMap();
		mm.add("one", 1);
		mm.addAll("two", Arrays.asList(1, 2, 3));

		assertNotNull(mm.get("one"));
		assertNotNull(mm.get("two"));

		mm.remove("one");
		mm.remove("two");

		assertNull(mm.get("one"));
		assertNull(mm.get("two"));
	}

	@Test
	public void testClear() {
		PodMultiMap mm = createMultiMap();
		addDefaultSet(mm);

		assertTrue(mm.size() > 0);
		mm.clear();
		assertEquals(0, mm.size());
	}

	@Test
	public void testSet() {
		PodMultiMap mm = createMultiMap();
		addDefaultSet(mm);

		if (mm.isCaseSensitive()) {
			assertArrayEquals(new Object[]{"1"}, mm.getAll("One").toArray());
			mm.set("One", "one");
			assertArrayEquals(new Object[]{"one"}, mm.getAll("One").toArray());
		}
		else {
			assertArrayEquals(new Object[]{"1"}, mm.getAll("one").toArray());
			mm.set("one", "one");
			assertArrayEquals(new Object[]{"one"}, mm.getAll("one").toArray());
		}
	}

	@Test
	public void testSetAll_withIterable() {
		PodMultiMap mm = createMultiMap();
		mm.addAll("one", Arrays.asList(1, 2));
		mm.add("two", 2);

		assertEquals(2, mm.get("two"));
		assertArrayEquals(new Object[]{1, 2}, mm.getAll("one").toArray());

		mm.setAll("one", Arrays.asList(3, 4));

		assertEquals(2, mm.get("two"));
		assertArrayEquals(new Object[]{3, 4}, mm.getAll("one").toArray());
	}

	@Test
	public void testSetAll_withMap() {
		PodMultiMap mm = createMultiMap();
		addDefaultSet(mm);

		Map<String, String> data = new HashMap<>();
		data.put("One", "0");

		mm.setAll(data);

		if (mm.isCaseSensitive()) {
			assertArrayEquals(new Object[]{ "0" }, mm.getAll("One").toArray());
			assertNull(mm.get("Two"));
		}
		else {
			assertArrayEquals(new Object[]{ "0" }, mm.getAll("one").toArray());
			assertNull(mm.get("two"));
		}
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
	public void testGetObject_withMultipleValues() {
		PodMultiMap mm = createMultiMap();
		mm.add("one", 1);
		mm.add("one", 2);
		mm.addAll("two", Arrays.asList(1, 2));

		assertEquals(2, mm.get("one"));
		assertEquals(2, mm.get("two"));
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

	protected abstract PodMultiMap createMultiMap();

}