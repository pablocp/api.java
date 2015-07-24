package com.liferay.launchpad.query;

import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;
public class QueryTest {

	@Test
	public void testQuery_empty() throws Exception {
		JSONAssert.assertEquals("{}", Query.builder().toString(), true);
	}

	@Test
	public void testQuery_withFilters() throws Exception {
		String body = Query.builder()
				.filter("field", 1)
				.toString();

		JSONAssert.assertEquals(
			"{\"filter\":[{\"field\":{\"operator\":\"=\",\"value\":1}}]}",
			body, true);

		body = Query.builder()
				.filter("field1", 1)
				.filter("field2", "regex", "value")
				.filter(Filter.of("field3", 0.55))
				.filter(Filter.of("field4", "pre", "str"))
				.filter(Filter.not("field5", 1))
				.filter(Filter.not("field7", "!=", 1))
				.filter(Filter.not(Filter.of("field8", 1)))
				.toString();

		JSONAssert.assertEquals(
			"{\"filter\":[" +
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
	public void testQuery_withLimitAndOffset() throws Exception {
		String body = Query.builder()
				.limit(1)
				.offset(2)
				.toString();

		JSONAssert.assertEquals("{\"limit\":1,\"offset\":2}", body, true);
	}

	@Test
	public void testQuery_withSearch() throws Exception {
		Query query = Query.builder().search(Search.builder());
		JSONAssert.assertEquals("{\"search\":{}}", query.toString(), true);
		query = Query.builder().search(Filter.equal("field", "value"));
		JSONAssert.assertEquals(
			"{\"search\":{\"query\":[{" +
				"\"field\":{\"operator\":\"=\",\"value\":\"value\"}}]}}",
			query.toString(), true);
		query = Query.builder().search("query");
		JSONAssert.assertEquals(
			"{\"search\":{\"query\":[{" +
				"\"*\":{\"operator\":\"match\",\"value\":\"query\"}}]}}",
			query.toString(), true);
		query = Query.builder().search("field", "query");
		JSONAssert.assertEquals(
			"{\"search\":{\"query\":[{" +
				"\"field\":{\"operator\":\"match\",\"value\":\"query\"}}]}}",
			query.toString(), true);
		query = Query.builder().search("field", "=", "query");
		JSONAssert.assertEquals(
			"{\"search\":{\"query\":[{" +
				"\"field\":{\"operator\":\"=\",\"value\":\"query\"}}]}}",
			query.toString(), true);
	}

	@Test
	public void testQuery_withSort() throws Exception {
		String body = Query.builder()
				.sort("field")
				.toString();

		JSONAssert.assertEquals("{\"sort\":[{\"field\":\"asc\"}]}", body, true);

		body = Query.builder()
				.sort("field1")
				.sort("field2", "asc")
				.sort("field3", "desc")
				.toString();

		JSONAssert.assertEquals(
			"{\"sort\":[" +
				"{\"field1\":\"asc\"}," +
				"{\"field2\":\"asc\"}," +
				"{\"field3\":\"desc\"}" +
			"]}", body, true);
	}

	@Test
	public void testQuery_withType() throws Exception {
		Query query = Query.builder().type("type");
		JSONAssert.assertEquals("{\"type\":\"type\"}", query.toString(), true);
		query = Query.builder().count();
		JSONAssert.assertEquals("{\"type\":\"count\"}", query.toString(), true);
		query = Query.builder().fetch();
		JSONAssert.assertEquals("{\"type\":\"fetch\"}", query.toString(), true);
		query = Query.builder().scan();
		JSONAssert.assertEquals("{\"type\":\"scan\"}", query.toString(), true);
	}

}