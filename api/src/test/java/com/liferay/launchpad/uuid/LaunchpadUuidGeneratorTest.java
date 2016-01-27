package com.liferay.launchpad.uuid;

import org.junit.Assert;
import org.junit.Test;
public class LaunchpadUuidGeneratorTest {

	@Test
	public void testDefaultUuidGenerator_constructorDummyCoverage() {
		new DefaultUuidGenerator();
	}

	@Test
	public void testDefaultUuidGenerator_gettersAndSetters() {
		LaunchpadUuidGenerator generator = () -> "";
		DefaultUuidGenerator.setDefaultGenerator(generator);
		Assert.assertEquals(generator, LaunchpadUuidGenerator.get());
	}

}