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

import java.security.SecureRandom;

/**
 * Provides a very simple way to generate unique ordered ids across distributed
 * shards (or even multiple systems). It's inspired on Simpleflake's id
 * generation and requires zero coordination with server ips or mac addresses
 * or–even worse–any coordination of code with database content.
 * <p/>
 * This implementation follows a pattern that a 64-bits long is prefixed with a
 * millisecond timestamp, but the remaining bits are completely random, e.g.:
 * <p/>
 * <pre>
 * id = 0000000000000000000000000000000000000000000   0   00000000000000000000
 *     |-------------------------------------------| |-| |--------------------|
 *                    timestamp                  sequence      random
 *                      bits                    safety bit      bits
 * </pre>
 *
 * @author Eduardo Lundgren
 */
public class TimeOrderedUuidGenerator implements LaunchpadUuidGenerator {

	/**
	 * Gets start count timestamp value.
	 */
	public TimeOrderedUuidGenerator() {
		this(TIMESTAMP_MIN);
	}

	/**
	 * Instantiates a new time ordered unique generator.
	 *
	 * @param startCountTimestampValue the start count timestamp value.
	 */
	public TimeOrderedUuidGenerator(long startCountTimestampValue) {
		if (startCountTimestampValue < 0) {
			throw new IllegalArgumentException(
				"Start timestamp should be positive");
		}

		this.startCountTimestampValue = startCountTimestampValue;
	}

	/**
	 * Generates a unique ordered id across distributed shards (or even multiple
	 * systems).
	 * @return the unique ordered id.
	 */
	@Override
	public String generateId() {
		long timestamp = currentTimeMillis();

		// Prevents the clock to move backwards

		while (timestamp < lastTimestamp) {
			timestamp = currentTimeMillis();
		}

		if ((timestamp < startCountTimestampValue) ||
			(timestamp > TIMESTAMP_MAX)) {

			throw new RuntimeException(
				"System clock was not valid, refusing to generate id");
		}

		if (timestamp != lastTimestamp) {
			lastTimestamp = timestamp;
			lastRandom = secureRandom.nextInt(RANDOM_MAX);
		}
		else {
			lastRandom++;
		}

		long uuid = timestamp - startCountTimestampValue;
		uuid <<= SEQUENCE_SAFETY_BITS;
		uuid <<= RANDOM_BITS;
		uuid |= lastRandom;

		return String.valueOf(uuid);
	}

	/**
	 * Gets start count timestamp value.
	 *
	 * @return the start count timestamp value.
	 */
	public long getStartCountTimestampValue() {
		return startCountTimestampValue;
	}

	/**
	 * Gets the current time in milliseconds.
	 *
	 * @return the current time in milliseconds.
	 */
	protected long currentTimeMillis() {
		return System.currentTimeMillis();
	}

	private static final int RANDOM_BITS = 20;

	private static final int RANDOM_MAX = -1 ^ (-1 << RANDOM_BITS);

	private static final int SEQUENCE_SAFETY_BITS = 1;

	private static final int TIMESTAMP_BITS = 43;

	private static final long TIMESTAMP_MAX = -1L ^ (-1L << TIMESTAMP_BITS);

	private static final long TIMESTAMP_MIN = 1388502000000L;

	private static int lastRandom = 0;
	private static long lastTimestamp = -1L;
	private static final SecureRandom secureRandom = new SecureRandom();

	private final long startCountTimestampValue;

}