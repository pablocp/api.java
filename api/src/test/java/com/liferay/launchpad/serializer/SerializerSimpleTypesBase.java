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

package com.liferay.launchpad.serializer;

import static org.junit.Assert.assertEquals;

import com.liferay.launchpad.sdk.ContentType;

import org.junit.Test;
public abstract class SerializerSimpleTypesBase {

	@Test
	public void testDouble() {
		assertEquals("9.87", launchpadSerializer.serialize(9.87));
	}

	@Test
	public void testInt() {
		assertEquals("1", launchpadSerializer.serialize(1));
	}

	private LaunchpadSerializer launchpadSerializer = LaunchpadSerializer.get(
		ContentType.JSON);

}