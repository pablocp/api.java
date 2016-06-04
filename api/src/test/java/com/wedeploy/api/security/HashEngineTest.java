package com.wedeploy.api.security;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class HashEngineTest {

	@After
	@Before
	public void cleanup() {
		HashEngine.instance().clearEngine();
	}

	@Test
	public void testHashEngine_constructorDummyCoverage() {
		new HashEngine();
	}

	@Test(expected = HashEngineException.class)
	public void testLookup_noGeneratorAndNoFactory() {
		HashGenerator.get("algorithm");
	}

	@Test
	public void testLookup_withFactorySet() {
		HashEngine.instance().setFactory(algorithm -> newHashGenerator());

		HashGenerator engine1 = HashGenerator.get("type");
		HashGenerator engine2 = HashGenerator.get("type");

		Assert.assertEquals(engine1, engine2);
	}

	@Test(expected = HashEngineException.class)
	public void testLookup_withFactorySet_createThrowsException() {
		HashEngine.instance().setFactory(algorithm -> {
			throw new HashEngineException("error", new Exception());
		});

		HashGenerator.get("algorithm");
	}

	@Test
	public void testLookup_withGeneratorSet() {
		HashGenerator generator = newHashGenerator();
		HashEngine.instance().register(
			HashAlgorithm.BCRYPT.getAlgorithm(), generator);

		HashGenerator bcrypt = HashGenerator.get(HashAlgorithm.BCRYPT);

		Assert.assertEquals(generator, bcrypt);
	}

	protected HashGenerator newHashGenerator() {
		return new HashGenerator() {

			@Override
			public boolean checkHash(String rawValue, String encryptedValue) {
				throw new HashEngineException(rawValue, new Exception());
			}

			@Override
			public String generateHash(String rawValue, String encryptedValue) {
				throw new HashEngineException(rawValue, new Exception());
			}

		};
	}

}