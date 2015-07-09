package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.Response;

public class Util {

	public static final String SEPARATOR = "/";

	/**
	 * Validates client response. Throws exception for invalid.
	 */
	public static void validateResponse(Response response) {
		switch (response.statusCode()) {
			case 200:
			case 204:
			case 304:
				break;
			default:
				throw new LaunchpadClientException(
					"Invalid response : " + response.statusCode());
		}
	}

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