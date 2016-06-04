package com.wedeploy.api.query;

import org.junit.Assert;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;
public class QueryTest {

	@Test
	public void testQuery_empty() throws Exception {
		JSONAssert.assertEquals(
			"{\"type\":\"fetch\"}", Query.fetch().toString(), true);
	}

	@Test
	public void testQuery_withAggregation() throws Exception {
		Query query = Query
			.aggregate("a", "f", "min")
			.aggregate(Aggregation.missing("m", "f"));

		JSONAssert.assertEquals(
			"{\"aggregation\":[" +
				"{\"f\":{\"operator\":\"min\",\"name\":\"a\"}}," +
				"{\"f\":{\"operator\":\"missing\",\"name\":\"m\"}}," +
				"]}",
			query.bodyAsJson(), true);
	}

	@Test
	public void testQuery_withFilters() throws Exception {
		String body = Query.filter("field", 1).toString();

		JSONAssert.assertEquals(
			"{\"filter\":[{\"field\":{\"operator\":\"=\",\"value\":1}}]}", body,
			true);

		body = Query.filter("field", ">", 1).toString();

		JSONAssert.assertEquals(
			"{\"filter\":[{\"field\":{\"operator\":\">\",\"value\":1}}]}", body,
			true);

		body = Query
				.filter("field1", 1)
				.filter("field1", 1)
				.filter("field2", "regex", "value")
				.filter(Filter.field("field3", 0.55))
				.filter(Filter.field("field4", "pre", "str"))
				.filter(Filter.not("field5", 1))
				.filter(Filter.not("field7", "!=", 1))
				.filter(Filter.not(Filter.field("field8", 1)))
				.bodyAsJson();

		JSONAssert.assertEquals(
			"{\"filter\":[" +
				"{\"field1\":{\"operator\":\"=\",\"value\":1}}," +
				"{\"field1\":{\"operator\":\"=\",\"value\":1}}," +
				"{\"field2\":{\"operator\":\"regex\",\"value\":\"value\"}}," +
				"{\"field3\":{\"operator\":\"=\",\"value\":0.55}}," +
				"{\"field4\":{\"operator\":\"pre\",\"value\":\"str\"}}," +
				"{\"not\":{\"field5\":{\"operator\":\"=\",\"value\":1}}}," +
				"{\"not\":{\"field7\":{\"operator\":\"!=\",\"value\":1}}}," +
				"{\"not\":{\"field8\":{\"operator\":\"=\",\"value\":1}}}" +
			"]}", body, true);
	}

	@Test
	public void testQuery_withHighlight() throws Exception {
		Query search = Query
			.highlight("field1")
			.highlight("field2")
			.highlight("field3");

		JSONAssert.assertEquals(
			"{\"highlight\":[\"field1\",\"field2\",\"field3\"]}",
			search.bodyAsJson(), true);
	}

	@Test
	public void testQuery_withLimitAndOffset() throws Exception {
		String body = Query
				.limit(1)
				.offset(2)
				.bodyAsJson();

		JSONAssert.assertEquals("{\"limit\":1,\"offset\":2}", body, true);
	}

	@Test
	public void testQuery_withSearch() throws Exception {
		Query query = Query.search(Filter.equal("field", "value"));

		JSONAssert.assertEquals(
			"{\"search\":[{" +
				"\"field\":{\"operator\":\"=\",\"value\":\"value\"}}]}",
			query.bodyAsJson(), true);
		query = Query.search("query");
		JSONAssert.assertEquals(
			"{\"search\":[{" +
				"\"*\":{\"operator\":\"match\",\"value\":\"query\"}}]}",
			query.bodyAsJson(), true);
		query = Query.search("field", "query");
		JSONAssert.assertEquals(
			"{\"search\":[{" +
				"\"field\":{\"operator\":\"match\",\"value\":\"query\"}}]}",
			query.bodyAsJson(), true);
		query = Query.search("field", "=", "query");
		JSONAssert.assertEquals(
			"{\"search\":[{" +
				"\"field\":{\"operator\":\"=\",\"value\":\"query\"}}]}",
			query.bodyAsJson(), true);

		query = Query.search("query")
			.search("query")
			.search("field", "value")
			.search("field", "=", "value")
			.search(Filter.field("field", "value"));
		JSONAssert.assertEquals(
			"{\"search\":[" +
				"{\"*\":{\"operator\":\"match\",\"value\":\"query\"}}," +
				"{\"*\":{\"operator\":\"match\",\"value\":\"query\"}}," +
				"{\"field\":{\"operator\":\"match\",\"value\":\"value\"}}," +
				"{\"field\":{\"operator\":\"=\",\"value\":\"value\"}}," +
				"{\"field\":{\"operator\":\"=\",\"value\":\"value\"}},]}",
			query.bodyAsJson(), true);
	}

	@Test
	public void testQuery_withSort() throws Exception {
		String body = Query
				.sort("field")
				.bodyAsJson();

		JSONAssert.assertEquals("{\"sort\":[{\"field\":\"asc\"}]}", body, true);

		body = Query
				.sort("field1")
				.sort("field2", "asc")
				.sort("field3", "desc")
				.bodyAsJson();

		JSONAssert.assertEquals(
			"{\"sort\":[" +
				"{\"field1\":\"asc\"}," +
				"{\"field2\":\"asc\"}," +
				"{\"field3\":\"desc\"}" +
			"]}", body, true);
	}

	@Test
	public void testQuery_withType() throws Exception {
		Query query = Query.type("type");

		JSONAssert.assertEquals(
			"{\"type\":\"type\"}", query.bodyAsJson(), true);
		query = Query.count();
		JSONAssert.assertEquals(
			"{\"type\":\"count\"}", query.bodyAsJson(), true);
		query = Query.fetch();
		JSONAssert.assertEquals(
			"{\"type\":\"fetch\"}", query.bodyAsJson(), true);
	}

	@Test
	public void testToString() {
		Query query = Query
			.filter("field", "value")
			.search("query")
			.aggregate("name", "field", "min")
			.highlight("field")
			.offset(1)
			.limit(10);

		Assert.assertEquals(query.bodyAsJson(), query.toString());
	}

}