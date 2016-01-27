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

import java.util.HashMap;
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
	 * If body is not set, returns {@code null}.
	 */
	public <T> List<T> bodyList(Class<T> componentType);

	/**
	 * Returns parsed {@link #body() body content}.
	 * If body is not set, returns {@code null}.
	 */
	public <K, V> Map<K, V> bodyMap(Class<K> keyType, Class<V> valueType);

	/**
	 * REturns parsed {@link #body() body content}.
	 * If body is not set, returns {@code null}.
	 */
	public default <V> Map<String, V> bodyMap(Class<V> valueType) {
		return bodyMap(String.class, valueType);
	}

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
		this.status(Status.FOUND);
		this.header("Location", url);
		this.end();
	}

	/**
	 * Returns associated request of this response.
	 */
	public Request request();

	/**
	 * Returns current session.
	 */
	public default Session session() {
		return request().session();
	}

	public default Response status(int statusCode) {
		Status status = Status.statusMap.get(statusCode);

		if (status == null) {
			return status(statusCode, "");
		}

		return status(status);
	}

	/**
	 * Sets the response status code and message.
	 */
	public default Response status(int statusCode, String statusMessage) {
		statusCode(statusCode);
		statusMessage(statusMessage);
		return this;
	}

	public default Response status(Status status) {
		return status(status.code(), status.message());
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

	public static enum Status {

		// success statuses

		OK(200, "OK"), CREATED(201, "Created"), ACCEPTED(202, "Accepted"),
		NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),
		NO_CONTENT(204, "No Content"), RESET_CONTENT(205, "Reset Content"),
		PARTIAL_CONTENT(206, "Partial Content"),

		// redirections

		MULTIPLE_CHOICES(300, "Multiple Choices"),
		MOVED_PERMANENTLY(301, "Moved Permanently"), FOUND(302, "Found"),
		SEE_OTHER(303, "See Other"), NOT_MODIFIED(304, "Not Modified"),
		USE_PROXY(305, "Use Proxy"),
		TEMPORARY_REDIRECT(307, "Temporary Redirect"),

		// client errors

		BAD_REQUEST(400, "Bad Request"), UNAUTHORIZED(401, "Unauthorized"),
		FORBIDDEN(403, "Forbidden"), NOT_FOUND(404, "Not Found"),
		METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
		NOT_ACCEPTABLE(406, "Not Acceptable"),
		PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
		REQUEST_TIMEOUT(408, "Request Timeout"), CONFLICT(409, "Conflict"),
		GONE(410, "Gone"),

		// server errors

		INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
		NOT_IMPLEMENTED(501, "Not Implemented"),
		BAD_GATEWAY(502, "Bad Gateway"),
		SERVICE_UNAVAILABLE(503, "Service Unavailable"),
		GATEWAY_TIMEOUT(504, "Gateway Timeout");

		public int code() {
			return code;
		}

		public String message() {
			return message;
		}

		private Status(int code, String message) {
			this.code = code;
			this.message = message;
		}

		private static final Map<Integer, Status> statusMap = new HashMap<>();

		static {
			for (Status status : Status.values()) {
				statusMap.put(status.code, status);
			}
		}

		private final int code;
		private final String message;

	}

}
