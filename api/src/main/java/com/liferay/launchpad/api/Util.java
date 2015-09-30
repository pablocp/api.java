package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.PodMultiMap;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;
public class Util {

	public static final String SEPARATOR = "/";

	/**
	 * Adds parameters into the url querystring.
	 */
	public static String addParametersToUrlQueryString(
		String url, PodMultiMap<String> params) {

		StringBuilder queryString = new StringBuilder();

		params.names().forEach(name -> {
			params.getAll(name).forEach(value -> {
				queryString.append(name);
				queryString.append("=");
				queryString.append(encodeURIComponent(value));
				queryString.append("&");
			});
		});

		if (queryString.length() > 1) {
			url += (url.indexOf('?') > -1) ? "&" : "?";
			url += queryString.substring(0, queryString.length() - 1);
		}

		return url;
	}

	/**
	 * Simulates Javascript encodeURIComponent.
	 */
	public static String encodeURIComponent(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8")
				.replaceAll("\\+", "%20")
				.replaceAll("\\%21", "!")
				.replaceAll("\\%27", "'")
				.replaceAll("\\%28", "(")
				.replaceAll("\\%29", ")")
				.replaceAll("\\%7E", "~");
		} catch (UnsupportedEncodingException e) {
			return null;
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
	public static String[] parseUrl(String url) {
		String base = null;
		String path = null;
		String qs = null;

		int domainAt = url.indexOf("//");

		if (domainAt > -1) {
			url = url.substring(domainAt + 2);
		}

		int pathAt = url.indexOf("/");

		if (pathAt == -1) {
			url += "/";
			pathAt = url.length() - 1;
		}

		base = url.substring(0, pathAt);
		path = url.substring(pathAt);

		int qsAt = path.indexOf("?");

		if (qsAt > -1) {
			qs = path.substring(qsAt, path.length());
			path = path.substring(0, qsAt);
		}
		else {
			qs = "";
		}

		return new String[] {base, path, qs};
	}

}