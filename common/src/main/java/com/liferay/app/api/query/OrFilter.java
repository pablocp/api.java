package com.liferay.app.api.query;

/**
 * Or Filter builder.
 */
public final class OrFilter extends CompositeFilter {

	protected OrFilter() { super("or"); }

	public OrFilter or(Filter filter) {
		add(filter);
		return this;
	}

	public OrFilter or(String field, Object value) {
		return or(Filter.of(field, value));
	}

	public OrFilter or(String field, String operator, Object value) {
		return or(Filter.of(field, operator, value));
	}

}