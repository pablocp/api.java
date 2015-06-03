package com.liferay.app.api.query;

/**
 * Filter builder.
 */
public final class DisMaxFilter extends CompositeFilter {

	protected DisMaxFilter() { super("dis_max");}

	public DisMaxFilter disMax(Filter filter) {
		add(filter);
		return this;
	}

	public DisMaxFilter disMax(String field, Object value) {
		return disMax(Filter.of(field, value));
	}

	public DisMaxFilter disMax(String field, String operator, Object value) {
		return disMax(Filter.of(field, operator, value));
	}

}