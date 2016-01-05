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

import java.util.List;
import java.util.Map;

/**
 * HTTP response.
 */
public interface Response {

	/**
	 * Returns body content.
	 */
	public String body();

	/**
	 * Sets the raw body content.
	 */
	public Response body(byte[] body);

	/**
	 * Sets the JSON body content.
	 */
	public Response body(Object body);

	/**
	 * Sets the body content. Body content can be set more then once.
	 */
	public Response body(String body);

	/**
	 * Sets the body content and common {@link ContentType content-type}.
	 * Just a shortcut call.
	 */
	public Response body(String body, ContentType contentType);

	/**
	 * Returns the raw body content.
	 */
	public byte[] bodyBytes();

	/**
	 * Returns parsed {@link #body() body content}.
	 * If body is not set, returns <code>null</code>.
	 */
	public <T> List<T> bodyList(Class<T> componentType);

	/**
	 * Returns parsed {@link #body() body content}.
	 * If body is not set, returns <code>null</code>.
	 */
	public <T> T bodyValue();

	/**
	 * Returns parsed {@link #body() body content}.
	 * If body is not set, returns <code>null</code>.
	 */
	public <T> T bodyValue(Class<T> type);

	/**
	 * Gets the content type header.
	 */
	public String contentType();

	/**
	 * Sets the content type header.
	 */
	public Response contentType(ContentType contentType);

	/**
	 * Returns the context that this response belongs to.
	 */
	public Context context();

	/**
	 * @return A map of all the cookies.
	 */
	Map<String, Cookie> cookies();

	/**
	 * Adds a cookie to the response.
	 */
	public Response cookie(Cookie cookie);

	/**
	 * Gets cookie value by name.
	 */
	public Cookie cookie(String name);

	/**
	 * Closes the response. Shortcut for {@code body(null)}.
	 */
	public void end();

	/**
	 * Sets the body and clsoes the response.
	 */
	public default void end(Object body) {
		body(body);
		end();
	}

	/**
	 * Sets the body content and closes the response.
	 */
	public default void end(String body) {
		body(body);
		end();
	}

	/**
	 * Sets the body content and common {@link ContentType content-type}
	 * and closes the response.
	 */
	public default void end(String body, ContentType contentType) {
		body(body, contentType);
		end();
	}

	/**
	 * Gets header value by name.
	 */
	public String header(String name);

	/**
	 * Sets the first header name with the specified value.
	 */
	public Response header(String name, String value);

	/**
	 * Returns headers.
	 */
	public PodMultiMap<String> headers();

	/**
	 * Returns <code>true</code> if response is already committed.
	 */
	public boolean isCommitted();

	/**
	 * Checks if the response contains a content type header with the specified value.
	 */
	public boolean isContentType(ContentType contentType);

	// success statuses

	int OK = 200;
	int CREATED = 201;
	int ACCEPTED = 202;
	int NON_AUTHORITATIVE_INFORMATION = 203;
	int NO_CONTENT = 204;
	int RESET_CONTENT = 205;
	int PARTIAL_CONTENT = 206;

	// redirections

	int MULTIPLE_CHOICES = 300;
	int MOVED_PERMANENTLY = 301;
	int FOUND = 302;
	int SEE_OTHER = 303;
	int NOT_MODIFIED = 304;
	int USE_PROXY = 305;
	int TEMPORARY_REDIRECT = 307;

	// client errors

	int BAD_REQUEST = 400;
	int UNAUTHORIZED = 401;
	int FORBIDDEN = 403;
	int NOT_FOUND = 404;
	int METHOD_NOT_ALLOWED = 405;
	int NOT_ACCEPTABLE = 406;
	int PROXY_AUTHENTICATION_REQUIRED = 407;
	int REQUEST_TIMEOUT = 408;
	int CONFLICT = 409;
	int GONE = 410;

	// server errors

	int INTERNAL_SERVER_ERROR = 500;
	int NOT_IMPLEMENTED = 501;
	int BAD_GATEWAY = 502;
	int SERVICE_UNAVAILABLE = 503;
	int GATEWAY_TIMEOUT = 504;

	/**
	 * Returns <code>true</code> if status code equals to provided one.
	 */
	public default boolean isStatusCode(int status) {
		return statusCode() == status;
	}

	/**
	 * Pipes response to another response and ends it.
	 */
	public Response pipe(Response response);

	/**
	 * Redirects response to an url.
	 */
	public default void redirect(String url) {
		this.status(FOUND);
		this.header("Location", url);
		this.end();
	}

	/**
	 * Returns associated request of this response.
	 */
	public Request request();

	public default Response status(int statusCode) {
		String statusMessage = "";

		switch (statusCode) {
			case OK: statusMessage = "OK"; break;
			case CREATED: statusMessage = "Created"; break;
			case ACCEPTED: statusMessage = "Accepted"; break;
			case NON_AUTHORITATIVE_INFORMATION: statusMessage =
				"Non-authoritative information"; break;
			case NO_CONTENT: statusMessage = "No content"; break;
			case RESET_CONTENT: statusMessage = "Reset content"; break;
			case PARTIAL_CONTENT: statusMessage = "Partial content"; break;
			case MULTIPLE_CHOICES: statusMessage = "Multiple choices"; break;
			case MOVED_PERMANENTLY: statusMessage = "Moved permanently"; break;
			case FOUND: statusMessage = "Found"; break;
			case SEE_OTHER: statusMessage = "See other"; break;
			case NOT_MODIFIED: statusMessage = "Not modified"; break;
			case USE_PROXY: statusMessage = "Use proxy"; break;
			case TEMPORARY_REDIRECT: statusMessage =
				"Temporary redirect"; break;
			case BAD_REQUEST: statusMessage = "Bad request"; break;
			case UNAUTHORIZED: statusMessage = "Unauthorized"; break;
			case FORBIDDEN: statusMessage = "Forbidden"; break;
			case NOT_FOUND: statusMessage = "Not found"; break;
			case METHOD_NOT_ALLOWED: statusMessage =
				"Method not allowed"; break;
			case NOT_ACCEPTABLE: statusMessage = "Not acceptable"; break;
			case PROXY_AUTHENTICATION_REQUIRED: statusMessage =
				"Proxy authentication required"; break;
			case REQUEST_TIMEOUT: statusMessage = "Request timeout"; break;
			case CONFLICT: statusMessage = "Conflict"; break;
			case GONE: statusMessage = "Gone"; break;
			case INTERNAL_SERVER_ERROR: statusMessage =
				"Internal server error"; break;
			case NOT_IMPLEMENTED: statusMessage = "Not implemented"; break;
			case BAD_GATEWAY: statusMessage = "Bad gateway"; break;
			case SERVICE_UNAVAILABLE: statusMessage =
				"Service unavailable"; break;
			case GATEWAY_TIMEOUT: statusMessage = "Gateway timeout"; break;
		}

		return status(statusCode, statusMessage);
	}

	/**
	 * Sets the response status code and message.
	 */
	public default Response status(int statusCode, String statusMessage) {
		statusCode(statusCode);
		statusMessage(statusMessage);
		return this;
	}

	/**
	 * Returns status code.
	 */
	public int statusCode();

	/**
	 * Sets the status code.
	 */
	public Response statusCode(int statusCode);

	/**
	 * Returns status message.
	 */
	public String statusMessage();

	/**
	 * Sets the status message.
	 */
	public Response statusMessage(String statusMessage);

	/**
	 * Checks if response succeeded. Any status code 2xx or 3xx is considered
	 * valid.
	 */
	public default boolean succeeded() {
		return statusCode() >= 200 && statusCode() <= 399;
	}

}