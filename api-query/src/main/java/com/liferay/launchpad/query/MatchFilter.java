package com.liferay.launchpad.query;

import java.util.Map;

/**
 * Match filter.
 */
public final class MatchFilter extends BaseFilter<Map> {

	public MatchFilter type(MatchType type) {
		this.value.put("type", type.name().toLowerCase());
		return this;
	}

	public enum MatchType {
		DEFAULT, PHRASE, PHRASE_PREFIX
	}

	protected MatchFilter(String field, Object value) {
		super(field, "match", Util.wrap("query", value));
	}

	protected MatchFilter(String field, Object value, MatchType type) {
		this(field, value);
		type(type);
	}

}