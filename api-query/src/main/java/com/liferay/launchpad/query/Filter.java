package com.liferay.launchpad.query;

import java.util.Arrays;

/**
 * Filter builder.
 */
public interface Filter extends Embodied {

	public static CompositeFilter and(Filter... filters) {
		return composite("and", filters);
	}

	public static SimpleFilter any(String field, Iterable values) {
		return of(field, "any", values);
	}

	public static SimpleFilter any(String field, Object... values) {
		return of(field, "any", Arrays.asList(values));
	}

	public static CompositeFilter composite(
		String operator, Filter... filters) {

		CompositeFilter compositeFilter = new CompositeFilter(operator);

		for (Filter filter : filters) {
			compositeFilter.add(filter);
		}

		return compositeFilter;
	}

	public static SimpleFilter equal(String field, Object value) {
		return of(field, "=", value);
	}

	public static SimpleFilter gt(String field, Object value) {
		return of(field, ">", value);
	}

	public static SimpleFilter gte(String field, Object value) {
		return of(field, ">=", value);
	}

	public static SimpleFilter lt(String field, Object value) {
		return of(field, "<", value);
	}

	public static SimpleFilter lte(String field, Object value) {
		return of(field, "<=", value);
	}

	public static SimpleFilter none(String field, Iterable values) {
		return of(field, "none", values);
	}

	public static SimpleFilter none(String field, Object... values) {
		return of(field, "none", Arrays.asList(values));
	}

	public static CompositeFilter not(Filter filter) {
		return composite("not", filter);
	}

	public static CompositeFilter not(String field, Object value) {
		return not(Filter.of(field, value));
	}

	public static CompositeFilter not(
		String field, String operator, Object value) {

			return not(Filter.of(field, operator, value));
	}

	public static SimpleFilter notEqual(String field, Object value) {
		return of(field, "!=", value);
	}

	public static SimpleFilter of(String field, Object value) {
		return of(field, "=", value);
	}

	public static SimpleFilter of(String field, String operator, Object value) {
		return new SimpleFilter(field, operator, value);
	}

	public static CompositeFilter or(Filter... filters) {
		return composite("or", filters);
	}

	public static SimpleFilter regex(String field, String value) {
		return of(field, "~", value);
	}

}