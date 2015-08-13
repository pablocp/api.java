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

		if (search != null) {
			map.put("search", search);
		}

		if (limit != null) {
			map.put("limit", limit);
		}

		if (offset != null) {
			map.put("offset", offset);
		}

		return map;
	}

	public Query filter(String field, Object value) {
		return filter(Filter.of(field, value));
	}

	public Query filter(String field, String operator, Object value) {
		return filter(Filter.of(field, operator, value));
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

	public Query search(Search search) {
		this.search = search;
		return this;
	}

	public Query search(Filter filter) {
		return search(Search.builder().query(filter));
	}

	public Query search(String text) {
		return search(Search.builder().query(text));
	}

	public Query search(String field, String text) {
		return search(Search.builder().query(field, text));
	}

	public Query search(String field, String operator, Object value) {
		return search(Search.builder().query(field, operator, value));
	}

	public Query type(String type) {
		this.type = type;
		return this;
	}

	public Query count() {
		return type("count");
	}

	public Query fetch() {
		return type("fetch");
	}

	public Query scan() {
		return type("scan");
	}

	@Override
	public String toString() {
		return bodyAsJson();
	}

	private final List<Map> sort = new ArrayList();
	private final List<Filter> filters = new ArrayList();
	private Search search;
	private Integer limit;
	private Integer offset;
	private String type;

}