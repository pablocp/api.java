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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
public class ValueUtilTest {

	@Test
	public void testIsJsonMap() {
		assertFalse(ValuesUtil.isJsonMap(null));
		assertFalse(ValuesUtil.isJsonMap(""));
		assertFalse(ValuesUtil.isJsonMap("   "));
		assertFalse(ValuesUtil.isJsonMap("[]"));

		assertTrue(ValuesUtil.isJsonMap("{}"));
		assertTrue(ValuesUtil.isJsonMap("{"));
		assertTrue(ValuesUtil.isJsonMap("  {"));
	}

	@Test
	public void testToString_withNonNull() {
		assertEquals("123", ValuesUtil.toString(Integer.valueOf(123)));
	}

	@Test
	public void testToString_withNull() {
		assertNull(ValuesUtil.toString(null));
	}

	@Test
	public void testValuesUtil_constructorDummyCoverage() {
		new ValuesUtil();
	}

}