package com.liferay.app.api;

public class Util {

	public static final String SEPARATOR = "/";

	/**
	 * Joins two paths.
	 */
	public static String joinPaths(String basePath, String path) {
		if (basePath.endsWith(SEPARATOR)) {
			if (path.startsWith(SEPARATOR)) {
				return basePath.substring(0, basePath.length() - 1) + path;
			}
			else {
				return basePath + path;
			}
		}
		else {
			if (path.startsWith(SEPARATOR)) {
				return basePath + path;
			}
			else {
				return basePath + SEPARATOR + path;
			}
		}
	}

}