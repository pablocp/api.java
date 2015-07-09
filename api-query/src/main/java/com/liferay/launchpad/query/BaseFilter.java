package com.liferay.launchpad.query;

import java.util.HashMap;
import java.util.Map;

/**
 * Basic filter builder, with support to filter upgrade.
 */
abstract class BaseFilter<T> extends Filter {

	public AndFilter and(Filter filter) {
		return new AndFilter().and(this).and(filter);
	}

	public AndFilter and(String field, Object value) {
		return and(of(field, value));
	}

	public AndFilter and(String field, String operator, Object value) {
		return and(of(field, operator, value));
	}

	public DisMaxFilter disMax(Filter filter) {
		return new DisMaxFilter().disMax(this).disMax(filter);
	}

	public DisMaxFilter disMax(String field, Object value) {
		return disMax(of(field, value));
	}

	public DisMaxFilter disMax(String field, String operator, Object value) {
		return disMax(of(field, operator, value));
	}

	public OrFilter or(Filter filter) {
		return new OrFilter().or(this).or(filter);
	}

	public OrFilter or(String field, Object value) {
		return or(of(field, value));
	}

	public OrFilter or(String field, String operator, Object value) {
		return or(of(field, operator, value));
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

	protected BaseFilter(String field, String operator, T value) {
		this.field = field;
		this.operator = operator;
		this.value = value;
	}

	private final String field;
	private final String operator;
	protected T value;

}