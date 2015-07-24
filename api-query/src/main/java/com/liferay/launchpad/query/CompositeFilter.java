package com.liferay.launchpad.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite filter builder.
 */
public final class CompositeFilter implements Filter {

	public CompositeFilter and(Filter filter) {
		return add("and", filter);
	}

	public CompositeFilter and(String field, Object value) {
		return and(Filter.of(field, value));
	}

	public CompositeFilter and(String field, String operator, Object value) {
		return and(Filter.of(field, operator, value));
	}

	@Override
	public Object body() {
		if (body.size() == 1) {
			return Util.wrap(operator, body.get(0));
		}
		else {
			return Util.wrap(operator, body);
		}
	}

	public CompositeFilter disMax(Filter filter) {
		return add("disMax", filter);
	}

	public CompositeFilter disMax(String field, Object value) {
		return disMax(Filter.of(field, value));
	}

	public CompositeFilter disMax(String field, String operator, Object value) {
		return disMax(Filter.of(field, operator, value));
	}

	public CompositeFilter or(Filter filter) {
		return add("or", filter);
	}

	public CompositeFilter or(String field, Object value) {
		return or(Filter.of(field, value));
	}

	public CompositeFilter or(String field, String operator, Object value) {
		return or(Filter.of(field, operator, value));
	}

	@Override
	public String toString() {
		return Util.toString(this);
	}

	protected CompositeFilter(String operator) {
		this.operator = operator;
	}

	protected void add(Filter filter) {
		this.body.add(filter);
	}

	protected CompositeFilter add(String newOperator, Filter filter) {
		if (this.operator.equals(newOperator)) {
			this.body.add(filter);
			return this;
		}

		CompositeFilter newFilter = new CompositeFilter(newOperator);
		newFilter.add(this);
		newFilter.add(filter);

		return newFilter;
	}

	private final List<Filter> body = new ArrayList();
	private final String operator;

}