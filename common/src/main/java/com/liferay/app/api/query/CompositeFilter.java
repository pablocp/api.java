package com.liferay.app.api.query;

import com.liferay.app.api.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter builder.
 */
abstract class CompositeFilter extends Filter {

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

	private final String operator;
	private final List<Filter> body = new ArrayList();

}