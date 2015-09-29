package com.liferay.launchpad.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Query builder.
 */
public final class Query implements Embodied {

	public static Query builder() {
		return new Query();
	}

	@Override
	public Map body() {
		Map<String, Object> map = new HashMap();

		if (type != null) {
			map.put("type", type);
		}

		if (!filters.isEmpty()) {
			map.put("filter", filters);
		}

		if (!sort.isEmpty()) {
			map.put("sort", sort);
		}

		if (limit != null) {
			map.put("limit", limit);
		}

		if (offset != null) {
			map.put("offset", offset);
		}

		if (!queries.isEmpty()) {
			map.put("search", queries);
		}

		if (!highlights.isEmpty()) {
			map.put("highlight", highlights);
		}

		if (!aggregations.isEmpty()) {
			map.put("aggregation", aggregations);
		}

		return map;
	}

	public Query search(Filter filter) {
		queries.add(filter);
		return this;
	}

	public Query search(String text) {
		return search(Filter.match(text));
	}

	public Query search(String field, String text) {
		return search(Filter.match(field, text));
	}

	public Query search(String field, String operator, Object value) {
		return search(Filter.field(field, operator, value));
	}

	public Query highlight(String field) {
		highlights.add(field);
		return this;
	}

	public Query aggregate(String name, String field, String operator) {
		return aggregate(Aggregation.of(name, field, operator));
	}

	public Query aggregate(Aggregation aggregation) {
		aggregations.add(aggregation);
		return this;
	}

	public Query filter(String field, Object value) {
		return filter(Filter.field(field, value));
	}

	public Query filter(String field, String operator, Object value) {
		return filter(Filter.field(field, operator, value));
	}

	public Query sort(String field) {
		return sort(field, "asc");
	}

	public Query sort(String field, String direction) {
		sort.add(Util.wrap(field, direction));
		return this;
	}

	public Query limit(int limit) {
		this.limit = limit;
		return this;
	}

	public Query offset(int offset) {
		this.offset = offset;
		return this;
	}

	private Query() { }

	public Query filter(Filter filter) {
		filters.add(filter);
		return this;
	}

	public Query type(String type) {
		this.type = type;
		return this;
	}

	public Query count() {
		return type("count");
	}

	public Query fetch() { return type("fetch"); }

	@Override
	public String toString() {
		return bodyAsJson();
	}

	private final List<Map> sort = new ArrayList();
	private final List<Filter> filters = new ArrayList();
	private Integer limit;
	private Integer offset;
	private String type;

	private final List<Filter> queries = new ArrayList();
	private final List<Aggregation> aggregations = new ArrayList();
	private final List<String> highlights = new ArrayList<>();

}