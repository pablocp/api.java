package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.PodMultiMap;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;
public class Util {

	protected Util() {}

	public static final String SEPARATOR = "/";

	/**
	 * Adds parameters into the url querystring.
	 */
	public static String addParametersToQueryString(
		String query, PodMultiMap<String> params) {

		StringBuilder queryString = new StringBuilder();

		if ((query != null) && (query.length() > 0)) {
			queryString.append(query).append('&');
		}

		params.names().forEach(
			name -> params.getAll(name).forEach(value -> {
				queryString.append(name);
				queryString.append("=");
				queryString.append(encodeURIComponent(value));
				queryString.append("&");
			}));

		if (params.size() > 0) {
			queryString.deleteCharAt(queryString.length()-1);
		}

		return queryString.toString();
	}

	/**
	 * Simulates Javascript encodeURIComponent.
	 */
	public static String encodeURIComponent(String s) {
		try {
			return URLEncoder.encode(s, DEFAULT_ENCODING)
				.replaceAll("\\+", "%20")
				.replaceAll("\\%21", "!")
				.replaceAll("\\%27", "'")
				.replaceAll("\\%28", "(")
				.replaceAll("\\%29", ")")
				.replaceAll("\\%7E", "~");
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}

	/**
	 * Joins two paths.
	 */
	public static String joinPaths(String basePath, String path) {
		if (basePath.endsWith(SEPARATOR)) {
			basePath = basePath.substring(0, basePath.length() - 1);
		}

		if (path.startsWith(SEPARATOR)) {
			path = path.substring(1);
		}

		return (basePath + SEPARATOR + path).replaceAll("\\/$", "");
	}

	/**
	 * Parses the url separating the domain and port from the path.
	 */
	public static String joinPathAndQuery(String path, String query) {
		StringBuilder builder = new StringBuilder();

		if (path != null) {
			builder.append(path);
		}

		if ((query != null) && (query.length() > 0)) {
			builder.append('?').append(query);
		}

		return builder.toString();
	}

	protected static String DEFAULT_ENCODING = "UTF-8";

}