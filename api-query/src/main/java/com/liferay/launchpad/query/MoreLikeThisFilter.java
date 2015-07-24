package com.liferay.launchpad.query;

import java.util.Arrays;
import java.util.Map;

/**
 * More regex this filter.
 */
public final class MoreLikeThisFilter extends BaseFilter<Map>
	implements SearchFilter {

	public MoreLikeThisFilter maxDf(int value) {
		this.value.put("maxDf", value);
		return this;
	}

	public MoreLikeThisFilter minDf(int value) {
		this.value.put("minDf", value);
		return this;
	}

	public MoreLikeThisFilter minTf(int value) {
		this.value.put("minTf", value);
		return this;
	}

	public MoreLikeThisFilter stopWords(String...words) {
		this.value.put("stopWords", Arrays.asList(words));
		return this;
	}

	protected MoreLikeThisFilter(String field, Object value) {
		super(field, "mlt", Util.wrap("query", value));
	}

}