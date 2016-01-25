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

	protected ResponseError() {}

	/**
	 * Creates error 400 response.
	 */
	public static Error400<Response> badRequest() {
		return badRequest(null);
	}

	/**
	 * Creates error 400 response.
	 */
	public static Error400<Response> badRequest(String message) {
		return new Error400<>(newResponseErrorData(), message);
	}

	/**
	 * Creates error 408 response.
	 */
	public static Error408<Response> requestTimeout() {
		return requestTimeout(null);
	}

	/**
	 * Creates error 408 response.
	 */
	public static Error408<Response> requestTimeout(String message) {
		return new Error408<>(newResponseErrorData(), message);
	}

	/**
	 * Creates error 403 response.
	 */
	public static Error403<Response> forbidden() {
		return forbidden(null);
	}

	/**
	 * Creates error 403 response.
	 */
	public static Error403<Response> forbidden(String message) {
		return new Error403<>(newResponseErrorData(), message);
	}

	/**
	 * Created error 404 response.
	 */
	public static Error401<Response> unauthorized() {
		return unauthorized(null);
	}

	/**
	 * Created error 404 response.
	 */
	public static Error401<Response> unauthorized(String message) {
		return new Error401<>(newResponseErrorData(), message);
	}

	/**
	 * Creates error 500 response.
	 */
	public static Error500<Response> internalError() {
		return internalError(null);
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
		return methodNotAllowed(null);
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
		return notFound(null);
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