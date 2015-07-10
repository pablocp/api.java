package com.liferay.launchpad.query;

/**
 * Concrete class for simple filters.
 */
public final class SimpleFilter<T> extends BaseFilter<T> {

	protected SimpleFilter(String field, String operator, T value) {
		super(field, operator, value);
	}

}