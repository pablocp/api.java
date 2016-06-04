package com.wedeploy.api.serializer.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.wedeploy.api.ApiClient;
import com.wedeploy.api.sdk.ContentType;
import com.wedeploy.api.sdk.MultiMap;
import com.wedeploy.api.serializer.Parser;
import com.wedeploy.api.serializer.Serializer;
import com.wedeploy.api.serializer.impl.model.Numbers;
import com.wedeploy.api.serializer.impl.model.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
public class JsonTest {

	@BeforeClass
	public static void beforeClass() {
		ApiClient.init();
	}

	@Test
	public void testParse_fromList() {
		String values = "[123, 456]";

		List<Long> numbers = parser().parseAsList(values, Long.class);

		assertEquals(2, numbers.size());

		assertEquals(123L, numbers.get(0).longValue());
		assertEquals(456L, numbers.get(1).longValue());
	}

	@Test
	public void testParse_rawValue() {
		Integer numbers = parser().parse("123");

		assertEquals(Integer.valueOf(123), numbers);
	}

	@Test
	public void testParse_toMap() {
		String values = "{\"one\" : 123, \"two\" : 456}";

		Map<String, Long> numbers = parser().parseAsMap(
			values, String.class, Long.class);

		assertEquals(2, numbers.size());

		assertEquals(Long.valueOf(123L), numbers.get("one"));
		assertEquals(Long.valueOf(456L), numbers.get("two"));
	}

	@Test
	public void testParse_withAnnotation() {
		String json = "{\"id\" : \"QWE\",\"name\" : \"Jenny\" }";

		User user = parser().parse(json, User.class);

		assertEquals("QWE", user.getUserId());
		assertEquals("Jenny", user.getName());
	}

	@Test
	public void testParse_withLongValue() {
		String values = "{\"id1\" : 12345678901, \"id2\" : \"1234567890\"}";

		Numbers numbers = parser().parse(values, Numbers.class);

		assertEquals(12345678901L, numbers.getId1().longValue());
		assertEquals(1234567890L, numbers.getId2());
	}

	@Test
	public void testSerialize_rawValue() {
		String json = serializer().serialize(123);

		assertEquals("123", json);
	}

	@Test
	public void testSerialize_toList() {
		String json = serializer().serialize(Arrays.asList(123L, 456L));
		assertEquals("[\"123\",\"456\"]", json);
	}

	@Test
	public void testSerialize_withAnnotation() {
		User user = new User();

		String json = serializer().serialize(user);

		assertTrue(json.contains("\"id\""));
		assertTrue(json.contains("\"name\""));
		assertFalse(json.contains("\"userId\""));
	}

	@Test
	public void testSerialize_withMap() {
		Map<String, Long> numbers = new HashMap<>();
		numbers.put("one", 123L);
		numbers.put("two", 456L);

		String json = serializer().serialize(numbers);

		assertEquals("{\"one\":\"123\",\"two\":\"456\"}", json);
	}

	@Test
	public void testSerialize_withMultiMap() {
		MultiMap<Long> multiMap = MultiMap.newMultiMap();
		multiMap.add("one", 123L);
		multiMap.add("one", 456L);

		String json = serializer().serialize(multiMap);

		assertEquals("{\"one\":[\"123\",\"456\"]}", json);
	}

	@Test
	public void testSerializer_withLongValue() {
		Numbers numbers = new Numbers();

		String json = serializer().serialize(numbers);

		assertTrue(json.contains("\"" + numbers.getId1() + "\""));
		assertTrue(json.contains("\"" + numbers.getId2() + "\""));
	}

	protected Parser parser() {
		return Parser.get(ContentType.JSON);
	}

	protected Serializer serializer() {
		return Serializer.get(ContentType.JSON);
	}

}