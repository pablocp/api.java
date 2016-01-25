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

/**
 * Error 401 errors.
 */
public class Error401<T> extends ErrorBase<T, Error401<T>> {

	public static final String[][] VALS = {
		{	//0
			"unauthorized", "Access is denied due to invalid credentials."
		}
	};

	public Error401(ErrorData<T> errorData, String message) {
		super(errorData, 401, message, "Unauthorized");
	}

	public Error401<T> unauthorized() {
		return unauthorized(null);
	}

	public Error401<T> unauthorized(String message) {
		return error(0, message);
	}

	@Override
	protected String[] vals(int index) {
		return VALS[index];
	}

}