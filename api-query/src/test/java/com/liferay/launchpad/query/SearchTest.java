package com.liferay.launchpad.query;

import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;
public class SearchTest {

	@Test
	public void testSearch_empty() throws Exception {
		JSONAssert.assertEquals("{}", Search.builder().toString(), true);
	}

	@Test
	public void testSearch_withAggregation() throws Exception {
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
			.aggregate("dist", Aggregation.distance(
					"f", "0,0", Range.from(0), Range.to(0))
				.range(Range.to(0))
				.range(0, 1)
				.range(Range.from(1))
				.unit("km"))
			.aggregate("r", Aggregation.range("f", Range.from(0), Range.to(0))
				.range(Range.to(0))
				.range(0, 1)
				.range(Range.from(1)));

		JSONAssert.assertEquals(
			"{\"aggregation\":[" +
				"{\"f\":{\"operator\":\"min\",\"name\":\"a\"}}," +
				"{\"f\":{\"operator\":\"missing\",\"name\":\"m\"}}," +
				"{\"f\":{\"operator\":\"terms\",\"name\":\"t\"}}," +
				"{\"f\":{\"operator\":\"sum\",\"name\":\"sum\"}}," +
				"{\"f\":{\"operator\":\"avg\",\"name\":\"avg\"}}," +
				"{\"f\":{\"operator\":\"count\",\"name\":\"count\"}}," +
				"{\"f\":{\"operator\":\"min\",\"name\":\"min\"}}," +
				"{\"f\":{\"operator\":\"max\",\"name\":\"max\"}}," +
				"{\"f\":{\"operator\":\"stats\",\"name\":\"s\"}}," +
				"{\"f\":{\"operator\":\"extendedStats\",\"name\":\"es\"}}," +
				"{\"f\":{\"operator\":\"histogram\",\"name\":\"h\"," +
					"\"value\":10}}," +
				"{\"f\":{\"operator\":\"geoDistance\",\"name\":\"dist\"," +
					"\"value\":{\"location\":\"0,0\",\"unit\":\"km\"," +
						"\"ranges\":[" +
							"{\"from\":0},{\"to\":0},{\"to\":0}," +
							"{\"from\":0,\"to\":1},{\"from\":1}" +
					"]}}}," +
				"{\"f\":{\"operator\":\"range\",\"name\":\"r\"," +
					"\"value\":[" +
						"{\"from\":0},{\"to\":0},{\"to\":0}," +
						"{\"from\":0,\"to\":1},{\"from\":1}" +
					"]}}," +
				"]}",
			search.toString(), true);
	}

	@Test
	public void testSearch_withCursor() throws Exception {
		Search search = Search.builder().cursor("value");
		JSONAssert.assertEquals(
			"{\"cursor\":\"value\"}", search.toString(), true);
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
			search.toString(), true);
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
				"}}", search.toString(), true);
	}

	@Test
	public void testSearch_withQuery() throws Exception {
		JSONAssert.assertEquals(
			"{\"query\":[{\"*\":{\"operator\":\"match\",\"value\":\"str\"}}]}",
			Search.builder().query("str").toString(), true);
		JSONAssert.assertEquals(
			"{\"query\":[{\"f\":{\"operator\":\"match\",\"value\":\"str\"}}]}",
			Search.builder().query("f", "str").toString(), true);
		JSONAssert.assertEquals(
			"{\"query\":[{\"f\":{\"operator\":\"=\",\"value\":\"str\"}}]}",
			Search.builder().query("f", "=", "str").toString(), true);
		JSONAssert.assertEquals(
			"{\"query\":[{\"*\":{\"operator\":\"match\",\"value\":\"str\"}}]}",
			Search.builder().query(SearchFilter.match("str")).toString(), true);
	}

}