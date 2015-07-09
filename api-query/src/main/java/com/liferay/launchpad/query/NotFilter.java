package com.liferay.launchpad.query;

/**
 * Not filter builder.
 */
public class NotFilter extends CompositeFilter {

	protected NotFilter() { super("not"); }

	public NotFilter not(Filter filter) {
		add(filter);
		return this;
	}

	public NotFilter not(String field, Object value) {
		return not(Filter.of(field, value));
	}

	public NotFilter not(String field, String operator, Object value) {
		return not(Filter.of(field, operator, value));
	}

}