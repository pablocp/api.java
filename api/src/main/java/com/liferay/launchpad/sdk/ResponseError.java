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
 * Main class for building response errors.
 */
public class ResponseError {

	/**
	 * Creates error 400 response.
	 */
	public static Error400<Response> badRequest() {
		return new Error400<>(newResponseErrorData(), null);
	}

	/**
	 * Creates error 400 response.
	 */
	public static Error400<Response> badRequest(String message) {
		return new Error400<>(newResponseErrorData(), message);
	}

	/**
	 * Creates error 403 response.
	 */
	public static Error403<Response> forbidden() {
		return new Error403<>(newResponseErrorData(), null);
	}

	/**
	 * Creates error 403 response.
	 */
	public static Error403<Response> forbidden(String message) {
		return new Error403<>(newResponseErrorData(), message);
	}

	/**
	 * Creates error 500 response.
	 */
	public static Error500<Response> internalError() {
		return new Error500<>(newResponseErrorData(), null);
	}

	/**
	 * Creates error 500 response.
	 */
	public static Error500<Response> internalError(String message) {
		return new Error500<>(newResponseErrorData(), message);
	}

	/**
	 * Creates error 405 response.
	 */
	public static Error405<Response> methodNotAllowed() {
		return new Error405<>(newResponseErrorData(), null);
	}

	/**
	 * Creates error 405 response.
	 */
	public static Error405<Response> methodNotAllowed(String message) {
		return new Error405<>(newResponseErrorData(), message);
	}

	/**
	 * Creates error 404 response.
	 */
	public static Error404<Response> notFound() {
		return new Error404<>(newResponseErrorData(), null);
	}

	/**
	 * Creates error 404 response.
	 */
	public static Error404<Response> notFound(String message) {
		return new Error404<>(newResponseErrorData(), message);
	}

	private static ErrorData<Response> newResponseErrorData() {
		return new ErrorData<Response>() {

			@Override
			protected void end(Response response) {
				response.status(statusCode(), statusMessage());
				headers(response::header);

				String errorBody = errorBody();

				response
					.contentType(ContentType.JSON)
					.body(errorBody)
					.end();
			}

		};
	}

}