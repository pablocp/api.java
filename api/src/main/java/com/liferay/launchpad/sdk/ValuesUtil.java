/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */

package com.liferay.launchpad.sdk;
public class ValuesUtil {

	/**
	 * Returns <code>true</code> if value is JSON map. If value is
	 * <code>null</code>, returns <code>false</code>.
	 */
	public static boolean isJsonMap(String value) {
		if (value == null) {
			return false;
		}

		int len = value.length();
		int ndx = 0;

		while (ndx < len) {
			char c = value.charAt(ndx);

			if ((c == ' ') || (c == '\t')) {
				ndx++;
				continue;
			}

			if (c == '{') {
				return true;
			}

			break;
		}

		return false;
	}

	/**
	 * Converts value to {@code String}; {@code null} inputs are not converted.
	 */
	public static String toString(Object value) {
		if (value == null) {
			return null;
		}

		return String.valueOf(value);
	}

}