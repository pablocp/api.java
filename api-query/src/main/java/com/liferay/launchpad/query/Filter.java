package com.liferay.launchpad.query;

import java.util.Arrays;

/**
 * Filter builder.
 */
public interface Filter extends Embodied {

	public static AndFilter andOf(Filter...filters) {
		AndFilter filter = new AndFilter();

		for (Filter f : filters) {
			filter.and(f);
		}

		return filter;
	}

	public static SimpleFilter equal(String field, Object value) {
		return of(field, "=", value);
	}

	public static SimpleFilter exists(String field) {
		return of(field, "exists", null);
	}

	public static SimpleFilter gt(String field, Object value) {
		return of(field, ">", value);
	}

	public static SimpleFilter gte(String field, Object value) {
		return of(field, ">=", value);
	}

	public static SimpleFilter in(String field, Object...values) {
		return of(field, "in", Arrays.asList(values));
	}

	public static SimpleFilter like(String field, String value) {
		return of(field, "like", value);
	}

	public static SimpleFilter lt(String field, Object value) {
		return of(field, "<", value);
	}

	public static SimpleFilter lte(String field, Object value) {
		return of(field, "<=", value);
	}

	public static SimpleFilter notEqual(String field, Object value) {
		return of(field, "!=", value);
	}

	public static SimpleFilter notIn(String field, Object...values) {
		return of(field, "nin", Arrays.asList(values));
	}

	public static NotFilter notOf(Filter filter) {
		return new NotFilter().not(filter);
	}

	public static NotFilter notOf(String field, Object value) {
		return new NotFilter().not(field, "=", value);
	}

	public static NotFilter notOf(String field, String operator, Object value) {
		return new NotFilter().not(field, operator, value);
	}

	public static SimpleFilter of(String field, Object value) {
		return of(field, "=", value);
	}

	public static SimpleFilter of(String field, String operator, Object value) {
		return new SimpleFilter(field, operator, value);
	}

	public static OrFilter orOf(Filter...filters) {
		OrFilter filter = new OrFilter();

		for (Filter f : filters) {
			filter.or(f);
		}

		return filter;
	}

}