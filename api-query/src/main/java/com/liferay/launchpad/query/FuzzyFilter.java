package com.liferay.launchpad.query;

import java.util.Map;

/**
 * Fuzzy filter.
 */
public final class FuzzyFilter extends BaseFilter<Map> implements SearchFilter {

	public FuzzyFilter fuzziness(Number fuzziness) {
		this.value.put("fuzziness", fuzziness);
		return this;
	}

	protected FuzzyFilter(String field, String operator, Object value) {
		super(field, operator, Util.wrap("query", value));
	}

	protected FuzzyFilter(
		String field, String operator, Object value, Number fuzziness) {

		this(field, operator, value);
		fuzziness(fuzziness);
	}

}