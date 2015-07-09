package com.liferay.launchpad.query;

import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;
public class SearchQueryTest {

	@Test
	public void testQuery_emptySearch() throws Exception {
		JSONAssert.assertEquals(
			"{\"search\":{\"type\":\"fetch\"}}",
			Query.builder().search(Search.builder()).toString(), true);
	}

	@Test
	public void testQuery_withAggregation() throws Exception {
		Search search = Search.builder()
			.aggregate("a", "f", "min")
			.aggregate("m", Aggregation.missing("f"))
			.aggregate("t", Aggregation.terms("f"))
			.aggregate("sum", Aggregation.sum("f"))
			.aggregate("avg", Aggregation.avg("f"))
			.aggregate("count", Aggregation.count("f"))
			.aggregate("min", Aggregation.min("f"))
			.aggregate("max", Aggregation.max("f"))
			.aggregate("s", Aggregation.stats("f"))
			.aggregate("es", Aggregation.extendedStats("f"))
			.aggregate("h", Aggregation.histogram("f", 10))
			.aggregate("dist", Aggregation.distance("f", "0,0")
				.range(Range.to(0))
				.range(0, 1)
				.range(Range.from(1))
				.unit("km"))
			.aggregate("r", Aggregation.range("f")
				.range(Range.to(0))
				.range(0, 1)
				.range(Range.from(1)));

		JSONAssert.assertEquals(
			"{\"search\":{\"type\":\"fetch\",\"aggregation\":[" +
				"{\"f\":{\"operator\":\"min\",\"name\":\"a\"}}," +
				"{\"f\":{\"operator\":\"missing\",\"name\":\"m\"}}," +
				"{\"f\":{\"operator\":\"terms\",\"name\":\"t\"}}," +
				"{\"f\":{\"operator\":\"sum\",\"name\":\"sum\"}}," +
				"{\"f\":{\"operator\":\"avg\",\"name\":\"avg\"}}," +
				"{\"f\":{\"operator\":\"count\",\"name\":\"count\"}}," +
				"{\"f\":{\"operator\":\"min\",\"name\":\"min\"}}," +
				"{\"f\":{\"operator\":\"max\",\"name\":\"max\"}}," +
				"{\"f\":{\"operator\":\"stats\",\"name\":\"s\"}}," +
				"{\"f\":{\"operator\":\"extended_stats\",\"name\":\"es\"}}," +
				"{\"f\":{\"operator\":\"histogram\",\"name\":\"h\"," +
					"\"value\":10}}," +
				"{\"f\":{\"operator\":\"geo_distance\",\"name\":\"dist\"," +
					"\"value\":{\"location\":\"0,0\",\"unit\":\"km\"," +
						"\"ranges\":[" +
							"{\"to\":0},{\"from\":0,\"to\":1},{\"from\":1}" +
					"]}}}," +
				"{\"f\":{\"operator\":\"range\",\"name\":\"r\"," +
					"\"value\":[" +
						"{\"to\":0},{\"from\":0,\"to\":1},{\"from\":1}" +
					"]}}," +
				"]}}",
			Query.builder().search(search).toString(), true);
	}

	@Test
	public void testQuery_withCursor() throws Exception {
		Search search = Search.builder().cursor("value");
		JSONAssert.assertEquals(
			"{\"search\":{\"type\":\"fetch\",\"cursor\":\"value\"}}",
			Query.builder().search(search).toString(), true);
	}

	@Test
	public void testQuery_withFilters() throws Exception {
		Search search = Search.builder()
			.preFilter("str")
			.preFilter("f", "str")
			.preFilter("f", "=", "str")
			.postFilter("str")
			.postFilter("f", "str")
			.postFilter("f", "=", "str");
		JSONAssert.assertEquals(
			"{\"search\":{\"type\":\"fetch\"," +
				"\"pre_filter\":[" +
					"{\"*\":{\"operator\":\"match\"," +
						"\"value\":{\"query\":\"str\"}}}," +
					"{\"f\":{\"operator\":\"match\"," +
						"\"value\":{\"query\":\"str\"}}}," +
					"{\"f\":{\"operator\":\"=\",\"value\":\"str\"}}]," +
				"\"post_filter\":[" +
					"{\"*\":{\"operator\":\"match\"," +
						"\"value\":{\"query\":\"str\"}}}," +
					"{\"f\":{\"operator\":\"match\"," +
						"\"value\":{\"query\":\"str\"}}}," +
					"{\"f\":{\"operator\":\"=\",\"value\":\"str\"}}]" +
			"}}",
			Query.builder().search(search).toString(), true);
	}

	@Test
	public void testQuery_withHighlight() throws Exception {
		Search search = Search.builder()
			.highlight("field1")
			.highlight("field2", 1)
			.highlight("field3", 1, 2);

		JSONAssert.assertEquals(
			"{\"search\":{\"type\":\"fetch\",\"highlight\":{" +
				"\"field1\":{}," +
				"\"field2\":{\"size\":1}," +
				"\"field3\":{\"size\":1,\"count\":2}" +
				"}}}", Query.builder().search(search).toString(), true);
	}

	@Test
	public void testQuery_withQuery() throws Exception {
		JSONAssert.assertEquals(
			"{\"search\":{\"type\":\"fetch\"," +
				"\"query\":[{\"*\":{\"operator\":\"match\"," +
					"\"value\":{\"query\":\"str\"}}}]}}",
			Query.builder().search("str").toString(), true);
		JSONAssert.assertEquals(
			"{\"search\":{\"type\":\"fetch\"," +
				"\"query\":[{\"f\":{\"operator\":\"match\"," +
					"\"value\":{\"query\":\"str\"}}}]}}",
			Query.builder().search("f", "str").toString(), true);
		JSONAssert.assertEquals(
			"{\"search\":{\"type\":\"fetch\"," +
				"\"query\":[{\"f\":{\"operator\":\"=\",\"value\":\"str\"}}]}}",
			Query.builder().search("f", "=", "str").toString(), true);
		JSONAssert.assertEquals(
			"{\"search\":{\"type\":\"fetch\"," +
				"\"query\":[{\"*\":{\"operator\":\"match\"," +
					"\"value\":{\"query\":\"str\"}}}]}}",
			Query.builder().search(Filter.match("str")).toString(), true);
	}

	@Test
	public void testQuery_withType() throws Exception {
		Search search = Search.builder().type(Search.SearchType.COUNT);
		JSONAssert.assertEquals(
			"{\"search\":{\"type\":\"count\"}}",
			Query.builder().search(search).toString(), true);
	}

}