package com.liferay.app.api.query;

import com.liferay.app.api.Embodied;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Filter builder.
 */
public abstract class Filter implements Embodied {

	protected Filter() { }

	public static SimpleFilter bbox(String field, Geo.BoundingBox box) {
		return of(field, "gp", box.getPoints());
	}

	public static SimpleFilter bbox(
		String field, Object upperLeft, Object lowerRight) {

		return bbox(field, Geo.bbox(upperLeft, lowerRight));
	}

	public static CommonTermsFilter common(String query) {
		return common(ALL, query);
	}

	public static CommonTermsFilter common(String query, double threshold) {
		return common(ALL, query, threshold);
	}

	public static CommonTermsFilter common(String field, String query) {
		return new CommonTermsFilter(field, query);
	}

	public static CommonTermsFilter common(
		String field, String query, double threshold) {

		return new CommonTermsFilter(field, query, threshold);
	}

	public static SimpleFilter distance(String field, Geo.Circle circle) {
		return distance(
			field, circle.getCenter(), Range.to(circle.getRadius()));
	}

	public static SimpleFilter distance(
		String field, Object location, Range range) {

		Map map = new HashMap();
		map.put("location", location);

		if (range.from != null) {
			map.put("min", range.from);
		}

		if (range.to != null) {
			map.put("max", range.to);
		}

		return of(field, "gd", map);
	}

	public static SimpleFilter distance(
		String field, Object location, String distance) {

		return distance(field, location, Range.to(distance));
	}

	public static SimpleFilter equal(String field, Object value) {
		return of(field, "=", value);
	}

	public static SimpleFilter exists(String field) {
		return of(field, "exists", null);
	}

	public static FuzzyFilter fuzzy(String query) {
		return fuzzy(ALL, query);
	}

	public static FuzzyFilter fuzzy(String query, Number fuzziness) {
		return fuzzy(ALL, query, fuzziness);
	}

	public static FuzzyFilter fuzzy(String field, String query) {
		return new FuzzyFilter(field, "fuzzy", query);
	}

	public static FuzzyFilter fuzzy(
		String field, String query, Number fuzziness) {

		return new FuzzyFilter(field, "fuzzy", query, fuzziness);
	}

	public static FuzzyFilter fuzzyLikeThis(String query) {
		return fuzzyLikeThis(ALL, query);
	}

	public static FuzzyFilter fuzzyLikeThis(String query, Number fuzziness) {
		return fuzzyLikeThis(ALL, query, fuzziness);
	}

	public static FuzzyFilter fuzzyLikeThis(String field, String query) {
		return new FuzzyFilter(field, "flt", query);
	}

	public static FuzzyFilter fuzzyLikeThis(
		String field, String query, Number fuzziness) {

		return new FuzzyFilter(field, "flt", query, fuzziness);
	}

	public static SimpleFilter gt(String field, Object value) {
		return of(field, ">", value);
	}

	public static SimpleFilter gte(String field, Object value) {
		return of(field, ">=", value);
	}

	public static SimpleFilter in(String field, Object...values) {
		return of(field, "in", Arrays.asList(values));
	}

	public static SimpleFilter like(String field, String value) {
		return of(field, "like", value);
	}

	public static SimpleFilter lt(String field, Object value) {
		return of(field, "<", value);
	}

	public static SimpleFilter lte(String field, Object value) {
		return of(field, "<=", value);
	}

	public static MatchFilter match(String query) {
		return match(ALL, query);
	}

	public static MatchFilter match(String field, String query) {
		return new MatchFilter(field, query);
	}

	public static SimpleFilter missing(String field) {
		return of(field, "missing", null);
	}

	public static MoreLikeThisFilter moreLikeThis(String query) {
		return moreLikeThis(ALL, query);
	}

	public static MoreLikeThisFilter moreLikeThis(String field, String query) {
		return new MoreLikeThisFilter(field, query);
	}

	public static SimpleFilter notEqual(String field, Object value) {
		return of(field, "!=", value);
	}

	public static SimpleFilter notIn(String field, Object...values) {
		return of(field, "nin", Arrays.asList(values));
	}

	public static SimpleFilter of(String field, Object value) {
		return of(field, "=", value);
	}

	public static SimpleFilter of(String field, String operator, Object value) {
		return new SimpleFilter(field, operator, value);
	}

	public static NotFilter notOf(Filter filter) {
		return new NotFilter().not(filter);
	}

	public static NotFilter notOf(String field, Object value) {
		return new NotFilter().not(field, "=", value);
	}

	public static NotFilter notOf(String field, String operator, Object value) {
		return new NotFilter().not(field, operator, value);
	}

	public static OrFilter orOf(Filter...filters) {
		OrFilter filter = new OrFilter();

		for (Filter f : filters) {
			filter.or(f);
		}

		return filter;
	}

	public static AndFilter andOf(Filter...filters) {
		AndFilter filter = new AndFilter();

		for (Filter f : filters) {
			filter.and(f);
		}

		return filter;
	}

	public static DisMaxFilter disMaxOf(Filter...filters) {
		DisMaxFilter filter = new DisMaxFilter();

		for (Filter f : filters) {
			filter.disMax(f);
		}

		return filter;
	}

	public static MatchFilter phrase(String query) {
		return phrase(ALL, query);
	}

	public static MatchFilter phrase(String field, String query) {
		return new MatchFilter(field, query, MatchFilter.MatchType.PHRASE);
	}

	public static MatchFilter phrasePrefix(String query) {
		return phrasePrefix(ALL, query);
	}

	public static MatchFilter phrasePrefix(String field, String query) {
		return new MatchFilter(
			field, query, MatchFilter.MatchType.PHRASE_PREFIX);
	}

	public static SimpleFilter polygon(String field, Object...points) {
		return of(field, "gp", Arrays.asList(points));
	}

	public static SimpleFilter prefix(String query) {
		return prefix(ALL, query);
	}

	public static SimpleFilter prefix(String field, String query) {
		return of(field, "pre", query);
	}

	public static SimpleFilter range(String field, Object min, Object max) {
		return of(field, "range", Range.range(min, max));
	}

	public static SimpleFilter range(String field, Range range) {
		return of(field, "range", range);
	}

	public static GeoShapeFilter shape(String field, Object...shapes) {
		return new GeoShapeFilter(field, shapes);
	}

	protected static final String ALL = "*";

}