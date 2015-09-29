package com.liferay.launchpad.query;

import java.util.Arrays;
import java.util.Map;

/**
 * More regex this filter.
 */
public final class MoreLikeThisFilter extends Filter {

	public MoreLikeThisFilter maxDf(int value) {
		mapValue.put("maxDf", value);
		return this;
	}

	public MoreLikeThisFilter minDf(int value) {
		mapValue.put("minDf", value);
		return this;
	}

	public MoreLikeThisFilter minTf(int value) {
		mapValue.put("minTf", value);
		return this;
	}

	public MoreLikeThisFilter stopWords(String...words) {
		mapValue.put("stopWords", Arrays.asList(words));
		return this;
	}

	protected MoreLikeThisFilter(String field, String query) {
		super(field, "mlt", Util.wrap("query", query));
		this.mapValue = (Map)this.value;
	}

	private final Map mapValue;

}