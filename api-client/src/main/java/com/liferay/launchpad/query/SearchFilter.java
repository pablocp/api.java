package com.liferay.launchpad.query;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic filter builder, with support to filter upgrade.
 */
public interface SearchFilter extends Embodied {

	public static final String ALL = "*";

	public static Filter bbox(String field, Geo.BoundingBox box) {
		return Filter.of(field, "gp", box.getPoints());
	}

	public static Filter bbox(
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

	public static Filter disMax(Filter...filters) {
		return Filter.composite("disMax", filters);
	}

	public static Filter distance(String field, Geo.Circle circle) {
		return distance(
			field, circle.getCenter(), Range.to(circle.getRadius()));
	}

	public static Filter distance(String field, Object location, Range range) {
		Map map = new HashMap();
		map.put("location", location);

		if (range.from != null) {
			map.put("min", range.from);
		}

		if (range.to != null) {
			map.put("max", range.to);
		}

		return Filter.of(field, "gd", map);
	}

	public static Filter distance(
		String field, Object location, String distance) {

		return distance(field, location, Range.to(distance));
	}

	public static Filter exists(String field) {
		return Filter.of(field, "exists", null);
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

	public static Filter match(String query) {
		return match(ALL, query);
	}

	public static Filter match(String field, String query) {
		return Filter.of(field, "match", query);
	}

	public static Filter missing(String field) {
		return Filter.of(field, "missing", null);
	}

	public static MoreLikeThisFilter moreLikeThis(String query) {
		return moreLikeThis(ALL, query);
	}

	public static MoreLikeThisFilter moreLikeThis(String field, String query) {
		return new MoreLikeThisFilter(field, query);
	}

	public static Filter phrase(String query) {
		return phrase(ALL, query);
	}

	public static Filter phrase(String field, String query) {
		return Filter.of(field, "phrase", query);
	}

	public static Filter phrasePrefix(String query) {
		return phrasePrefix(ALL, query);
	}

	public static Filter phrasePrefix(String field, String query) {
		return Filter.of(field, "phrasePrefix", query);
	}

	public static Filter polygon(String field, Object...points) {
		return Filter.of(field, "gp", Arrays.asList(points));
	}

	public static Filter prefix(String query) {
		return prefix(ALL, query);
	}

	public static Filter prefix(String field, String query) {
		return Filter.of(field, "pre", query);
	}

	public static Filter range(String field, Object min, Object max) {
		return Filter.of(field, "range", Range.range(min, max));
	}

	public static Filter range(String field, Range range) {
		return Filter.of(field, "range", range);
	}

	public static GeoShapeFilter shape(String field, Object...shapes) {
		return new GeoShapeFilter(field, shapes);
	}

}