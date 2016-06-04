package com.wedeploy.api.uuid;

import org.junit.Assert;
import org.junit.Test;
public class UuidGeneratorTest {

	@Test
	public void testDefaultUuidGenerator_constructorDummyCoverage() {
		new DefaultUuidGenerator();
	}

	@Test
	public void testDefaultUuidGenerator_gettersAndSetters() {
		UuidGenerator generator = () -> "";
		DefaultUuidGenerator.setDefaultGenerator(generator);
		Assert.assertEquals(generator, UuidGenerator.get());
	}

}