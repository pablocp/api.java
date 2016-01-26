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

import java.util.Arrays;

/**
 * Error 405 errors.
 */
public class Error405<T> extends ErrorBase<T, Error405<T>> {

	public static final String[][] VALS = {
		{	//0
			"httpMethodNotAllowed",
			"The HTTP method associated with the request is not supported."
		}
	};

	public Error405(ErrorData<T> errorData, String message) {
		super(errorData, 405, message, "Method Not Allowed");
	}

	public Error405<T> allowHeader(String... allowedMethods) {
		return header("Allow", Arrays.toString(allowedMethods));
	}

	public Error405<T> httpMethodNotAllowed() {
		return httpMethodNotAllowed(null);
	}

	public Error405<T> httpMethodNotAllowed(String message) {
		return error(0, message);
	}

	@Override
	protected String[] vals(int index) {
		return VALS[index];
	}

}