package com.liferay.launchpad.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter builder.
 */
abstract class CompositeFilter implements Filter {

	protected void add(Filter filter) {
		this.body.add(filter);
	}

	protected CompositeFilter(String operator) {
		this.operator = operator;
	}

	@Override
	public Object body() {
		return Util.wrap(operator, body);
	}

	@Override
	public String toString() {
		return Util.toString(this);
	}

	private final String operator;
	private final List<Filter> body = new ArrayList();

}