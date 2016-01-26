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
 * Error 403 errors.
 */
public class Error403<T> extends ErrorBase<T, Error403<T>> {

	public static final String[][] VALS = {
		{	//0
			"forbidden",
			"The requested operation is forbidden and cannot be completed."
		},
		{	//1
			"limitExceeded",
			"The request cannot be completed due to access or rate limitations."
		},
		{	// 2
			"quotaExceeded",
			"The requested operation requires more resources than the quota" +
				" allows."
		},
		{	// 3
			"rateLimitExceeded",
			"Too many requests have been sent within a given time span."
		},
		{	// 4
			"responseTooLarge", "The requested resource is too large to return."
		},
		{	// 5
			"unknownAuth",
			"The API server does not recognize the authorization scheme used" +
				" for the request."
		},
		{	// 6
			"corsRequestOrigin", "The CORS request is from an unknown origin."
		},

	};

	public Error403(ErrorData<T> errorData, String message) {
		super(errorData, 403, message, "Forbidden");
	}

	public Error403<T> corsRequestOrigin() {
		return corsRequestOrigin(null);
	}

	public Error403<T> corsRequestOrigin(String message) {
		return error(6, message);
	}

	public Error403<T> forbidden() {
		return forbidden(null);
	}

	public Error403<T> forbidden(String message) {
		return error(0, message);
	}

	public Error403<T> limitExceeded() {
		return limitExceeded(null);
	}

	public Error403<T> limitExceeded(String message) {
		return error(1, message);
	}

	public Error403<T> quotaExceeded() {return quotaExceeded(null); }

	public Error403<T> quotaExceeded(String message) {
		return error(2, message);
	}

	public Error403<T> rateLimitExceeded() {
		return rateLimitExceeded(null);
	}

	public Error403<T> rateLimitExceeded(String message) {
		return error(3, message);
	}

	public Error403<T> responseTooLarge() {
		return responseTooLarge(null);
	}

	public Error403<T> responseTooLarge(String message) {
		return error(4, message);
	}

	public Error403<T> unknownAuth() {
		return unknownAuth(null);
	}

	public Error403<T> unknownAuth(String message) {
		return error(5, message);
	}

	@Override
	protected String[] vals(int index) {
		return VALS[index];
	}

}