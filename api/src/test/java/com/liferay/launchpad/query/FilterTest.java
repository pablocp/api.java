package com.liferay.launchpad.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;
public class FilterTest {

	@Test
	public void testFilter_combiningDifferentFilters() throws Exception {
		Filter filter = Filter.of("a", 1)
			.and("a", 1)
			.and("a", 1)
			.or("a", 1)
			.or("a", 1)
			.disMax("a", 1)
			.disMax("a", 1);

		JSONAssert.assertEquals(
			"{\"disMax\":[" +
				"{\"or\":[" +
					"{\"and\":[" +
						"{\"a\":{\"operator\":\"=\",\"value\":1}}," +
						"{\"a\":{\"operator\":\"=\",\"value\":1}}," +
						"{\"a\":{\"operator\":\"=\",\"value\":1}}]}," +
					"{\"a\":{\"operator\":\"=\",\"value\":1}}," +
					"{\"a\":{\"operator\":\"=\",\"value\":1}}]}," +
				"{\"a\":{\"operator\":\"=\",\"value\":1}}," +
				"{\"a\":{\"operator\":\"=\",\"value\":1}}]}",
			filter.bodyAsJson(), true);
	}

	@Test
	public void testFilter_withAnd() throws Exception {
		List<String> bodies = new ArrayList();
		bodies.add(Filter.of("field", 1).and("field", 1).bodyAsJson());
		bodies.add(Filter.of("field", 1).and("field", "=", 1).bodyAsJson());
		bodies.add(
			Filter.of("field", 1).and(Filter.of("field", 1)).bodyAsJson());
		bodies.add(
			Filter.and(
				Filter.of("field", 1), Filter.of("field", 1)).bodyAsJson());

		for (String body : bodies) {
			JSONAssert.assertEquals(getCompositeFilter("and", 2), body, true);
		}

		String body = Filter.of("field", 1)
			.and("field", 1)
			.and("field", 1)
			.and("field", "=", 1)
			.and(Filter.of("field", 1))
			.bodyAsJson();

		JSONAssert.assertEquals(getCompositeFilter("and", 5), body, true);
	}

	@Test
	public void testFilter_withCommonFilter() throws Exception {
		JSONAssert.assertEquals(
			"{\"*\":{\"operator\":\"common\",\"value\":{\"query\":\"str\"}}}",
			Filter.common("str").bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"*\":{\"operator\":\"common\"," +
				"\"value\":{\"query\":\"str\",\"threshold\":0.5}}}",
			Filter.common("str", 0.5).bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"common\",\"value\":{\"query\":\"str\"}}}",
			Filter.common("f", "str").bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"common\"," +
				"\"value\":{\"query\":\"str\",\"threshold\":0.5}}}",
			Filter.common("f", "str", 0.5).bodyAsJson(), true);
	}

	@Test
	public void testFilter_withFuzzyFilter() throws Exception {
		JSONAssert.assertEquals(
			"{\"*\":{\"operator\":\"fuzzy\",\"value\":{\"query\":\"str\"}}}",
			Filter.fuzzy("str").bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"*\":{\"operator\":\"fuzzy\"," +
				"\"value\":{\"query\":\"str\",\"fuzziness\":0.5}}}",
			Filter.fuzzy("str", 0.5).bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"fuzzy\",\"value\":{\"query\":\"str\"}}}",
			Filter.fuzzy("f", "str").bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"fuzzy\"," +
				"\"value\":{\"query\":\"str\",\"fuzziness\":0.5}}}",
			Filter.fuzzy("f", "str", 0.5).bodyAsJson(), true);
	}

	@Test
	public void testFilter_withGeo() throws Exception {
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"gp\",\"value\":[\"0,0\",\"0,0\"]}}",
			Filter.bbox("f", "0,0", "0,0").bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"gp\",\"value\":[\"0,0\",\"0,0\"]}}",
			Filter.bbox("f", Geo.bbox("0,0", "0,0")).bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"gp\",\"value\":[\"0,0\",[0,1],[0,1]]}}",
			Filter.polygon(
				"f", "0,0", Arrays.asList(0d, 1d),
				Geo.point(1, 0)).bodyAsJson(),
			true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"gd\"," +
				"\"value\":{\"location\":\"0,0\",\"max\":\"10m\"}}}",
			Filter.distance("f", "0,0", "10m").bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"gd\"," +
				"\"value\":{\"location\":\"0,0\",\"max\":\"10m\"}}}",
			Filter.distance("f", Geo.circle("0,0", "10m")).bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"gd\",\"value\":" +
				"{\"location\":\"0,0\",\"min\":\"1m\",\"max\":\"10m\"}}}",
			Filter.distance(
				"f", "0,0", Range.range("1m", "10m")).bodyAsJson(), true);

		String body = Filter.shape("f", "0,0")
			.shape(Arrays.asList(0, 0))
			.shape(new int[] {0, 0})
			.shape(Geo.point(0, 0))
			.shape(new int[0])
			.shape(Arrays.asList())
			.shape(Geo.bbox("0,0", "0,0"))
			.shape(Geo.line("0,0", "0,0"))
			.shape(Geo.circle("0,0", "1m"))
			.shape(Geo.polygon("0,0", "0,0").hole("0,0", "0,0"))
			.bodyAsJson();

		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"gs\",\"value\":{" +
				"\"type\":\"geometrycollection\",\"geometries\":[" +
				"\"0,0\",[0,0],[0,0],[0,0],[],[]," +
				"{\"type\":\"envelope\",\"coordinates\":[\"0,0\",\"0,0\"]}," +
				"{\"type\":\"linestring\",\"coordinates\":[\"0,0\",\"0,0\"]}," +
				"{\"type\":\"circle\"," +
					"\"coordinates\":\"0,0\",\"radius\":\"1m\"}," +
				"{\"type\":\"polygon\"," +
					"\"coordinates\":[[\"0,0\",\"0,0\"],[\"0,0\",\"0,0\"]]}," +
				"]}}}",
			body, true);
	}

	@Test
	public void testFilter_withMatchFilter() throws Exception {
		JSONAssert.assertEquals(
			"{\"*\":{\"operator\":\"match\",\"value\":\"str\"}}",
			Filter.match("str").bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"match\",\"value\":\"str\"}}",
			Filter.match("f", "str").bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"*\":{\"operator\":\"phrase\",\"value\":\"str\"}}",
			Filter.phrase("str").bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"phrase\",\"value\":\"str\"}}",
			Filter.phrase("f", "str").bodyAsJson(), true);
	}

	@Test
	public void testFilter_withMoreLikeThisFilter() throws Exception {
		JSONAssert.assertEquals(
			"{\"*\":{\"operator\":\"mlt\",\"value\":{\"query\":\"str\"}}}",
			Filter.moreLikeThis("str").bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"mlt\",\"value\":{\"query\":\"str\"}}}",
			Filter.moreLikeThis("f", "str").bodyAsJson(), true);

		String body = Filter.moreLikeThis("str")
			.stopWords("w1", "w2")
			.minTf(1)
			.minDf(2)
			.maxDf(3)
			.bodyAsJson();

		JSONAssert.assertEquals(
			"{\"*\":{\"operator\":\"mlt\",\"value\":{" +
				"\"query\":\"str\"," +
				"\"stopWords\":[\"w1\",\"w2\"]," +
				"\"minTf\":1," +
				"\"minDf\":2," +
				"\"maxDf\":3}}}",
			body, true);

		body = Filter.moreLikeThis("f", "str")
			.stopWords("w1")
			.minTf(1)
			.minDf(2)
			.maxDf(3)
			.bodyAsJson();

		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"mlt\",\"value\":{" +
				"\"query\":\"str\"," +
				"\"stopWords\":[\"w1\"]," +
				"\"minTf\":1," +
				"\"minDf\":2," +
				"\"maxDf\":3}}}",
			body, true);
	}

	@Test
	public void testFilter_withPrefixFilter() throws Exception {
		JSONAssert.assertEquals(
			"{\"*\":{\"operator\":\"pre\",\"value\":\"str\"}}",
			Filter.prefix("str").bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"pre\",\"value\":\"str\"}}",
			Filter.prefix("f", "str").bodyAsJson(), true);
	}

	@Test
	public void testFilter_withSimpleFilters() throws Exception {
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"=\",\"value\":1}}",
			Filter.equal("f", 1).bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"!=\",\"value\":1}}",
			Filter.notEqual("f", 1).bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\">\",\"value\":1}}",
			Filter.gt("f", 1).bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\">=\",\"value\":1}}",
			Filter.gte("f", 1).bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"<\",\"value\":1}}",
			Filter.lt("f", 1).bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"<=\",\"value\":1}}",
			Filter.lte("f", 1).bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"any\",\"value\":[1,2]}}",
			Filter.any("f", 1, 2).bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"any\",\"value\":[1,2]}}",
			Filter.any("f", Arrays.asList(1, 2)).bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"none\",\"value\":[1,2]}}",
			Filter.none("f", 1, 2).bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"none\",\"value\":[1,2]}}",
			Filter.none("f", Arrays.asList(1, 2)).bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"~\",\"value\":\"str\"}}",
			Filter.regex("f", "str").bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"exists\"}}",
			Filter.exists("f").bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"missing\"}}",
			Filter.missing("f").bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"range\",\"value\":{\"from\":1,\"to\":2}}}",
			Filter.range("f", 1, 2).bodyAsJson(), true);
		JSONAssert.assertEquals(
			"{\"f\":{\"operator\":\"range\",\"value\":{\"to\":1}}}",
			Filter.range("f", Range.to(1)).bodyAsJson(), true);
	}

	@Test
	public void testToString() {
		Filter filter = Filter.of("field", "=", "value");
		Assert.assertEquals(
			Query.builder().filter(filter).toString(), filter.toString());
	}

	private String getCompositeFilter(String operator, int count) {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"" + operator + "\":[");

		for (int i = 0; i < count; i++) {
			builder.append("{\"field\":{\"operator\":\"=\",\"value\":1}},");
		}

		builder.setCharAt(builder.length()-1, ']');
		builder.append("}");

		return builder.toString();
	}

}