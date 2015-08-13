package com.liferay.launchpad.query;

import org.junit.Assert;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;
public class SearchTest {

	@Test
	public void testSearch_empty() throws Exception {
		JSONAssert.assertEquals("{}", Search.builder().bodyAsJson(), true);
	}

	@Test
	public void testSearch_withAggregation() throws Exception {
		Search search = Search.builder()
			.aggregate("a", "f", "min")
			.aggregate(Aggregation.missing("m", "f"));

		JSONAssert.assertEquals(
			"{\"aggregation\":[" +
				"{\"f\":{\"operator\":\"min\",\"name\":\"a\"}}," +
				"{\"f\":{\"operator\":\"missing\",\"name\":\"m\"}}," +
			"]}",
			search.bodyAsJson(), true);
	}

	@Test
	public void testSearch_withCursor() throws Exception {
		Search search = Search.builder().cursor("value");
		JSONAssert.assertEquals(
			"{\"cursor\":\"value\"}", search.bodyAsJson(), true);
	}

	@Test
	public void testSearch_withFilters() throws Exception {
		Search search = Search.builder()
			.preFilter("str")
			.preFilter("f", "str")
			.preFilter("f", "=", "str")
			.postFilter("str")
			.postFilter("f", "str")
			.postFilter("f", "=", "str");
		JSONAssert.assertEquals(
			"{" +
				"\"preFilter\":[" +
					"{\"*\":{\"operator\":\"match\",\"value\":\"str\"}}," +
					"{\"f\":{\"operator\":\"match\",\"value\":\"str\"}}," +
					"{\"f\":{\"operator\":\"=\",\"value\":\"str\"}}]," +
				"\"postFilter\":[" +
					"{\"*\":{\"operator\":\"match\",\"value\":\"str\"}}," +
					"{\"f\":{\"operator\":\"match\",\"value\":\"str\"}}," +
					"{\"f\":{\"operator\":\"=\",\"value\":\"str\"}}]" +
			"}",
			search.bodyAsJson(), true);
	}

	@Test
	public void testSearch_withHighlight() throws Exception {
		Search search = Search.builder()
			.highlight("field1")
			.highlight("field2", 1)
			.highlight("field3", 1, 2);

		JSONAssert.assertEquals(
			"{\"highlight\":{" +
				"\"field1\":{}," +
				"\"field2\":{\"size\":1}," +
				"\"field3\":{\"size\":1,\"count\":2}" +
				"}}", search.bodyAsJson(), true);
	}

	@Test
	public void testSearch_withQuery() throws Exception {
		JSONAssert.assertEquals(
			"{\"query\":[{\"*\":{\"operator\":\"match\",\"value\":\"str\"}}]}",
			Search.builder().query("str").bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"query\":[{\"f\":{\"operator\":\"match\",\"value\":\"str\"}}]}",
			Search.builder().query("f", "str").bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"query\":[{\"f\":{\"operator\":\"=\",\"value\":\"str\"}}]}",
			Search.builder().query("f", "=", "str").bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"query\":[{\"*\":{\"operator\":\"match\",\"value\":\"str\"}}]}",
			Search.builder().query(SearchFilter.match("str")).bodyAsJson(),
			true);
	}

	@Test
	public void testToString() {
		Search search = Search.builder()
			.preFilter("field", "text")
			.query("query")
			.postFilter("filter")
			.aggregate("name", "field", "min")
			.highlight("field")
			.cursor("cursor");

		Assert.assertEquals(
			Query.builder().search(search).toString(), search.toString());
	}

}