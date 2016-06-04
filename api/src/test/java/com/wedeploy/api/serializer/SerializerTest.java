package com.wedeploy.api.serializer;

import com.wedeploy.api.model.User;
import com.wedeploy.api.sdk.ContentType;
import com.wedeploy.api.serializer.impl.JsonParser;
import com.wedeploy.api.serializer.impl.JsonSerializer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class SerializerTest {

	@After
	@Before
	public void cleanup() {
		SerializerEngine.instance().clearEngines();
	}

	@Test
	public void testParser() {
		Engines engines1 = newEngines();
		Engines engines2 = newEngines();
		SerializerEngine.instance().registerEngines("1", engines1, true);
		SerializerEngine.instance().registerEngines(
			ContentType.TEXT.contentType(), engines2, false);

		Parser parser = Parser.get();
		Assert.assertEquals(engines1.getParser(), parser);
		parser = Parser.get("1");
		Assert.assertEquals(engines1.getParser(), parser);
		parser = Parser.get(ContentType.TEXT);
		Assert.assertEquals(engines2.getParser(), parser);
	}

	@Test(expected = SerializerException.class)
	public void testParser_enginesNotFound() {
		Parser.get("1");
	}

	@Test(expected = SerializerException.class)
	public void testParser_noDefaultEnginesSet() {
		Parser.get();
	}

	@Test
	public void testParseSilently_withString() {
		SerializerEngine.instance().registerEngines("type", newEngines(), true);

		Assert.assertNull(Parser.get().parseSilently(""));
	}

	@Test
	public void testParseSilently_withStringAndClass() {
		SerializerEngine.instance().registerEngines("type", newEngines(), true);

		Assert.assertNull(Parser.get().parseSilently("", User.class));
	}

	@Test
	public void testRegisterEngines_overrideDefault() {
		SerializerEngine.instance().registerEngines("1", newEngines(), true);
		Engines engines = newEngines();
		SerializerEngine.instance().registerEngines(
			ContentType.TEXT.contentType(), engines, true);

		Parser parser = Parser.get();
		Assert.assertEquals(engines.getParser(), parser);
		Serializer serializer = Serializer.get();
		Assert.assertEquals(engines.getSerializer(), serializer);
	}

	@Test
	public void testSerializer() {
		Engines engines1 = newEngines();
		Engines engines2 = newEngines();
		SerializerEngine.instance().registerEngines("1", engines1, true);
		SerializerEngine.instance().registerEngines(
			ContentType.TEXT.contentType(), engines2, false);

		Serializer serializer;
		serializer = Serializer.get();
		Assert.assertEquals(engines1.getSerializer(), serializer);
		serializer = Serializer.get("1");
		Assert.assertEquals(engines1.getSerializer(), serializer);
		serializer = Serializer.get(ContentType.TEXT);
		Assert.assertEquals(engines2.getSerializer(), serializer);
	}

	@Test(expected = SerializerException.class)
	public void testSerializer_enginesNotFound() {
		Serializer.get("1");
	}

	@Test(expected = SerializerException.class)
	public void testSerializer_noDefaultEnginesSet() {
		Serializer.get();
	}

	@Test
	public void testSerializerEngine_constructorDummyCoverage() {
		new SerializerEngine();
	}

	protected Engines newEngines() {
		return new Engines(new JsonSerializer(), new JsonParser());
	}

}