package com.liferay.launchpad.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Aggregation builder.
 */
public class Aggregation {

	public static Aggregation avg(String field) {
		return of(field, "avg");
	}

	public static Aggregation count(String field) {
		return of(field, "count");
	}

	public static DistanceAggregation distance(
		String field, Object location, Range...ranges) {

		return new DistanceAggregation(field, location, ranges);
	}

	public static Aggregation extendedStats(String field) {
		return of(field, "extended_stats");
	}

	public static Aggregation histogram(String field, int interval) {
		return new Aggregation(field, "histogram", interval);
	}

	public static Aggregation max(String field) {
		return of(field, "max");
	}

	public static Aggregation min(String field) {
		return of(field, "min");
	}

	public static Aggregation missing(String field) {
		return of(field, "missing");
	}

	public static Aggregation of(String field, String operator) {
		return new Aggregation(field, operator);
	}

	public static RangeAggregation range(String field, Range...ranges) {
		return new RangeAggregation(field, Arrays.asList(ranges));
	}

	public static Aggregation stats(String field) {
		return of(field, "stats");
	}

	public static Aggregation sum(String field) {
		return of(field, "sum");
	}

	public static Aggregation terms(String field) {
		return of(field, "terms");
	}

	public static final class DistanceAggregation extends Aggregation {

		public DistanceAggregation range(Object from, Object to) {
			return range(Range.range(from, to));
		}

		public DistanceAggregation range(Range range) {
			this.ranges.add(range);
			return this;
		}

		public DistanceAggregation unit(String unit) {
			((Map)value).put("unit", unit);
			return this;
		}

		private DistanceAggregation(
			String field, Object location, Range...ranges) {

			super(field, "geo_distance", new HashMap());

			Map map = (Map)value;
			this.ranges = new ArrayList();
			this.ranges.addAll(Arrays.asList(ranges));

			map.put("location", location);
			map.put("ranges", this.ranges);
		}

		private final List<Object> ranges;

	}

	public static final class RangeAggregation extends Aggregation {

		public RangeAggregation range(Object from, Object to) {
			return range(Range.range(from, to));
		}

		public RangeAggregation range(Range range) {
			((List)this.value).add(range);
			return this;
		}

		private RangeAggregation(
			String field, Object location, Range...ranges) {

			super(field, "range", new ArrayList());

			((List)this.value).addAll(Arrays.asList(ranges));
		}

	}

	protected String getField() {
		return field;
	}

	protected String getOperator() {
		return operator;
	}

	protected Object getValue() {
		return value;
	}

	protected final Object value;

	private Aggregation(String field, String operator) {
		this(field, operator, null);
	}

	private Aggregation(String field, String operator, Object value) {
		this.field = field;
		this.operator = operator;
		this.value = value;
	}

	private final String field;
	private final String operator;

}