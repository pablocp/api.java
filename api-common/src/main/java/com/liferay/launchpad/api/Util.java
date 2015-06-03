package com.liferay.launchpad.api;

import com.liferay.launchpad.sdk.Response;
import java.util.HashMap;
import java.util.Map;

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

		public static Map wrap(String name, Object value) {
			Map map = new HashMap();
			map.put(name, value);
			return map;
		}

}
