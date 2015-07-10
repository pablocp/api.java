package com.liferay.launchpad.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;
public class QueryTest {

	@Test
	public void testQuery_empty() throws Exception {
		JSONAssert.assertEquals("{}", Query.builder().toString(), true);
	}

	@Test
	public void testQuery_withAnd() throws Exception {
		List<String> bodies = new ArrayList();
		bodies.add(Query.builder()
			.filter(Filter.of("field", 1).and("field", 1))
			.toString());
		bodies.add(Query.builder()
			.filter(Filter.of("field", 1).and("field", "=", 1))
			.toString());
		bodies.add(Query.builder()
			.filter(Filter.of("field", 1).and(Filter.of("field", 1)))
			.toString());
		bodies.add(Query.builder()
			.filter(Filter.andOf(
				Filter.of("field", 1), Filter.of("field", 1)))
			.toString());

		for (String body : bodies) {
			JSONAssert.assertEquals(getCompositeFilter("and", 2), body, true);
		}

		String body = Query.builder()
			.filter(
				Filter.of("field", 1)
					.and("field", 1)
					.and("field", 1)
					.and("field", "=", 1)
					.and(Filter.of("field", 1)))
			.toString();

		JSONAssert.assertEquals(getCompositeFilter("and", 5), body, true);
	}

	@Test
	public void testQuery_withCommonFilter() throws Exception {
		JSONAssert.assertEquals(
			"{\"filter\":[{\"*\":" +
				"{\"operator\":\"common\",\"value\":{\"query\":\"str\"}}}]}",
			Query.builder().filter(
				SearchFilter.common("str")).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"*\":" +
				"{\"operator\":\"common\"," +
				"\"value\":{\"query\":\"str\",\"threshold\":0.5}}}]}",
			Query.builder().filter(
				SearchFilter.common("str", 0.5)).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":" +
				"{\"operator\":\"common\",\"value\":{\"query\":\"str\"}}}]}",
			Query.builder().filter(
				SearchFilter.common("f", "str")).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":" +
				"{\"operator\":\"common\"," +
				"\"value\":{\"query\":\"str\",\"threshold\":0.5}}}]}",
			Query.builder().filter(
				SearchFilter.common("f", "str", 0.5)).toString(), true);
	}

	@Test
	public void testQuery_withDisMax() throws Exception {
		List<String> bodies = new ArrayList();
		bodies.add(Query.builder()
			.filter(Filter.of("field", 1).disMax("field", 1))
			.toString());
		bodies.add(Query.builder()
			.filter(Filter.of("field", 1).disMax("field", "=", 1))
			.toString());
		bodies.add(Query.builder()
			.filter(Filter.of("field", 1).disMax(Filter.of("field", 1)))
			.toString());
		bodies.add(Query.builder()
			.filter(SearchFilter.disMaxOf(
				Filter.of("field", 1), Filter.of("field", 1)))
			.toString());

		for (String body : bodies) {
			JSONAssert.assertEquals(
				getCompositeFilter("dis_max", 2), body, true);
		}

		String body = Query.builder()
			.filter(
				Filter.of("field", 1)
					.disMax("field", 1)
					.disMax("field", 1)
					.disMax("field", "=", 1)
					.disMax(Filter.of("field", 1)))
			.toString();

		JSONAssert.assertEquals(getCompositeFilter("dis_max", 5), body, true);
	}

	@Test
	public void testQuery_withFilters() throws Exception {
		String body = Query.builder()
				.filter("field", 1)
				.toString();

		JSONAssert.assertEquals(
			"{\"filter\":[" +
				"{\"field\":{\"operator\":\"=\",\"value\":1}}" +
			"]}", body, true);

		body = Query.builder()
				.filter("field1", 1)
				.filter("field2", "like", "value")
				.filter(Filter.of("field3", 0.55))
				.filter(Filter.of("field4", "pre", "str"))
				.filter(Filter.notOf("field5", 1).not("field6", 1))
				.filter(Filter.notOf("field7", "!=", 1))
				.filter(Filter.notOf(Filter.of("field8", 1)))
				.toString();

		JSONAssert.assertEquals(
			"{\"filter\":[" +
				"{\"field1\":{\"operator\":\"=\",\"value\":1}}," +
				"{\"field2\":{\"operator\":\"like\",\"value\":\"value\"}}," +
				"{\"field3\":{\"operator\":\"=\",\"value\":0.55}}," +
				"{\"field4\":{\"operator\":\"pre\",\"value\":\"str\"}}," +
				"{\"not\":[" +
					"{\"field5\":{\"operator\":\"=\",\"value\":1}}," +
					"{\"field6\":{\"operator\":\"=\",\"value\":1}}]}," +
				"{\"not\":[{\"field7\":{\"operator\":\"!=\",\"value\":1}}]}," +
				"{\"not\":[{\"field8\":{\"operator\":\"=\",\"value\":1}}]}" +
			"]}", body, true);
	}

	@Test
	public void testQuery_withFuzzyFilter() throws Exception {
		JSONAssert.assertEquals(
			"{\"filter\":[{\"*\":" +
				"{\"operator\":\"fuzzy\",\"value\":{\"query\":\"str\"}}}]}",
			Query.builder().filter(SearchFilter.fuzzy("str")).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"*\":" +
				"{\"operator\":\"fuzzy\"," +
				"\"value\":{\"query\":\"str\",\"fuzziness\":0.5}}}]}",
			Query.builder().filter(SearchFilter.fuzzy("str", 0.5)).toString(),
			true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":" +
				"{\"operator\":\"fuzzy\",\"value\":{\"query\":\"str\"}}}]}",
			Query.builder().filter(SearchFilter.fuzzy("f", "str")).toString(),
			true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":" +
				"{\"operator\":\"fuzzy\"," +
				"\"value\":{\"query\":\"str\",\"fuzziness\":0.5}}}]}",
			Query.builder().filter(
				SearchFilter.fuzzy("f", "str", 0.5)).toString(), true);
	}

	@Test
	public void testQuery_withFuzzyLikeThisFilter() throws Exception {
		JSONAssert.assertEquals(
			"{\"filter\":[{\"*\":" +
					"{\"operator\":\"flt\",\"value\":{\"query\":\"str\"}}}]}",
			Query.builder().filter(
				SearchFilter.fuzzyLikeThis("str")).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"*\":" +
				"{\"operator\":\"flt\"," +
				"\"value\":{\"query\":\"str\",\"fuzziness\":0.5}}}]}",
			Query.builder().filter(
				SearchFilter.fuzzyLikeThis("str", 0.5)).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":" +
				"{\"operator\":\"flt\",\"value\":{\"query\":\"str\"}}}]}",
			Query.builder().filter(
				SearchFilter.fuzzyLikeThis("f", "str")).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":" +
				"{\"operator\":\"flt\"," +
				"\"value\":{\"query\":\"str\",\"fuzziness\":0.5}}}]}",
			Query.builder().filter(
				SearchFilter.fuzzyLikeThis("f", "str", 0.5)).toString(), true);
	}

	@Test
	public void testQuery_withGeo() throws Exception {
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":" +
				"{\"operator\":\"gp\",\"value\":[\"0,0\",\"0,0\"]}}]}",
			Query.builder().filter(
				SearchFilter.bbox("f", "0,0", "0,0")).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":" +
				"{\"operator\":\"gp\",\"value\":[\"0,0\",\"0,0\"]}}]}",
			Query.builder().filter(
				SearchFilter.bbox(
					"f", Geo.bbox("0,0", "0,0"))).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":" +
				"{\"operator\":\"gp\",\"value\":[\"0,0\",[0,1],[0,1]]}}]}",
			Query.builder().filter(
				SearchFilter.polygon(
					"f", "0,0", Arrays.asList(0d, 1d),
					Geo.point(1, 0))).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":" +
				"{\"operator\":\"gd\"," +
				"\"value\":{\"location\":\"0,0\",\"max\":\"10m\"}}}]}",
			Query.builder().filter(
				SearchFilter.distance("f", "0,0", "10m")).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":" +
				"{\"operator\":\"gd\"," +
				"\"value\":{\"location\":\"0,0\",\"max\":\"10m\"}}}]}",
			Query.builder().filter(
				SearchFilter.distance(
					"f", Geo.circle("0,0", "10m"))).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":" +
				"{\"operator\":\"gd\"," +
				"\"value\":{\"location\":\"0,0\"," +
					"\"min\":\"1m\",\"max\":\"10m\"}}}]}",
			Query.builder().filter(
				SearchFilter.distance(
					"f", "0,0", Range.range("1m", "10m"))).toString(), true);

		String body = Query.builder()
			.filter(
				SearchFilter.shape("f", "0,0")
					.shape(Arrays.asList(0, 0))
					.shape(new int[] {0, 0})
					.shape(Geo.point(0, 0))
					.shape(new int[0])
					.shape(Arrays.asList())
					.shape(Geo.bbox("0,0", "0,0"))
					.shape(Geo.line("0,0", "0,0"))
					.shape(Geo.circle("0,0", "1m"))
					.shape(Geo.polygon("0,0", "0,0").hole("0,0", "0,0")))
			.toString();

		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":{\"operator\":\"gs\",\"value\":{" +
				"\"type\":\"geometrycollection\",\"geometries\":[" +
				"\"0,0\",[0,0],[0,0],[0,0],[],[]," +
				"{\"type\":\"envelope\",\"coordinates\":[\"0,0\",\"0,0\"]}," +
				"{\"type\":\"linestring\",\"coordinates\":[\"0,0\",\"0,0\"]}," +
				"{\"type\":\"circle\"," +
					"\"coordinates\":\"0,0\",\"radius\":\"1m\"}," +
				"{\"type\":\"polygon\"," +
					"\"coordinates\":[[\"0,0\",\"0,0\"],[\"0,0\",\"0,0\"]]}," +
				"]}}}]}",
			body, true);
	}

	@Test
	public void testQuery_withLimitAndOffset() throws Exception {
		String body = Query.builder()
				.limit(1)
				.from(2)
				.toString();

		JSONAssert.assertEquals("{\"limit\":1,\"offset\":2}", body, true);
	}

	@Test
	public void testQuery_withMatchFilter() throws Exception {
		JSONAssert.assertEquals(
			"{\"filter\":[{\"*\":{\"operator\":\"match\"," +
				"\"value\":{\"query\":\"str\"}}}]}",
			Query.builder().filter(SearchFilter.match("str")).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":{\"operator\":\"match\"," +
				"\"value\":{\"query\":\"str\"}}}]}",
			Query.builder().filter(SearchFilter.match("f", "str")).toString(),
			true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"*\":{\"operator\":\"match\"," +
				"\"value\":{\"query\":\"str\",\"type\":\"phrase\"}}}]}",
			Query.builder().filter(SearchFilter.phrase("str")).toString(),
			true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":{\"operator\":\"match\"," +
				"\"value\":{\"query\":\"str\",\"type\":\"phrase\"}}}]}",
			Query.builder().filter(SearchFilter.phrase("f", "str")).toString(),
			true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"*\":{\"operator\":\"match\"," +
				"\"value\":{\"query\":\"str\",\"type\":\"phrase_prefix\"}}}]}",
			Query.builder().filter(
				SearchFilter.phrasePrefix("str")).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":{\"operator\":\"match\"," +
				"\"value\":{\"query\":\"str\",\"type\":\"phrase_prefix\"}}}]}",
			Query.builder().filter(
				SearchFilter.phrasePrefix("f", "str")).toString(), true);

		String body = Query.builder()
			.filter(SearchFilter.match("str").type(MatchFilter.MatchType.DEFAULT))
			.toString();

		JSONAssert.assertEquals(
			"{\"filter\":[{\"*\":{\"operator\":\"match\"," +
				"\"value\":{\"query\":\"str\",\"type\":\"default\"}}}]}",
			body, true);
	}

	@Test
	public void testQuery_withMoreLikeThisFilter() throws Exception {
		JSONAssert.assertEquals(
			"{\"filter\":[{\"*\":" +
				"{\"operator\":\"mlt\",\"value\":{\"query\":\"str\"}}}]}",
			Query.builder().filter(
				SearchFilter.moreLikeThis("str")).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":" +
				"{\"operator\":\"mlt\",\"value\":{\"query\":\"str\"}}}]}",
			Query.builder().filter(
				SearchFilter.moreLikeThis("f", "str")).toString(), true);

		String body = Query.builder()
			.filter(
				SearchFilter.moreLikeThis("str")
					.stopWords("w1", "w2")
					.minTf(1)
					.minDf(2)
					.maxDf(3))
				.toString();

		JSONAssert.assertEquals(
			"{\"filter\":[{\"*\":{\"operator\":\"mlt\",\"value\":{" +
				"\"query\":\"str\"," +
				"\"stop_words\":[\"w1\",\"w2\"]," +
				"\"min_tf\":1," +
				"\"min_df\":2," +
				"\"max_df\":3}}}]}",
			body, true);

		body = Query.builder()
			.filter(
				SearchFilter.moreLikeThis("f", "str")
					.stopWords("w1")
					.minTf(1)
					.minDf(2)
					.maxDf(3))
			.toString();

		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":{\"operator\":\"mlt\",\"value\":{" +
				"\"query\":\"str\"," +
				"\"stop_words\":[\"w1\"]," +
				"\"min_tf\":1," +
				"\"min_df\":2," +
				"\"max_df\":3}}}]}",
			body, true);
	}

	@Test
	public void testQuery_withOr() throws Exception {
		List<String> bodies = new ArrayList();
		bodies.add(Query.builder()
			.filter(Filter.of("field", 1).or("field", 1))
			.toString());
		bodies.add(Query.builder()
			.filter(Filter.of("field", 1).or("field", "=", 1))
			.toString());
		bodies.add(Query.builder()
			.filter(Filter.of("field", 1).or(Filter.of("field", 1)))
			.toString());
		bodies.add(Query.builder()
			.filter(Filter.orOf(
				Filter.of("field", 1), Filter.of("field", 1)))
			.toString());

		for (String body : bodies) {
			JSONAssert.assertEquals(getCompositeFilter("or", 2), body, true);
		}

		String body = Query.builder()
			.filter(
				Filter.of("field", 1)
					.or("field", 1)
					.or("field", 1)
					.or("field", "=", 1)
					.or(Filter.of("field", 1)))
			.toString();

		JSONAssert.assertEquals(getCompositeFilter("or", 5), body, true);
	}

	@Test
	public void testQuery_withPrefixFilter() throws Exception {
		JSONAssert.assertEquals(
			"{\"filter\":[{\"*\":{\"operator\":\"pre\",\"value\":\"str\"}}]}",
			Query.builder().filter(SearchFilter.prefix("str")).toString(),
			true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":{\"operator\":\"pre\",\"value\":\"str\"}}]}",
			Query.builder().filter(SearchFilter.prefix("f", "str")).toString(),
			true);
	}

	@Test
	public void testQuery_withSimpleFilters() throws Exception {
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":{\"operator\":\"=\",\"value\":1}}]}",
			Query.builder().filter(Filter.equal("f", 1)).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":{\"operator\":\"!=\",\"value\":1}}]}",
			Query.builder().filter(Filter.notEqual("f", 1)).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":{\"operator\":\">\",\"value\":1}}]}",
			Query.builder().filter(Filter.gt("f", 1)).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":{\"operator\":\">=\",\"value\":1}}]}",
			Query.builder().filter(Filter.gte("f", 1)).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":{\"operator\":\"<\",\"value\":1}}]}",
			Query.builder().filter(Filter.lt("f", 1)).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":{\"operator\":\"<=\",\"value\":1}}]}",
			Query.builder().filter(Filter.lte("f", 1)).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":{\"operator\":\"in\",\"value\":[1,2]}}]}",
			Query.builder().filter(Filter.in("f", 1, 2)).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":{\"operator\":\"nin\",\"value\":[1,2]}}]}",
			Query.builder().filter(Filter.notIn("f", 1, 2)).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":{\"operator\":\"like\",\"value\":\"str\"}}]}",
			Query.builder().filter(Filter.like("f", "str")).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":{\"operator\":\"exists\"}}]}",
			Query.builder().filter(Filter.exists("f")).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":{\"operator\":\"missing\"}}]}",
			Query.builder().filter(SearchFilter.missing("f")).toString(), true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":" +
				"{\"operator\":\"range\",\"value\":{\"from\":1,\"to\":2}}}]}",
			Query.builder().filter(SearchFilter.range("f", 1, 2)).toString(),
			true);
		JSONAssert.assertEquals(
			"{\"filter\":[{\"f\":" +
				"{\"operator\":\"range\",\"value\":{\"to\":1}}}]}",
			Query.builder().filter(SearchFilter.range("f", Range.to(1))).toString(),
			true);
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
		JSONAssert.assertEquals(
			"{\"type\":\"type\"}",
			query.toString(), true);
		query = Query.builder().count();
		JSONAssert.assertEquals(
			"{\"type\":\"count\"}",
			query.toString(), true);
		query = Query.builder().fetch();
		JSONAssert.assertEquals(
			"{\"type\":\"fetch\"}",
			query.toString(), true);
		query = Query.builder().scan();
		JSONAssert.assertEquals(
			"{\"type\":\"scan\"}",
			query.toString(), true);
	}

	private String getCompositeFilter(String operator, int count) {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"filter\":[{\"" + operator + "\":[");

		for (int i = 0; i < count; i++) {
			builder.append("{\"field\":{\"operator\":\"=\",\"value\":1}},");
		}

		builder.setCharAt(builder.length()-1, ']');
		builder.append("}]}");

		return builder.toString();
	}

}