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
	public String header(CharSequence name);

	/**
	 * Sets the first header name with the specified value.
	 */
	public Response header(CharSequence name, String value);

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

	/**
	 * Sets the response status code and default status message.
	 */
	public default Response status(int statusCode) {
		return status(statusCode, Status.defaultMessage(statusCode));
	}

	/**
	 * Sets the response status code and message.
	 */
	public Response status(int statusCode, String statusMessage);

	/**
	 * Returns status code.
	 */
	public int statusCode();

	/**
	 * Returns status message.
	 */
	public String statusMessage();

	/**
	 * Checks if response succeeded. Any status code 2xx or 3xx is considered
	 * valid.
	 */
	public default boolean succeeded() {
		return Status.succeeded(statusCode());
	}

	/**
	 * Simple static collection of all statuses as constants.
	 * <p>
	 * Developers note: the first urge we have is to wrap all the codes
	 * and the messages as enums. While noble, this does not work well, as
	 * message can be changed; and user may use more status codes that we
	 * provide. Enums would not work; so naturally we would head forward to use
	 * the class to wrap the status code and the message. This would work.
	 * However, that introduces level of complexity that is not necessary.
	 * For example, if response returns instance of such Status class, we would
	 * need to use equals() to check against the real values. If we dont expose
	 * the status class, then we dont have to use, hence we are back to the
	 * beginning :)
	 * <p>
	 * We could use also a Map for getting default value, but a switch should
	 * produce more optimized bytecode. Since we do not change this part of code
	 * much, it make sense to have it like this.
	 */
	public static final class Status {

		public static final int ACCEPTED = 202;

		public static final int BAD_GATEWAY = 502;

		// client errors

		public static final int BAD_REQUEST = 400;
		public static final int CONFLICT = 409;

		public static final int CREATED = 201;

		public static final int FORBIDDEN = 403;

		public static final int FOUND = 302;

		public static final int GATEWAY_TIMEOUT = 504;

		public static final int GONE = 410;

		// server errors

		public static final int INTERNAL_SERVER_ERROR = 500;

		public static final int METHOD_NOT_ALLOWED = 405;

		public static final int MOVED_PERMANENTLY = 301;

		// redirections

		public static final int MULTIPLE_CHOICES = 300;

		public static final int NO_CONTENT = 204;

		public static final int NON_AUTHORITATIVE_INFORMATION = 203;

		public static final int NOT_ACCEPTABLE = 406;

		public static final int NOT_FOUND = 404;

		public static final int NOT_IMPLEMENTED = 501;

		public static final int NOT_MODIFIED = 304;

		// success statuses

		public static final int OK = 200;

		public static final int PARTIAL_CONTENT = 206;

		public static final int PROXY_AUTHENTICATION_REQUIRED = 407;

		public static final int REQUEST_TIMEOUT = 408;

		public static final int RESET_CONTENT = 205;

		public static final int SEE_OTHER = 303;

		public static final int SERVICE_UNAVAILABLE = 503;

		public static final int TEMPORARY_REDIRECT = 307;

		public static final int UNAUTHORIZED = 401;

		public static final int USE_PROXY = 305;

		/**
		 * Returns default message for given status.
		 */
		public static String defaultMessage(int statusCode) {
			switch (statusCode) {
				case OK: return "OK";
				case CREATED: return "Created";
				case ACCEPTED: return "Accepted";
				case NON_AUTHORITATIVE_INFORMATION:
					return "Non-authoritative information";
				case NO_CONTENT: return "No content";
				case RESET_CONTENT: return "Reset content";
				case PARTIAL_CONTENT: return "Partial content";
				case MULTIPLE_CHOICES: return "Multiple choices";
				case MOVED_PERMANENTLY: return "Moved permanently";
				case FOUND: return "Found";
				case SEE_OTHER: return "See other";
				case NOT_MODIFIED: return "Not modified";
				case USE_PROXY: return "Use proxy";
				case TEMPORARY_REDIRECT: return "Temporary redirect";
				case BAD_REQUEST: return "Bad request";
				case UNAUTHORIZED: return "Unauthorized";
				case FORBIDDEN: return "Forbidden";
				case NOT_FOUND: return "Not found";
				case METHOD_NOT_ALLOWED: return "Method not allowed";
				case NOT_ACCEPTABLE: return "Not acceptable";
				case PROXY_AUTHENTICATION_REQUIRED:
					return "Proxy authentication required";
				case REQUEST_TIMEOUT: return "Request timeout";
				case CONFLICT: return "Conflict";
				case GONE: return "Gone";
				case INTERNAL_SERVER_ERROR: return "Internal server error";
				case NOT_IMPLEMENTED: return "Not implemented";
				case BAD_GATEWAY: return "Bad gateway";
				case SERVICE_UNAVAILABLE: return "Service unavailable";
				case GATEWAY_TIMEOUT: return "Gateway timeout";
				default: return "(" + statusCode + ")";
			}
		}

		/**
		 * Checks if response succeeded. Any status code 2xx or 3xx is considered
		 * valid.
		 */
		public static boolean succeeded(int statusCode) {
			return statusCode >= 200 && statusCode <= 399;
		}

		protected Status() {
		}

	}

}
