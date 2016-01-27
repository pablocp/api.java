package com.liferay.launchpad.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Query builder.
 */
public interface Query extends Embodied {

	public static Query.Builder aggregate(Aggregation aggregation) {
		return Query.builder().aggregate(aggregation);
	}

	public static Query.Builder aggregate(
		String name, String field, String operator) {

		return aggregate(Aggregation.of(name, field, operator));
	}

	public static Query.Builder builder() {
		return new Builder();
	}

	public static Query.Builder count() {
		return Query.builder().count();
	}

	public static Query.Builder fetch() {
		return Query.builder().fetch();
	}

	public static Query.Builder filter(Filter filter) {
		return Query.builder().filter(filter);
	}

	public static Query.Builder filter(String field, Object value) {
		return filter(Filter.field(field, value));
	}

	public static Query.Builder filter(
		String field, String operator, Object value) {

		return filter(Filter.field(field, operator, value));
	}

	public static Query.Builder highlight(String field) {
		return Query.builder().highlight(field);
	}

	public static Query.Builder limit(int limit) {
		return Query.builder().limit(limit);
	}

	public static Query.Builder offset(int offset) {
		return Query.builder().offset(offset);
	}

	public static Query.Builder search(Filter filter) {
		return Query.builder().search(filter);
	}

	public static Query.Builder search(String text) {
		return search(Filter.match(text));
	}

	public static Query.Builder search(String field, String text) {
		return search(Filter.match(field, text));
	}

	public static Query.Builder search(
		String field, String operator, Object value) {

		return search(Filter.field(field, operator, value));
	}

	public static Query.Builder sort(String field) {
		return Query.builder().sort(field);
	}

	public static Query.Builder sort(String field, String direction) {
		return Query.builder().sort(field, direction);
	}

	public static Query.Builder type(String type) {
		return Query.builder().type(type);
	}

	public static final class Builder implements Query {

		public Builder aggregate(Aggregation aggregation) {
			aggregations.add(aggregation);
			return this;
		}

		public Builder aggregate(String name, String field, String operator) {
			return aggregate(Aggregation.of(name, field, operator));
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

		public Builder count() {
			return type("count");
		}

		public Builder fetch() {
			return type("fetch");
		}

		public Builder filter(Filter filter) {
			filters.add(filter);
			return this;
		}

		public Builder filter(String field, Object value) {
			return filter(Filter.field(field, value));
		}

		public Builder filter(String field, String operator, Object value) {
			return filter(Filter.field(field, operator, value));
		}

		public Builder highlight(String field) {
			highlights.add(field);
			return this;
		}

		public Builder limit(int limit) {
			this.limit = limit;
			return this;
		}

		public Builder offset(int offset) {
			this.offset = offset;
			return this;
		}

		public Builder search(Filter filter) {
			queries.add(filter);
			return this;
		}

		public Builder search(String text) {
			return search(Filter.match(text));
		}

		public Builder search(String field, String text) {
			return search(Filter.match(field, text));
		}

		public Builder search(String field, String operator, Object value) {
			return search(Filter.field(field, operator, value));
		}

		public Builder sort(String field) {
			return sort(field, "asc");
		}

		public Builder sort(String field, String direction) {
			sort.add(Util.wrap(field, direction));
			return this;
		}

		@Override
		public String toString() {
			return bodyAsJson();
		}

		public Builder type(String type) {
			this.type = type;
			return this;
		}

		private Builder() {
		}

		private final List<Aggregation> aggregations = new ArrayList();
		private final List<Filter> filters = new ArrayList();
		private final List<String> highlights = new ArrayList<>();
		private Integer limit;
		private Integer offset;
		private final List<Filter> queries = new ArrayList();
		private final List<Map> sort = new ArrayList();
		private String type;

	}

}