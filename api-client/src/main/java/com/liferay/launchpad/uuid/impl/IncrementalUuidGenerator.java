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

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simple UUID generator that increments on its usage.
 * Useful in tests and debugging. Can be used in production
 * but only on single-jvm and when last generated number is
 * stored.
 *
 * @author Igor Spasić
 */
public class IncrementalUuidGenerator implements LaunchpadUuidGenerator {

	public IncrementalUuidGenerator() {
		this(0);
	}

	public IncrementalUuidGenerator(int start) {
		checkValue(start);
		UUID = new AtomicInteger(start);
	}

	@Override
	public String generateId() {
		int uuid = UUID.incrementAndGet();

		return String.valueOf(uuid);
	}

	public void reset() {
		UUID.set(0);
	}

	public void reset(int value) {
		checkValue(value);
		UUID.set(value);
	}

	private void checkValue(int value) {
		if (value < 0) {
			throw new IllegalArgumentException("Negative value: " + value);
		}
	}

	private final AtomicInteger UUID;

}