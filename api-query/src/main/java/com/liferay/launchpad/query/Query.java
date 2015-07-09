package com.liferay.launchpad.query;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Collection;
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

	public Query from(int offset) {
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

	@Override
	public String toString() {
		return toString(this);
	}

	private String toString(Collection value) {
		if (value.size() == 0) {
			return "[]";
		}

		StringBuilder builder = new StringBuilder();
		builder.append('[');

		for (Object item : value) {
			builder.append(toString(item)).append(',');
		}

		builder.setCharAt(builder.length() - 1, ']');

		return builder.toString();
	}

	private String toString(Map<String, Object> value) {
		if (value.size() == 0) {
			return "{}";
		}

		StringBuilder builder = new StringBuilder();
		builder.append('{');

		for (Map.Entry<String, Object> entry : value.entrySet()) {
			builder.append(toString(entry.getKey())).append(':');
			builder.append(toString(entry.getValue())).append(",");
		}

		builder.setCharAt(builder.length() - 1, '}');

		return builder.toString();
	}

	private String toString(Object value) {
		if (value instanceof Embodied) {
			return toString(((Embodied)value).body());
		}

		if (value instanceof Collection) {
			return toString((Collection)value);
		}

		if (value instanceof Map) {
			return toString((Map)value);
		}

		if (value instanceof String) {
			return "\"" + value + "\"";
		}

		if (value.getClass().isArray()) {
			int length = Array.getLength(value);

			if (length == 0) {
				return "[]";
			}

			StringBuilder builder = new StringBuilder();
			builder.append('[');

			for (int i = 0; i < length; i++) {
				builder.append(toString(Array.get(value, i))).append(',');
			}

			builder.setCharAt(builder.length()-1, ']');

			return builder.toString();
		}

		return value.toString();
	}

	private final List<Map> sort = new ArrayList();
	private final List<Filter> filters = new ArrayList();
	private Search search;
	private Integer limit;
	private Integer offset;

}