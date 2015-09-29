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
		return new Builder().aggregate(aggregation);
	}

	public static Query.Builder aggregate(
		String name, String field, String operator) {

		return aggregate(Aggregation.of(name, field, operator));
	}

	public static Query.Builder count() {
		return new Builder().count();
	}

	public static Query.Builder fetch() {
		return new Builder().fetch();
	}

	public static Query.Builder filter(Filter filter) {
		return new Builder().filter(filter);
	}

	public static Query.Builder filter(String field, Object value) {
		return filter(Filter.field(field, value));
	}

	public static Query.Builder filter(
		String field, String operator, Object value) {

		return filter(Filter.field(field, operator, value));
	}

	public static Query.Builder highlight(String field) {
		return new Builder().highlight(field);
	}

	public static Query.Builder limit(int limit) {
		return new Builder().limit(limit);
	}

	public static Query.Builder offset(int offset) {
		return new Builder().offset(offset);
	}

	public static Query.Builder search(Filter filter) {
		return new Builder().search(filter);
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
		return new Builder().sort(field);
	}

	public static Query.Builder sort(String field, String direction) {
		return new Builder().sort(field, direction);
	}

	public static Query.Builder type(String type) {
		return new Builder().type(type);
	}

	public static final class Builder implements Query {

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

		public Builder highlight(String field) {
			highlights.add(field);
			return this;
		}

		public Builder aggregate(String name, String field, String operator) {
			return aggregate(Aggregation.of(name, field, operator));
		}

		public Builder aggregate(Aggregation aggregation) {
			aggregations.add(aggregation);
			return this;
		}

		public Builder filter(String field, Object value) {
			return filter(Filter.field(field, value));
		}

		public Builder filter(String field, String operator, Object value) {
			return filter(Filter.field(field, operator, value));
		}

		public Builder sort(String field) {
			return sort(field, "asc");
		}

		public Builder sort(String field, String direction) {
			sort.add(Util.wrap(field, direction));
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

		private Builder() {}

		public Builder filter(Filter filter) {
			filters.add(filter);
			return this;
		}

		public Builder type(String type) {
			this.type = type;
			return this;
		}

		public Builder count() {
			return type("count");
		}

		public Builder fetch() {
			return type("fetch");
		}

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

}