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
 * Error 400 errors.
 */
public class Error400<T> extends ErrorBase<T, Error400<T>> {

	public static final String[][] VALS = {
		{	//0
			"badRequest", "The API request is invalid or improperly formed."
		},
		{	// 1
			"badContent",
			"The content type of the request data or the content type of a" +
				" part of a multipart request is not supported."
		},
		{	// 2
			"invalidQuery",
			"The request is invalid. Check the API documentation to determine" +
				" what parameters are supported for the request and to see if" +
				" the request contains an invalid combination of parameters" +
				" or an invalid parameter value."
		},
		{	// 3
			"invalidDocumentValue",
			"The request failed because it contained an invalid parameter or" +
				" parameter value for the document. Review the API" +
				" documentation to determine which parameters are valid for" +
				" your request."
		},
		{	// 4
			"keyExpired",
			"The API key provided in the request expired, which means the API" +
				"server is unable to make the request."
		},
		{	// 5
			"keyInvalid",
			"The API key provided in the request is invalid, which means the" +
				" API server is unable to make the request."
		},
		{	// 6
			"parseError", "The API server cannot parse the request body."
		},
		{	// 7
			"required",
			"The API request is missing required information. The required" +
				" information could be a parameter or resource property."
		},
		{	// 8
			"exists", "Resource already exists."
		},
		{	// 6
			"validationError", "Validation of input failed."
		}
	};

	public Error400(ErrorData<T> errorData, String message) {
		super(errorData, 400, message, "Bad Request");
	}

	public Error400<T> badContent() {
		return badContent(null);
	}

	public Error400<T> badContent(String message) {
		return error(1, message);
	}

	public Error400<T> badRequest() {
		return badRequest(null);
	}

	public Error400<T> badRequest(String message) {
		return error(0, message);
	}

	public Error400<T> exists() {
		return exists(null);
	}

	public Error400<T> exists(String message) {
		return error(8, message);
	}

	public Error400<T> invalidDocumentValue() {
		return invalidDocumentValue(null);
	}

	public Error400<T> invalidDocumentValue(String message) {
		return error(3, message);
	}

	public Error400<T> invalidQuery() {
		return invalidQuery(null);
	}

	public Error400<T> invalidQuery(String message) {
		return error(2, message);
	}

	public Error400<T> keyExpired() {
		return keyExpired(null);
	}

	public Error400<T> keyExpired(String message) {
		return error(4, message);
	}

	public Error400<T> keyInvalid() {
		return keyInvalid(null);
	}

	public Error400<T> keyInvalid(String message) {
		return error(5, message);
	}

	public Error400<T> parseError() {
		return parseError(null);
	}

	public Error400<T> parseError(String message) {
		return error(6, message);
	}

	public Error400<T> required() {
		return required(null);
	}

	public Error400<T> required(String message) {
		return error(7, message);
	}

	public Error400<T> validationError() {
		return validationError(null);
	}

	public Error400<T> validationError(String message) {
		return error(9, message);
	}

	@Override
	protected String[] vals(int index) {
		return VALS[index];
	}

}