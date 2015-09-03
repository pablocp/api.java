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

/**
 * @author Filipe Martins
 */
public class IncrementalUuidGeneratorTest extends LaunchpadUuidGeneratorTest {

	@Test(expected = IllegalArgumentException.class)
	public void testReset_withInvalidValue() {
		IncrementalUuidGenerator gen = new IncrementalUuidGenerator();
		gen.reset(-11);
		gen.generateId();
	}

	@Test
	public void testReset_withNoValue() {
		IncrementalUuidGenerator gen = new IncrementalUuidGenerator();
		gen.reset();
		Assert.assertEquals("1", gen.generateId());
	}

	@Test
	public void testReset_withValidValue() {
		IncrementalUuidGenerator gen = new IncrementalUuidGenerator();
		gen.reset(46);
		Assert.assertEquals("47", gen.generateId());
	}

	@Override
	protected List<LaunchpadUuidGenerator> getUuidGenerators() {
		List<LaunchpadUuidGenerator> generators = new ArrayList<>();
		generators.add(new IncrementalUuidGenerator());
		generators.add(new IncrementalUuidGenerator(477));
		generators.add(new IncrementalUuidGenerator(0));
		generators.add(new IncrementalUuidGenerator(1_111_111));
		return generators;
	}

}