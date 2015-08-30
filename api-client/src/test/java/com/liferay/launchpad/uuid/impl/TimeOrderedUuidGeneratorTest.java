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

package com.liferay.launchpad.uuid.impl;

import com.liferay.launchpad.uuid.LaunchpadUuidGenerator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Eduardo Lundgren
 */
public class TimeOrderedUuidGeneratorTest extends LaunchpadUuidGeneratorTest {

	@Test
	public void testClockBackwards() {
		long now = System.currentTimeMillis();
		TimeOrderedUuidGenerator gen = Mockito.mock(
			TimeOrderedUuidGenerator.class);
		Mockito.when(gen.currentTimeMillis()).thenReturn(now, now - 1, now + 1);
		Mockito.when(gen.generate()).thenCallRealMethod();
		String id1 = gen.generate();
		String id2 = gen.generate();
		String id3 = gen.generate();
		Assert.assertNotEquals(id1, id2);
		Assert.assertNotEquals(id1, id3);
		Assert.assertNotEquals(id2, id3);
	}

	@Test
	public void testEmptyConstructor() {
		TimeOrderedUuidGenerator gen = new TimeOrderedUuidGenerator();
		Assert.assertEquals(1388502000000L, gen.getStartCountTimestampValue());
	}

	@Test(expected = RuntimeException.class)
	public void testFutureStartTimestampConstructor() {
		long future = Long.MAX_VALUE;
		TimeOrderedUuidGenerator gen = new TimeOrderedUuidGenerator(future);
		gen.generate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeValueConstructor() {
		TimeOrderedUuidGenerator gen = new TimeOrderedUuidGenerator(-1L);
		gen.generate();
	}

	@Test
	public void testSetStartTimestampConstructor() {
		long now = System.currentTimeMillis();
		TimeOrderedUuidGenerator gen = new TimeOrderedUuidGenerator(now);
		Assert.assertEquals(now, gen.getStartCountTimestampValue());
	}

	@Override
	protected List<LaunchpadUuidGenerator> getUuidGenerators() {
		List<LaunchpadUuidGenerator> generators = new ArrayList<>();
		generators.add(new TimeOrderedUuidGenerator());
		generators.add(new TimeOrderedUuidGenerator(27));
		generators.add(new TimeOrderedUuidGenerator(1L << 40));
		generators.add(new TimeOrderedUuidGenerator(9_876_543_210L));
		generators.add(new TimeOrderedUuidGenerator(0));
		return generators;
	}

}