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

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Filipe Martins
 */
public abstract class LaunchpadUuidGeneratorTest {

	@Test
	public void testGenerateUUID() {
		for (LaunchpadUuidGenerator gen : getUuidGenerators()) {
			final int total = NUMBER_OF_REQUESTS;
			Set<String> ids = new HashSet<>();

			for (int i = 0; i < total; i++) {
				ids.add(gen.generate());
			}

			Assert.assertEquals(total, ids.size());
		}
	}

	@Test
	public void testOrder() throws InterruptedException {
		for (LaunchpadUuidGenerator gen : getUuidGenerators()) {
			final int total = NUMBER_OF_REQUESTS;
			final Set<Long> ids = new LinkedHashSet<>(total);
			final Object lock = new Object();
			ExecutorService taskExecutor = Executors.newFixedThreadPool(10);

			for (int i = 0; i < total; i++) {
				taskExecutor.execute(() -> {
					synchronized (lock) {

						// this is NOT a real multi-thread test, since there is
						// a single lock for them all don't know it is possible
						// to test just generateUUID method order in
						// multi-thread environment. Be careful when order of
						// persistent insertion must be match the UUID order: it
						// may happens that entity with earlier UUID gets
						// inserted later! If not synchronized, there might be
						// differences!

						ids.add(Long.valueOf(gen.generate()));
					}
				});
			}

			taskExecutor.shutdown();
			taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

			long lastId = -1L;

			for (long id : ids) {
				if (id <= lastId) {
					Assert.fail(
						"Unordered ids detected: " + id + "<=" + lastId);
				}

				lastId = id;
			}
		}
	}

	protected abstract List<LaunchpadUuidGenerator> getUuidGenerators();

	protected static final int NUMBER_OF_REQUESTS = 1_000_000;

}