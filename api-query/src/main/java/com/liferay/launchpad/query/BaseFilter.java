package com.liferay.launchpad.query;

import java.util.HashMap;
import java.util.Map;

/**
 * Basic filter builder, with support to upgrade to composite filter.
 */
abstract class BaseFilter<T> implements Filter {

	public CompositeFilter and(Filter filter) {
		return Filter.and(this, filter);
	}

	public CompositeFilter and(String field, Object value) {
		return and(Filter.of(field, value));
	}

	public CompositeFilter and(String field, String operator, Object value) {
		return and(Filter.of(field, operator, value));
	}

	public CompositeFilter disMax(Filter filter) {
		return SearchFilter.disMax(this, filter);
	}

	public CompositeFilter disMax(String field, Object value) {
		return disMax(Filter.of(field, value));
	}

	public CompositeFilter disMax(String field, String operator, Object value) {
		return disMax(Filter.of(field, operator, value));
	}

	public CompositeFilter or(Filter filter) {
		return Filter.or(this, filter);
	}

	public CompositeFilter or(String field, Object value) {
		return or(Filter.of(field, value));
	}

	public CompositeFilter or(String field, String operator, Object value) {
		return or(Filter.of(field, operator, value));
	}

	@Override
	public Object body() {
		Map map = new HashMap();
		map.put("operator", operator);

		if (value != null) {
			map.put("value", value);
		}

		return Util.wrap(field, map);
	}

	@Override
	public String toString() {
		return Util.toString(this);
	}

	protected BaseFilter(String field, String operator, T value) {
		this.field = field;
		this.operator = operator;
		this.value = value;
	}

	protected final String field;
	protected final String operator;
	protected T value;

}