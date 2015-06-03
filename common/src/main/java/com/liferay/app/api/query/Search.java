package com.liferay.app.api.query;

import com.liferay.app.api.Embodied;
import com.liferay.app.api.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Search builder.
 */
public final class Search implements Embodied {

	public Search preFilter(Filter filter) {
		preFilters.add(filter);
		return this;
	}

	public Search preFilter(String text) {
		return preFilter(Filter.match(text));
	}

	public Search preFilter(String field, String text) {
		return preFilter(Filter.match(field, text));
	}

	public Search preFilter(String field, String operator, Object value) {
		return preFilter(Filter.of(field, operator, value));
	}

	public Search postFilter(Filter filter) {
		postFilters.add(filter);
		return this;
	}

	public Search postFilter(String text) {
		return postFilter(Filter.match(text));
	}

	public Search postFilter(String field, String text) {
		return postFilter(Filter.match(field, text));
	}

	public Search postFilter(String field, String operator, Object value) {
		return postFilter(Filter.of(field, operator, value));
	}

	public Search query(Filter filter) {
		queries.add(filter);
		return this;
	}

	public Search query(String text) {
		return query(Filter.match(text));
	}

	public Search query(String field, String text) {
		return query(Filter.match(field, text));
	}

	public Search query(String field, String operator, Object value) {
		return query(Filter.of(field, operator, value));
	}

	public Search highlight(String field) {
		highlights.put(field, new HashMap());
		return this;
	}

	public Search highlight(String field, int size) {
		highlights.put(field, Util.wrap("size", size));
		return this;
	}

	public Search highlight(String field, int size, int count) {
		Map map = Util.wrap("size", size);
		map.put("count", count);
		highlights.put(field, map);
		return this;
	}

	public Search cursor(String cursor) {
		this.cursor = cursor;
		return this;
	}

	public Search type(SearchType type) {
		this.type = type;
		return this;
	}

	public static Search builder() {
		return new Search();
	}

	public Search aggregate(String name, String field, String operator) {
		return aggregate(name, Aggregation.of(field, operator));
	}

	public Search aggregate(String name, Aggregation aggregation) {
		Map map = new HashMap();
		map.put("name", name);
		map.put("operator", aggregation.getOperator());

		if (aggregation.getValue() != null) {
			map.put("value", aggregation.getValue());
		}

		aggregations.add(Util.wrap(aggregation.getField(), map));
		return this;
	}

	private Search() { }

	@Override
	public Map body() {
		Map<String, Object> map = new HashMap();

		map.put("type", type.name().toLowerCase());

		if (!preFilters.isEmpty()) {
			map.put("pre_filter", preFilters);
		}

		if (!postFilters.isEmpty()) {
			map.put("post_filter", postFilters);
		}

		if (!queries.isEmpty()) {
			map.put("query", queries);
		}

		if (!highlights.isEmpty()) {
			map.put("highlight", highlights);
		}

		if (cursor != null) {
			map.put("cursor", cursor);
		}

		if (!aggregations.isEmpty()) {
			map.put("aggregation", aggregations);
		}

		return map;
	}

	private final List<Filter> preFilters = new ArrayList();
	private final List<Filter> postFilters = new ArrayList();
	private final List<Filter> queries = new ArrayList();
	private final List<Map> aggregations = new ArrayList();
	private final Map highlights = new HashMap();
	private SearchType type = SearchType.FETCH;
	private String cursor;

	public enum SearchType {
		COUNT, SCAN, FETCH
	}

}