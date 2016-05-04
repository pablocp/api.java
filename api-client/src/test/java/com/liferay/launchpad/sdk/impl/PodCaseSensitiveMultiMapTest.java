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

import com.liferay.launchpad.sdk.PodMultiMap;

import org.junit.Assert;
import org.junit.Test;
public class PodCaseSensitiveMultiMapTest extends PodMultiMapTest {

	@Test
	public void testCaseSensitiveMultiMap() {
		PodMultiMap map = createMultiMap();

		map.add("key", "value");
		Assert.assertEquals("value", map.get("key"));
		Assert.assertNull(map.get("KEY"));
	}

	@Override
	protected PodMultiMap createMultiMap() {
		return PodMultiMap.newCaseSensitiveMultiMap();
	}

}