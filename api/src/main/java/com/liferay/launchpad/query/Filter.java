package com.liferay.launchpad.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Filter builder.
 */
public class Filter implements Embodied {

	public static final String ALL = "*";

	public static Filter and(Filter... filters) {
		return composite("and", filters);
	}

	public static Filter any(String field, Iterable values) {
		return of(field, "any", values);
	}

	public static Filter any(String field, Object... values) {
		return of(field, "any", Arrays.asList(values));
	}

	public static Filter bbox(String field, Geo.BoundingBox box) {
		return Filter.of(field, "gp", box.getPoints());
	}

	public static Filter bbox(
		String field, Object upperLeft, Object lowerRight) {

		return bbox(field, Geo.bbox(upperLeft, lowerRight));
	}

	public static CommonTermsFilter common(String query) {
		return common(ALL, query);
	}

	public static CommonTermsFilter common(String query, double threshold) {
		return common(ALL, query, threshold);
	}

	public static CommonTermsFilter common(String field, String query) {
		return new CommonTermsFilter(field, query);
	}

	public static CommonTermsFilter common(
		String field, String query, double threshold) {

		return new CommonTermsFilter(field, query, threshold);
	}

	public static Filter composite(String operator, Filter... filters) {
		Filter compositeFilter = new Filter(operator, new ArrayList<>());

		if (filters != null) {
			for (Filter filter : filters) {
				compositeFilter.addToComposite(filter);
			}
		}

		return compositeFilter;
	}

	public static Filter distance(String field, Geo.Circle circle) {
		return distance(
			field, circle.getCenter(), Range.to(circle.getRadius()));
	}

	public static Filter distance(String field, Object location, Range range) {
		Map map = new HashMap();
		map.put("location", location);

		if (range.from != null) {
			map.put("min", range.from);
		}

		if (range.to != null) {
			map.put("max", range.to);
		}

		return Filter.of(field, "gd", map);
	}

	public static Filter distance(
		String field, Object location, String distance) {

		return distance(field, location, Range.to(distance));
	}

	public static Filter equal(String field, Object value) {
		return of(field, "=", value);
	}

	public static Filter exists(String field) {
		return Filter.of(field, "exists", null);
	}

	public static FuzzyFilter fuzzy(String query) {
		return fuzzy(ALL, query);
	}

	public static FuzzyFilter fuzzy(String query, Integer fuzziness) {
		return fuzzy(ALL, query, fuzziness);
	}

	public static FuzzyFilter fuzzy(String field, String query) {
		return new FuzzyFilter(field, "fuzzy", query);
	}

	public static FuzzyFilter fuzzy(
		String field, String query, Integer fuzziness) {

		return new FuzzyFilter(field, "fuzzy", query, fuzziness);
	}

	public static Filter gt(String field, Object value) {
		return of(field, ">", value);
	}

	public static Filter gte(String field, Object value) {
		return of(field, ">=", value);
	}

	public static Filter lt(String field, Object value) {
		return of(field, "<", value);
	}

	public static Filter lte(String field, Object value) {
		return of(field, "<=", value);
	}

	public static Filter match(String query) {
		return match(ALL, query);
	}

	public static Filter match(String field, String query) {
		return Filter.of(field, "match", query);
	}

	public static Filter missing(String field) {
		return Filter.of(field, "missing", null);
	}

	public static MoreLikeThisFilter moreLikeThis(String query) {
		return moreLikeThis(ALL, query);
	}

	public static MoreLikeThisFilter moreLikeThis(String field, String query) {
		return new MoreLikeThisFilter(field, query);
	}

	public static Filter none(String field, Iterable values) {
		return of(field, "none", values);
	}

	public static Filter none(String field, Object... values) {
		return of(field, "none", Arrays.asList(values));
	}

	public static Filter not(Filter filter) {
		return composite("not", filter);
	}

	public static Filter not(String field, Object value) {
		return not(Filter.of(field, value));
	}

	public static Filter not(String field, String operator, Object value) {
		return not(Filter.of(field, operator, value));
	}

	public static Filter notEqual(String field, Object value) {
		return of(field, "!=", value);
	}

	public static Filter of(String field, Object value) {
		return of(field, "=", value);
	}

	public static Filter of(String field, String operator, Object value) {
		return new Filter(field, operator, value);
	}

	public static Filter or(Filter... filters) {
		return composite("or", filters);
	}

	public static Filter phrase(String query) {
		return phrase(ALL, query);
	}

	public static Filter phrase(String field, String query) {
		return Filter.of(field, "phrase", query);
	}

	public static Filter polygon(String field, Object...points) {
		return Filter.of(field, "gp", Arrays.asList(points));
	}

	public static Filter prefix(String query) {
		return prefix(ALL, query);
	}

	public static Filter prefix(String field, String query) {
		return Filter.of(field, "pre", query);
	}

	public static Filter range(String field, Object min, Object max) {
		return Filter.of(field, "range", Range.range(min, max));
	}

	public static Filter range(String field, Range range) {
		return Filter.of(field, "range", range);
	}

	public static Filter regex(String field, String value) {
		return of(field, "~", value);
	}

	public static GeoShapeFilter shape(String field, Object...shapes) {
		return new GeoShapeFilter(field, shapes);
	}

	public Filter and(Filter filter) {
		if (isComposite(this.operator)) {
			return this.addToComposite("and", filter);
		}

		return Filter.and(this, filter);
	}

	public Filter and(String field, Object value) {
		return and(Filter.of(field, value));
	}

	public Filter and(String field, String operator, Object value) {
		return and(Filter.of(field, operator, value));
	}

	@Override
	public Object body() {
		if (isComposite(operator)) {
			List valueList = (List)value;

			if (valueList.size() == 1) {
				return Util.wrap(operator, valueList.get(0));
			}

			return Util.wrap(operator, value);
		}

		Map map = new HashMap();
		map.put("operator", operator);

		if (value != null) {
			map.put("value", value);
		}

		return Util.wrap(field, map);
	}

	public Filter or(Filter filter) {
		if (isComposite(this.operator)) {
			return this.addToComposite("or", filter);
		}

		return Filter.or(this, filter);
	}

	public Filter or(String field, Object value) {
		return or(Filter.of(field, value));
	}

	public Filter or(String field, String operator, Object value) {
		return or(Filter.of(field, operator, value));
	}

	@Override
	public String toString() {
		return Util.toString(Query.builder().filter(this));
	}

	protected Filter(String operator, Object value) {
		this.field = null;
		this.operator = operator;
		this.value = value;
	}

	protected Filter(String field, String operator, Object value) {
		if (isComposite(operator)) {
			throw new IllegalArgumentException(
				"\"" + operator + "\" is a composite filter operator. " +
					"Please use Filter.composite(operator, filters) for that.");
		}

		this.field = field;
		this.operator = operator;
		this.value = value;
	}

	protected Filter addToComposite(Filter filter) {
		((List)this.value).add(filter);
		return this;
	}

	protected Filter addToComposite(String newOperator, Filter filter) {
		if (this.operator.equals(newOperator)) {
			return addToComposite(filter);
		}

		return Filter.composite(newOperator, this, filter);
	}

	protected final String field;
	protected final String operator;
	protected final Object value;

	private boolean isComposite(String filter) {
		return (filter != null) && COMPOSITE_FILTERS.contains(filter);
	}

	private static final Set<String> COMPOSITE_FILTERS = new HashSet<>(
		Arrays.asList("and", "or", "not"));

}