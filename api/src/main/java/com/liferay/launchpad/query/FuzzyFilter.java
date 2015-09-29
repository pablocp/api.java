package com.liferay.launchpad.query;

import java.util.Map;

/**
 * Fuzzy filter.
 */
public final class FuzzyFilter extends Filter {

	public FuzzyFilter fuzziness(Number fuzziness) {
		this.mapValue.put("fuzziness", fuzziness);
		return this;
	}

	protected FuzzyFilter(String field, String operator, String query) {
		super(field, operator, Util.wrap("query", query));
		this.mapValue = (Map)this.value;
	}

	protected FuzzyFilter(
		String field, String operator, String query, Number fuzziness) {

		this(field, operator, query);
		fuzziness(fuzziness);
	}

	private final Map mapValue;

}