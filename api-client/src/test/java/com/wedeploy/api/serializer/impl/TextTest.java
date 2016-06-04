package com.wedeploy.api.serializer.impl;

import static org.junit.Assert.assertEquals;

import com.wedeploy.api.ApiClient;
import com.wedeploy.api.sdk.ContentType;
import com.wedeploy.api.serializer.Parser;
import com.wedeploy.api.serializer.Serializer;
import com.wedeploy.api.serializer.impl.model.Numbers;
import com.wedeploy.api.serializer.impl.model.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.typeconverter.TypeConversionException;

import org.junit.BeforeClass;
import org.junit.Test;
public class TextTest {

	@BeforeClass
	public static void beforeClass() {
		ApiClient.init();
	}

	@Test
	public void testParse_fromList() {
		String values = "1";

		List<Integer> numbers = parser().parseAsList(values, Integer.class);

		assertEquals(1, numbers.size());

		assertEquals(1, numbers.get(0).intValue());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testParse_fromMap() {
		parser().parseAsMap("{\"one\" : 123}", String.class, Long.class);
	}

	@Test
	public void testParse_rawValue() {
		String numbers = parser().parse("123");

		assertEquals("123", numbers);
	}

	@Test(expected = TypeConversionException.class)
	public void testParse_withModel() {
		parser().parse("id:name", User.class);
	}

	@Test(expected = ClassCastException.class)
	public void testParse_withNonStringValue() {
		Long value = parser().parse("12345678901");
	}

	@Test
	public void testSerialize_rawValue() {
		String text = serializer().serialize(123);

		assertEquals("123", text);
	}

	@Test
	public void testSerialize_toList() {
		String text = serializer().serialize(Arrays.asList(123L, 456L));
		assertEquals("[123, 456]", text);
	}

	@Test
	public void testSerialize_toMap() {
		Map<String, Long> numbers = new HashMap<>();
		numbers.put("one", 123L);
		numbers.put("two", 456L);

		String text = serializer().serialize(numbers);

		assertEquals(numbers.toString(), text);
	}

	@Test
	public void testSerialize_withAnnotation() {
		User user = new User();

		String text = serializer().serialize(user);

		assertEquals(user.toString(), text);
	}

	@Test
	public void testSerializer_withLongValue() {
		Numbers numbers = new Numbers();

		String text = serializer().serialize(numbers);

		assertEquals(numbers.toString(), text);
	}

	protected Parser parser() {
		return Parser.get(ContentType.TEXT);
	}

	protected Serializer serializer() {
		return Serializer.get(ContentType.TEXT);
	}

}