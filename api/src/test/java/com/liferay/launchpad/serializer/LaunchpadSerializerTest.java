package com.liferay.launchpad.serializer;

import com.liferay.launchpad.api.model.User;
import com.liferay.launchpad.sdk.ContentType;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class LaunchpadSerializerTest {

	@After
	@Before
	public void cleanup() {
		LaunchpadSerializerEngine.instance().clearEngines();
	}

	@Test
	public void testLaunchpadSerializerEngine_constructorDummyCoverage() {
		new LaunchpadSerializerEngine();
	}

	@Test
	public void testParser() {
		Engines engines1 = newEngines();
		Engines engines2 = newEngines();
		LaunchpadSerializerEngine.instance().registerEngines(
			"1", engines1, true);
		LaunchpadSerializerEngine.instance().registerEngines(
			ContentType.TEXT.contentType(), engines2, false);

		LaunchpadParser parser = LaunchpadParser.get();
		Assert.assertEquals(engines1.getParser(), parser);
		parser = LaunchpadParser.get("1");
		Assert.assertEquals(engines1.getParser(), parser);
		parser = LaunchpadParser.get(ContentType.TEXT);
		Assert.assertEquals(engines2.getParser(), parser);
	}

	@Test(expected = LaunchpadSerializerException.class)
	public void testParser_enginesNotFound() {
		LaunchpadParser.get("1");
	}

	@Test(expected = LaunchpadSerializerException.class)
	public void testParser_noDefaultEnginesSet() {
		LaunchpadParser.get();
	}

	@Test
	public void testParseSilently_withString() {
		LaunchpadSerializerEngine.instance().registerEngines(
			"type", newEngines(), true);

		Assert.assertNull(LaunchpadParser.get().parseSilently(""));
	}

	@Test
	public void testParseSilently_withStringAndClass() {
		LaunchpadSerializerEngine.instance().registerEngines(
			"type", newEngines(), true);

		Assert.assertNull(LaunchpadParser.get().parseSilently("", User.class));
	}

	@Test
	public void testRegisterEngines_overrideDefault() {
		LaunchpadSerializerEngine.instance().registerEngines(
			"1", newEngines(), true);
		Engines engines = newEngines();
		LaunchpadSerializerEngine.instance().registerEngines(
			ContentType.TEXT.contentType(), engines, true);

		LaunchpadParser parser = LaunchpadParser.get();
		Assert.assertEquals(engines.getParser(), parser);
		LaunchpadSerializer serializer = LaunchpadSerializer.get();
		Assert.assertEquals(engines.getSerializer(), serializer);
	}

	@Test
	public void testSerializer() {
		Engines engines1 = newEngines();
		Engines engines2 = newEngines();
		LaunchpadSerializerEngine.instance().registerEngines(
			"1", engines1, true);
		LaunchpadSerializerEngine.instance().registerEngines(
			ContentType.TEXT.contentType(), engines2, false);

		LaunchpadSerializer serializer;
		serializer = LaunchpadSerializer.get();
		Assert.assertEquals(engines1.getSerializer(), serializer);
		serializer = LaunchpadSerializer.get("1");
		Assert.assertEquals(engines1.getSerializer(), serializer);
		serializer = LaunchpadSerializer.get(ContentType.TEXT);
		Assert.assertEquals(engines2.getSerializer(), serializer);
	}

	@Test(expected = LaunchpadSerializerException.class)
	public void testSerializer_enginesNotFound() {
		LaunchpadSerializer.get("1");
	}

	@Test(expected = LaunchpadSerializerException.class)
	public void testSerializer_noDefaultEnginesSet() {
		LaunchpadSerializer.get();
	}

	protected Engines newEngines() {
		LaunchpadSerializer serializer = (obj, deep) -> {
			throw new LaunchpadSerializerException("serializer error");
		};

		LaunchpadParser parser = new LaunchpadParser() {

			@Override
			public <T> T parse(String string) {
				throw new LaunchpadSerializerException(string);
			}

			@Override
			public <T> T parse(String string, Class<T> type) {
				throw new LaunchpadSerializerException(string, new Exception());
			}

			@Override
			public <T> List<T> parseAsList(
				String string, Class<T> componentType) {

				throw new LaunchpadSerializerException(string);
			}

		};

		return new Engines(serializer, parser);
	}

}