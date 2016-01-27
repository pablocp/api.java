package com.liferay.launchpad.security;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class LaunchpadHashEngineTest {

	@After
	@Before
	public void cleanup() {
		LaunchpadHashEngine.instance().clearEngine();
	}

	@Test
	public void testLaunchpadHashEngine_constructorDummyCoverage() {
		new LaunchpadHashEngine();
	}

	@Test(expected = LaunchpadHashException.class)
	public void testLookup_noGeneratorAndNoFactory() {
		LaunchpadHashGenerator.get("algorithm");
	}

	@Test
	public void testLookup_withFactorySet() {
		LaunchpadHashEngine.instance().setFactory(
			algorithm -> newHashGenerator());

		LaunchpadHashGenerator engine1 = LaunchpadHashGenerator.get("type");
		LaunchpadHashGenerator engine2 = LaunchpadHashGenerator.get("type");

		Assert.assertEquals(engine1, engine2);
	}

	@Test(expected = LaunchpadHashException.class)
	public void testLookup_withFactorySet_createThrowsException() {
		LaunchpadHashEngine.instance().setFactory(algorithm -> {
			throw new LaunchpadHashException("error", new Exception());
		});

		LaunchpadHashGenerator.get("algorithm");
	}

	@Test
	public void testLookup_withGeneratorSet() {
		LaunchpadHashGenerator generator = newHashGenerator();
		LaunchpadHashEngine.instance().register(
			HashAlgorithm.BCRYPT.getAlgorithm(), generator);

		LaunchpadHashGenerator bcrypt = LaunchpadHashGenerator.get(
			HashAlgorithm.BCRYPT);

		Assert.assertEquals(generator, bcrypt);
	}

	protected LaunchpadHashGenerator newHashGenerator() {
		return new LaunchpadHashGenerator() {

			@Override
			public boolean checkHash(String rawValue, String encryptedValue) {
				throw new LaunchpadHashException(rawValue, new Exception());
			}

			@Override
			public String generateHash(String rawValue, String encryptedValue) {
				throw new LaunchpadHashException(rawValue, new Exception());
			}

		};
	}

}