package com.liferay.launchpad.query;

/**
 * Filter builder.
 */
public final class DisMaxFilter extends CompositeFilter {

	protected DisMaxFilter() { super("dis_max"); }

	public DisMaxFilter disMax(Filter filter) {
		add(filter);
		return this;
	}

	public DisMaxFilter disMax(String field, Object value) {
		return disMax(of(field, value));
	}

	public DisMaxFilter disMax(String field, String operator, Object value) {
		return disMax(of(field, operator, value));
	}

}