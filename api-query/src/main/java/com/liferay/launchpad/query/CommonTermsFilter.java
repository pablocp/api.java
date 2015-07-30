package com.liferay.launchpad.query;

import java.util.Map;

/**
 * Common terms filter.
 */
public final class CommonTermsFilter extends Filter implements SearchFilter {

	public CommonTermsFilter threshold(double threshold) {
		this.mapValue.put("threshold", threshold);
		return this;
	}

	protected CommonTermsFilter(String field, String query) {
		super(field, "common", Util.wrap("query", query));
		this.mapValue = (Map)this.value;
	}

	protected CommonTermsFilter(String field, String query, double threshold) {
		this(field, query);
		threshold(threshold);
	}

	private final Map mapValue;

}