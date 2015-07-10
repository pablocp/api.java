package com.liferay.launchpad.query;

/**
 * And Filter builder.
 */
public final class AndFilter extends CompositeFilter {

	protected AndFilter() { super("and"); }

	public AndFilter and(Filter filter) {
		add(filter);
		return this;
	}

	public AndFilter and(String field, Object value) {
		return and(Filter.of(field, value));
	}

	public AndFilter and(String field, String operator, Object value) {
		return and(Filter.of(field, operator, value));
	}
}